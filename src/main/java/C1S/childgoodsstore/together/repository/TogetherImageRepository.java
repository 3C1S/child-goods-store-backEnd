package C1S.childgoodsstore.together.repository;

import C1S.childgoodsstore.entity.Together;
import C1S.childgoodsstore.entity.TogetherImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TogetherImageRepository extends JpaRepository<TogetherImage, Long> {

    @Query("DELETE FROM TogetherImage ti WHERE ti.together.togetherId = :togetherId")
    void deleteTogetherImagesByTogetherTogetherId(@Param("togetherId") Long togetherId);

    List<TogetherImage> findByTogether(Together together);
}
