package C1S.childgoodsstore.child.controller;

import C1S.childgoodsstore.child.dto.ChildDto;
import C1S.childgoodsstore.child.dto.ChildSaveDto;
import C1S.childgoodsstore.child.service.ChildService;
import C1S.childgoodsstore.product.dto.output.ProductViewDto;
import C1S.childgoodsstore.security.auth.PrincipalDetails;
import C1S.childgoodsstore.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // 자녀 정보 등록
    @PostMapping()
    public ResponseEntity<ApiResponse<ChildDto>> createChild(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Valid ChildSaveDto childSaveDto) {
        return ResponseEntity.ok(ApiResponse.success(childService.save(principalDetails.getUser(), childSaveDto)));
    }

    // 자녀 정보 조회
    @GetMapping()
    public ResponseEntity<ApiResponse<List<ChildDto>>> getChildrenByUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(ApiResponse.success(childService.getChildrenByUser(principalDetails.getUser())));
    }

    // 자녀 정보 수정
    @PatchMapping()
    public ResponseEntity<ApiResponse<ChildDto>> updateProduct(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Valid ChildDto childDto) {
        return ResponseEntity.ok().body(ApiResponse.success(childService.updateChild(principalDetails.getUser(), childDto)));
    }

    // 자녀 맞춤 상품 조회
    // flask로 이동 예정
    @GetMapping("/{childId}/user/{userId}")
    public ResponseEntity<ApiResponse<List<ProductViewDto>>> getChildRecommendation(
            @PathVariable Long childId,
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(childService.getChildRecommendation(childId, userId, pageable)));
    }
}
