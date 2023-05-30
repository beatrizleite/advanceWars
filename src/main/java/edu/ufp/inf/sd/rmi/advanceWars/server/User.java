package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class User implements Serializable {

    private String name;
    private String pwd;
    private Token token;

    public User(String name, String pwd, Token token) {
        this.name = name;
        this.pwd = pwd;
        this.token = token;
    }

    public User(String name, String pwd, String token) {
        this.name = name;
        this.pwd = pwd;
        this.token = new Token(token);
    }

    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public String getName() throws RemoteException {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
