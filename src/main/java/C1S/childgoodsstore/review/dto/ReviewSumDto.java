package C1S.childgoodsstore.review.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ReviewSumDto {
    private Long sum = 0L;
    private Long count = 0L;

    public ReviewSumDto(Long sum, Long count){
        this.sum = sum;
        this.count = count;
    }
}
