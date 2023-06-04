package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {

    private DB db;
    private ArrayList<GameSessionRI> sessions;
    private AdvanceWarsServer aws;
    public GameFactoryImpl(AdvanceWarsServer aws) throws RemoteException {
        super();
        this.sessions = new ArrayList<>();
        this.aws = aws;
        this.db = DB.getInstance();
    }


    @Override
    public GameSessionRI login(String username, String pwd) throws RemoteException {
        if(db.userExists(username)) {
            GameSessionImpl gameSession = new GameSessionImpl(this.aws, db.getUser(username));
            System.out.println("We're in GameFactoryImpl and the user "+username+" just logged in!");
            return gameSession;
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
