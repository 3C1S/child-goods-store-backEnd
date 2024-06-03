package C1S.childgoodsstore.together.repository;

import C1S.childgoodsstore.entity.TogetherTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TogetherTagRepository extends JpaRepository<TogetherTag, Long> {

    @Query("DELETE FROM TogetherTag t WHERE t.together.togetherId = :togetherId")
    void deleteTogetherTagsByTogetherTogetherId(@Param("togetherId") Long togetherId);
}
