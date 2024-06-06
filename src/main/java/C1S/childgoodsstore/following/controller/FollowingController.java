package C1S.childgoodsstore.following.controller;

import C1S.childgoodsstore.auth.presentation.AuthenticationPrincipal;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.following.dto.FollowInterfaceDto;
import C1S.childgoodsstore.following.service.FollowingService;
import C1S.childgoodsstore.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowingController {

    private final FollowingService followingService;

    @GetMapping("/user/follower/{userId}")
    public ResponseEntity<ApiResponse<List<FollowInterfaceDto>>> getFollower(@PathVariable("userId") Long userId,
                                                                             @RequestParam(name = "page", defaultValue = "0") int page,
                                                                             @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(followingService.getFollower(userId, page, size)));
    }

    @GetMapping("/user/following/{userId}")
    public ResponseEntity<ApiResponse<List<FollowInterfaceDto>>> getFollowing(@PathVariable("userId") Long userId,
                                                                              @RequestParam(name = "page", defaultValue = "0") int page,
                                                                              @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(followingService.getFollowing(userId, page, size)));
    }

    @PostMapping("/user/follow/{followId}")
    public ResponseEntity<ApiResponse> follow(@C1S.childgoodsstore.auth.presentation.AuthenticationPrincipal User user,
                           @PathVariable("followId") Long followId){
        followingService.follow(user.getUserId(), followId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/user/unfollow/{followId}")
    public ResponseEntity<ApiResponse> unfollow(@AuthenticationPrincipal User user,
                             @PathVariable("followId") Long followId){
        followingService.unfollow(user.getUserId(), followId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }


}
