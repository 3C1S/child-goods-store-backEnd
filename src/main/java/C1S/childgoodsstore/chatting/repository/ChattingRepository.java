package C1S.childgoodsstore.chatting.repository;

import C1S.childgoodsstore.chatting.dto.ChattingRoomDto;
import C1S.childgoodsstore.entity.Chatting;
import C1S.childgoodsstore.entity.ChattingRoom;
import C1S.childgoodsstore.following.dto.FollowInterfaceDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChattingRepository extends JpaRepository<Chatting, Long> {


    @Query("SELECT c FROM Chatting c WHERE c.createdAt = (SELECT MAX(c2.createdAt) FROM Chatting c2 WHERE c2.chattingRoom.chatRoomId = :chatRoomId)")
    Optional<Chatting> findByChattingRoom(@Param("chatRoomId") Long chatRoomId);

    @Query("select c from Chatting  c where c.chattingRoom.chatRoomId = :chatRoomId")
    List<Chatting> getChatting(@Param("chatRoomId") Long chatRoomId, Pageable pageable);
}
