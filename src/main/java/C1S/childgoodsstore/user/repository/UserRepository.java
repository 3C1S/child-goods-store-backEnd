package C1S.childgoodsstore.user.repository;

import C1S.childgoodsstore.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(Long userId);
    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.totalScore = :totalScore, u.scoreNum = :scoreNum where u.userId = :userId")
    void updateByUserId(@Param("totalScore") Integer totalScore, @Param("scoreNum") Integer scoreNum,
                                 @Param("userId") Long userId);
}