package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.font.TextHitInfo;

@Entity
@Getter
@Setter
@ToString
@Table(name = "chatting_room_user")
public class ChattingRoomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomUserId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChattingRoom chattingRoom;

    private Boolean isLeader; //주최자 혹은 판매자 인지 아닌지

    public ChattingRoomUser(User user, ChattingRoom chattingRoom, boolean isLeader) {
        this.user = user;
        this.chattingRoom = chattingRoom;
        this.isLeader = isLeader;
    }

    public ChattingRoomUser() {

    }
}
