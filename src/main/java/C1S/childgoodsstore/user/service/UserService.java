package C1S.childgoodsstore.user.service;

import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findByUserId(Integer userId){
        return userRepository.findByUserId(userId);
    }
}
