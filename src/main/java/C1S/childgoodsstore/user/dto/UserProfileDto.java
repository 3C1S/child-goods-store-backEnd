package C1S.childgoodsstore.user.dto;

import C1S.childgoodsstore.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserProfileDto {

    private Long userId;
    private String nickName;
    private String profileImg;
    private String introduce;

    @JsonProperty("isFollowed")
    private boolean isFollowed;

    private int followNum;
    private int followingNum;
    private double averageStars;
    private LocalDateTime createdAt;

    public UserProfileDto(User user, boolean isFollowed, int followingNum, int followerNum) {
        this.userId = user.getUserId();
        this.nickName = user.getNickName();
        this.profileImg = user.getProfileImg();
        this.introduce = user.getIntroduce();
        this.isFollowed = isFollowed;
        this.followNum = followerNum;
        this.followingNum = followingNum;
        this.createdAt = user.getCreatedAt();
        if(user.getScoreNum() == 0) {
            this.averageStars = 0;
        }
        else {
            this.averageStars = Math.round(((double) user.getTotalScore() / user.getScoreNum()) * 10.0) / 10.0;
        }
    }
}
