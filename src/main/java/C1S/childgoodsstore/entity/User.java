package C1S.childgoodsstore.entity;

import C1S.childgoodsstore.enums.ROLE;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private String phone;
    private String region;
    private String town;
    private String state;
    private Integer totalScore;
    private Integer scoreNum;
    @Enumerated(EnumType.STRING)
    private ROLE role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> children;
    public User() {

    }

    public User(String email, String password, String phone, ROLE role){
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        setCreatedAt();
        setUpdatedAt();
    }


}
