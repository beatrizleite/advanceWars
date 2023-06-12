package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {
    GameFactoryImpl gameFactory;
    private User user;
    private String username;
    DB db;
    protected GameSessionImpl(GameFactoryImpl gameFactory, User user) throws RemoteException {
        super();
        this.gameFactory = gameFactory;
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
        return this.db.getGames();
    }

    private void addSession(String name, DB db) throws RemoteException {
        this.db.addSession(name, this);
    }

    public AdvanceWarsRI getGame(int id) throws RemoteException {
        return db.getGame(id);
    }
    @Override
    public AdvanceWarsRI createGame(String map, GameSessionRI gameSessionRI) throws RemoteException {
        AdvanceWarsRI game = new AdvanceWarsImpl(map, username);
        this.db.addGame(game);
        return game;
    }

    public void removeGame(AdvanceWarsRI game) throws RemoteException{
        this.db.removeGame(game);
    }
    public void logout() throws RemoteException {
        System.out.println("User "+username+ " logged out!");
        this.db.removeSession(this.username);
    }

}
