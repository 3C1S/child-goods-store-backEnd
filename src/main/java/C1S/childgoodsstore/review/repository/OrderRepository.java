package C1S.childgoodsstore.review.repository;

import C1S.childgoodsstore.entity.Order;
import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.entity.Together;
import C1S.childgoodsstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    Optional<Order> findByUserAndProduct(User user, Product product);

    Optional<Order> findByUserAndTogether(User user, Together together);

}
