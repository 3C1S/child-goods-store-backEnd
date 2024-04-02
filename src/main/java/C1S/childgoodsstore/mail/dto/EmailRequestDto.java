package C1S.childgoodsstore.mail.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequestDto {

    @Email
    //1) @기호를 포함해야 한다.
    //2) @기호를 기준으로 이메일 주소를 이루는 로컬호스트와 도메인 파트가 존재해야 한다.
    //3) 도메인 파트는 최소 하나의 점과 그 뒤에 최소한 2개의 알파벳을 가진다는 것을 검증한다.
    @NotEmpty(message = "ariel020918@naver.com")
    private String email;
}