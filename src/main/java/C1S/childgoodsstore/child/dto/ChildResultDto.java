package C1S.childgoodsstore.child.dto;

import C1S.childgoodsstore.entity.Child;
import C1S.childgoodsstore.enums.AGE;
import C1S.childgoodsstore.enums.GENDER;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public class ChildResultDto extends ChildSaveDto {
    private final Long childId;

    public ChildResultDto(Long childId, String name, GENDER gender, AGE age, List<String> tag, String childImg){
        super(name, gender, age, tag, childImg);
        this.childId = childId;
    }

    public ChildResultDto(Child child) {
        super(child.getName(), child.getGender(), child.getAge(), extractTags(child), child.getChildImg());
        this.childId = child.getChildId();
    }

    private static List<String> extractTags(Child child) {
        return child.getChildTags() != null ? child.getChildTags().stream()
                .map(childTag -> childTag.getTag().getName())
                .collect(Collectors.toList()) : new ArrayList<>();
    }
}
