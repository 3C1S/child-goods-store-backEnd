package C1S.childgoodsstore.tag.repository;

import C1S.childgoodsstore.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT t.name FROM Tag t WHERE t.name LIKE :keyword%")
    List<String> findByTagStartingWith(@Param("keyword") String keyword);

    Optional<Tag> findByName(String tagName);
}