package C1S.childgoodsstore.together.repository;

import C1S.childgoodsstore.entity.Together;
import C1S.childgoodsstore.entity.TogetherImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TogetherImageRepository extends JpaRepository<TogetherImage, Long> {

    List<TogetherImage> findByTogether(Together together);
}
