package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {
    private DB db;
    private ArrayList<AdvanceWarsRI> games;
    private AdvanceWarsServer aws;
    private User user;
    private String username;

    protected GameSessionImpl(AdvanceWarsServer aws, User user) throws RemoteException {
        super();
        this.games = new ArrayList<>();
        this.aws = aws;
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
    public ArrayList<AdvanceWarsRI> getGames() throws RemoteException {
        return this.games;
    }

    @Override
    public AdvanceWarsRI createGame() throws RemoteException {
        AdvanceWarsRI advanceWarsRI = new AdvanceWarsImpl();
        games.add(advanceWarsRI);
        db.addGame(user.getName(), advanceWarsRI);
        return advanceWarsRI;
    }
}
