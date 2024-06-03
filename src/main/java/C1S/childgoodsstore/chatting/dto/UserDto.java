package C1S.childgoodsstore.chatting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long userId;

    private String nickName;

    private String profileImg;

    public UserDto(Long userId, String nickName, String profileImg) {
        this.userId = userId;
        this.nickName = nickName;
        this.profileImg = profileImg;
    }
}
