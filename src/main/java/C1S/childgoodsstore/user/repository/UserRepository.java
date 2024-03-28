package C1S.childgoodsstore.user.repository;

import C1S.childgoodsstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(int userId);
    Optional<User> findByEmail(String email);

    @Query("SELECT COUNT(f) FROM Following f WHERE f.user.userId = :userId")
    int countFollowingsByUserId(int userId);

    @Query("SELECT COUNT(f) FROM Following f WHERE f.followId = :userId")
    int countFollowersByUserId(int userId);
}
