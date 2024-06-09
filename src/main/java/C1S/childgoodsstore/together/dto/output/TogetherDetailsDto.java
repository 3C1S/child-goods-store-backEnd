package C1S.childgoodsstore.together.dto.output;

import C1S.childgoodsstore.entity.Together;
import C1S.childgoodsstore.enums.AGE;
import C1S.childgoodsstore.enums.MAIN_CATEGORY;
import C1S.childgoodsstore.enums.PRODUCT_SALE_STATUS;
import C1S.childgoodsstore.enums.SUB_CATEGORY;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TogetherDetailsDto {

    private Long togetherId;
    private UserDetailDto user;
    private String togetherName;
    private int totalPrice;
    private int purchasePrice;
    private MAIN_CATEGORY mainCategory;
    private SUB_CATEGORY subCategory;
    private AGE age;
    private PRODUCT_SALE_STATUS state;
    private String details;
    private String link;
    private LocalDateTime deadline;
    private String address;
    private String detailAddress;
    private int totalNum;
    private int purchaseNum;
    private List<String> tag;
    private List<String> togetherImage;
    private boolean togetherHeart;
    private Long chattingId;

    public TogetherDetailsDto() {}

    public TogetherDetailsDto(Together together, UserDetailDto user, List<String> togetherImage, List<String> tag, boolean togetherHeart, Long chattingId) {

        this.togetherId = together.getTogetherId();
        this.user = user;
        this.togetherName = together.getTogetherName();
        this.totalPrice = together.getTotalPrice();

        if(together.getSoldNum() != 0) {
            this.purchasePrice = together.getTotalPrice() / together.getSoldNum();
        }
        else {
            this.purchasePrice = together.getTotalPrice();
        }

        this.mainCategory = together.getMainCategory();
        this.subCategory = together.getSubCategory();
        this.age = together.getAge();
        //this.state = together.getState();
        this.details = together.getDetails();
        this.link = together.getLink();
        this.deadline = together.getDeadline();
        this.address = together.getAddress();
        this.detailAddress = together.getDetailAddress();
        this.totalNum = together.getTotalNum();
        this.purchaseNum = together.getSoldNum();
        this.tag = tag;
        this.togetherImage = togetherImage;
        this.togetherHeart = togetherHeart;
        this.chattingId = chattingId;
    }
}
