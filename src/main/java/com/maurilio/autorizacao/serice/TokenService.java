package com.maurilio.autorizacao.serice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.maurilio.autorizacao.security.UserData;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    public String gerarToken(UserData user){

        return JWT.create()
                .withIssuer("authorization")
                .withSubject(user.getUsername())
                .withClaim("username", user.getUsername())
                .withExpiresAt(LocalDateTime.now()
                                            .plusMinutes(30)
                                            .toInstant(ZoneOffset.of("-03:00")
                                        )
                ).sign(Algorithm.HMAC256("secret1010"));
    }

    public String getSubject(String token){
        return JWT.require(Algorithm.HMAC256("secret1010"))
                .withIssuer("authorization")
                .build().verify(token).getSubject();
    }
}
