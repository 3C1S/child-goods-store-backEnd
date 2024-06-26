package C1S.childgoodsstore.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //auth
    EMAIL_AUTHENTICATION_FAILED(HttpStatus.BAD_REQUEST, "2001", "이메일로 전송된 인증번호와 입력한 인증번호가 일치하지 않습니다."),

    //user
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "3001", "해당 사용자가 존재하지 않습니다."),
    USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "3002", "해당 이메일이 이미 존재합니다"),
    CHILD_NOT_FOUND(HttpStatus.NOT_FOUND, "3003", "등록한 자녀가 없습니다."),

    //following
    FOLLOW_ALREADY(HttpStatus.CONFLICT, "4001", "이미 팔로우하고 있습니다"),
    UNFOLLOW_ALREADY(HttpStatus.CONFLICT, "4002", "이미 언팔로우한 상태입니다."),

    //review
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "5001", "해당 후기가 존재하지 않습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "5002", "주문 내역이 존재하지 않습니다."),

    //product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "6001", "해당 상품이 존재하지 않습니다."),
    PRODUCT_HEART_ALREADY(HttpStatus.CONFLICT, "6002", "이미 좋아요를 누른 상품입니다."),
    PRODUCT_UNHEART_ALREADY(HttpStatus.CONFLICT, "6003", "좋아요가 눌러져 있지 않은 상품입니다."),
    PRODUCT_NOT_OWNED(HttpStatus.BAD_REQUEST, "6004", "해당 상품에 접근 권한이 없습니다."),

    //together
    TOGETHER_NOT_FOUND(HttpStatus.BAD_REQUEST, "7001", "해당 공동구매 상품이 없습니다."),
    TOGETHER_NOT_OWNED(HttpStatus.BAD_REQUEST, "7002", "해당 공동구매 상품에 접근 권한이 없습니다."),

    //chatting
    INVALID_PRODUCT_CATEGORY(HttpStatus.BAD_REQUEST, "8001", "유효하지 않은 상품 카테고리입니다."),

    //child
    CHILD_NOT_OWNED(HttpStatus.BAD_REQUEST, "9001", "해당 자녀에 접근 권한이 없습니다."),

    //chatting
    CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "10001", " 해당 채팅방이 존재하지 않습니다."),
    CHATROOM_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "10002", "해당 채팅방에 사용자는 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}