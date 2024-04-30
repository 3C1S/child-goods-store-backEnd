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
    @Query("SELECT p FROM Product p")
    Page<Product> findByCriteria(Pageable pageable);


    Page<Product> findByProductNameContaining(String productName, Pageable pageable);
}
