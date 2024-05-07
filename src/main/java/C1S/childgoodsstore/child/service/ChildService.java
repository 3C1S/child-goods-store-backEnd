package C1S.childgoodsstore.child.service;

import C1S.childgoodsstore.child.dto.ChildResultDto;
import C1S.childgoodsstore.child.dto.ChildSaveDto;
import C1S.childgoodsstore.child.repository.ChildRepository;
import C1S.childgoodsstore.child.repository.ChildTagRepository;
import C1S.childgoodsstore.entity.*;
import C1S.childgoodsstore.tag.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;
    private final TagRepository tagRepository;
    private final ChildTagRepository childTagRepository;

    @Transactional
    public ChildResultDto save(User user, ChildSaveDto childSaveDto) {
        // 태그를 제외한 자녀 정보 저장
        Child child = childRepository.saveAndFlush(new Child(user, childSaveDto));

        // ChildSaveDto로부터 받은 태그 리스트를 처리하여 저장
        saveChildTags(childSaveDto.getTag(), child);

        return new ChildResultDto(child.getChildId(), childSaveDto.getName(), childSaveDto.getGender(), childSaveDto.getAge(), childSaveDto.getTag(), childSaveDto.getChildImg());
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
    public List<ChildResultDto> getChildrenByUser(User user) {
        List<Child> children = childRepository.findByParent(user);

        List<ChildResultDto> childResultDtoList = new ArrayList<>();

        for (Child child : children) {
            childResultDtoList.add(new ChildResultDto(child));
        }
        return childResultDtoList;
    }

}