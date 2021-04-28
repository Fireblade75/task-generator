package nl.firepy.taskgenerator.security;

import com.auth0.jwt.interfaces.Claim;
import lombok.*;
import nl.firepy.taskgenerator.common.errors.exceptions.TaskGenAppException;

import java.util.HashMap;
import java.util.Map;

@Data @Builder @AllArgsConstructor @ToString
public class TokenPayload {
    private static final String EMAIL_KEY = "email";

    private String email;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(EMAIL_KEY, email);
        return map;
    }

    public static TokenPayload fromMap(Map<String, Claim> map) {
        try {
            String email = map.get(EMAIL_KEY).as(String.class);
            return TokenPayload.builder()
                    .email(email)
                    .build();
        } catch (ClassCastException e) {
            throw new TaskGenAppException("Failed to parse JWT payload", e);
        }
    }
}
