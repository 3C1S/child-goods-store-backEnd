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
    private String region;
    private String town;
    private String state;

    @Column(name = "total_score")
    private Integer totalScore; //받은 리뷰 별점 총합

    @Column(name = "score_num")
    private Integer scoreNum; //받은 리뷰 개수
    @Enumerated(EnumType.STRING)

    private ROLE role;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> children;


    public User() {}

    public User(String email, String password, ROLE role){
        this.email = email;
        this.password = password;
        this.role = role;
        this.totalScore = 0;
        this.scoreNum = 0;
        setCreatedAt();
        setUpdatedAt();
    }

    public void setUser(InfoSaveDto infoSaveDto) {

        this.nickName = infoSaveDto.getNickName();
        this.profileImg = infoSaveDto.getProfileImg();
        this.introduce = infoSaveDto.getIntroduce();
        this.region = infoSaveDto.getRegion();
        this.town = infoSaveDto.getTown();
    }
}
