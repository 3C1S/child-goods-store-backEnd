package C1S.childgoodsstore.auth.dto;

//123
public class LoginUser {

    private Long id;

    private LoginUser() {
    }

    public LoginUser(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
