package edu.ufp.inf.sd.rmi.advanceWars.server;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWTCreator;

import java.sql.Date;
import java.time.Instant;

public class Token {

    //https://www.youtube.com/watch?v=Q6HaAgOB6_Q
    private String issuer = "advanceWarsService";
    private int exp = 60;
    private String token;

    public Token(String user) {
        //this.token = generateToken(user);
        this.token = user;
    }

    public Token() {
        //this.token = this.generateToken();
        this.token = issuer;
    }

    private String generateToken(String issuer) {
        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(Date.from(Instant.now()))
                .sign(Algorithm.HMAC256(issuer));
    }

    private String generateToken() {
        return JWT.create()
                .withIssuer(this.issuer)
                .withIssuedAt(Date.from(Instant.now()))
                .sign(Algorithm.HMAC256(this.issuer));
    }

    public boolean verify() {
        /*
        DecodedJWT dec_token = JWT.decode(token);
        return dec_token.getIssuer().equals(issuer);
         */
        return true;
    }

    public void update(String issuer) {
        this.token = generateToken(issuer);
    }

}
