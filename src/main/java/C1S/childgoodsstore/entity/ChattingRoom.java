package C1S.childgoodsstore.entity;

import C1S.childgoodsstore.enums.PRODUCT_CATEGORY;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    private int userCount; //채팅방 인원수

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "together_id", nullable = true)
    private Together together;

    private PRODUCT_CATEGORY category; //중고인지 공동인지 구분자

    public ChattingRoom(Product product) {
        this.userCount = 2;
        this.product = product;
        this.category = PRODUCT_CATEGORY.PRODUCT;
    }

    public ChattingRoom(Together together) {
        this.userCount = 2;
        this.together = together;
        this.category = PRODUCT_CATEGORY.TOGETHER;
    }

    public ChattingRoom() {

    }
}
