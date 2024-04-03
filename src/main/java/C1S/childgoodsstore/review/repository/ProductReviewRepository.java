package C1S.childgoodsstore.review.repository;

import C1S.childgoodsstore.entity.ProductReview;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.review.dto.ReviewSumDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    @Query(value = "select p from ProductReview p where p.productReviewId = ?1 and p.user.userId = ?2 and p.product.productId = ?3")
    Optional<ProductReview> findByProductReviewIdAndUserIdAndProductId(Long productReviewId, Long userId, Long productId);

    Optional<ProductReview> findByProductReviewIdAndUser(Long productReviewId, User user);

    List<ProductReview> findByUser(User user);

    @Query("SELECT NEW C1S.childgoodsstore.review.dto.ReviewSumDto(SUM(pr.score), COUNT(*)) FROM ProductReview pr WHERE pr.user.userId = :userId")
    ReviewSumDto findSumScoreAndReviewCountByUserId(@Param("userId") Long userId);


    @Modifying
    @Query("update ProductReview p set p.score = :score, p.content = :content where p.productReviewId = :reviewId")
    void updateByProductReviewId(@Param("score") Integer score, @Param("content") String content,
                                @Param("reviewId") Long reviewId);
}
