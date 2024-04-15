package C1S.childgoodsstore.product.dto.input;

import C1S.childgoodsstore.enums.PRODUCT_SALE_STATUS;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductStateDto {
    @NotNull(message = "State is required.")
    private PRODUCT_SALE_STATUS state;

    private Long userId;
}
