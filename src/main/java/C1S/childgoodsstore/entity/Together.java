package C1S.childgoodsstore.entity;

import jakarta.annotation.Nullable;
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
    @JoinColumn(name = "userId")
    private User user;

    private String togetherName;

    private Integer totalPrice;

    private String details;

    private String mainCategory;

    private String subCategory;

    private String link;

    private LocalDateTime deadline;

    private String address;

    private String detailAddress;

    private Integer totalNum; //목표 개수

    private Integer soldNum; //판매 개수

    private Integer participantNum; //참여자 수
}
