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

    //내 정보 조회(자동로그인)
    @GetMapping("/user")
    public ResponseEntity<ApiResponse<AutoLoginResultDto>> getMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(ApiResponse.success(userService.autoLogin(principalDetails.getUser().getUserId())));
    }

    //내 정보(프로필) 작성
    @PostMapping("/user")
    public ResponseEntity<ApiResponse<InfoResultDto>> createMyInFo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                   @RequestBody @Valid InfoSaveDto infoSaveDto) {
        return ResponseEntity.ok(ApiResponse.success(userService.saveInfo(principalDetails.getUser().getUserId(), infoSaveDto)));
    }

    //내 정보(프로필) 수정
    @PatchMapping("/user")
    public ResponseEntity<ApiResponse<InfoResultDto>> updateMyInFo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                   @RequestBody @Valid InfoSaveDto infoSaveDto) {
        return ResponseEntity.ok(ApiResponse.success(userService.saveInfo(principalDetails.getUser().getUserId(), infoSaveDto)));
    }

    //내 프로필 조회
    @GetMapping("/user/profile")
    public ResponseEntity<ApiResponse<MyProfileDto>> getMyProfile(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(ApiResponse.success(userService.getMyProfile(principalDetails.getUser().getUserId())));
    }

    //타 유저의 프로필 조회
    @GetMapping("/user/profile/{userId}")
    public ResponseEntity<ApiResponse<UserProfileDto>> getUserProfile(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                      @PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(ApiResponse.success(userService.getUserProfile(principalDetails.getUser().getUserId(), userId)));
    }

    @DeleteMapping("/user")
    public ResponseEntity<ApiResponse> withdrawalUser(@AuthenticationPrincipal PrincipalDetails principalDetails){
        userService.withdrawalUser(principalDetails.getUser());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}