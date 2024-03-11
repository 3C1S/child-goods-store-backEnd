package C1S.childgoodsstore.security.auth;

import lombok.RequiredArgsConstructor;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// /login 일 때 동작
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User userEntity = userRepository.findByEmail(userEmail).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found with email: " + userEmail);
        });

        return new PrincipalDetails(userEntity);
    }
}
