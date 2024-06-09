package C1S.childgoodsstore.chatting.handler;

import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import C1S.childgoodsstore.security.auth.PrincipalDetails;
import C1S.childgoodsstore.security.jwt.JwtProperties;
import C1S.childgoodsstore.user.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {
    private final UserRepository userRepository;

    // websocket을 통해 들어온 요청이 처리되기전 실행된다.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT == headerAccessor.getCommand()) { // websocket 연결요청
            String accessToken = headerAccessor.getFirstNativeHeader("Authorization");
            String token = accessToken.replace(JwtProperties.TOKEN_PREFIX, "");

            try {

                String userEmail = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                        .getClaim("email").asString();
                User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
//                PrincipalDetails principalDetails = new PrincipalDetails(user);
//                Authentication authentication = new UsernamePasswordAuthenticationToken(
//                        principalDetails, null,
//                        principalDetails.getAuthorities());
//                headerAccessor.setUser(authentication);

                headerAccessor.getSessionAttributes().put("user", user);

            }catch (TokenExpiredException e) {
                e.printStackTrace();
            } catch (JWTVerificationException e) {
                e.printStackTrace();
            }
        }
        return message;
    }
}
