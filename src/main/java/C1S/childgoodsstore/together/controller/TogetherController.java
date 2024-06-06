package C1S.childgoodsstore.together.controller;

import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.global.response.ApiResponse;
import C1S.childgoodsstore.together.dto.LikeTogetherRequest;
import C1S.childgoodsstore.together.service.TogetherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import C1S.childgoodsstore.auth.presentation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/together")
public class TogetherController {
    private final TogetherService togetherService;

    //공동구매 관심 등록
    @PostMapping("/heart")
    public ResponseEntity<ApiResponse<Void>> likeTogether(@AuthenticationPrincipal User user, @Valid @RequestBody LikeTogetherRequest likeTogetherRequest) {
        return ResponseEntity.ok().body(ApiResponse.success(togetherService.likeTogether(user, likeTogetherRequest)));
    }

    //공동구매 관심 삭제
    @DeleteMapping("/heart/{togetherId}")
    public ResponseEntity<ApiResponse<Void>> unlikeTogether(@AuthenticationPrincipal User user, @PathVariable Long togetherId) {
        return ResponseEntity.ok().body(ApiResponse.success(togetherService.unlikeTogether(user, togetherId)));
    }

}
