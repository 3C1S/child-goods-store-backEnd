package C1S.childgoodsstore.oauth.basic.controller;

import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.global.response.ApiResponse;
import C1S.childgoodsstore.jwt.domain.AuthToken;
import C1S.childgoodsstore.jwt.service.JWTService;
import C1S.childgoodsstore.oauth.basic.domain.OauthServerType;
import C1S.childgoodsstore.oauth.basic.service.OauthService;
import C1S.childgoodsstore.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OauthController {

    private final OauthService oauthService;
    private final UserService userService;
    private final JWTService jwtService;

    @Value("${jwt.access.header}")
    private static String ACCESS_TOKEN_HEADER;

    @Value("${jwt.refresh.header}")
    private static String REFRESH_TOKEN_HEADER;

    @SneakyThrows
    @GetMapping("/{oauthServerType}")
    ResponseEntity<Void> redirectAuthCodeRequestUrl(
            @PathVariable OauthServerType oauthServerType,
            HttpServletResponse response
    ) {
        String redirectUrl = oauthService.getAuthCodeRequestUrl(oauthServerType);
        response.sendRedirect(redirectUrl);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/{oauthServerType}")
    public String login(
            @PathVariable OauthServerType oauthServerType,
            @RequestParam("code") String code
            ) {
        User user = oauthService.login(oauthServerType, code);

        AuthToken authToken = jwtService.createAuthToken(user.getUserId());
        userService.updateRefreshToken(user.getUserId(), authToken.getRefreshToken());

        return "kiddymarket://kiddymarket.shop/oauth/login/kakao"+code;
    }
}
