package C1S.childgoodsstore.entity;

import C1S.childgoodsstore.child.dto.ChildSaveDto;
import C1S.childgoodsstore.enums.AGE;
import C1S.childgoodsstore.enums.GENDER;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_id")
    private Long childId;

    private String name;
    private GENDER gender;
    private AGE age;
    private String tag;
    @Column(name = "child_img")
    private String childImg;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User parent;

    public Child() {}

    public Child(User parent, ChildSaveDto childDto) {

        this.parent = parent;
        this.name = childDto.getName();
        this.gender = childDto.getGender();
        this.age = childDto.getAge();
        this.tag = childDto.getTag();
        this.childImg = childDto.getChildImg();
    }
}