package C1S.childgoodsstore.user.controller;

import C1S.childgoodsstore.security.auth.PrincipalDetails;
import C1S.childgoodsstore.user.dto.*;
import C1S.childgoodsstore.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import C1S.childgoodsstore.global.response.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Long>> signUpUser(@Valid @RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok().body(ApiResponse.success(userService.save(signUpDto)));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<AutoLoginResultDto>> getMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(ApiResponse.success(userService.autoLogin(principalDetails.getUser().getUserId())));
    }

    @PostMapping("/user")
    public ResponseEntity<ApiResponse<InfoResultDto>> createMyInFo(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Valid InfoSaveDto infoSaveDto) {
        return ResponseEntity.ok(ApiResponse.success(userService.saveInfo(principalDetails.getUser().getUserId(), infoSaveDto)));
    }

    @PatchMapping("/user")
    public ResponseEntity<ApiResponse<InfoResultDto>> updateMyInFo(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Valid InfoSaveDto infoSaveDto) {
        return ResponseEntity.ok(ApiResponse.success(userService.saveInfo(principalDetails.getUser().getUserId(), infoSaveDto)));
    }

    @GetMapping("/user/profile")
    public ResponseEntity<ApiResponse<ProfileDto>> getMyProfile(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(ApiResponse.success(userService.getProfile(principalDetails.getUser().getUserId())));
    }

    @GetMapping("/user/profile/{userId}")
    public ResponseEntity<ApiResponse<ProfileDto>> getUserProfile(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(ApiResponse.success(userService.getProfile(userId)));
    }

    @DeleteMapping("/user")
    public ResponseEntity<ApiResponse> withdrawalUser(@AuthenticationPrincipal PrincipalDetails principalDetails){
        userService.withdrawalUser(principalDetails.getUser());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}