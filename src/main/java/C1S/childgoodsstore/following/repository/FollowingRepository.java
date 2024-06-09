package C1S.childgoodsstore.following.repository;

import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.following.dto.FollowInterfaceDto;
import C1S.childgoodsstore.entity.Following;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowingRepository extends JpaRepository<Following, Integer> {

    @Query("select u.userId as userId, u.profileImg as profileImg, u.nickName as nickName from User u where u.userId in (select f.user.userId from Following f where f.followId = ?1)")
    List<FollowInterfaceDto> getFollower(Long userId, Pageable pageable);

    @Query("select u.userId as userId, u.profileImg as profileImg, u.nickName as nickName from User u where u.userId in (select f.followId from Following f where f.user.userId = ?1)")
    List<FollowInterfaceDto> getFollowing(Long userId, Pageable pageable);

    @Query(value = "insert into Following (user_id, follow_id) values (:userId, :followId)", nativeQuery = true)
    void follow(@Param("userId") Long userId, @Param("followId") Long followId);

    @Query(value = "delete from Following where user_id=?1 and follow_id=?2", nativeQuery = true)
    void unfollow(Long userId, Long followId);

    @Query(value = "select f from Following f where f.user.userId = ?1 and f.followId = ?2")
    Optional<Following> checkAlready(@Param("userId") Long userId, @Param("followId") Long followId);

    void deleteByUser(User user);

    void deleteByFollowId(Long follow_id);

    //팔로잉 숫자(해당 유저가 팔로우하는 사람 수)
    @Query("SELECT COUNT(f) FROM Following f WHERE f.user.userId = :userId")
    int countFollowingsByUserId(@Param("userId") Long userId);

    //팔로워 숫자(해당 유저를 팔로우하는 사람 수)
    @Query("SELECT COUNT(f) FROM Following f WHERE f.followId = :userId")
    int countFollowersByUserId(@Param("userId") Long userId);

    @Query("SELECT f FROM Following f WHERE f.user.userId = :userId AND f.followId = :followId")
    Optional<Following> isFollow(@Param("userId") Long userId, @Param("followId") Long followId);
}
