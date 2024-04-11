package C1S.childgoodsstore.profile.controller;

import C1S.childgoodsstore.product.service.ProductService;
import C1S.childgoodsstore.profile.dto.MypageProductListDto;
import C1S.childgoodsstore.profile.dto.PurchaseProductListDto;
import C1S.childgoodsstore.profile.service.ProfileService;
import C1S.childgoodsstore.security.auth.PrincipalDetails;
import C1S.childgoodsstore.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/product/{userId}")
    public ResponseEntity<ApiResponse<List<MypageProductListDto>>> getMypageSaleProduct(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(ApiResponse.success(profileService.getMypageSaleProduct(userId)));
    }

    @GetMapping("/product/heart")
    public ResponseEntity<ApiResponse<List<MypageProductListDto>>> getMyProductHeart(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok().body(ApiResponse.success(profileService.getMyProductHeart(principalDetails.getUser().getUserId())));
    }

    @GetMapping("product/purchase")
    public ResponseEntity<ApiResponse<List<PurchaseProductListDto>>> getPurchaseProduct(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok().body(ApiResponse.success(profileService.getPurchaseProduct(principalDetails.getUser().getUserId())));
    }
}
