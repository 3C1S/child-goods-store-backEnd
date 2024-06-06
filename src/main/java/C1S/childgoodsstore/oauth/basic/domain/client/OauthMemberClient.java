package C1S.childgoodsstore.oauth.basic.domain.client;


import C1S.childgoodsstore.oauth.basic.domain.OauthServerType;
import C1S.childgoodsstore.oauth.basic.entity.OauthMember;

public interface OauthMemberClient {
    OauthServerType supportServer();

    OauthMember fetch(String code);
}
