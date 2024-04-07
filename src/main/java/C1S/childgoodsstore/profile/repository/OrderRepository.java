package C1S.childgoodsstore.profile.repository;

import C1S.childgoodsstore.entity.Orders;
import C1S.childgoodsstore.entity.ProductHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAllByUserUserId(Long userId);
}
