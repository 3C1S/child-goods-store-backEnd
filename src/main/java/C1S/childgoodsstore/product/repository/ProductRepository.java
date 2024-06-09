package C1S.childgoodsstore.product.repository;

import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.enums.PRODUCT_SALE_STATUS;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductId(Long productId);

    List<Product> findAllByUserUserId(Long userId);

    Page<Product> findAllByUserUserId(Long userId, Pageable pageable);

    List<Product> findByUser(User user);

    Page<Product> findByProductNameContaining(String productName, Pageable pageable);

    Page<Product> findAll(Specification<Product> specification, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Product p set p.state = :state, p.user = null where p.user = :user")
    void deleteProductByUser(@Param("state")PRODUCT_SALE_STATUS state, @Param("user") User user);
}