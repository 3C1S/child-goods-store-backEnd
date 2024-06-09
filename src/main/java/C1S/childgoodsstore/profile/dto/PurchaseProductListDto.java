package C1S.childgoodsstore.profile.dto;

import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.enums.MAIN_CATEGORY;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class PurchaseProductListDto {

    private Long id;
    private String sellerName;
    private String title;
    private int price;
    private LocalDateTime saleCompleteDate;
    private String image;
    private boolean isReview;
    private MAIN_CATEGORY category;

    public PurchaseProductListDto(Product product, String image, LocalDateTime saleCompleteDate, boolean isReview) {

        this.id = product.getProductId();
        this.sellerName = product.getUser().getNickName();
        this.title = product.getProductName();
        this.price = product.getPrice();
        this.category = product.getMainCategory();
        this.saleCompleteDate = saleCompleteDate;
        this.image = image;
        this.isReview = isReview;
    }
}
