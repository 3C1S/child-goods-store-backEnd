package C1S.childgoodsstore.child.service;

import C1S.childgoodsstore.child.dto.ChildResultDto;
import C1S.childgoodsstore.child.dto.ChildSaveDto;
import C1S.childgoodsstore.child.dto.RecommendProductDto;
import C1S.childgoodsstore.child.repository.ChildRepository;
import C1S.childgoodsstore.entity.Child;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.util.exception.CustomException;
import C1S.childgoodsstore.util.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;

    @Transactional
    public ChildResultDto save(User user, ChildSaveDto childDto) {
        Child child = childRepository.saveAndFlush(new Child(user, childDto));

        return new ChildResultDto(child);
    }

    @Transactional
    public List<ChildResultDto> getChildrenByUser(User user) {
        List<Child> children = childRepository.findByParent(user);

        if (children.isEmpty()) {
            throw new CustomException(ErrorCode.CHILD_NOT_FOUND);
        }

        List<ChildResultDto> childResultDtoList = new ArrayList<>();

        for (Child child : children) {
            childResultDtoList.add(new ChildResultDto(child));
        }
        return childResultDtoList;
    }

//    @Transactional
//    public List<RecommendProductDto> getChildRecommendProducts(User user) {
//
//        List<RecommendProductDto> recommendProducts;
//        return recommendProducts;
//    }
}