package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {
    AdvanceWarsServer aws;
    private DB db;
    private ArrayList<GameSessionRI> sessions;
    public GameFactoryImpl(AdvanceWarsServer aws) throws RemoteException {
        super();
        this.aws = aws;
        this.sessions = new ArrayList<>();
        this.db = DB.getInstance();
    }


    @Override
    public GameSessionRI login(String username, String pwd) throws RemoteException {
        User user = db.getUser(username);
        if(db.userExists(username)) {
            if(!user.getToken().verify()) {
                user.getToken().update(username);
            }
            if(!db.sessionExists(username)) {
                GameSessionImpl session = new GameSessionImpl(aws, user);
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

    @Override
    public ArrayList<GameSessionRI> getSessions() throws RemoteException {
        return sessions;
    }

    @Override
    public void setSession(ArrayList<GameSessionRI> sessions) throws RemoteException {
        this.sessions = sessions;
    }

    protected void removeSession(String username) throws RemoteException {
        this.sessions.remove(username);
        System.out.println("We're in GameFactoryImpl and the user "+username+" just logged out!");
    }
}
