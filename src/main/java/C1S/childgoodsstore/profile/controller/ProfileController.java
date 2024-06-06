package C1S.childgoodsstore.profile.controller;

import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.profile.dto.MypageProductListDto;
import C1S.childgoodsstore.profile.dto.PurchaseProductListDto;
import C1S.childgoodsstore.profile.dto.TogetherDto;
import C1S.childgoodsstore.profile.dto.TogetherOrderDto;
import C1S.childgoodsstore.profile.service.ProfileService;
import C1S.childgoodsstore.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import C1S.childgoodsstore.auth.presentation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;


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
    public ResponseEntity<ApiResponse<List<MypageProductListDto>>> getMyProductHeart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(ApiResponse.success(profileService.getMyProductHeart(user.getUserId())));
    }

    @GetMapping("product/purchase")
    public ResponseEntity<ApiResponse<List<PurchaseProductListDto>>> getPurchaseProduct(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(ApiResponse.success(profileService.getPurchaseProduct(user.getUserId())));
    }

    @GetMapping("/together/heart")
    public ResponseEntity<ApiResponse<List<TogetherDto>>> getLikesForTogether(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(ApiResponse.success(profileService.getLikesForTogether(user.getUserId(), pageable)));
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
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(ApiResponse.success(profileService.getTogetherOrder(user, pageable)));
    }
}
