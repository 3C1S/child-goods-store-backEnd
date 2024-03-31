package C1S.childgoodsstore.product.repository;

import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.entity.ProductHeart;
import C1S.childgoodsstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT ph FROM ProductHeart ph WHERE ph.user = :user AND ph.product = :product")
    Optional<ProductHeart> findByUserAndProduct(@Param("user") Long userId, @Param("product") Long productId);

    Product findByProductId(Long productId);

    ProductHeart save(ProductHeart productHeart);
}
