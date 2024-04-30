package C1S.childgoodsstore.product.repository;

import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductId(Long productId);

    List<Product> findAllByUserUserId(Long userId);
    List<Product> findByUser(User user);

    Page<Product> findByProductNameContaining(String productName, Pageable pageable);

    Page<Product> findAll(Specification<Product> specification, Pageable pageable);
}