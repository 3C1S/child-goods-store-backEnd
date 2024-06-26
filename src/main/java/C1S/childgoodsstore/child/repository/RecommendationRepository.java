package C1S.childgoodsstore.child.repository;

import C1S.childgoodsstore.entity.Child;
import C1S.childgoodsstore.entity.Recommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    Page<Recommendation> findByChild(Child child, Pageable pageable);
}
