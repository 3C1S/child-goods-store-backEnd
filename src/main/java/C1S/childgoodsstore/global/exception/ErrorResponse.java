package C1S.childgoodsstore.global.exception;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@NoArgsConstructor
public class ErrorResponse<T> {

    private Integer code;
    private String message;
    private T data;

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = Integer.parseInt(code);
        this.message = message;
        this.data = null;
    }
}