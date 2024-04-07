package C1S.childgoodsstore.entity;

import C1S.childgoodsstore.enums.MAIN_CATEGORY;
import C1S.childgoodsstore.enums.SUB_CATEGORY;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Together extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "together_id")
    private Long togetherId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String togetherName;
    private int totalPrice;
    private String details;
    private MAIN_CATEGORY mainCategory;
    private SUB_CATEGORY subCategory;
    private String link;
    private LocalDateTime deadline;
    private String address;
    private String detailAddress;
    private int totalNum;
    private int soldNum;
    private int participantNum;

    public Together() {
        setCreatedAt();
        setUpdatedAt();
    }
}
