package C1S.childgoodsstore.chatting.repository;

import C1S.childgoodsstore.entity.ChattingRoom;
import C1S.childgoodsstore.entity.Together;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
    // 상품 ID로 채팅방 검색
    List<ChattingRoom> findByProductProductId(Long productId);

    ChattingRoom findByTogether(Together together);

    ChattingRoom findByChatRoomId(Long chatRoomId);

    @Modifying
    @Transactional
    @Query("UPDATE ChattingRoom r SET r.userCount = r.userCount + 1 WHERE r.chatRoomId = :chatRoomId")
    void upUserCount(@Param("chatRoomId") Long chatRoomId);

    @Query("SELECT r.chatRoomId FROM ChattingRoom r WHERE r.together.togetherId = :togetherId")
    Long findByTogetherTogetherId(@Param("togetherId") Long togetherId);
}
