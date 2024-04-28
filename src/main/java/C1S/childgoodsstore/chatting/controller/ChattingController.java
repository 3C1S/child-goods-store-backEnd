package C1S.childgoodsstore.chatting.controller;

import C1S.childgoodsstore.chatting.dto.ChattingRoomRequest;
import C1S.childgoodsstore.chatting.service.ChattingService;
import C1S.childgoodsstore.global.response.ApiResponse;
import C1S.childgoodsstore.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting")
public class ChattingController {

    private final ChattingService chattingService;

    // 채팅방 생성
    @PostMapping("/room")
    public ResponseEntity<ApiResponse<Long>> createRoom(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody ChattingRoomRequest chattingRoomRequest) {
        return ResponseEntity.ok(ApiResponse.success(chattingService.createChatRoom(principalDetails.getUser(), chattingRoomRequest)));
    }

}