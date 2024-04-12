package C1S.childgoodsstore.tag.repository;

import C1S.childgoodsstore.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT t.tag FROM Tag t WHERE t.tag LIKE :keyword%")
    List<String> findByTagStartingWith(@Param("keyword") String keyword);

}
