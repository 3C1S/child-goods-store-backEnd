package C1S.childgoodsstore.chatting.dto;

import C1S.childgoodsstore.enums.PRODUCT_CATEGORY;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomInfo {

    private Long id;

    private PRODUCT_CATEGORY category;

    public ChatRoomInfo(Long id, PRODUCT_CATEGORY category) {
        this.id = id;
        this.category = category;
    }
}
