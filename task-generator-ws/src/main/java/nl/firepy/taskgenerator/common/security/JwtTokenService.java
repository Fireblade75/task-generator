package nl.firepy.taskgenerator.common.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Log4j2
@ApplicationScoped
public class JwtTokenService {
    private static final String issuer = "Firepy";
    Algorithm algorithm = Algorithm.HMAC256("ABAC17813");

    private final JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .acceptLeeway(1)
            .build();

    public String getToken(TokenPayload payload) {
        Instant now = OffsetDateTime.now(ZoneOffset.UTC).toInstant();

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
            log.info(exception);
            return null;
        }
    }

    public int getExpireTime() {
        return 60 * 60; // 1 hour
    }
}
