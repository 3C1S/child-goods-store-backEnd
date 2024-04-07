package C1S.childgoodsstore.profile.dto;

import C1S.childgoodsstore.entity.Product;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class PurchaseProductListDto {

    private String sellerName;
    private Long productId;
    private String productName;
    private int price;
    private LocalDateTime saleCompleteDate;
    private boolean isReview;

    public PurchaseProductListDto(Product product, LocalDateTime saleCompleteDate) {

        this.sellerName = product.getUser().getNickName();
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.saleCompleteDate = saleCompleteDate;
    }
}
