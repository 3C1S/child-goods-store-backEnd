package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@ToString
public class Orders {

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

    @ManyToOne
    @JoinColumn(name = "togetherId")
    private Together together;

    private LocalDateTime createdAt;

    public Orders() {
        this.createdAt = LocalDateTime.now().withNano(0);
    }
}
