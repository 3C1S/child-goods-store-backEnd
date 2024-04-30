package C1S.childgoodsstore.product.dto.input;

import C1S.childgoodsstore.enums.AGE;
import C1S.childgoodsstore.enums.MAIN_CATEGORY;
import C1S.childgoodsstore.enums.SUB_CATEGORY;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 상품 검색 조건을 담는 데이터 전송 객체
 */
@Getter
@Setter
public class ProductSearchCriteriaDto {
    @Length(max = 100, message = "Main category name too long")
    private MAIN_CATEGORY mainCategory; // 메인 카테고리

    @Length(max = 100, message = "Sub category name too long")
    private SUB_CATEGORY subCategory; // 서브 카테고리

    @NotNull(message = "Region cannot be null")
    @Length(max = 100, message = "Region name too long")
    private String region; // 지역

    @Length(max = 50, message = "Age range description too long")
    private AGE age; // 연령대

    @Min(value = 0, message = "Minimum price must not be negative")
    private Integer minPrice; // 최소 가격

    @Min(value = 0, message = "Maximum price must not be negative")
    private Integer maxPrice; // 최대 가격

    @Min(value = 0, message = "Page number must not be negative")
    private Integer page; // 페이지 번호

}
