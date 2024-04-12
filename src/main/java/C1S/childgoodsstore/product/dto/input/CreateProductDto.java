package C1S.childgoodsstore.product.dto.input;

import C1S.childgoodsstore.enums.MAIN_CATEGORY;
import C1S.childgoodsstore.enums.PRODUCT_STATE;
import C1S.childgoodsstore.enums.SUB_CATEGORY;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {

    @NotBlank(message = "Product name cannot be blank")
    @Length(max = 255, message = "Product name must not exceed 255 characters")
    private String productName;

    @Min(value = 0, message = "Price must be positive")
    private int price;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotNull(message = "Product state must not be null")
    private PRODUCT_STATE productState;

    @NotNull(message = "Main category must not be null")
    private MAIN_CATEGORY mainCategory;

    @NotNull(message = "Sub category must not be null")
    private SUB_CATEGORY subCategory;

    private List<String> imageList = new ArrayList<>();
    private List<String> tag = new ArrayList<>();

}