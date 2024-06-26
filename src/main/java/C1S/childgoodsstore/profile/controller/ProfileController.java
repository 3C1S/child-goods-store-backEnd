package C1S.childgoodsstore.profile.controller;

import C1S.childgoodsstore.profile.dto.MypageProductListDto;
import C1S.childgoodsstore.profile.dto.PurchaseProductListDto;
import C1S.childgoodsstore.profile.dto.TogetherDto;
import C1S.childgoodsstore.profile.dto.TogetherOrderDto;
import C1S.childgoodsstore.profile.service.ProfileService;
import C1S.childgoodsstore.security.auth.PrincipalDetails;
import C1S.childgoodsstore.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    //마이페이지 판매 게시글 목록 조회
    @GetMapping("/product/{userId}")
    public ResponseEntity<ApiResponse<List<MypageProductListDto>>> getMypageSaleProduct(
            @PathVariable("userId") Long userId,
            @RequestParam(name = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok().body(ApiResponse.success(profileService.getMypageSaleProduct(userId, page)));
    }

    //상품 관심 목록 조회
    @GetMapping("/product/heart")
    public ResponseEntity<ApiResponse<List<MypageProductListDto>>> getMyProductHeart(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(name = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok().body(ApiResponse.success(profileService.getMyProductHeart(principalDetails.getUser().getUserId(), page)));
    }

    //상품 구매 내역 조회
    @GetMapping("product/purchase")
    public ResponseEntity<ApiResponse<List<PurchaseProductListDto>>> getPurchaseProduct(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(name = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok().body(ApiResponse.success(profileService.getPurchaseProduct(principalDetails.getUser().getUserId(), page)));
    }

    @GetMapping("/together/heart")
    public ResponseEntity<ApiResponse<List<TogetherDto>>> getLikesForTogether(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(ApiResponse.success(profileService.getLikesForTogether(principalDetails.getUser().getUserId(), pageable)));
    }

    @GetMapping("/together/{userId}")
    public ResponseEntity<ApiResponse<List<TogetherDto>>> getProfileTogether(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(ApiResponse.success(profileService.getProfileTogether(userId, pageable)));
    }

    @GetMapping("/together/purchase")
    public ResponseEntity<ApiResponse<List<TogetherOrderDto>>> getTogetherOrder(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(ApiResponse.success(profileService.getTogetherOrder(principalDetails.getUser(), pageable)));
    }
}
