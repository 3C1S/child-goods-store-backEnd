package C1S.childgoodsstore.oauth.basic.service;

import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.oauth.basic.domain.OauthServerType;
import C1S.childgoodsstore.oauth.basic.domain.client.OauthMemberClientComposite;
import C1S.childgoodsstore.oauth.basic.entity.OauthMember;
import C1S.childgoodsstore.oauth.basic.provider.AuthCodeRequestUrlProviderComposite;
import C1S.childgoodsstore.oauth.basic.repository.OauthMemberRepository;
import C1S.childgoodsstore.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final OauthMemberRepository oauthMemberRepository;
    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final UserService userService;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }
@Transactional
    public User login(OauthServerType oauthServerType, String authCode) {
        OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType, authCode);

        User user = userService.findByOauthServerIdAndSocial(
                        oauthMember.oauthId().getOauthServerId(),
                        oauthServerType.toString())
                .orElseGet(() -> userService.save(oauthMember.toUser()));

        return user;
    }
}
