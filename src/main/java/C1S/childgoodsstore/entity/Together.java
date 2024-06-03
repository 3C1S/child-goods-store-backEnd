package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private Integer likeNum = 0;

    @OneToMany(mappedBy = "together", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TogetherImage> togetherImage = new ArrayList<>();

    public void incrementLikeNum() {
        this.likeNum += 1;
    }

    public void decrementLikeNum() {
        if (this.likeNum > 0) {
            this.likeNum -= 1;
        }
    }
}