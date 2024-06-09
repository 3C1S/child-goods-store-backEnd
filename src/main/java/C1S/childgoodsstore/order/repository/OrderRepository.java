package C1S.childgoodsstore.order.repository;

import C1S.childgoodsstore.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderRecord, Long> {

    List<OrderRecord> findAllByUser(User user); // 오류 발생

    Optional<OrderRecord> findByUserAndProduct(User user, Product product);

    Optional<OrderRecord> findByUserAndTogether(User user, Together together);

    @Modifying
    @Query("update OrderRecord o set o.user = null where o.user = :user")
    void deleteByUser(@Param("user") User user);

    Page<OrderRecord> findByUserAndTogetherIsNotNull(User user, Pageable pageable);
}