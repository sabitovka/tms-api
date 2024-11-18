package io.sabitovka.tms.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {
    private final int expirationTime;
    private final Algorithm algorithm;

    public JwtTokenProvider(@Value("${security.jwt.secret}") String jwtSecret, @Value("${security.jwt.expirationMinutes}") int expirationTime) {
        algorithm = Algorithm.HMAC256(jwtSecret);
        this.expirationTime = expirationTime * 60 * 1000;
    }

    public String createToken(String username) {
        return JWT.create()
                .withIssuer("Task Management System API")
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .withJWTId(UUID.randomUUID()
                        .toString())
                .sign(algorithm);
    }

    public DecodedJWT decodeToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getUsernameFromToken(String token) {
        return decodeToken(token).getSubject();
    }
}
