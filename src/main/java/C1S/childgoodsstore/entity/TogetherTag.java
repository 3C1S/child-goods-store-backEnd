package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "together_tag")
public class TogetherTag {
    @Id
    @GeneratedValue
    private Long togetherTagId;

    @ManyToOne
    @JoinColumn(name = "togetherId")
    private Together together;

    @ManyToOne
    @JoinColumn(name = "tagId")
    private Product tagId;
}
