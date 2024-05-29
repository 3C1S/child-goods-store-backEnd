package C1S.childgoodsstore.chatting.controller;

import C1S.childgoodsstore.chatting.dto.MessageDto;
import C1S.childgoodsstore.chatting.service.MessageService;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.global.response.ApiResponse;
import C1S.childgoodsstore.user.repository.UserRepository;
import C1S.childgoodsstore.user.service.UserService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final RabbitTemplate template;
    private final MessageService messageService;
//    private final UserRepository userRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper; // Jackson ObjectMapper


    // 채팅방 대화
    @MessageMapping("chat.talk.{chatRoomId}")
    public ResponseEntity<ApiResponse> talkUser(@DestinationVariable("chatRoomId") Long chatRoomId, @Payload MessageDto message,
                                                SimpMessageHeaderAccessor accessor) {
        User user = (User) accessor.getSessionAttributes().get("user");
//        template.convertAndSend("chat.exchange", "room." + message.getChatRoomId(), message, messagePostProcessor -> {
//            messagePostProcessor.getMessageProperties().setHeader("userId", user.getUserId());
//            return messagePostProcessor;
//        });
        template.convertAndSend("chat.exchange", "room." + message.getChatRoomId(), message);
        messageService.saveMessage(message, user, LocalDateTime.now());
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // RabbitMQ listener example
//    @RabbitListener(queues = "chat.queue")
//    public void receive(Message message) {
//        try {
//            // 메시지 본문을 바이트 배열에서 MessageDto로 변환
//            byte[] body = message.getBody();
//            MessageDto messageDto = objectMapper.readValue(body, MessageDto.class);
//
//            // 메시지 헤더에서 userId 추출
//            Long userId = (Long) message.getMessageProperties().getHeaders().get("userId");
//
//            User user = userRepository.findByUserId(userId).get();
//
//            // 메시지를 저장
//            messageService.saveMessage(messageDto, user, LocalDateTime.now());
//            System.out.println("received : " + messageDto.getMessage());
//        } catch (IOException e) {
//        }
//    }

}
