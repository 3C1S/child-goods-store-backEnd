package C1S.childgoodsstore.together.service;

import C1S.childgoodsstore.entity.*;
import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import C1S.childgoodsstore.tag.repository.TagRepository;
import C1S.childgoodsstore.together.dto.input.CreateTogetherDto;
import C1S.childgoodsstore.together.repository.TogetherImageRepository;
import C1S.childgoodsstore.together.repository.TogetherRepository;
import C1S.childgoodsstore.together.repository.TogetherTagRepository;
import C1S.childgoodsstore.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

//    public List<TogetherListDto> getTogetherList(Long userId) {
//
//        List<Together> togethers;
//    }

    public Long postTogether(User user, CreateTogetherDto togetherDto) {

        getUserById(user.getUserId());

        Together together = new Together(user, togetherDto);
        Together savedTogether = togetherRepository.saveAndFlush(together);

        saveTogetherImages(togetherDto.getTogetherImage(), savedTogether);
        saveTogetherTags(togetherDto.getTag(), savedTogether);

        return savedTogether.getTogetherId();
    }

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

        togetherImageRepository.deleteTogetherImagesByTogetherTogetherId(together.getTogetherId());
        saveTogetherImages(togetherDto.getTogetherImage(), together);

        togetherTagRepository.deleteTogetherTagsByTogetherTogetherId(together.getTogetherId());
        saveTogetherTags(togetherDto.getTag(), together);

        togetherRepository.saveAndFlush(together);
        return together.getTogetherId();
    }

    private User getUserById(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private Together getTogetherById(Long togetherId) {
        return togetherRepository.findById(togetherId)
                .orElseThrow(() -> new CustomException(ErrorCode.TOGETHER_NOT_FOUND));
    }

    private void saveTogetherImages(List<String> imageUrls, Together together) {

        if (imageUrls != null && !imageUrls.isEmpty()) {
            for (int i = 0; i < imageUrls.size(); i++) {

                TogetherImage togetherImage = new TogetherImage(together, imageUrls.get(i), i+1);
                togetherImageRepository.saveAndFlush(togetherImage);
            }
        }
    }

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
}
