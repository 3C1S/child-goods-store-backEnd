package C1S.childgoodsstore.product.dto.output;

import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.entity.ProductImage;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.enums.MAIN_CATEGORY;
import C1S.childgoodsstore.enums.PRODUCT_SALE_STATUS;
import C1S.childgoodsstore.enums.PRODUCT_STATE;
import C1S.childgoodsstore.enums.SUB_CATEGORY;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 상품 상세 정보를 제공하기 위한 데이터 전송 객체
@Getter
@Setter
public class ProductDetailsDto {
    private Long productId;
    private UserDto user;
    private String productName;
    private int price;
    private String content;
    private MAIN_CATEGORY mainCategory;
    private SUB_CATEGORY subCategory;
    private PRODUCT_STATE productState;
    private PRODUCT_SALE_STATUS state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> tag;
    private List<String> productImages;
    private boolean productHeart;

    // UserDto 내부 클래스
    @Getter
    @Setter
    public static class UserDto {
        private Long userIdx;
        private String nickName;
        private String profileImg;
        private double averageStars;

        public UserDto(Long userIdx, String nickName, String profileImg, double averageStars) {
            this.userIdx = userIdx;
            this.nickName = nickName;
            this.profileImg = profileImg;
            this.averageStars = averageStars;
        }
    }

    public ProductDetailsDto(Long productId, UserDto user, String productName, int price, String content,
                      MAIN_CATEGORY mainCategory, SUB_CATEGORY subCategory, PRODUCT_STATE productState,
                      PRODUCT_SALE_STATUS state, LocalDateTime createdAt, LocalDateTime updatedAt,
                      List<String> tag, List<String> productImages, boolean productHeart) {
        this.productId = productId;
        this.user = user;
        this.productName = productName;
        this.price = price;
        this.content = content;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.productState = productState;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.tag = tag;
        this.productImages = productImages;
        this.productHeart = productHeart;
    }

    public static ProductDetailsDto fromProduct(Product product, boolean productHeart) {
        UserDto userDto = new UserDto(
                product.getUser().getUserId(),
                product.getUser().getNickName(),
                product.getUser().getProfileImg(),
                calculateAverageStars(product.getUser())
        );

        return new ProductDetailsDto(
                product.getProductId(),
                userDto,
                product.getProductName(),
                product.getPrice(),
                product.getContent(),
                product.getMainCategory(),
                product.getSubCategory(),
                product.getProductState(),
                product.getState(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                extractTags(product),
                extractProductImages(product),
                productHeart
        );
    }

    private static double calculateAverageStars(User user) {
        return user.getScoreNum() > 0 ? (double) user.getTotalScore() / user.getScoreNum() : 0.0;
    }

    private static List<String> extractTags(Product product) {
        return product.getProductTags() != null ? product.getProductTags().stream()
                .map(productTag -> productTag.getTag().getName())
                .collect(Collectors.toList()) : new ArrayList<>();
    }

    private static List<String> extractProductImages(Product product) {
        return product.getProductImages() != null ? product.getProductImages().stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList()) : new ArrayList<>();
    }
}
