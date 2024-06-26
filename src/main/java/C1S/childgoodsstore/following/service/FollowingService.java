package C1S.childgoodsstore.following.service;

import C1S.childgoodsstore.entity.Following;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.following.dto.FollowInterfaceDto;
import C1S.childgoodsstore.following.repository.FollowingRepository;
import C1S.childgoodsstore.user.repository.UserRepository;
import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowingService {

    private final FollowingRepository followingRepository;
    private final UserRepository userRepository;

    public List<FollowInterfaceDto> getFollower(Long userId, int page, int size){

        Optional<User> user = userRepository.findByUserId(userId);

        if(user.isEmpty()){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Pageable pageable = PageRequest.of(page, size);
        return followingRepository.getFollower(userId, pageable);
    }

    public List<FollowInterfaceDto> getFollowing(Long userId, int page, int size){

        Optional<User> user = userRepository.findByUserId(userId);

        if(user.isEmpty()){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Pageable pageable = PageRequest.of(page, size);
        return followingRepository.getFollowing(userId, pageable);
    }

    public void follow(Long userId, Long followId){

        Optional<Following> following = followingRepository.checkAlready(userId, followId);

        if(!following.isEmpty()){
            throw new CustomException(ErrorCode.FOLLOW_ALREADY);
        }

        Following following1 = new Following();
        following1.setUser(userRepository.findByUserId(userId).get());
        following1.setFollowId(followId);

        followingRepository.save(following1);
    }

    public void unfollow(Long userId, Long followId){

        Optional<Following> following = followingRepository.checkAlready(userId, followId);

        if(following.isEmpty()){
            throw new CustomException(ErrorCode.UNFOLLOW_ALREADY);
        }

        Following following1 = following.get();
        followingRepository.delete(following1);
    }

}
