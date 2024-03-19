package C1S.childgoodsstore.following.controller;

import C1S.childgoodsstore.address.dto.AddressInterfaceDto;
import C1S.childgoodsstore.following.dto.FollowInterfaceDto;
import C1S.childgoodsstore.entity.Response;
import C1S.childgoodsstore.following.service.FollowingService;
import C1S.childgoodsstore.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowingController {

    private final FollowingService followingService;

    @GetMapping("/user/follower/{userId}")
    public ResponseEntity<ApiResponse<List<FollowInterfaceDto>>> getFollower(@PathVariable("userId") Integer userId) {

        //존재하는 유저인지 체크해야 하나

        //List<FollowInterfaceDto> followerList = followingService.getFollower(userId);

        //Response response = new Response();

        //response.setCode(1000);
        //response.setMessage("팔로워 목록 조회에 성공하였습니다.");

        /*List<Map<String, Object>> followers = new ArrayList<>();

        for (FollowInterfaceDto follower : followerList) {
            Map<String, Object> userJson = new HashMap<>();
            userJson.put("user", follower);
            followers.add(userJson);
        }*/

        //response.setData(followerList);

        return ResponseEntity.ok(ApiResponse.success(followingService.getFollower(userId)));
    }

    @GetMapping("/user/following/{userId}")
    public ResponseEntity<ApiResponse<List<FollowInterfaceDto>>> getFollowing(@PathVariable("userId") Integer userId) {

        return ResponseEntity.ok(ApiResponse.success(followingService.getFollowing(userId)));
    }

    @PostMapping("/user/follow/{followId}")
    public ResponseEntity<ApiResponse> follow(
                           @PathVariable("followId") String followId){

        //user 받아오기

        followingService.follow(2, Integer.valueOf(followId));

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/user/unfollow/{followId}")
    public ResponseEntity<ApiResponse> unfollow(
                             @PathVariable("followId") String followId){

        //user 받아오기

        followingService.unfollow(2, Integer.valueOf(followId)); //임의로 userIdx 값 넣어 놓음.

        return ResponseEntity.ok(ApiResponse.success(null));
    }


}
