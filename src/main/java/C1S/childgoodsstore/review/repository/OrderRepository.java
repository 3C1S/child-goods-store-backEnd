package C1S.childgoodsstore.review.repository;

import C1S.childgoodsstore.entity.Orders;
import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.entity.Together;
import C1S.childgoodsstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser(User user);

    Optional<Orders> findByUserAndProduct(User user, Product product);

    Optional<Orders> findByUserAndTogether(User user, Together together);

}
