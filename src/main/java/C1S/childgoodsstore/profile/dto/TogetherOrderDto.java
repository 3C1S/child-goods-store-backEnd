package C1S.childgoodsstore.profile.dto;


import C1S.childgoodsstore.entity.OrderRecord;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TogetherOrderDto {
    private Long id;
    private String sellerName;
    private String title;
    private Integer price;
    private LocalDateTime saleCompleteDate;
    private String image;
    private Boolean isReview;
    private String category;

    public TogetherOrderDto(OrderRecord orderRecord,String image, Boolean isReview){
        this.id = orderRecord.getOrderId();
        this.sellerName = orderRecord.getTogether().getUser().getNickName();
        this.title = orderRecord.getTogether().getTogetherName();
        this.price = orderRecord.getTogether().getTotalPrice()/orderRecord.getTogether().getParticipantNum();
        this.saleCompleteDate = orderRecord.getCreatedAt();
        this.image = image;
        this.isReview = isReview;
        this.category = "TOGETHER";


    }
}