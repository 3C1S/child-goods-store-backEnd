package C1S.childgoodsstore.chatting.controller;

import C1S.childgoodsstore.chatting.dto.MessageDto;
import C1S.childgoodsstore.chatting.service.MessageService;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final RabbitTemplate template;
    private final MessageService messageService;

    // 채팅방 대화
    @MessageMapping("chat.talk.{chatRoomId}")
    public ResponseEntity<ApiResponse> talkUser(@DestinationVariable("chatRoomId") Long chatRoomId, @Payload MessageDto message,
                                                SimpMessageHeaderAccessor accessor) {
        User user = (User) accessor.getSessionAttributes().get("user");
        template.convertAndSend("chat.exchange", "room." + message.getChatRoomId(), message);
        messageService.saveMessage(message, user, LocalDateTime.now());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
