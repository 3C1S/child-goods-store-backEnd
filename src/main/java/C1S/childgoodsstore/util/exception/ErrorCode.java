package C1S.childgoodsstore.util.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //user
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "404", "해당 사용자가 존재하지 않습니다."),
    USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "409", "해당 이메일이 이미 존재합니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
