package C1S.childgoodsstore.product.repository;

import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.enums.AGE;
import C1S.childgoodsstore.enums.MAIN_CATEGORY;
import C1S.childgoodsstore.enums.SUB_CATEGORY;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductId(Long productId);

    List<Product> findAllByUserUserId(Long userId);
    List<Product> findByUser(User user);

    // 메인 카테고리, 서브 카테고리, 나이, 지역, 가격 조건으로 상품 반환
    // maxPrice가 200000인 경우 가격의 상한은 제한이 걸리지 않는다.
    @Query("SELECT p FROM Product p JOIN p.user u " +
            "WHERE (:mainCategory IS NULL OR p.mainCategory = :mainCategory) " +
            "AND (:subCategory IS NULL OR p.subCategory = :subCategory) " +
            "AND (:age IS NULL OR p.age = :age) " +
            "AND (:region IS NULL OR u.region = :region) " +
            "AND p.price >= :minPrice " +
            "AND (:maxPrice = 200000 OR p.price <= :maxPrice)")
    Page<Product> findByCriteria(@Param("mainCategory") MAIN_CATEGORY mainCategory,
                                 @Param("subCategory") SUB_CATEGORY subCategory,
                                 @Param("age") AGE age,
                                 @Param("region") String region,
                                 @Param("minPrice") Integer minPrice,
                                 @Param("maxPrice") Integer maxPrice,
                                 Pageable pageable);
}
