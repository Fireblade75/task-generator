package nl.firepy.taskgenerator.common.security;

import com.auth0.jwt.interfaces.Claim;
import lombok.*;
import nl.firepy.taskgenerator.common.errors.exceptions.TaskGenAppException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data @Builder @AllArgsConstructor @ToString
public class TokenPayload {
    private static final String EMAIL_KEY = "email";
    private static final String ROLES_KEY = "roles";

    private String email;
    private List<String> roles;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(EMAIL_KEY, email);
        map.put(ROLES_KEY, roles);
        return map;
    }

    public static TokenPayload fromMap(Map<String, Claim> map) {
        try {
            String email = map.get(EMAIL_KEY).as(String.class);
            List<String> roles = map.get(ROLES_KEY).asList(String.class);
            return TokenPayload.builder()
                    .email(email)
                    .roles(roles)
                    .build();
        } catch (ClassCastException e) {
            throw new TaskGenAppException("Failed to parse JWT payload", e);
        }
    }
}
