package C1S.childgoodsstore.product.controller;

import C1S.childgoodsstore.product.service.ProductService;
import C1S.childgoodsstore.security.auth.PrincipalDetails;
import C1S.childgoodsstore.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/heart/{productId}")
    public ResponseEntity<ApiResponse> setProductHeart(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("productId") Long productId) {
        productService.setHeart(principalDetails.getUser().getUserId(), productId);
        return ResponseEntity.ok().body(ApiResponse.success(null));
    }

    @DeleteMapping("/heart/{productId}")
    public ResponseEntity<ApiResponse> deleteProductHeart(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("productId") Long productId) {
        productService.deleteHeart(principalDetails.getUser().getUserId(), productId);
        return ResponseEntity.ok().body(ApiResponse.success(null));
    }
}