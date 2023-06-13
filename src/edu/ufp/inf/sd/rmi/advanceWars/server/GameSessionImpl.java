package edu.ufp.inf.sd.rmi.advanceWars.server;

import edu.ufp.inf.sd.rmi.advanceWars.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {
    GameFactoryImpl gameFactory;
    private User user;
    private String username;
    private ArrayList<AdvanceWarsRI> games = new ArrayList<>();
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

    public AdvanceWarsRI getGame(UUID id) throws RemoteException {
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

    @Override
    public void leaveGame(UUID id, ObserverRI observer) throws RemoteException {
        this.games = getGames();
        for (AdvanceWarsRI game: games) {
            if(game.getId() == id) {
                game.detach(observer);
                if(game.howManyPlayers() == 0) {
                    removeGame(game);
                }
            }
        }
    }

    @Override
    public synchronized void deleteGame() throws RemoteException {
        for (AdvanceWarsRI game : games) {
            if(game.howManyPlayers() == 0) {
                games.remove(game);
            }
        }
    }


}
