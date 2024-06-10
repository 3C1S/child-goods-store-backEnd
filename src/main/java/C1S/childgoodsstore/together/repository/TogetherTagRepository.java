package C1S.childgoodsstore.together.repository;

import C1S.childgoodsstore.entity.Together;
import C1S.childgoodsstore.entity.TogetherTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TogetherTagRepository extends JpaRepository<TogetherTag, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM TogetherTag t WHERE t.together.togetherId = :togetherId")
    void deleteTogetherTagsByTogetherTogetherId(@Param("togetherId") Long togetherId);

    @Query("SELECT t.tag.id FROM TogetherTag t WHERE t.together.togetherId = :togetherId")
    List<Long> findAllByTogetherId(@Param("togetherId") Long togetherId);
}
