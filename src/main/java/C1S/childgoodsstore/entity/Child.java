package C1S.childgoodsstore.entity;

import C1S.childgoodsstore.child.dto.ChildSaveDto;
import C1S.childgoodsstore.enums.AGE;
import C1S.childgoodsstore.enums.GENDER;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "child_img")
    private String childImg;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User parent;
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChildTag> childTags = new ArrayList<>();

    public Child() {}

    public Child(User parent, ChildSaveDto childDto) {

        this.parent = parent;
        this.name = childDto.getName();
        this.gender = childDto.getGender();
        this.age = childDto.getAge();
        this.childImg = childDto.getChildImg();
    }
}