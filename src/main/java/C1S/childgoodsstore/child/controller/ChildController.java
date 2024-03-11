package C1S.childgoodsstore.child.controller;

import C1S.childgoodsstore.child.dto.ChildResultDto;
import C1S.childgoodsstore.child.dto.ChildSaveDto;
import C1S.childgoodsstore.child.service.ChildService;
import C1S.childgoodsstore.entity.Child;
import C1S.childgoodsstore.security.auth.PrincipalDetails;
import C1S.childgoodsstore.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/child")
public class ChildController {

    private final ChildService childService;

//자녀정보등록수정조회
//리뷰 작성
    @PostMapping()
    public ResponseEntity<ApiResponse<ChildResultDto>> createReview(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Valid ChildSaveDto childDto) {
        return ResponseEntity.ok(ApiResponse.success(childService.save(principalDetails.getUser(), childDto)));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ChildResultDto>>> getReviewsByUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(ApiResponse.success(childService.getChildrenByUser(principalDetails.getUser())));
    }
}
