package C1S.childgoodsstore.util.exception;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@NoArgsConstructor
public class ErrorResponse<T> {

    private String code;
    private String message;
    private T data;

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }
}