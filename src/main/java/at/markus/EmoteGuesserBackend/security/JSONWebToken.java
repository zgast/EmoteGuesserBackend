package at.markus.EmoteGuesserBackend.security;

import at.markus.EmoteGuesserBackend.document.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Lazy;

import java.util.Map;

public class JSONWebToken {
    private static Algorithm algorithm = Algorithm.HMAC256(Keys.JWT);
    @Lazy
    private static JWTVerifier verifier = JWT.require(algorithm).withIssuer("EmoteGuesser").build();

    public static  String issue(User user) {
        return JWT.create()
                .withIssuer("EmoteGuesser")
                .withClaim("username",user.getName())
                .withClaim("userID" ,user.getUserId())
                .sign(algorithm);
    }

    public  static Map<String, Claim> parse(String token){
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }
}
