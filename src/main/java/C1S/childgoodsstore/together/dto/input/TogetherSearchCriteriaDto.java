package C1S.childgoodsstore.together.dto.input;

import C1S.childgoodsstore.enums.AGE;
import C1S.childgoodsstore.enums.MAIN_CATEGORY;
import C1S.childgoodsstore.enums.SUB_CATEGORY;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TogetherSearchCriteriaDto {

    private MAIN_CATEGORY mainCategory;
    private SUB_CATEGORY subCategory;
    private String region;
    private AGE age;
    private Integer page;
}
