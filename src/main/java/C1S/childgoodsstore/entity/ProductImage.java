package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product_image")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_order")
    private int imageOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // 기본 생성자
    public ProductImage() {}

    public ProductImage(String imageUrl, int imageOrder, Product product) {
        this.imageUrl = imageUrl;
        this.imageOrder = imageOrder;
        this.product = product;
    }
}