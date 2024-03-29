package C1S.childgoodsstore.user.repository;

import C1S.childgoodsstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(Long userId);
    Optional<User> findByEmail(String email);

    @Query("SELECT COUNT(f) FROM Following f WHERE f.user.userId = :userId")
    int countFollowingsByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(f) FROM Following f WHERE f.followId = :userId")
    int countFollowersByUserId(@Param("userId") Long userId);
}