package C1S.childgoodsstore.together.repository;

import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.entity.Together;
import C1S.childgoodsstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TogetherRepository extends JpaRepository<Together, Long> {

    Optional<Together> findByTogetherId(Long togetherId);

    List<Together> findByUser(User user);
}
