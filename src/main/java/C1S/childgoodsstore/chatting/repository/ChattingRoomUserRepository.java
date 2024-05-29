package C1S.childgoodsstore.chatting.repository;

import C1S.childgoodsstore.entity.ChattingRoom;
import C1S.childgoodsstore.entity.ChattingRoomUser;
import C1S.childgoodsstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChattingRoomUserRepository extends JpaRepository<ChattingRoomUser, Long> {
    // 채팅방 ID로 채팅방 사용자 검색
    List<ChattingRoomUser> findByChattingRoom(ChattingRoom chattingRoom);

    List<ChattingRoomUser> findByUser(User user);

    @Query("select c from ChattingRoomUser c where c.chattingRoom = :chattingRoom and c.user = :user")
    ChattingRoomUser findByChatRoomAndUser(@Param("chattingRoom") ChattingRoom chattingRoom,
                                             @Param("user") User user);

}
