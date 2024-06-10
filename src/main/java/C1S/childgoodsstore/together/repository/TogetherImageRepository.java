package C1S.childgoodsstore.together.repository;

import C1S.childgoodsstore.entity.Together;
import C1S.childgoodsstore.entity.TogetherImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface TogetherImageRepository extends JpaRepository<TogetherImage, Long> {

    List<TogetherImage> findByTogether(Together together);

    @Query("SELECT ti FROM TogetherImage ti WHERE ti.together.togetherId = :togetherId AND ti.imageOrder = :imageOrder")
    Optional<TogetherImage> findByTogetherIdAndOrder(@Param("togetherId") Long togetherId, @Param("imageOrder") int imageOrder);

    @Modifying
    @Transactional
    @Query("DELETE FROM TogetherImage ti WHERE ti.together.togetherId = :togetherId")
    void deleteTogetherImagesByTogetherTogetherId(@Param("togetherId") Long togetherId);

    @Query("SELECT ti.imageUrl FROM TogetherImage ti WHERE ti.together.togetherId = :togetherId ORDER BY ti.imageOrder")
    List<String> findAllByTogetherId(@Param("togetherId") Long togetherId);

    Optional<TogetherImage> findByTogetherAndImageOrder(Together together, int imageOrder);
}
