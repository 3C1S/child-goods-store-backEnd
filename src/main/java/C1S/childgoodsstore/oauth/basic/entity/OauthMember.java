package C1S.childgoodsstore.oauth.basic.entity;

import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.enums.ROLE;
import C1S.childgoodsstore.oauth.basic.domain.OauthId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "oauth_member",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "oauth_id_unique",
                        columnNames = {
                                "oauth_server_id",
                                "oauth_server"
                        }
                ),
        }
)
public class OauthMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private OauthId oauthId;
    private String email;

    public Long id() {
        return id;
    }

    public OauthId oauthId() {
        return oauthId;
    }


    public User toUser(){
        return new User(email, oauthId.getOauthServerId(),oauthId.getOauthServerType(), ROLE.USER);
    }
}
