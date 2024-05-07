package C1S.childgoodsstore.child.service;

import C1S.childgoodsstore.child.dto.ChildDto;
import C1S.childgoodsstore.child.dto.ChildSaveDto;
import C1S.childgoodsstore.child.repository.ChildRepository;
import C1S.childgoodsstore.child.repository.ChildTagRepository;
import C1S.childgoodsstore.entity.*;
import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import C1S.childgoodsstore.tag.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;
    private final TagRepository tagRepository;
    private final ChildTagRepository childTagRepository;

    @Transactional
    public ChildDto save(User user, ChildSaveDto childSaveDto) {
        // 태그를 제외한 자녀 정보 저장
        Child child = childRepository.saveAndFlush(new Child(user, childSaveDto));

        // ChildSaveDto로부터 받은 태그 리스트를 처리하여 저장
        saveChildTags(childSaveDto.getTag(), child);

        return new ChildDto(child.getChildId(), childSaveDto.getName(), childSaveDto.getGender(), childSaveDto.getAge(), childSaveDto.getTag(), childSaveDto.getChildImg());
    }
    private void saveChildTags(List<String> tagNames, Child child) {
        if (tagNames != null && !tagNames.isEmpty()) {
            for (String tagName : tagNames) {
                // 태그 이름으로 Tag 테이블 검색, 없으면 새로 생성하여 저장
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(tagName)));
                // Product와 Tag를 연결하는 ProductTag 인스턴스 생성
                ChildTag childTag = new ChildTag(child, tag);
                // 생성된 ProductTag 인스턴스를 데이터베이스에 저장
                childTagRepository.save(childTag);
            }
        }
    }

    @Transactional
    public List<ChildDto> getChildrenByUser(User user) {
        List<Child> children = childRepository.findByParent(user);

        List<ChildDto> childResultDtoList = new ArrayList<>();

        for (Child child : children) {
            childResultDtoList.add(new ChildDto(child));
        }
        return childResultDtoList;
    }

    // controller - 자녀 정보 수정
    public ChildDto updateChild(User user, ChildDto childDto) {
        // 자녀 정보 존재 및 소유권 확인
        Child child = validateChildOwnership(user, childDto.getChildId());

        // 자녀 기본 정보 업데이트
        child.setName(childDto.getName());
        child.setAge(childDto.getAge());
        child.setChildImg(childDto.getChildImg());
        child.setGender(childDto.getGender());
        // 자녀 태그 업데이트
        updateChildTags(child, childDto.getTag());
        // 업데이트 정보 저장
        Child updatedChild = childRepository.save(child);

        return new ChildDto(updatedChild);

    }

    // 자녀 태그 수정
    public void updateChildTags(Child child, List<String> tagNames) {
        if (tagNames == null) {
            // 입력된 태그가 없으면 기존 태그 제거
            child.getChildTags().clear();
        } else {
            // 기존 태그 목록에서 이름을 기준으로 맵 생성
            Map<String, ChildTag> existingTags = child.getChildTags().stream()
                    .collect(Collectors.toMap(tag -> tag.getTag().getName(), tag -> tag));

            List<ChildTag> updatedTags = new ArrayList<>();

            for (String tagName : tagNames) {
                // 이미 존재하는 태그인지 확인
                if (!existingTags.containsKey(tagName)) {
                    // 새로운 태그 생성
                    Tag newTag = new Tag(tagName);
                    tagRepository.save(newTag); // 새 태그 저장
                    ChildTag newChildTag = new ChildTag(child, newTag);
                    updatedTags.add(newChildTag);
                } else {
                    // 이미 존재하는 태그면 기존 태그 사용
                    updatedTags.add(existingTags.get(tagName));
                }
            }

            // 모든 기존 태그를 제거하고 새로운 태그 목록을 할당
            child.getChildTags().clear();
            child.getChildTags().addAll(updatedTags);
        }
    }

    // 자녀 정보 존재 및 소유권 확인
    private Child validateChildOwnership(User user, Long childId) {
        // 자녀 정보 존재 확인
        Child child = childRepository.findById(Math.toIntExact(childId))
                .orElseThrow(() -> new CustomException(ErrorCode.CHILD_NOT_FOUND, "Child not found with ID: " + childId));
        // 자녀의 소유권 확인
        if (!child.getParent().getUserId().equals(user.getUserId())) {
            throw new CustomException(ErrorCode.CHILD_NOT_OWNED, "You do not own the child with ID: " + childId);
        }
        return child;

    }
}