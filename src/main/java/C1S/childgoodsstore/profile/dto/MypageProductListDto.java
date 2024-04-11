package C1S.childgoodsstore.profile.dto;

import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.enums.PRODUCT_SALE_STATUS;
import lombok.Getter;

@Getter
public class MypageProductListDto {

    private Long productId;
    private String productName;
    private int price;
    private PRODUCT_SALE_STATUS state;
    private String productImg;
    private boolean productHeart;

    public MypageProductListDto() {}

    public MypageProductListDto(Product product, String productImg, boolean productHeart) {

        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.state = product.getState();
        this.productImg = productImg;
        this.productHeart = productHeart;
    }
}
