package C1S.childgoodsstore.chatting.repository;

import C1S.childgoodsstore.entity.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
    // 상품 ID로 채팅방 검색
    List<ChattingRoom> findByProductProductId(Long productId);
}
