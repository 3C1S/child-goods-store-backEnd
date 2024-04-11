package C1S.childgoodsstore.review.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class SaveReviewDto {
    private Long productId;
    private Integer score;
    private String content;
}
