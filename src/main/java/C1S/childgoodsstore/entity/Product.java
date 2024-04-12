package C1S.childgoodsstore.entity;

import C1S.childgoodsstore.enums.MAIN_CATEGORY;
import C1S.childgoodsstore.enums.PRODUCT_SALE_STATUS;
import C1S.childgoodsstore.enums.PRODUCT_STATE;
import C1S.childgoodsstore.enums.SUB_CATEGORY;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
public class Product extends BaseEntity {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "product_name")
    private String productName;
    private int price;
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private PRODUCT_SALE_STATUS state;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_state")
    private PRODUCT_STATE productState;

    @Enumerated(EnumType.STRING)
    @Column(name = "main_category")
    private MAIN_CATEGORY mainCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "sub_category")
    private SUB_CATEGORY subCategory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductTag> productTags = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductHeart> productHearts = new ArrayList<>();

    public Product() {
        setCreatedAt();
        setUpdatedAt();
    }
}