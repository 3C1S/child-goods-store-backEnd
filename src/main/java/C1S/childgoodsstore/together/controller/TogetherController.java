package C1S.childgoodsstore.together.controller;

import C1S.childgoodsstore.global.response.ApiResponse;
import C1S.childgoodsstore.security.auth.PrincipalDetails;
import C1S.childgoodsstore.together.dto.input.CreateTogetherDto;
import C1S.childgoodsstore.together.dto.LikeTogetherRequest;
import C1S.childgoodsstore.together.service.TogetherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/together")
public class TogetherController {

    private final TogetherService togetherService;

//    @GetMapping("")
//    public ResponseEntity<ApiResponse<List<TogetherListDto>>> getTogetherList(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//        return ResponseEntity.ok().body(ApiResponse.success(togetherService.getTogetherList(principalDetails.getUser().getUserId())));
//    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Long>> postTogether(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                          @Valid @RequestBody CreateTogetherDto togetherDto) {
        return ResponseEntity.ok().body(ApiResponse.success(togetherService.postTogether(principalDetails.getUser(), togetherDto)));
    }

    @PatchMapping("{togetherId}")
    public ResponseEntity<ApiResponse<Long>> updateTogether(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                            @PathVariable("togetherId") Long togetherId,
                                                            @Valid @RequestBody CreateTogetherDto togetherDto) {
        return ResponseEntity.ok().body(ApiResponse.success(togetherService.updateTogether(principalDetails.getUser(), togetherId, togetherDto)));
    }

    //공동구매 관심 등록
    @PostMapping("/heart")
    public ResponseEntity<ApiResponse<Void>> likeTogether(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid @RequestBody LikeTogetherRequest likeTogetherRequest) {
        return ResponseEntity.ok().body(ApiResponse.success(togetherService.likeTogether(principalDetails.getUser(), likeTogetherRequest)));
    }

    //공동구매 관심 삭제
    @DeleteMapping("/heart")
    public ResponseEntity<ApiResponse<Void>> unlikeTogether(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid @RequestBody LikeTogetherRequest likeTogetherRequest) {
        return ResponseEntity.ok().body(ApiResponse.success(togetherService.unlikeTogether(principalDetails.getUser(), likeTogetherRequest)));
    }
}
