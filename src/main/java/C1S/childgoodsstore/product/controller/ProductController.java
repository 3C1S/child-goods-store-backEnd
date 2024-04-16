package C1S.childgoodsstore.product.controller;

import C1S.childgoodsstore.product.dto.input.CreateProductDto;
import C1S.childgoodsstore.product.dto.input.ProductSearchCriteriaDto;
import C1S.childgoodsstore.product.dto.input.ProductStateDto;
import C1S.childgoodsstore.product.dto.output.HomeUsedProductViewDto;
import C1S.childgoodsstore.product.dto.output.ProductDetailsDto;
import C1S.childgoodsstore.product.dto.output.PurchaseProspectDto;
import C1S.childgoodsstore.product.service.ProductService;
import C1S.childgoodsstore.security.auth.PrincipalDetails;
import C1S.childgoodsstore.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    // 상품 등록
    @PostMapping()
    public ResponseEntity<ApiResponse<Long>> postProduct(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid @RequestBody CreateProductDto productDto) {
        return ResponseEntity.ok().body(ApiResponse.success(productService.postProduct(principalDetails.getUser(), productDto)));
    }

    // 상품 수정
    @PatchMapping("{productId}")
    public ResponseEntity<ApiResponse<Long>> updateProduct(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("productId") Long productId, @Valid @RequestBody CreateProductDto productDto) {
        return ResponseEntity.ok().body(ApiResponse.success(productService.updateProduct(principalDetails.getUser(), productId, productDto)));
    }

    // 상품 조회
    @GetMapping("{productId}")
    public ResponseEntity<ApiResponse<ProductDetailsDto>> getProduct(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("productId") Long productId) {
        return ResponseEntity.ok().body(ApiResponse.success(productService.getProduct(principalDetails.getUser(), productId)));
    }

    // 홈화면 상품 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<HomeUsedProductViewDto>>> getHomeScreenProducts(@AuthenticationPrincipal PrincipalDetails principalDetails, ProductSearchCriteriaDto criteria) {
        return ResponseEntity.ok().body(ApiResponse.success(productService.getHomeScreenProducts(principalDetails.getUser(), criteria)));
    }

    // 중고 상품 이름으로 상품 검색
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<HomeUsedProductViewDto>>> searchProductsByProductName(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam String productName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(ApiResponse.success(productService.searchProductsByProductName(principalDetails.getUser(), productName, pageable)));
    }

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

    // 상품 판매 상태 업데이트
    @PatchMapping("/state/{productId}")
    public ResponseEntity<ApiResponse<Long>> updateProductState(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("productId") Long productId, @Valid @RequestBody ProductStateDto productStateDto) {
        return ResponseEntity.ok().body(ApiResponse.success(productService.updateProductState(principalDetails.getUser(), productId, productStateDto)));
    }

    // 구매 예정자 조회
    @GetMapping("/buyer/{productId}")
    public ResponseEntity<ApiResponse<List<PurchaseProspectDto>>> getProductBuyer(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("productId") Long productId) {
        return ResponseEntity.ok().body(ApiResponse.success(productService.getProductBuyer(principalDetails.getUser(), productId)));
    }
}
