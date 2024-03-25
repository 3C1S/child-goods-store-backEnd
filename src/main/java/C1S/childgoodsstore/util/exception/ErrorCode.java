package C1S.childgoodsstore.util.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //user
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "404", "해당 사용자가 존재하지 않습니다."),
    USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "409", "해당 이메일이 이미 존재합니다"),
    CHILD_NOT_FOUND(HttpStatus.NOT_FOUND, "404", "등록한 자녀가 없습니다."),

    //following
    FOLLOW_ALREADY(HttpStatus.CONFLICT, "409", "이미 팔로우하고 있습니다"),
    UNFOLLOW_ALREADY(HttpStatus.CONFLICT, "409", "이미 언팔로우한 상태입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
