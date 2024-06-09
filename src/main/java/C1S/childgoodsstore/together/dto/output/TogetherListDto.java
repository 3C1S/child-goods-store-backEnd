package C1S.childgoodsstore.together.dto.output;

import C1S.childgoodsstore.entity.Together;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
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

    public TogetherListDto() {}

    public TogetherListDto(Together together, String togetherImage, boolean togetherHeart) {

        this.togetherId = together.getTogetherId();
        this.togetherName = together.getTogetherName();
        this.totalPrice = together.getTotalPrice();

        if(together.getSoldNum() != 0) {
            this.purchasePrice = together.getTotalPrice() / together.getSoldNum();
        }
        else {
            this.purchasePrice = together.getTotalPrice();
        }

        this.totalNum = together.getTotalNum();
        this.purchaseNum = together.getSoldNum();
        this.deadline = together.getDeadline();
        this.togetherImage = togetherImage;
        this.togetherHeart = togetherHeart;
    }
}
