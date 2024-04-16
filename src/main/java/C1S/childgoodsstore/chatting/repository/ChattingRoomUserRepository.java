package C1S.childgoodsstore.chatting.repository;

import C1S.childgoodsstore.entity.ChattingRoom;
import C1S.childgoodsstore.entity.ChattingRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChattingRoomUserRepository extends JpaRepository<ChattingRoomUser, Long> {
    // 채팅방 ID로 채팅방 사용자 검색
    List<ChattingRoomUser> findByChattingRoom(ChattingRoom chattingRoom);

}
