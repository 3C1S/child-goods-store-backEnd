package C1S.childgoodsstore.entity;

import C1S.childgoodsstore.child.dto.ChildSaveDto;
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
    private Integer age;
    private String tag;
    private String childImg;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Child() {

    }

    public Child(User user, ChildSaveDto childDto){
        this.user = user;
        this.name = childDto.getName();
        this.gender = childDto.getGender();
        this.age = childDto.getAge();
        this.tag = childDto.getTag();
        this.childImg = childDto.getChildImg();
    }


}
