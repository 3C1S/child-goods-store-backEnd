package C1S.childgoodsstore.product.dto.output;

import lombok.Getter;
import lombok.Setter;


/**
 * HomeUsedProductViewDto는 홈 스크린에 표시되는 제품의 요약 정보를 담는 DTO입니다.
 * 이 DTO는 제품의 기본 정보와 사용자의 '좋아요' 상태를 포함합니다.
 */
@Getter
@Setter
public class HomeUsedProductViewDto {
    private Long productId; // 중고 상품 아이디
    private String productName; // 중고 상품 이름
    private int price; // 중고 상품 가격
    private String productImage; // 중고 상품 메인 이미지
    private boolean productHeart; // 로그인 유저의 중고 상품 좋아요 여부

    public HomeUsedProductViewDto(Long productId, String productName, int price, String productImage, boolean productHeart) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.productImage = productImage;
        this.productHeart= productHeart;
    }
}
