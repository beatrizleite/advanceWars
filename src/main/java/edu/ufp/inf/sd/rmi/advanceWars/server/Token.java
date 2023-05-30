package edu.ufp.inf.sd.rmi.advanceWars.server;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;;

public class Token {
    private String token;
    public Token(String user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(user);
            this.token = JWT.create()
                    .withIssuer(user)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            // Invalid Signing configuration / Couldn't convert Claims.
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
