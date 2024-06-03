package C1S.childgoodsstore.together.repository;

import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.entity.Together;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.enums.PRODUCT_SALE_STATUS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TogetherRepository extends JpaRepository<Together, Long> {

    Optional<Together> findByTogetherId(Long togetherId);

    List<Together> findByUser(User user);

    @Modifying
    @Query("update Together t set t.user = null where t.user = :user")
    void deleteTogetherByUser(@Param("user") User user);
}
