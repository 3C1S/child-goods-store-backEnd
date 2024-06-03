package C1S.childgoodsstore.chatting.dto;

import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.enums.PRODUCT_CATEGORY;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChattingRoomList { //채팅방 목록 조회

    private Long chatRoomId; //채팅방 아이디

    private PRODUCT_CATEGORY category; //중고인지 공동인지 구분자

    private Long id; // 상품 id (productId or togetherId)

    private String productName;

    @Nullable
    private String productImage;

    private int participantsNum; //채팅방 인원수

    private int price;

    @Nullable
    private Integer unitPrice = null; //totalprice/participantNum

    @Nullable
    private LocalDateTime endDate = null;

    @Nullable
    private String message; //가장 마지막에 온 메시지

    @Nullable
    private LocalDateTime createdAt; //마지막 채팅 시간

    public ChattingRoomList(Long chatRoomId, PRODUCT_CATEGORY category, Long productId, String productName, @Nullable String productImage,
                            int participantsNum, int price, String message, LocalDateTime createdAt) {
        this.chatRoomId = chatRoomId;
        this.category = category;
        this.id = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.participantsNum = participantsNum;
        this.price = price;
        this.message = message;
        this.createdAt = createdAt;
    }

    public ChattingRoomList(Long chatRoomId, PRODUCT_CATEGORY category, Long togetherId, String togetherName, @Nullable String togetherImage,
                            int participantsNum, int price, int unitPrice,
                            LocalDateTime endDate, String message, LocalDateTime createdAt) {
        this.chatRoomId = chatRoomId;
        this.category = category;
        this.id = togetherId;
        this.productName = togetherName;
        this.productImage = togetherImage;
        this.participantsNum = participantsNum;
        this.price = price;
        this.unitPrice = unitPrice;
        this.endDate = endDate;
        this.message = message;
        this.createdAt = createdAt;
    }

    public ChattingRoomList(Long chatRoomId, PRODUCT_CATEGORY category, Long productId, String productName, @Nullable String productImage,
                            int participantsNum, int price) {
        this.chatRoomId = chatRoomId;
        this.category = category;
        this.id = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.participantsNum = participantsNum;
        this.price = price;
    }

    public ChattingRoomList(Long chatRoomId, PRODUCT_CATEGORY category, Long togetherId, String togetherName, @Nullable String togetherImage,
                            int participantsNum, int price, int unitPrice, LocalDateTime endDate) {
        this.chatRoomId = chatRoomId;
        this.category = category;
        this.id = togetherId;
        this.productName = togetherName;
        this.productImage = togetherImage;
        this.participantsNum = participantsNum;
        this.price = price;
        this.unitPrice = unitPrice;
        this.endDate = endDate;
    }
}
