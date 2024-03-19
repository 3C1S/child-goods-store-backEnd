package C1S.childgoodsstore.entity;

import lombok.Data;

@Data
public class Response {

    private int code;
    private String message;
    private Object data;

    public Response()
    {
        this.code = 4000;
        this.data = null;
        this.message = null;
    }
}
