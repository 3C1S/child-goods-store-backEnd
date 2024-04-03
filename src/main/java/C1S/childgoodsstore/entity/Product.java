package C1S.childgoodsstore.entity;

import C1S.childgoodsstore.enums.MAIN_CATEGORY;
import C1S.childgoodsstore.enums.PRODUCT_SALE_STATUS;
import C1S.childgoodsstore.enums.PRODUCT_STATE;
import C1S.childgoodsstore.enums.SUB_CATEGORY;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Product")
@Getter
@Setter
@ToString
public class Product extends BaseEntity {

    @Id
    @Column(name = "productId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String productName;
    private int price;
    private String content;
    private PRODUCT_SALE_STATUS state;
    private PRODUCT_STATE productState;
    private MAIN_CATEGORY mainCategory;
    private SUB_CATEGORY subCategory;

    public Product() {
        setCreatedAt();
        setUpdatedAt();
    }
}
