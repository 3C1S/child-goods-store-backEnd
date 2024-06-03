package C1S.childgoodsstore.together.dto.output;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TogetherListDto {

    private Long togetherId;
    private String togetherName;
    private int totalPrice;
    private int purchasePrice;
    private int totalNum;
    private int purchaseNum;
    private LocalDateTime deadline;
    private String togetherImage;
    private boolean togetherHeart;
}
