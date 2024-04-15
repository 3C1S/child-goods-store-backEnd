package C1S.childgoodsstore.product.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseProspectDto {
    private Long userId;
    private String nickName;
    private String profileImg;

    public PurchaseProspectDto(Long userId, String nickName, String profileImg) {
        this.userId = userId;
        this.nickName = nickName;
        this.profileImg = profileImg;
    }
}
