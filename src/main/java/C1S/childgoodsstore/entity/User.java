package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String email;
    private String password;
    private String nickName;
    private String introduce;
    private String profileImg;
    private String phoneNum;
    private String region;
    private String town;
    private String state;
    private Integer totalScore;
    private Integer scoreNum;
}
