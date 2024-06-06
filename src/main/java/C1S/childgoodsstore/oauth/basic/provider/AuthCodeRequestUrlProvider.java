package C1S.childgoodsstore.oauth.basic.provider;


import C1S.childgoodsstore.oauth.basic.domain.OauthServerType;

public interface AuthCodeRequestUrlProvider {
    OauthServerType supportServer();
    String provide();
}
