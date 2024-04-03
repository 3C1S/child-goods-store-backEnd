package C1S.childgoodsstore.child.controller;

import C1S.childgoodsstore.child.dto.ChildResultDto;
import C1S.childgoodsstore.child.dto.ChildSaveDto;
import C1S.childgoodsstore.child.dto.RecommendProductDto;
import C1S.childgoodsstore.child.service.ChildService;
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

    @PostMapping()
    public ResponseEntity<ApiResponse<ChildResultDto>> createChild(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Valid ChildSaveDto childDto) {
        return ResponseEntity.ok(ApiResponse.success(childService.save(principalDetails.getUser(), childDto)));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ChildResultDto>>> getChildrenByUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(ApiResponse.success(childService.getChildrenByUser(principalDetails.getUser())));
    }

//    @GetMapping("/{childId}")
//    public ResponseEntity<ApiResponse<List<RecommendProductDto>>> getRecommendedProductsByChild(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//        return ResponseEntity.ok(ApiResponse.success(childService.getChildRecommendProducts(principalDetails.getUser())));
//    }
}
