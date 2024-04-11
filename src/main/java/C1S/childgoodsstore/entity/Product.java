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
@Table(name = "product")
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

    @Enumerated(EnumType.STRING)
    private PRODUCT_SALE_STATUS state;

    @Enumerated(EnumType.STRING)
    private PRODUCT_STATE productState;

    @Enumerated(EnumType.STRING)
    private MAIN_CATEGORY mainCategory;

    @Enumerated(EnumType.STRING)
    private SUB_CATEGORY subCategory;

    public Product() {
        setCreatedAt();
        setUpdatedAt();
    }
}