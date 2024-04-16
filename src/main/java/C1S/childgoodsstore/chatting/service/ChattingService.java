package C1S.childgoodsstore.chatting.service;

import C1S.childgoodsstore.chatting.dto.ChattingRoomRequest;
import C1S.childgoodsstore.chatting.repository.ChattingRoomRepository;
import C1S.childgoodsstore.chatting.repository.ChattingRoomUserRepository;
import C1S.childgoodsstore.entity.*;
import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import C1S.childgoodsstore.product.repository.ProductRepository;
import C1S.childgoodsstore.together.repository.TogetherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChattingService {

    private final ProductRepository productRepository;
    private final TogetherRepository togetherRepository;
    private final ChattingRoomRepository chattingRoomRepository;
    private final ChattingRoomUserRepository chattingRoomUserRepository;

    // controller - 채팅방 생성
    public Long createChatRoom(User user, ChattingRoomRequest chattingRoomRequest) {
        // 채팅방 상품 타입에 따라 다른 유형을 처리하기 위해 스위치를 사용
        ChattingRoom createdChattingRoom = switch (chattingRoomRequest.getCategory()) {
            // 중고 거래 상품 채팅방 생성
            case PRODUCT -> createProductChatRoom(user, chattingRoomRequest.getId());
            // 공동 구매 상품 채팅방 생성
            case TOGETHER -> createTogetherChatRoom(user, chattingRoomRequest.getId());
            default ->
                    throw new CustomException(ErrorCode.INVALID_PRODUCT_CATEGORY, "Invalid product category: " + chattingRoomRequest.getCategory());
        };
        // 생성된 채팅방의 ID 반환
        return createdChattingRoom.getChatRoomId();
    }

    // 중고거래 상품에 대한 채팅방 생성
    private ChattingRoom createProductChatRoom(User user, Long productId) {
        // 중고거래 상품 ID로 상품을 찾거나 존재하지 않을 경우 예외를 발생
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND, "Product id: " + productId));
        // 찾은 상품에 대한 새 채팅방을 생성하고 저장
        ChattingRoom chattingRoom = chattingRoomRepository.save(new ChattingRoom(product));
        // 제품 판매자와 구매 희망자를 채팅방 참가자로 등록
        saveChattingRoomUsers(product.getUser(), user, chattingRoom);
        return chattingRoom;
    }

    // 공동구매 상품에 대한 채팅방 생성
    private ChattingRoom createTogetherChatRoom(User user, Long togetherId) {
        // 공동구매 상품 ID로 상품을 찾거나 존재하지 않을 경우 예외를 발생
        Together together = togetherRepository.findById(togetherId)
                .orElseThrow(() -> new CustomException(ErrorCode.TOGETHER_NOT_FOUND, "Together id: " + togetherId));
        // 이 공동구매 상품에 대해 이미 존재하는 채팅방이 있는지 확인
        ChattingRoom existingChattingRoom = chattingRoomRepository.findByTogether(together);

        if (existingChattingRoom == null) {
            // 채팅방이 존재하지 않는 경우 새로운 채팅방 생성
            ChattingRoom chattingRoom = chattingRoomRepository.save(new ChattingRoom(together));
            // 제품 판매자와 구매 희망자를 채팅방 참가자로 등록
            saveChattingRoomUsers(together.getUser(), user, chattingRoom);
            return chattingRoom;
        } else {
            // 채팅방이 존재하는 경우 구매 희망자만 채팅방 참가자로 추가
            saveChattingRoomUsers(user, existingChattingRoom);
            return existingChattingRoom;
        }
    }

    // 채팅방 리더와 참가자를 채팅방에 저장
    private void saveChattingRoomUsers(User leader, User user, ChattingRoom chattingRoom) {
        // 채팅방 리더(판매자) 저장
        chattingRoomUserRepository.save(new ChattingRoomUser(leader, chattingRoom, true));
        // 채팅방 참여자(구매 희망자) 저장
        chattingRoomUserRepository.save(new ChattingRoomUser(user, chattingRoom, false));
    }

    // 기존 채팅방에 사용자를 참가자로 추가
    private void saveChattingRoomUsers(User user, ChattingRoom chattingRoom) {
        // 채팅방 참여자(구매 희망자) 저장
        chattingRoomUserRepository.save(new ChattingRoomUser(user, chattingRoom, false));
    }
}

