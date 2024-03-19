package C1S.childgoodsstore.following.service;

import C1S.childgoodsstore.entity.Following;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.following.dto.FollowInterfaceDto;
import C1S.childgoodsstore.following.repository.FollowingRepository;
import C1S.childgoodsstore.user.repository.UserRepository;
import C1S.childgoodsstore.util.exception.CustomException;
import C1S.childgoodsstore.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowingService {

    private final FollowingRepository followingRepository;
    private final UserRepository userRepository;

    public List<FollowInterfaceDto> getFollower(Integer userId){

        Optional<User> user = userRepository.findByUserId(userId);

        if(user.isEmpty()){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return followingRepository.getFollower(userId);
    }

    public List<FollowInterfaceDto> getFollowing(Integer userId){

        Optional<User> user = userRepository.findByUserId(userId);

        if(user.isEmpty()){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return followingRepository.getFollowing(userId);
    }

    public void follow(Integer userId, Integer followId){

        Optional<Following> following = followingRepository.checkAlready(userId, followId);

        if(!following.isEmpty()){
            throw new CustomException(ErrorCode.FOLLOW_ALREADY);
        }

        followingRepository.follow(userId, followId);
    }

    public void unfollow(Integer userId, Integer followId){

        Optional<Following> following = followingRepository.checkAlready(userId, followId);

        if(following.isEmpty()){
            throw new CustomException(ErrorCode.UNFOLLOW_ALREADY);
        }

        followingRepository.unfollow(userId, followId);
    }

}
