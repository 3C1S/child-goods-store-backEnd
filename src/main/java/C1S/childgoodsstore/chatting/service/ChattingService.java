package C1S.childgoodsstore.chatting.service;

import C1S.childgoodsstore.chatting.dto.ChatRoomInfo;
import C1S.childgoodsstore.chatting.dto.ChattingRoomDto;
import C1S.childgoodsstore.chatting.dto.ChattingRoomList;
import C1S.childgoodsstore.chatting.dto.ChattingRoomRequest;
import C1S.childgoodsstore.chatting.repository.ChattingRepository;
import C1S.childgoodsstore.chatting.repository.ChattingRoomRepository;
import C1S.childgoodsstore.chatting.repository.ChattingRoomUserRepository;
import C1S.childgoodsstore.entity.*;
import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import C1S.childgoodsstore.product.repository.ProductRepository;
import C1S.childgoodsstore.together.repository.TogetherRepository;
import C1S.childgoodsstore.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChattingService {

    private final ProductRepository productRepository;
    private final TogetherRepository togetherRepository;
    private final ChattingRoomRepository chattingRoomRepository;
    private final ChattingRoomUserRepository chattingRoomUserRepository;
    private final ChattingRepository chattingRepository;
    private final UserRepository userRepository;

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

        //chattingRoom의 userCount를 1 증가해야 함
        chattingRoomRepository.upUserCount(chattingRoom.getChatRoomId());

    }

    public List<ChattingRoomList> getRoomList(User user){ //채팅방 목록 조회

        List<ChattingRoomList> result = new ArrayList<>();
        List<ChattingRoomUser> chattingRoom = chattingRoomUserRepository.findByUser(user);

        if(chattingRoom.isEmpty()) return result; //참여하고 있는 방이 없는 경우,

        for(ChattingRoomUser room: chattingRoom){

            Optional<Chatting> chat = chattingRepository.findByChattingRoom(room.getChattingRoom().getChatRoomId());

            if(chat.isEmpty()){
                ChattingRoomList chattingRoomList = switch (room.getChattingRoom().getCategory()) {
                    case PRODUCT -> new ChattingRoomList(room.getChattingRoom().getChatRoomId(),
                            room.getChattingRoom().getCategory(), room.getChattingRoom().getProduct().getProductId(),
                            room.getChattingRoom().getProduct().getProductName(), room.getChattingRoom().getUserCount(),
                            room.getChattingRoom().getProduct().getPrice());
                    case TOGETHER -> new ChattingRoomList(room.getChattingRoom().getChatRoomId(),
                            room.getChattingRoom().getCategory(), room.getChattingRoom().getTogether().getTogetherId(),
                            room.getChattingRoom().getTogether().getTogetherName(), room.getChattingRoom().getUserCount(),
                            room.getChattingRoom().getTogether().getTotalPrice(),
                            //이렇게 unitPrice를 구하면 될지,,,
                            (int) Math.ceil(room.getChattingRoom().getTogether().getTotalPrice() / room.getChattingRoom().getUserCount()),
                            room.getChattingRoom().getTogether().getDeadline());
                };
                result.add(chattingRoomList);
            }
            else {
                Chatting chatting = chat.get();
                ChattingRoomList chattingRoomList = switch (room.getChattingRoom().getCategory()) {
                    case PRODUCT -> new ChattingRoomList(room.getChattingRoom().getChatRoomId(),
                            room.getChattingRoom().getCategory(), room.getChattingRoom().getProduct().getProductId(),
                            room.getChattingRoom().getProduct().getProductName(), room.getChattingRoom().getUserCount(),
                            room.getChattingRoom().getProduct().getPrice(), chatting.getMessage(), chatting.getCreatedAt());
                    case TOGETHER -> new ChattingRoomList(room.getChattingRoom().getChatRoomId(),
                            room.getChattingRoom().getCategory(), room.getChattingRoom().getTogether().getTogetherId(),
                            room.getChattingRoom().getTogether().getTogetherName(), room.getChattingRoom().getUserCount(),
                            room.getChattingRoom().getTogether().getTotalPrice(),
                            //이렇게 unitPrice를 구하면 될지,,,
                            (int) Math.ceil(room.getChattingRoom().getTogether().getTotalPrice() / room.getChattingRoom().getUserCount()),
                            room.getChattingRoom().getTogether().getDeadline(),
                            chatting.getMessage(), chatting.getCreatedAt());
                };
                result.add(chattingRoomList);
            }
        }
        result.sort(Comparator.comparing(ChattingRoomList::getCreatedAt).reversed());
        return result;
    }

    //채팅방 상세 조회
    public List<ChattingRoomDto> getChatRoomDetails(Long chatRoomId, int page, int size, User user){

        ChattingRoom chatroom = chattingRoomRepository.findByChatRoomId(chatRoomId);
        if(chatroom==null) throw new CustomException(ErrorCode.CHATROOM_NOT_FOUND);

        ChattingRoomUser chattingRoomUser = chattingRoomUserRepository.findByChatRoomAndUser(chatroom, user);
        if(chattingRoomUser==null) throw new CustomException(ErrorCode.CHATROOM_USER_NOT_FOUND);

        Pageable pageable = PageRequest.of(page, size);
        List<Chatting> chattings = chattingRepository.getChatting(chatRoomId, pageable);
        List<ChattingRoomDto> result = new ArrayList<>();

        for(Chatting chatting: chattings){
            ChattingRoomDto chattingRoomDto = new ChattingRoomDto(chatting.getUser(),
                    chatting.getMessage(), chatting.getCreatedAt());
            result.add(chattingRoomDto);
        }

        List<ChattingRoomUser> users = chattingRoomUserRepository.findByChattingRoom(chattingRoomRepository.findByChatRoomId(chatRoomId));

        User leader = null;
        for(ChattingRoomUser roomUser: users){
            if(roomUser!=null){
                if(roomUser.getIsLeader()){
                    leader = roomUser.getUser();
                    break;
                }
            }
        }

        if(leader!=null){
            for(ChattingRoomDto chattingRoomDto: result){
                if(chattingRoomDto.getUser()!=null){
                    if(chattingRoomDto.getUser().getUserId().equals(leader.getUserId())){
                        chattingRoomDto.setIsLeader(true);
                    }
                }
            }
        }

        //result.sort(Comparator.comparing(ChattingRoomDto::getCreatedAt).reversed());
        return result;
    }

    public ChatRoomInfo getChatRoomInfo(Long chatRoomId){

        ChattingRoom chatroom = chattingRoomRepository.findByChatRoomId(chatRoomId);
        if(chatroom==null) throw new CustomException(ErrorCode.CHATROOM_NOT_FOUND);

        ChattingRoom chattingRoom = chattingRoomRepository.findByChatRoomId(chatRoomId);
        ChatRoomInfo chatRoomInfo = switch (chattingRoom.getCategory()){
            case PRODUCT -> new ChatRoomInfo(chattingRoom.getProduct().getProductId(), chattingRoom.getCategory());
            case TOGETHER -> new ChatRoomInfo(chattingRoom.getTogether().getTogetherId(), chattingRoom.getCategory());
        };
        return chatRoomInfo;
    }
}

