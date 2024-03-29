package C1S.childgoodsstore.user.dto;

import C1S.childgoodsstore.enums.ROLE;
import C1S.childgoodsstore.entity.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private Long userId;
    private String nickName;
    private String introduce;
    private String profileImg;
    private int followNum;
    private int followingNum;
    private double averageStars;

    public ProfileDto(User user, int followingNum, int followNum, double averageStars) {
        this.userId = user.getUserId();
        this.nickName = user.getNickName();
        this.introduce = user.getIntroduce();
        this.profileImg = user.getProfileImg();
    }
}