package C1S.childgoodsstore.review.repository;

import C1S.childgoodsstore.entity.ProductReview;
import C1S.childgoodsstore.entity.Together;
import C1S.childgoodsstore.entity.TogetherReview;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.review.dto.ReviewSumDto;
import C1S.childgoodsstore.together.repository.TogetherRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Transactional
public interface TogetherReviewRepository extends JpaRepository<TogetherReview, Long> {

    @Query(value = "select t from TogetherReview t where t.togetherReviewId = ?1 and t.user.userId = ?2 and t.together.togetherId = ?3")
    Optional<TogetherReview> findByTogetherReviewIdAndUserIdAndTogetherId(Long togetherReviewId, Long userId, Long togetherId);

    Optional<TogetherReview> findByTogetherReviewIdAndUser(Long togetherReviewId, User user);

    List<TogetherReview> findByUser(User user);

    @Query("SELECT NEW C1S.childgoodsstore.review.dto.ReviewSumDto(SUM(tr.score), COUNT(*)) FROM TogetherReview tr WHERE tr.user.userId = :userId")
    ReviewSumDto findSumScoreAndReviewCountByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("update TogetherReview t set t.score = :score, t.content = :content where t.togetherReviewId = :reviewId")
    void updateByTogetherReviewId(@Param("score") Integer score, @Param("content") String content,
                                @Param("reviewId") Long reviewId);

    Optional<TogetherReview> findByTogether(Together together);

    Optional<TogetherReview> findByUserAndTogether(User user, Together together);
}
