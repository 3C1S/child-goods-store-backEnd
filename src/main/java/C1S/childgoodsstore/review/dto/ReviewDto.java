package C1S.childgoodsstore.review.dto;

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
    private String type;
    private Long id;
    private Long userId;
    private String userName;
    private Double averageStars;
    private Long totalReview;
    private Integer score;
    private String content;
    private LocalDateTime createdAt;
    private String name;
    private String userImage;

    public ReviewDto(Long reviewId, String type, Long id, Long userId, String userName,
                     Integer score, String content, LocalDateTime createdAt, String name, String userImage) {
        this.reviewId = reviewId;
        this.type = type;
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.score = score;
        this.content = content;
        this.createdAt = createdAt;
        this.name = name;
        this.userImage = userImage;
    }

}
