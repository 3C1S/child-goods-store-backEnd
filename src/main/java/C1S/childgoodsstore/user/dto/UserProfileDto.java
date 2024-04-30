package C1S.childgoodsstore.user.dto;

import C1S.childgoodsstore.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private Long userId;
    private String nickName;
    private String introduce;
    private boolean isFollowed;
    private String profileImg;
    private int followNum;
    private int followingNum;
    private double averageStars;

    public UserProfileDto(User user, boolean isFollowed, int followingNum, int followNum, double averageStars) {
        this.userId = user.getUserId();
        this.nickName = user.getNickName();
        this.introduce = user.getIntroduce();
        this.profileImg = user.getProfileImg();
        this.isFollowed = isFollowed;
        this.followNum = followNum;
        this.followingNum = followingNum;
        this.averageStars = averageStars;
    }
}
