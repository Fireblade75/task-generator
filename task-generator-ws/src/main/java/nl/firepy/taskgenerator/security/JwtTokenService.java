package nl.firepy.taskgenerator.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtTokenService {
    private static final String issuer = "Firepy";
    Algorithm algorithm = Algorithm.HMAC256("ABAC17813");

    JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build();

    public String getToken(TokenPayload payload) {
        Instant now = LocalDateTime.now().toInstant(ZoneOffset.UTC);

        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(
                        Date.from(now))
                .withExpiresAt(
                        Date.from(now.plus(1, ChronoUnit.HOURS)))
                .withPayload(payload.toMap())
                .sign(algorithm);
    }

    public TokenPayload verify(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            return TokenPayload.fromMap(jwt.getClaims());
        } catch (JWTVerificationException exception){
            return null;
        }
    }
}
