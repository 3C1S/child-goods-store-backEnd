package C1S.childgoodsstore.child.dto;

import C1S.childgoodsstore.enums.GENDER;
import lombok.Getter;

@Getter
public class ChildSaveDto {

    private String name;
    private GENDER gender;
    private Integer age;
    private String tag;
    private String childImg;

}
