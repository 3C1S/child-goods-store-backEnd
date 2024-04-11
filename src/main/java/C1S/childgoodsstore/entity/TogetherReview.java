package C1S.childgoodsstore.entity;

import C1S.childgoodsstore.review.dto.SaveReviewDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TogetherReview")
@Getter
@Setter
@ToString
public class TogetherReview extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long togetherReviewId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "togetherId")
    private Together together;

    private Integer score;

    private String content;

    private String type; //리뷰 상품 타입(중고 상품인지 공동구매 상품인지)

    public TogetherReview(User user, Together together, SaveReviewDto reviewDto) {
        this.user = user;
        this.together = together;
        this.score = reviewDto.getScore();
        this.content = reviewDto.getContent();
        this.type = "TOGETHER";
        setCreatedAt();
        setUpdatedAt();
    }

    public TogetherReview() {

    }
}
