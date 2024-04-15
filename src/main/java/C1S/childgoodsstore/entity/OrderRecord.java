package C1S.childgoodsstore.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_record")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "together_id", nullable = true)
    private Together together;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Product Order 생성자
    public OrderRecord(User user, Product product) {
        this.user = user;
        this.product = product;
        this.createdAt = LocalDateTime.now();
    }

    // Together Order 생성자
    public OrderRecord(User user, Together together) {
        this.user = user;
        this.together = together;
        this.createdAt = LocalDateTime.now();
    }
}
