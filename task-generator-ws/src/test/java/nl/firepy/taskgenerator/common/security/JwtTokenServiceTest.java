package nl.firepy.taskgenerator.common.security;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
class JwtTokenServiceTest {

    private static JwtTokenService jwtTokenService;

    @BeforeAll
    static void init() {
        jwtTokenService = new JwtTokenService();
    }

    @Test
    void getToken() {
        TokenPayload payload = TokenPayload.builder().email("info@example.com").build();
        String[] token = jwtTokenService.getToken(payload).split("\\.");
        byte[] decodedPayload = Base64.getDecoder().decode(token[1].getBytes(StandardCharsets.UTF_8));

        JsonParser parser = Json.createParser(new ByteArrayInputStream(decodedPayload));
        parser.next();
        JsonObject jsonObject = parser.getObject();

        assertThat(jsonObject.getString("email")).isEqualTo("info@example.com");
        assertThat(jsonObject.getString("iss")).isEqualTo("Firepy");
    }

    @Test
    void verifyNewToken() {
        TokenPayload payload = TokenPayload.builder().email("info@example.com").build();
        TokenPayload tokenPayload = jwtTokenService.verify(jwtTokenService.getToken(payload));
        assertThat(tokenPayload).isNotNull();
        assertThat(tokenPayload.getEmail()).isEqualTo("info@example.com");
    }

    @Test
    void verifyOldToken() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJGaXJlcHkiLCJleHAiOjE2MTk3MDY0MjksImlhdCI6MTYxOTcwNjQyOCwiZW1haWwiOiJpbmZvQGV4YW1wbGUuY29tIn0.NeIPGtWAP7KMM6shqC8N_q3MRsOOXg9YALAfi1oqm9o";
        TokenPayload tokenPayload = jwtTokenService.verify(token);
        assertThat(tokenPayload).isNull();
    }

    @Test
    void verifyBrokenToken() {
        String token = "94a08da1fecbb6e8b46990538c7b50b2";
        TokenPayload tokenPayload = jwtTokenService.verify(token);
        assertThat(tokenPayload).isNull();
    }
}