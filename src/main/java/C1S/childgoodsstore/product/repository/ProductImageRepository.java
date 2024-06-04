package C1S.childgoodsstore.product.repository;

import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Query("SELECT pi FROM ProductImage pi WHERE pi.product.productId = :productId AND pi.imageOrder = :imageOrder")
    Optional<ProductImage> findByProductIdAndOrder(@Param("productId") Long productId, @Param("imageOrder") int imageOrder);

    Collection<ProductImage> findByProduct(Product product);
}