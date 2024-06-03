package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "together_image")
public class TogetherImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "together_image_id")
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_order")
    private int imageOrder;

    @ManyToOne
    @JoinColumn(name = "together_id")
    private Together together;

    // 기본 생성자
    public TogetherImage() {}

    public TogetherImage(String imageUrl, int imageOrder, Together together) {
        this.imageUrl = imageUrl;
        this.imageOrder = imageOrder;
        this.together = together;
    }
}