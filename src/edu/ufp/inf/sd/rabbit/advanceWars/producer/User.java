package edu.ufp.inf.sd.rabbit.advanceWars.producer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class User extends UnicastRemoteObject {

    private String name;
    private String pwd;

    private Token token;

    public User(String name, String pwd) throws RemoteException {
        super();
        this.name = name;
        this.pwd = pwd;
        this.token = new Token();
    }

    private boolean checkToken() {
        return token.verify();
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
