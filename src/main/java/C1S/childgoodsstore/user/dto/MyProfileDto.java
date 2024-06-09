package C1S.childgoodsstore.user.dto;

import C1S.childgoodsstore.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyProfileDto {

    private Long userId;
    private String nickName;
    private String profileImg;
    private String introduce;
    private int followNum;
    private int followingNum;
    private double averageStars;
    private LocalDateTime createdAt;

    public MyProfileDto(User user, int followingNum, int followerNum) {
        this.userId = user.getUserId();
        this.nickName = user.getNickName();
        this.profileImg = user.getProfileImg();
        this.introduce = user.getIntroduce();
        this.createdAt = user.getCreatedAt();
        this.followNum = followerNum;
        this.followingNum = followingNum;
        if(user.getScoreNum() == 0) {
            this.averageStars = 0;
        }
        else {
            this.averageStars = Math.round(((double) user.getTotalScore() / user.getScoreNum()) * 10.0) / 10.0;
        }
    }
}