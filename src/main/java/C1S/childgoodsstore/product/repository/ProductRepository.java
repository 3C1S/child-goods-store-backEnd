package C1S.childgoodsstore.product.repository;

import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductId(Long productId);

    List<Product> findByUser(User user);
}
