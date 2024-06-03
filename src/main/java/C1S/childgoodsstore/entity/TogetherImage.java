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
    @Column(name = "img_idx")
    private Long imgIdx;

    @ManyToOne
    @JoinColumn(name = "together_id")
    private Together together;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "order")
    private int order;

    public TogetherImage() {}

    public TogetherImage(Together together, String imageUrl, int order) {
        this.together = together;
        this.imageUrl = imageUrl;
        this.order = order;
    }
}
