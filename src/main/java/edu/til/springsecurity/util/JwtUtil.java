package edu.til.springsecurity.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.til.springsecurity.domain.User;

import java.util.Map;

public class JwtUtil {

    private static final String SECRET = "joshiSecret";
    private static final String ISSUER = "kangjoshi";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

    public static String create(User user) {
        return JWT.create()
                .withClaim("userId", user.getId())
                .withIssuer(ISSUER)
                .sign(ALGORITHM);
    }

    public static DecodedJWT verify(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(ALGORITHM)
                .withIssuer(ISSUER)
                .build();

        return jwtVerifier.verify(token);
    }

    public static Map<String, Claim> getAllPayload(String token) throws JWTVerificationException {
        DecodedJWT jwt = verify(token);
        return jwt.getClaims();
    }

}
