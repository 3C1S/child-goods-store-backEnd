package C1S.childgoodsstore.child.dto;

import C1S.childgoodsstore.enums.PRODUCT_SALE_STATUS;
import lombok.Getter;

@Getter
public class RecommendProductDto {

    private Long productId;
    private String productName;
    private int price;
    private PRODUCT_SALE_STATUS state;
    private String productImage;
    private boolean productHeart;
}
