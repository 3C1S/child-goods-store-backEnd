package C1S.childgoodsstore.child.dto;

import C1S.childgoodsstore.enums.AGE;
import C1S.childgoodsstore.enums.GENDER;
import lombok.Getter;

import java.util.List;

@Getter
public class ChildSaveDto {

    private final String name;
    private final GENDER gender;
    private final AGE age;
    private final List<String> tag;
    private final String childImg;

    public ChildSaveDto(String name, GENDER gender, AGE age, List<String> tag, String childImg) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.tag = tag;
        this.childImg = childImg;
    }
}
