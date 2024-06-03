package C1S.childgoodsstore.child.repository;

import C1S.childgoodsstore.entity.Child;
import C1S.childgoodsstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChildRepository extends JpaRepository<Child, Integer> {
    List<Child> findByParent(User user);

    void deleteByParent(User user);
}
