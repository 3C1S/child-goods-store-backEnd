package C1S.childgoodsstore.review.repository;

import C1S.childgoodsstore.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    List<Order> findAllByUser(User user); // 오류 발생

    Optional<Order> findByUserAndProduct(User user, Product product);

    Optional<Order> findByUserAndTogether(User user, Together together);

}