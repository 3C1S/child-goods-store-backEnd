package C1S.childgoodsstore.entity;

import C1S.childgoodsstore.review.dto.SaveReviewDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product_review")
@Getter
@Setter
@ToString
public class ProductReview extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_review_id")
    private Long productReviewId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer score;

    private String content;

    private String type; //리뷰 상품 타입(중고 상품인지 공동구매 상품인지)

    public ProductReview(User user, Product product, SaveReviewDto reviewDto) {
        this.user = user;
        this.product = product;
        this.score = reviewDto.getScore();
        this.content = reviewDto.getContent();
        this.type = "PRODUCT";
        setCreatedAt();
        setUpdatedAt();
    }

    public ProductReview() {

    }
}
