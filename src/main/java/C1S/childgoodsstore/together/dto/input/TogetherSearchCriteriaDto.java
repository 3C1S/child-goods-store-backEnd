package C1S.childgoodsstore.together.dto.input;

import C1S.childgoodsstore.enums.AGE;
import C1S.childgoodsstore.enums.MAIN_CATEGORY;
import C1S.childgoodsstore.enums.SUB_CATEGORY;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TogetherSearchCriteriaDto {

    @JsonProperty("mainCategory")
    private MAIN_CATEGORY mainCategory;

    @JsonProperty("subCategory")
    private SUB_CATEGORY subCategory;

    @JsonProperty("region")
    private String region;

    @JsonProperty("age")
    private AGE age;

    @JsonProperty("page")
    private Integer page;
}
