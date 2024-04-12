package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "chatting_room")
public class ChattingRoom {
    //https://dev-gorany.tistory.com/325
    //https://devsungwon.tistory.com/entry/Spring-%EC%86%8C%EC%BC%93%ED%86%B5%EC%8B%A0-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%B1%84%ED%8C%85-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-4-%EC%B1%84%ED%8C%85%EB%B0%A9-DB%EC%A0%80%EC%9E%A5
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    private String roomName; //채팅방 이름

    private Long userCount; //채팅방 인원수

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "together_id", nullable = true)
    private Together together;

    private String category; //중고인지 공동인지 구분자

    public ChattingRoom(String roomName, Long userCount, Product product, String category) {
        this.roomName = roomName;
        this.userCount = userCount;
        this.product = product;
        this.category = category;
    }

    public ChattingRoom(String roomName, Long userCount, Together together, String category) {
        this.roomName = roomName;
        this.userCount = userCount;
        this.together = together;
        this.category = category;
    }

    public ChattingRoom() {

    }
}
