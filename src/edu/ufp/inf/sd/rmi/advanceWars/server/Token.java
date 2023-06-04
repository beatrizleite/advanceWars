package edu.ufp.inf.sd.rmi.advanceWars.server;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.sql.Date;
import java.time.Instant;

public class Token {

    private String issuer = "advanceWarsService";
    private int exp = 60;
    private String token;
    public Token(String user) {
        this.token = "token";
    }

    public Token() {
        this.token = this.generateToken();
    }

    private String generateToken() {
        return JWT.create().withIssuer(issuer).withIssuedAt(Date.from(Instant.now())).sign(Algorithm.HMAC256(issuer));
    }

    public boolean verify() {
        DecodedJWT dec_token = JWT.decode(token);
        return dec_token.getIssuer().equals(issuer);
    }

}
