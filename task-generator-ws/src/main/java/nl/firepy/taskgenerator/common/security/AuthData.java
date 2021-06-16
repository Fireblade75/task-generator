package nl.firepy.taskgenerator.common.security;

import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.NotAuthorizedException;
import java.util.List;

@RequestScoped
@Log4j2
public class AuthData {

    private TokenPayload tokenPayload = null;

    public boolean isAuthenticated() {
        return tokenPayload != null;
    }

    public void setTokenPayload(TokenPayload tokenPayload) {
        this.tokenPayload = tokenPayload;
    }

    public String getEmail() {
        if(!isAuthenticated()) {
            log.info("Requested mail without setting token payload");
            throw new NotAuthorizedException("User not authenticated");
        }
        return tokenPayload.getEmail();
    }

    public List<String> getRoles() {
        if(!isAuthenticated()) {
            log.info("Requested roles without setting token payload");
            throw new NotAuthorizedException("User not authenticated");
        }
        return tokenPayload.getRoles();
    }
}
