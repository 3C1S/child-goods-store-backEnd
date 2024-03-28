package C1S.childgoodsstore.user.dto;

import C1S.childgoodsstore.entity.User;
import lombok.Getter;

@Getter
public class InfoResultDto {

    private int userId;
    private String nickName;
    private String profileImg;
    private String introduce;
    private String phoneNum;
    private String region;
    private String town;

    public InfoResultDto() {}
    public InfoResultDto(User user) {

        this.userId = user.getUserId();
        this.nickName = user.getNickName();
        this.profileImg = user.getProfileImg();
        this.introduce = user.getIntroduce();
        this.phoneNum = user.getPhone();
        this.region = user.getRegion();
        this.town = user.getTown();
    }
}
