package C1S.childgoodsstore.review.dto;

import C1S.childgoodsstore.enums.PRODUCT_CATEGORY;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ReviewDto{

    private Long reviewId;
    private PRODUCT_CATEGORY type;
    private Long id;
    @Nullable
    private Long userId;
    private String userName;
    private Double averageStars;
    private Long totalReview;
    private Integer score;
    private String content;
    private LocalDateTime createdAt;
    private String name;
    private String profileImg;

    public ReviewDto(Long reviewId, PRODUCT_CATEGORY type, Long id, @Nullable Long userId, String userName,
                     Integer score, String content, LocalDateTime createdAt, String name, String profileImg) {
        this.reviewId = reviewId;
        this.type = type;
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.score = score;
        this.content = content;
        this.createdAt = createdAt;
        this.name = name;
        this.profileImg = profileImg;
    }

}
