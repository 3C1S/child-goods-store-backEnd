package C1S.childgoodsstore.user.controller;

import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.jwt.domain.AuthToken;
import C1S.childgoodsstore.jwt.service.JWTService;
import C1S.childgoodsstore.user.dto.*;
import C1S.childgoodsstore.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import C1S.childgoodsstore.auth.presentation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import C1S.childgoodsstore.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JWTService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Long>> signUpUser(@Valid @RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok().body(ApiResponse.success(userService.save(signUpDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthToken>> signInUser(@RequestBody SignInDto signInDto) {
        final String email = signInDto.getEmail();
        final String password = signInDto.getPassword();

        User user = userService.login(email, password);

        AuthToken authToken = jwtService.createAuthToken(user.getUserId());
        userService.updateRefreshToken(user.getUserId(), authToken.getRefreshToken());

        return ResponseEntity.ok().body(ApiResponse.success(authToken));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<AutoLoginResultDto>> getMyInfo(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(userService.autoLogin(user.getUserId())));
    }

    @PostMapping("/user")
    public ResponseEntity<ApiResponse<InfoResultDto>> createMyInFo(@AuthenticationPrincipal User user, @RequestBody @Valid InfoSaveDto infoSaveDto) {
        return ResponseEntity.ok(ApiResponse.success(userService.saveInfo(user.getUserId(), infoSaveDto)));
    }

    @PatchMapping("/user")
    public ResponseEntity<ApiResponse<InfoResultDto>> updateMyInFo(@AuthenticationPrincipal User user, @RequestBody @Valid InfoSaveDto infoSaveDto) {
        return ResponseEntity.ok(ApiResponse.success(userService.saveInfo(user.getUserId(), infoSaveDto)));
    }

    @GetMapping("/user/profile")
    public ResponseEntity<ApiResponse<ProfileDto>> getMyProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(userService.getProfile(user.getUserId())));
    }

    @GetMapping("/user/profile/{userId}")
    public ResponseEntity<ApiResponse<ProfileDto>> getUserProfile(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(ApiResponse.success(userService.getProfile(userId)));
    }

    @DeleteMapping("/user")
    public ResponseEntity<ApiResponse> withdrawalUser(@AuthenticationPrincipal User user){
        userService.withdrawalUser(user);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}