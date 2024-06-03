package C1S.childgoodsstore.chatting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class MessageDto { //채팅 보내기

    private Long chatRoomId;

    private String message;
}
