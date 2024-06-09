package C1S.childgoodsstore.profile.dto;

import C1S.childgoodsstore.entity.Together;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TogetherDto {
    private Long togetherId;
    private String togetherName;
    private Integer totalPrice;
    private Integer purchasePrice;
    private Integer totalNum;
    private Integer purchaseNum;
    private LocalDateTime deadline;
    private String togetherImage;
    private Boolean togetherHeart;

    public static TogetherDto fromEntity(Together together, String togetherImage) {
        TogetherDto dto = new TogetherDto();
        dto.setTogetherId(together.getTogetherId());
        dto.setTogetherName(together.getTogetherName());
        dto.setTotalPrice(together.getTotalPrice());
        dto.setPurchasePrice(together.getTotalPrice()/together.getTotalNum());
        dto.setTotalNum(together.getTotalNum());
        dto.setPurchaseNum(together.getSoldNum());
        dto.setDeadline(together.getDeadline());
        dto.setTogetherImage(togetherImage);
        dto.setTogetherHeart(true);  // 예시: 사용자가 좋아요를 눌렀는지 정보
        return dto;
    }
}
