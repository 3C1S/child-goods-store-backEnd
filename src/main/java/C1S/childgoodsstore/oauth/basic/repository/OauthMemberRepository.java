package C1S.childgoodsstore.oauth.basic.repository;

import C1S.childgoodsstore.oauth.basic.domain.OauthId;
import C1S.childgoodsstore.oauth.basic.entity.OauthMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthMemberRepository extends JpaRepository<OauthMember, Long>{
    Optional<OauthMember> findByOauthId(OauthId oauthId);
}
