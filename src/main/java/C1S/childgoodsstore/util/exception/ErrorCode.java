package C1S.childgoodsstore.util.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //user
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "6404", "해당 사용자가 존재하지 않습니다."),
    USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "6409", "해당 이메일이 이미 존재합니다"),
    USER_EMAIL_MISMATCH(HttpStatus.BAD_REQUEST,"6400", "해당 이메일을 가진 사용자가 없습니다."),
    USER_NAME_EMAIL_MISMATCH(HttpStatus.BAD_REQUEST,"6400", "해당 이메일, 이름을 가진 사용자가 없습니다."),
    OTP_MISMATCH(HttpStatus.BAD_REQUEST,"6400", "인증 번호가 틀렸습니다."),
    OTP_NOT_FOUND(HttpStatus.NOT_FOUND,"6404", "인증 번호가 만료되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
