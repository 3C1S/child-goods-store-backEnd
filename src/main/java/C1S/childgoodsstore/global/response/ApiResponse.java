package C1S.childgoodsstore.global.response;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

    private Integer code;
    private T data;

    public ApiResponse(String code, T data) {
        this.code = Integer.parseInt(code);
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = Integer.parseInt(code);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // 성공 응답 생성
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(String.valueOf(HttpStatus.OK.value()), data);
    }

}