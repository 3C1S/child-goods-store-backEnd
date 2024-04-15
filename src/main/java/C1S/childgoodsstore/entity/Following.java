package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "following")
@Getter
@Setter
@ToString
public class Following {

    @Id
    @Column(name = "following_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "follow_id")
    private Long followId;
}
