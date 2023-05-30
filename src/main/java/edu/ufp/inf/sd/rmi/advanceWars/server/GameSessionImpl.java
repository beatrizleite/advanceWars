package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {

    private DB db;
    private HashMap<String, AdvanceWarsRI> games;
    private AdvanceWarsServer advanceWarsServer;
    private User user;
    private String username;

    protected GameSessionImpl(AdvanceWarsServer advanceWarsServer, User user) throws RemoteException {
        super();
        this.games = new HashMap<>();
        this.advanceWarsServer = advanceWarsServer;
        this.user = user;
        this.username = user.getName();
        this.db = DB.getInstance();
    }

    @Override
    public User getUser() throws RemoteException {
        return this.user;
    }

    @Override
    public String getUsername() throws RemoteException {
        return this.username;
    }

    @Override
    public HashMap getGames() throws RemoteException {
        return this.games;
    }

    @Override
    public AdvanceWarsRI createGame() throws RemoteException {
        AdvanceWarsRI advanceWarsRI = new AdvanceWarsImpl();
        games.put(user.getName(), advanceWarsRI);
        db.addGame(user.getName(), advanceWarsRI);
        return advanceWarsRI;
    }

}
