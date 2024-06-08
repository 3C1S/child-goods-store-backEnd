package C1S.childgoodsstore.together.repository;

import C1S.childgoodsstore.entity.Together;
import C1S.childgoodsstore.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TogetherRepository extends JpaRepository<Together, Long> {

    Optional<Together> findByTogetherId(Long togetherId);

    List<Together> findByUser(User user);

    @Modifying
    @Query("update Together t set t.user = null where t.user = :user")
    void deleteTogetherByUser(@Param("user") User user);

    Page<Together> findAllByTogetherIdIn(List<Long> ids, Pageable pageable);

    Page<Together> findByUser(User user, Pageable pageable);

    Page<Together> findAll(Specification<Together> specification, Pageable pageable);
}
