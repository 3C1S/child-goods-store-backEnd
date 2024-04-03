package C1S.childgoodsstore.together.repository;

import C1S.childgoodsstore.entity.Together;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TogetherRepository extends JpaRepository<Together, Long> {

    Optional<Together> findByTogetherId(Long togetherId);
}
