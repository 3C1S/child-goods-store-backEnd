package C1S.childgoodsstore.auth.presentation;


import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import C1S.childgoodsstore.jwt.service.JWTService;
import C1S.childgoodsstore.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
@RequiredArgsConstructor
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final JWTService jwtService;
    private final UserService userService;
    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        AuthenticationPrincipal annotation = parameter.getParameterAnnotation(AuthenticationPrincipal.class);
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        if(request.getHeader(HttpHeaders.AUTHORIZATION) == null  && annotation.required() == false) {
            return null;
        }

        String accessToken = AuthorizationExtractor.extract(request);

        jwtService.validateToken(accessToken);
        Long id = jwtService.extractId(accessToken).orElseThrow(
                () -> {
                    throw new CustomException(ErrorCode.INVALID_TOKEN_ID);
                }
        );

        return userService.getById(id);
    }
}
