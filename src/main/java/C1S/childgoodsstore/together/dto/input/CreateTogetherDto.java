package C1S.childgoodsstore.together.dto.input;

import C1S.childgoodsstore.enums.AGE;
import C1S.childgoodsstore.enums.MAIN_CATEGORY;
import C1S.childgoodsstore.enums.SUB_CATEGORY;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateTogetherDto {

    private String togetherName;
    private int totalPrice;
    private String details;
    private MAIN_CATEGORY mainCategory;
    private SUB_CATEGORY subCategory;
    private String link;
    private LocalDateTime deadline;
    private String address;
    private String detailAddress;
    private int totalNum;
    private List<String> tag = new ArrayList<>();
    private List<String> togetherImage = new ArrayList<>();
    private AGE age;
}
