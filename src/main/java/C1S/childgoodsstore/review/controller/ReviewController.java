package C1S.childgoodsstore.review.controller;

import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.review.dto.ReviewDto;
import C1S.childgoodsstore.review.dto.SaveReviewDto;
import C1S.childgoodsstore.review.service.ReviewService;
import C1S.childgoodsstore.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import C1S.childgoodsstore.auth.presentation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // /review/{userIdx} - 내가 받은 후기 조회 get
    @GetMapping("/review/{userId}")
    public ResponseEntity<ApiResponse<List<ReviewDto>>> getReview(@PathVariable("userId") Long userId,
                                                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                                                  @RequestParam(name = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(ApiResponse.success(reviewService.getReview(userId, page, size)));
    }

    // /review 후기 등록 post
    @PostMapping("/review")
    public ResponseEntity<ApiResponse> saveReview(@AuthenticationPrincipal User user,
                                                  @RequestBody SaveReviewDto reviewDto) {
        reviewService.saveReview(user, reviewDto);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // /review/{reviewId} 후기 수정 patch
    @PatchMapping("/review/{reviewId}")
    public ResponseEntity<ApiResponse> modifyReview(@PathVariable("reviewId") Long reviewId, @AuthenticationPrincipal User user,
                                                    @RequestBody SaveReviewDto reviewDto){
        reviewService.modifyReview(user,
                reviewId, reviewDto);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // /review/{reviewId} 후기 삭제 delete
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable("reviewId") Long reviewId,
                                                    @AuthenticationPrincipal User user,
                                                    @RequestParam("type") String type){

        reviewService.deleteReview(user, reviewId, type);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
