package C1S.childgoodsstore.entity;

import C1S.childgoodsstore.enums.ROLE;
import C1S.childgoodsstore.user.dto.InfoSaveDto;
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
    @Column(name = "profile_img")
    private String profileImg;
    private String phone;
    private String region;
    private String town;
    private String state;

    @Column(name = "total_score")
    private Integer totalScore = 0; //받은 리뷰 별점 총합

    @Column(name = "score_num")
    private Integer scoreNum = 0; //받은 리뷰 개수
    @Enumerated(EnumType.STRING)

    private ROLE role;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> children;


    public User() {}

    public User(String email, String password, String phone, ROLE role){
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        setCreatedAt();
        setUpdatedAt();
    }

    public void setUser(InfoSaveDto infoSaveDto) {

        this.nickName = infoSaveDto.getNickName();
        this.profileImg = infoSaveDto.getProfileImg();
        this.introduce = infoSaveDto.getIntroduce();
        this.phone = infoSaveDto.getPhoneNum();
        this.region = infoSaveDto.getRegion();
        this.town = infoSaveDto.getTown();
    }
}
