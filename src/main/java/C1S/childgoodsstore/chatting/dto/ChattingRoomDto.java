package C1S.childgoodsstore.chatting.dto;

import C1S.childgoodsstore.entity.User;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChattingRoomDto { //채팅방 상세 조회
    @Nullable
    private UserDto user;
    private String message;
    private Boolean isLeader;
    private LocalDateTime createdAt; //전송 시간

    public ChattingRoomDto(@Nullable User user, String message, LocalDateTime createdAt) {
        if(user!=null){
            this.user = new UserDto(user.getUserId(), user.getNickName(), user.getProfileImg());
        }
        else this.user = null;

        this.message = message;
        this.isLeader = false;
        this.createdAt = createdAt;
    }
}
