package C1S.childgoodsstore.user.service;

import C1S.childgoodsstore.enums.ROLE;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.user.dto.*;
import C1S.childgoodsstore.user.repository.UserRepository;
import C1S.childgoodsstore.util.exception.CustomException;
import C1S.childgoodsstore.util.exception.ErrorCode;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(SignUpDto signUpDto) {

        Optional<User> findUser = userRepository.findByEmail(signUpDto.getEmail());

        if(!findUser.isEmpty())
            throw new CustomException(ErrorCode.USER_EMAIL_DUPLICATED);

        User savedUser = userRepository.saveAndFlush(new User(signUpDto.getEmail(), bCryptPasswordEncoder.encode(signUpDto.getPassword()), signUpDto.getPhone(), ROLE.USER));

        return savedUser.getUserId();
    }

    public AutoLoginResultDto autoLogin(Long userId) {

        User user;

        try{
            user = userRepository.findByUserId(userId).get();
        } catch (RuntimeException e) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        AutoLoginResultDto autoLoginResultDto = new AutoLoginResultDto(user);
        return autoLoginResultDto;
    }

    public ProfileDto getProfile(Long userId) {

        User user;
        int followNum = 0, followingNum = 0;
        double averageStars = 0.0;

        try{
            user = userRepository.findByUserId(userId).get();
        } catch (RuntimeException e) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        followNum = userRepository.countFollowersByUserId(userId);
        followingNum = userRepository.countFollowingsByUserId(userId);
        if (user.getScoreNum() != null) {
            averageStars = user.getTotalScore() / (double) user.getScoreNum();
        }

        ProfileDto profileDto = new ProfileDto(user, followingNum, followNum, averageStars);
        return profileDto;
    }

    public InfoResultDto saveInfo(Long userId, InfoSaveDto infoSaveDto) {

        User user;

        try{
            user = userRepository.findByUserId(userId).get();
        } catch (RuntimeException e) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        user.setUser(infoSaveDto);
        InfoResultDto infoResultDto = new InfoResultDto(user);
        return infoResultDto;
    }
}
