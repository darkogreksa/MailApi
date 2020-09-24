package uns.ac.rs.MailApi.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private String token;
    private UserDetails principal;

    public AuthenticationToken(UserDetails principal) {
        super(principal.getAuthorities());
        this.principal = principal;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

}
