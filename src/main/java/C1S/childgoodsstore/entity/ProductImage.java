package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ProductImage")
@Getter
@Setter
@ToString
public class ProductImage {

    @Id
    @Column(name = "imageId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    private String imageLink;
    private int imageOrder;
}
