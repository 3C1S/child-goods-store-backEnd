package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "chatting")
public class Chatting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chattingId;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChattingRoom chattingRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; //보낸 사람

    private String message;

    private LocalDateTime createdAt;

    public Chatting(ChattingRoom chattingRoom, User user, String message, LocalDateTime createdAt) {
        this.chattingRoom = chattingRoom;
        this.user = user;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Chatting() {

    }
}
