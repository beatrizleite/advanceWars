package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {

    private DB db;
    private HashMap<String, GameSessionRI> sessions;
    private AdvanceWarsServer aws;
    public GameFactoryImpl(AdvanceWarsServer aws) throws RemoteException {
        super();
        this.sessions = new HashMap<>();
        this.aws = aws;
        this.db = DB.getInstance();
    }


    @Override
    public GameSessionRI login(String username, String pwd) throws RemoteException {
        if(db.userExists(username)) {
            if(!sessions.containsKey(username)) {
                GameSessionRI gameSessionRI = new GameSessionImpl(this.aws, db.getUser(username));
                this.sessions.put(username, gameSessionRI);
                System.out.println("We're in GameFactoryImpl and the user "+username+" just logged in!");
                db.addSession(username, gameSessionRI);

                return gameSessionRI;
            } else {
                return sessions.get(username);
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
    public HashMap<String, GameSessionRI> getSessions() throws RemoteException {
        return sessions;
    }

    @Override
    public void setSession(HashMap<String, GameSessionRI> sessions) throws RemoteException {
        this.sessions = sessions;
    }

    protected void removeSession(String username) throws RemoteException {
        this.sessions.remove(username);
        System.out.println("We're in GameFactoryImpl and the user "+username+" just logged out!");
    }
}
