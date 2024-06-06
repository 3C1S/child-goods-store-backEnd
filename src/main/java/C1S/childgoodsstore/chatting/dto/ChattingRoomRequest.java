package C1S.childgoodsstore.chatting.dto;

import C1S.childgoodsstore.enums.PRODUCT_CATEGORY;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ChattingRoomRequest {
    @NotNull
    private Long id; // 상품 id (productId or togetherId)

    @NotNull
    private PRODUCT_CATEGORY category; // 상품 타입 (PRODUCT or TOGETHER)

    public ChattingRoomRequest() {}

    public ChattingRoomRequest(Long id, PRODUCT_CATEGORY category) {

        this.id = id;
        this.category = category;
    }
}
