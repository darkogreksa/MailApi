package uns.ac.rs.MailApi.security.model;

public class UserAuthenticationToken {

    private String token;
    private Long expiresIn;

    public UserAuthenticationToken() {
        super();
        this.token = null;
        this.expiresIn = null;
    }

    public UserAuthenticationToken(String token, long expiresIn) {
        super();
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

}
