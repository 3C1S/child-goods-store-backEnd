package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "together_id")
    private Together together;

    private Integer purchaseNum;

    public Participants(User user, Together together, Integer purchaseNum) {
        this.user = user;
        this.together = together;
        this.purchaseNum = purchaseNum;
    }

    public Participants() {

    }
}
