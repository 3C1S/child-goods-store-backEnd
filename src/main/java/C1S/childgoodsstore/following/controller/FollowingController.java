package C1S.childgoodsstore.following.controller;

import C1S.childgoodsstore.following.dto.FollowInterfaceDto;
import C1S.childgoodsstore.following.service.FollowingService;
import C1S.childgoodsstore.security.auth.PrincipalDetails;
import C1S.childgoodsstore.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowingController {

    private final FollowingService followingService;

    @GetMapping("/user/follower/{userId}")
    public ResponseEntity<ApiResponse<List<FollowInterfaceDto>>> getFollower(@PathVariable("userId") Long userId) {

        return ResponseEntity.ok(ApiResponse.success(followingService.getFollower(userId)));
    }

    @GetMapping("/user/following/{userId}")
    public ResponseEntity<ApiResponse<List<FollowInterfaceDto>>> getFollowing(@PathVariable("userId") Long userId) {

        return ResponseEntity.ok(ApiResponse.success(followingService.getFollowing(userId)));
    }

    @PostMapping("/user/follow/{followId}")
    public ResponseEntity<ApiResponse> follow(@AuthenticationPrincipal PrincipalDetails principalDetails,
                           @PathVariable("followId") Long followId){
        //followingService.follow(principalDetails.getUser().getUserId(), followId);
        followingService.follow(1L, followId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/user/unfollow/{followId}")
    public ResponseEntity<ApiResponse> unfollow(@AuthenticationPrincipal PrincipalDetails principalDetails,
                             @PathVariable("followId") Long followId){
        followingService.unfollow(principalDetails.getUser().getUserId(), followId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }


}
