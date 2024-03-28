package C1S.childgoodsstore.user.service;

import C1S.childgoodsstore.entity.Following;
import C1S.childgoodsstore.enums.ROLE;
import C1S.childgoodsstore.entity.Child;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.user.dto.InfoResultDto;
import C1S.childgoodsstore.user.dto.InfoSaveDto;
import C1S.childgoodsstore.user.dto.ProfileDto;
import C1S.childgoodsstore.user.dto.SignUpDto;
import C1S.childgoodsstore.user.repository.UserRepository;
import C1S.childgoodsstore.util.exception.CustomException;
import C1S.childgoodsstore.util.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public int save(SignUpDto signUpDto) {

        Optional<User> findUser = userRepository.findByEmail(signUpDto.getEmail());

        if(!findUser.isEmpty())
            throw new CustomException(ErrorCode.USER_EMAIL_DUPLICATED);

        User savedUser = userRepository.saveAndFlush(new User(signUpDto.getEmail(), bCryptPasswordEncoder.encode(signUpDto.getPassword()), signUpDto.getPhone(), ROLE.USER));

        return savedUser.getUserId();
    }

    public ProfileDto getProfile(int userId) {

        User user;
        int followNum, followingNum;
        double averageStars;
        try{
            user = userRepository.findByUserId(userId).get();
            followNum = userRepository.countFollowersByUserId(userId);
            followingNum = userRepository.countFollowingsByUserId(userId);
            averageStars = user.getTotalScore() / (double)user.getScoreNum();
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
        ProfileDto profileDto = new ProfileDto(user, followingNum, followNum, averageStars);
        return profileDto;
    }

    @Transactional
    public InfoResultDto saveInfo(int userId, InfoSaveDto infoSaveDto) {

        User user;
        try{
            user = userRepository.findByUserId(userId).get();
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }

        user.setUser(infoSaveDto);
        InfoResultDto infoResultDto = new InfoResultDto(user);
        return infoResultDto;
    }
}
