package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {

    private DB db;
    private HashMap<String, GameSessionRI> sessions;
    private AdvanceWarsServer advanceWarsServer;

    public GameFactoryImpl(AdvanceWarsServer advanceWarsServer) throws RemoteException {
        super();
        this.sessions = new HashMap<>();
        this.advanceWarsServer = advanceWarsServer;
        this.db = DB.getInstance();
    }

    @Override
    public GameSessionRI login(String username, String pwd) throws RemoteException {
        if(db.userExists(username, pwd)) {
            if(!sessions.containsKey(username)) {
                GameSessionRI gameSessionRI = new GameSessionImpl(this.advanceWarsServer, db.getUser(username));
                this.sessions.put(username, gameSessionRI);
                System.out.println("We're in GameFactoryImpl and the user "+username+" logged in!");
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
        System.out.println("We're in GameFactoryImpl and the user "+username+" was jsut registered!");
        return db.register(username, pwd);
    }

    @Override
    public HashMap<String, GameSessionRI> getSessions() throws RemoteException {
        return null;
    }

    @Override
    public void setSessions(HashMap<String, GameSessionRI> sessions) throws RemoteException {

    }
}
