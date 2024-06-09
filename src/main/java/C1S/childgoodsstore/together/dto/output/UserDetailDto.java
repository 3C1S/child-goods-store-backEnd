package C1S.childgoodsstore.together.dto.output;

import lombok.Getter;
import lombok.Setter;
import C1S.childgoodsstore.entity.User;

@Getter
@Setter
public class UserDetailDto {

    private Long userIdx;
    private String nickName;
    private String profileImg;
    private double averageStars;

    public UserDetailDto() {}

    public UserDetailDto(User user) {

        this.userIdx = user.getUserId();
        this.nickName = user.getNickName();
        this.profileImg = user.getProfileImg();
        if(user.getScoreNum() == 0) {
            this.averageStars = 0;
        }
        else {
            this.averageStars = user.getTotalScore() / user.getScoreNum();
        }
    }
}
