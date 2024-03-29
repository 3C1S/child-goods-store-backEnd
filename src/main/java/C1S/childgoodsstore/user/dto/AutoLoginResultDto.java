package C1S.childgoodsstore.user.dto;

import C1S.childgoodsstore.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AutoLoginResultDto {

    private Long userId;
    private String nickName;
    private String introduce;
    private String profileImg;
    private String phoneNum;
    private String region;
    private String town;
    private LocalDateTime createAt;

    public AutoLoginResultDto() {}

    public AutoLoginResultDto(User user) {

        this.userId = user.getUserId();
        this.nickName = user.getNickName();
        this.introduce = user.getIntroduce();
        this.profileImg = user.getProfileImg();
        this.phoneNum = user.getPhone();
        this.region = user.getRegion();
        this.town = user.getTown();
        this.createAt = user.getCreatedAt();
    }
}
