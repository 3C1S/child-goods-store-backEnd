package C1S.childgoodsstore.global.response;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

    private Integer code;
    private T data;

    public ApiResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // 성공 응답 생성
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(1000, data);
    }

}