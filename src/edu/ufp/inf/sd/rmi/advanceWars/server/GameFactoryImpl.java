package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {
    AdvanceWarsServer aws;
    private DB db;
    private ArrayList<GameSessionRI> sessions;
    public GameFactoryImpl() throws RemoteException {
        super();
        this.sessions = new ArrayList<>();
        this.db = DB.getInstance();
    }

    @Override
    public GameSessionRI login(String username, String pwd) throws RemoteException {
        if(db.userExists(username)) {
            User user = db.getUser(username);
            if(db.sessionExists(username)) {
                throw new RemoteException("User is already logged in!");
            }
            if(!user.getToken().verify()) {
                user.getToken().update(username);
            }
            if(!db.sessionExists(username)) {
                GameSessionImpl session = new GameSessionImpl(this, user);
                System.out.println("We're in GameFactoryImpl and the user "+username+" just logged in!");
                return session;
            } else {
                throw new RemoteException();
            }
        }
        return null;
    }

    @Override
    public boolean register(String username, String pwd) throws RemoteException {
        System.out.println("We're in GameFactoryImpl and the user "+username+" was just registed");
        return db.register(username, pwd);
    }

    private GameSessionRI newSession(User user) throws RemoteException {
        return new GameSessionImpl(this, user);
    }

    private GameSessionRI getSession(User user) {
        return db.getSession(user);
    }


}
