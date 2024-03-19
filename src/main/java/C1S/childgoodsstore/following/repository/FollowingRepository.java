package C1S.childgoodsstore.following.repository;

import C1S.childgoodsstore.following.dto.FollowInterfaceDto;
import C1S.childgoodsstore.entity.Following;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowingRepository extends JpaRepository<Following, Integer> {

    @Query(value = "select u from User u where u.userId in (select f.user.userId from Following f where f.followId = ?1)")
    List<FollowInterfaceDto> getFollower(Integer userId);

    @Query(value = "select u from User u where u.userId in (select f.followId from Following f where f.user.userId = ?1)")
    List<FollowInterfaceDto> getFollowing(Integer userId);

    @Query(value = "insert into Following (user_id, follow_id) values (:userId, :followId)", nativeQuery = true)
    void follow(@Param("userId") Integer userId, @Param("followId") Integer followId);

    @Query(value = "delete from Following where user_id=?1 and follow_id=?2", nativeQuery = true)
    void unfollow(Integer userId, Integer followId);

    @Query(value = "select f from Following f where f.user.userId = ?1 and f.followId = ?2")
    Optional<Following> checkAlready(@Param("userId") Integer userId, @Param("followId") Integer followId);

}
