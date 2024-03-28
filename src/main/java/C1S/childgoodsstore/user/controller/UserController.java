package C1S.childgoodsstore.user.controller;

import C1S.childgoodsstore.security.auth.PrincipalDetails;
import C1S.childgoodsstore.user.dto.InfoResultDto;
import C1S.childgoodsstore.user.dto.InfoSaveDto;
import C1S.childgoodsstore.user.dto.SignUpDto;
import C1S.childgoodsstore.user.dto.ProfileDto;
import C1S.childgoodsstore.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import C1S.childgoodsstore.util.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Integer>> signUpUser(@Valid @RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok().body(ApiResponse.success(userService.save(signUpDto)));
    }

//    @GetMapping("")
//    public ResponseEntity<ApiResponse<Long>> getMyInfo() {
//        return ResponseEntity.ok().body(ApiResponse.success());
//    }
//
    @PostMapping("")
    public ResponseEntity<ApiResponse<InfoResultDto>> createMyInFo(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Valid InfoSaveDto infoSaveDto) {
        return ResponseEntity.ok(ApiResponse.success(userService.saveInfo(1, infoSaveDto)));
        //return ResponseEntity.ok(ApiResponse.success(userService.saveInfo(principalDetails.getUser().getUserId(), infoSaveDto)));
    }

    @PatchMapping("")
    public ResponseEntity<ApiResponse<InfoResultDto>> updateMyInFo(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Valid InfoSaveDto infoSaveDto) {
        return ResponseEntity.ok(ApiResponse.success(userService.saveInfo(1, infoSaveDto)));
        //return ResponseEntity.ok(ApiResponse.success(userService.saveInfo(principalDetails.getUser().getUserId(), infoSaveDto)));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileDto>> getMyProfile(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(ApiResponse.success(userService.getProfile(1)));
        //return ResponseEntity.ok(ApiResponse.success(userService.getProfile(principalDetails.getUser().getUserId())));
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse<ProfileDto>> getUserProfile(@PathVariable int userId) {
        return ResponseEntity.ok().body(ApiResponse.success(userService.getProfile(userId)));
    }
}
