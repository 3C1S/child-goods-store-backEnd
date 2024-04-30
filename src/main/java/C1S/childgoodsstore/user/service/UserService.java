package C1S.childgoodsstore.user.service;

import C1S.childgoodsstore.entity.Following;
import C1S.childgoodsstore.enums.ROLE;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.following.repository.FollowingRepository;
import C1S.childgoodsstore.user.dto.*;
import C1S.childgoodsstore.user.repository.UserRepository;
import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowingRepository followingRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private User getUserById(Long userId) {
        User user;
        try{
            user = userRepository.findByUserId(userId).get();
        } catch (RuntimeException e) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    public Long save(SignUpDto signUpDto) {

        Optional<User> findUser = userRepository.findByEmail(signUpDto.getEmail());

        if(!findUser.isEmpty())
            throw new CustomException(ErrorCode.USER_EMAIL_DUPLICATED);

        User savedUser = userRepository.saveAndFlush(new User(signUpDto.getEmail(), bCryptPasswordEncoder.encode(signUpDto.getPassword()), ROLE.USER));

        return savedUser.getUserId();
    }

    public AutoLoginResultDto autoLogin(Long userId) {

        User user = getUserById(userId);
        AutoLoginResultDto autoLoginResultDto = new AutoLoginResultDto(user);
        return autoLoginResultDto;
    }

    public MyProfileDto getMyProfile(Long userId) {

        User user = getUserById(userId);
        int followNum = 0, followingNum = 0;
        double averageStars = 0.0;

        followNum = userRepository.countFollowersByUserId(userId);
        followingNum = userRepository.countFollowingsByUserId(userId);
        if (user.getScoreNum() != null) {
            averageStars = user.getTotalScore() / (double) user.getScoreNum();
        }

        MyProfileDto myProfileDto = new MyProfileDto(user, followingNum, followNum, averageStars);
        return myProfileDto;
    }

    public UserProfileDto getUserProfile(Long myUserId, Long userId) {

        User user = getUserById(userId);
        int followNum = 0, followingNum = 0;
        double averageStars = 0.0;
        boolean isFollowed = false;

        followNum = userRepository.countFollowersByUserId(userId);
        followingNum = userRepository.countFollowingsByUserId(userId);
        if (user.getScoreNum() != null) {
            averageStars = user.getTotalScore() / (double) user.getScoreNum();
        }

        Optional<Following> following = followingRepository.checkAlready(myUserId, userId);
        if(!following.isEmpty()) {
            isFollowed = true;
        }

        UserProfileDto userProfileDto = new UserProfileDto(user, isFollowed, followingNum, followNum, averageStars);
        return userProfileDto;
    }

    public InfoResultDto saveInfo(Long userId, InfoSaveDto infoSaveDto) {

        User user = getUserById(userId);
        user.setUser(infoSaveDto);
        InfoResultDto infoResultDto = new InfoResultDto(user);
        return infoResultDto;
    }
}
