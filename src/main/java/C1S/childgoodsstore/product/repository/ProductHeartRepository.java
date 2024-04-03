package C1S.childgoodsstore.product.repository;

import C1S.childgoodsstore.entity.ProductHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProductHeartRepository extends JpaRepository<ProductHeart, Long> {

//    @Query("SELECT ph FROM ProductHeart ph WHERE ph.user = :userId AND ph.product = :productId")
//    Optional<ProductHeart> findByUserAndProduct(@Param("userId") Long userId, @Param("productId") Long productId);

    @Query("SELECT ph FROM ProductHeart ph WHERE ph.user.userId = :userId AND ph.product.productId = :productId")
    Optional<ProductHeart> findByUserAndProduct(@Param("userId") Long userId, @Param("productId") Long productId);

    @Transactional
    ProductHeart save(ProductHeart productHeart);

    @Transactional
    void deleteByHeartId(Long heartId);
}
