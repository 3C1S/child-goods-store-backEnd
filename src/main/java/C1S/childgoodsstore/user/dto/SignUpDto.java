package C1S.childgoodsstore.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SignUpDto {
    @NotNull(message = "이메일이 null 입니다.")
    @Email
    private final String email;

    @NotNull(message = "비밀번호가 null 입니다.")
    private final String password;

}
