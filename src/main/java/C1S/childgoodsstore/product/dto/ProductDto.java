package C1S.childgoodsstore.product.dto;

import C1S.childgoodsstore.enums.MAIN_CATEGORY;
import C1S.childgoodsstore.enums.PRODUCT_STATE;
import C1S.childgoodsstore.enums.SUB_CATEGORY;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductDto {

    @NotBlank(message = "Product name cannot be blank")
    @Length(max = 255, message = "Product name must not exceed 255 characters")
    private final String productName;

    @Min(value = 0, message = "Price must be positive")
    private final int price;

    @NotBlank(message = "Content cannot be blank")
    private final String content;

    @NotNull(message = "Product state must not be null")
    private final PRODUCT_STATE productState;

    @NotNull(message = "Main category must not be null")
    private final MAIN_CATEGORY mainCategory;

    @NotNull(message = "Sub category must not be null")
    private final SUB_CATEGORY subCategory;

    private final List<String> imageList = new ArrayList<>();
    private final List<String> tag = new ArrayList<>();

    public ProductDto(String productName, int price, String content, PRODUCT_STATE productState, MAIN_CATEGORY mainCategory, SUB_CATEGORY subCategory, List<String> imageList, List<String> tag) {
        this.productName = productName;
        this.price = price;
        this.content = content;
        this.productState = productState;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.imageList.addAll(imageList);
        this.tag.addAll(tag);
    }

}