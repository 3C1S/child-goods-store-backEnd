package C1S.childgoodsstore.chatting.service;

import C1S.childgoodsstore.chatting.dto.MessageDto;
import C1S.childgoodsstore.chatting.repository.ChattingRepository;
import C1S.childgoodsstore.chatting.repository.ChattingRoomRepository;
import C1S.childgoodsstore.entity.Chatting;
import C1S.childgoodsstore.entity.ChattingRoom;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ChattingRepository chattingRepository;
    private final ChattingRoomRepository chattingRoomRepository;

    public void saveMessage(MessageDto message, User user, LocalDateTime createdAt){
        ChattingRoom chattingRoom = chattingRoomRepository.findByChatRoomId(message.getChatRoomId());
        chattingRepository.save(new Chatting(chattingRoom, user, message.getMessage(), createdAt));
    }
}
