package C1S.childgoodsstore.together.service;

import C1S.childgoodsstore.chatting.dto.ChattingRoomRequest;
import C1S.childgoodsstore.chatting.repository.ChattingRoomRepository;
import C1S.childgoodsstore.chatting.service.ChattingService;
import C1S.childgoodsstore.entity.*;
import C1S.childgoodsstore.enums.PRODUCT_CATEGORY;
import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import C1S.childgoodsstore.tag.repository.TagRepository;
import C1S.childgoodsstore.together.dto.input.CreateTogetherDto;
import C1S.childgoodsstore.together.dto.input.TogetherSearchCriteriaDto;
import C1S.childgoodsstore.together.dto.output.TogetherDetailsDto;
import C1S.childgoodsstore.together.dto.output.TogetherListDto;
import C1S.childgoodsstore.together.dto.output.UserDetailDto;
import C1S.childgoodsstore.together.repository.TogetherImageRepository;
import C1S.childgoodsstore.together.repository.TogetherRepository;
import C1S.childgoodsstore.together.repository.TogetherTagRepository;
import C1S.childgoodsstore.user.repository.UserRepository;
import jakarta.persistence.criteria.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import C1S.childgoodsstore.entity.Together;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.together.dto.LikeTogetherRequest;
import C1S.childgoodsstore.util.RedisUtil;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class TogetherService {

    private final TogetherRepository togetherRepository;
    private final TogetherImageRepository togetherImageRepository;
    private final TogetherTagRepository togetherTagRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final RedisUtil redisUtil;
    private final ChattingService chattingService;
    private final ChattingRoomRepository chattingRoomRepository;

    //공동구매 상품 목록 조회
    public List<TogetherListDto> getTogetherList(User user, TogetherSearchCriteriaDto criteria) {

        Specification<Together> specification = Specification.where(null);

        if (criteria.getMainCategory() != null) {
            specification = specification.and((root, query, builder) ->
                    builder.equal(root.get("mainCategory"), criteria.getMainCategory()));
        }

        if (criteria.getSubCategory() != null) {
            specification = specification.and((root, query, builder) ->
                    builder.equal(root.get("subCategory"), criteria.getSubCategory()));
        }

        if (criteria.getAge() != null) {
            specification = specification.and((root, query, builder) ->
                    builder.equal(root.get("age"), criteria.getAge()));
        }

        if ("MY_REGION".equals(criteria.getRegion())) {
            specification = specification.and((root, query, builder) ->
                    builder.equal(root.get("user").get("region"), user.getRegion()));
        }

        Pageable pageable = PageRequest.of(criteria.getPage(), 10);
        Page<Together> togethers = togetherRepository.findAll(specification, pageable);

        List<TogetherListDto> togetherListDtos = new ArrayList<>();
        for (Together together : togethers) {

            List<TogetherImage> images = togetherImageRepository.findByTogether(together);
            String imageUrl = null;
            if(images != null && !images.isEmpty())
                imageUrl = images.get(0).getImageUrl();

            boolean isHeart = redisUtil.getTogetherLikes(String.valueOf(user.getUserId())).contains(together.getTogetherId().toString());
            TogetherListDto dto = new TogetherListDto(together, imageUrl, isHeart);
            togetherListDtos.add(dto);
        }
        return togetherListDtos;
    }

    //공동구매글 등록
    public Long postTogether(User user, CreateTogetherDto togetherDto) {

        getUserById(user.getUserId());

        Together together = new Together(user, togetherDto);
        Together savedTogether = togetherRepository.saveAndFlush(together);

        saveTogetherImages(togetherDto.getTogetherImage(), savedTogether);
        saveTogetherTags(togetherDto.getTag(), savedTogether);

        ChattingRoomRequest request = new ChattingRoomRequest(savedTogether.getTogetherId(), PRODUCT_CATEGORY.TOGETHER);
        chattingService.createChatRoom(user, request);

        return savedTogether.getTogetherId();
    }

    //공동구매글 수정
    public Long updateTogether(User user, Long togetherId, CreateTogetherDto togetherDto) {

        getUserById(user.getUserId());
        Together together = getTogetherById(togetherId);

        if(!together.getUser().getUserId().equals(user.getUserId())) {
            throw new CustomException(ErrorCode.TOGETHER_NOT_OWNED);
        }

        together.setTogetherName(togetherDto.getTogetherName());
        together.setTotalPrice(togetherDto.getTotalPrice());
        together.setDetails(togetherDto.getDetails());
        together.setMainCategory(togetherDto.getMainCategory());
        together.setAge(togetherDto.getAge());
        together.setSubCategory(togetherDto.getSubCategory());
        together.setLink(togetherDto.getLink());
        together.setDeadline(togetherDto.getDeadline());
        together.setAddress(togetherDto.getAddress());
        together.setDetailAddress(togetherDto.getDetailAddress());
        together.setTotalNum(togetherDto.getTotalNum());
        together.setUpdatedAt();

        togetherImageRepository.deleteTogetherImagesByTogetherTogetherId(together.getTogetherId());
        saveTogetherImages(togetherDto.getTogetherImage(), together);

        togetherTagRepository.deleteTogetherTagsByTogetherTogetherId(together.getTogetherId());
        saveTogetherTags(togetherDto.getTag(), together);

        togetherRepository.saveAndFlush(together);
        return together.getTogetherId();
    }

    //공동구매 상품 상세조회
    public TogetherDetailsDto getTogetherDetails(User user, Long togetherId) {

        Together together = getTogetherById(togetherId);

        List<String> togetherImages = togetherImageRepository.findAllByTogetherId(togetherId);
        List<String> togetherTags = togetherTagRepository.findAllByTogetherId(togetherId);
        boolean isHeart = redisUtil.getTogetherLikes(String.valueOf(user.getUserId())).contains(togetherId.toString());
        Long chattingId = chattingRoomRepository.findByTogetherTogetherId(togetherId);

        TogetherDetailsDto togetherDetailsDto = new TogetherDetailsDto(together, new UserDetailDto(user),togetherImages, togetherTags, isHeart, chattingId);

        return togetherDetailsDto;
    }

    //유저 찾기
    private User getUserById(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    //상품 찾기
    private Together getTogetherById(Long togetherId) {
        return togetherRepository.findById(togetherId)
                .orElseThrow(() -> new CustomException(ErrorCode.TOGETHER_NOT_FOUND));
    }

    //이미지 저장 및 수정
    private void saveTogetherImages(List<String> imageUrls, Together together) {

        if (imageUrls != null && !imageUrls.isEmpty()) {
            for (int i = 0; i < imageUrls.size(); i++) {

                TogetherImage togetherImage = new TogetherImage(together, imageUrls.get(i), i+1);
                togetherImageRepository.saveAndFlush(togetherImage);
            }
        }
    }

    //태그 저장 및 수정
    private void saveTogetherTags(List<String> tagNames, Together together) {
        if (tagNames != null && !tagNames.isEmpty()) {
            for (String tagName : tagNames) {

                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(tagName)));
                TogetherTag togetherTag = new TogetherTag(together, tag);
                togetherTagRepository.saveAndFlush(togetherTag);
            }
        }
    }

    public Void likeTogether(User user, LikeTogetherRequest likeTogetherRequest) {
        Together together = togetherRepository.findById(likeTogetherRequest.getTogetherId())
                .orElseThrow(() -> new CustomException(ErrorCode.TOGETHER_NOT_FOUND));

        redisUtil.addTogetherLike(user.getUserId().toString(), likeTogetherRequest.getTogetherId().toString());

        together.incrementLikeNum();
        togetherRepository.save(together);

        return null;
    }

    public Void unlikeTogether(User user, Long togetherId) {
        Together together = togetherRepository.findByTogetherId(togetherId)
                .orElseThrow(() -> new CustomException(ErrorCode.TOGETHER_NOT_FOUND));
        redisUtil.removeTogetherLike(user.getUserId().toString(), togetherId.toString());

        together.decrementLikeNum();
        togetherRepository.save(together);

        return null;
    }
}
