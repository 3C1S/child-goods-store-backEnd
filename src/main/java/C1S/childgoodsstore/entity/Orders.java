package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@ToString
public class Orders extends BaseEntity {

    @Id
    @Column(name = "productOrderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productOrderId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public Orders() {
        setCreatedAt();
    }
}
