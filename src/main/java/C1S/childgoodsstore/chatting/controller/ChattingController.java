package C1S.childgoodsstore.chatting.controller;

import C1S.childgoodsstore.chatting.dto.*;
import C1S.childgoodsstore.chatting.service.ChattingService;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import C1S.childgoodsstore.auth.presentation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting")
public class ChattingController {

    private final ChattingService chattingService;

    // 채팅방 생성
    @PostMapping("/room")
    public ResponseEntity<ApiResponse<Long>> createRoom(@AuthenticationPrincipal User user, @RequestBody ChattingRoomRequest chattingRoomRequest) {
        return ResponseEntity.ok(ApiResponse.success(chattingService.createChatRoom(user, chattingRoomRequest)));
    }

    //채팅방 목록 조회
    @GetMapping()
    public ResponseEntity<ApiResponse<List<ChattingRoomList>>> getRoomList(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(ApiResponse.success(chattingService.getRoomList(user)));
    }

    //채팅방 상세 조회
    @GetMapping("/room/{chatRoomId}")
    public ResponseEntity<ApiResponse<List<ChattingRoomDto>>> getChatRoomDetails(@PathVariable("chatRoomId") Long chatRoomId,
                                                                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                                                                 @RequestParam(name = "size", defaultValue = "10") int size,
                                                                                 @AuthenticationPrincipal User user){
        return ResponseEntity.ok(ApiResponse.success(chattingService.getChatRoomDetails(chatRoomId, page, size, user)));
    }

    //채팅방 정보 조회
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ApiResponse<ChatRoomInfo>> getChatRoomInfo(@PathVariable("chatRoomId") Long chatRoomId,
                                                                     @AuthenticationPrincipal User user){
        return ResponseEntity.ok(ApiResponse.success(chattingService.getChatRoomInfo(chatRoomId)));
    }

}
