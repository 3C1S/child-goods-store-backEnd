package C1S.childgoodsstore.together.repository;

import C1S.childgoodsstore.entity.Together;
import C1S.childgoodsstore.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TogetherRepository extends JpaRepository<Together, Long> {

    Optional<Together> findByTogetherId(Long togetherId);

    List<Together> findByUser(User user);

    Page<Together> findAllByTogetherIdIn(List<Long> ids, Pageable pageable);

    Page<Together> findByUser(User user, Pageable pageable);
}
