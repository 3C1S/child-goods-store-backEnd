package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "together_tag")
public class TogetherTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "together_tag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "together_id")
    private Together together;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public TogetherTag() {}

    public TogetherTag(Together together, Tag tag) {
        this.together = together;
        this.tag = tag;
    }
}