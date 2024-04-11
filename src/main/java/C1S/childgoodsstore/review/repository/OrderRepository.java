package C1S.childgoodsstore.review.repository;

import C1S.childgoodsstore.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser(User user);

    List<Orders> findAllByUser(User user); // 오류 발생

    Optional<Orders> findByUserAndProduct(User user, Product product);

    Optional<Orders> findByUserAndTogether(User user, Together together);

}
