package C1S.childgoodsstore.child.dto;

import C1S.childgoodsstore.entity.Child;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.enums.GENDER;
import lombok.Getter;

@Getter
public class ChildResultDto {
    private Long childId;
    private String name;
    private GENDER gender;
    private Integer age;
    private String tag;
    private String childImg;

    public ChildResultDto() {
    }
    public ChildResultDto(Child child){
        this.childId = child.getChildId();
        this.name = child.getName();
        this.gender = child.getGender();
        this.age = child.getAge();
        this.tag = child.getTag();
        this.childImg = child.getChildImg();
    }

}
