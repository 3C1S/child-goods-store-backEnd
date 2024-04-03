package C1S.childgoodsstore.entity;

import ch.qos.logback.core.model.INamedModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Product")
@Getter
@Setter
@ToString
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String productName;

    private Integer price;

    private String content;

    private String state; //판매 완료 여부

    private String productState; //상품의 상태

    private String mainCategory;

    private String subCategory;

}
