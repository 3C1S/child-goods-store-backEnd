package C1S.childgoodsstore.child.repository;

import C1S.childgoodsstore.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChildRepository extends JpaRepository<Child, Integer> {
}
