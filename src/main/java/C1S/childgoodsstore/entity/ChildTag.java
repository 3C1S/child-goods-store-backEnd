package C1S.childgoodsstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "child_tag")
public class ChildTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public ChildTag() {}

    public ChildTag(Child child, Tag tag) {
        this.child = child;
        this.tag = tag;
    }
}
