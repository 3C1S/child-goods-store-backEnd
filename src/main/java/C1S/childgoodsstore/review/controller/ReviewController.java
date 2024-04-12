package C1S.childgoodsstore.review.controller;

import C1S.childgoodsstore.review.dto.ReviewDto;
import C1S.childgoodsstore.review.dto.SaveReviewDto;
import C1S.childgoodsstore.review.service.ReviewService;
import C1S.childgoodsstore.security.auth.PrincipalDetails;
import C1S.childgoodsstore.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // /review/{userIdx} - 내가 받은 후기 조회 get
    @GetMapping("/review/{userId}")
    public ResponseEntity<ApiResponse<List<ReviewDto>>> getReview(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(ApiResponse.success(reviewService.getReview(userId)));
    }

    // /review 후기 등록 post
    @PostMapping("/review")
    public ResponseEntity<ApiResponse> saveReview(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                  @RequestBody SaveReviewDto reviewDto) {
        reviewService.saveReview(principalDetails.getUser(), reviewDto);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // /review/{reviewId} 후기 수정 patch
    @PatchMapping("/review/{reviewId}")
    public ResponseEntity<ApiResponse> modifyReview(@PathVariable("reviewId") Long reviewId, @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                    @RequestBody SaveReviewDto reviewDto){
        reviewService.modifyReview(principalDetails.getUser(),
                reviewId, reviewDto);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // /review/{reviewId} 후기 삭제 delete
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable("reviewId") Long reviewId,
                                                    @AuthenticationPrincipal PrincipalDetails principalDetails){

        reviewService.deleteReview(principalDetails.getUser(), reviewId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }


}
