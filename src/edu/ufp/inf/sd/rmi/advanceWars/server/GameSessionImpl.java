package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {
    private DB db;
    private ArrayList<AdvanceWarsRI> games;
    private User user;
    private String username;

    protected GameSessionImpl(DB db, User user) throws RemoteException {
        super();
        this.games = new ArrayList<>();
        this.user = user;
        this.username = user.getName();
        this.db = DB.getInstance();
        addSession(username, db);
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
        return this.db.getGames();
    }

    private void addSession(String name, DB db) throws RemoteException {
        this.db.addSession(name, this);
    }

    public AdvanceWarsRI getGame(int id) throws RemoteException {
        return db.getGame(id);
    }
    @Override
    public AdvanceWarsRI createGame(int players, String map, GameSessionRI gameSessionRI) throws RemoteException {
        ArrayList<AdvanceWarsRI> games;
        AdvanceWarsRI game = new AdvanceWarsImpl(map, username);
        game.setGameState(new State("waiting"));
        games = this.db.getGames();
        games.add(game);
        this.db.addGame(game);
        return game;
    }

    public void removeGame(AdvanceWarsRI game) throws RemoteException{
        this.db.removeGame(game);
    }
    public void logout() throws RemoteException {
        this.db.removeSession(this.username);
    }

}
