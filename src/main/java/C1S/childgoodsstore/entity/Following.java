package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Following")
@Getter
@Setter
@ToString
public class Following {

    @Id
    @Column(name = "followingId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followingId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private Long followId;
}
