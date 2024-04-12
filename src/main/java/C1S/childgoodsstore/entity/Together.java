package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
@Entity
@Table(name = "Together")
@Getter
@Setter
@ToString
public class Together extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long togetherId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "together_name")
    private String togetherName;

    @Column(name = "total_price")
    private Integer totalPrice;

    private String details;

    @Column(name = "main_category")
    private String mainCategory;

    @Column(name = "sub_category")
    private String subCategory;

    private String link;
    private LocalDateTime deadline;

    private String address;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "total_num")
    private Integer totalNum; //목표 개수

    @Column(name = "sold_num")
    private Integer soldNum; //판매 개수

    @Column(name = "participant_num")
    private Integer participantNum; //참여자 수
}