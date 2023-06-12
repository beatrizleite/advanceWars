package edu.ufp.inf.sd.rmi.advanceWars.client;

import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsRI;
import edu.ufp.inf.sd.rmi.advanceWars.server.State;
import engine.Game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {
    private String user;
    private int chr;
    private Game game;
    private AdvanceWarsRI gameLobby;
    private State state;

    public ObserverImpl(AdvanceWarsRI gameLobby, String user, int chr, Game game) throws RemoteException {
        super();
        this.user = user;
        this.chr = chr;
        this.game = game;
        this.gameLobby = gameLobby;
        this.gameLobby.attach(this);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getChr() {
        return chr;
    }

    public void setChr(int chr) {
        this.chr = chr;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public AdvanceWarsRI getGameLobby() {
        return gameLobby;
    }

    public void setGameLobby(AdvanceWarsRI gameLobby) {
        this.gameLobby = gameLobby;
    }

    public void update() throws RemoteException {
        state = gameLobby.getState();
        System.out.println("Observer state updated: "+ state);
        Game.updates(state.getState());
    }
}
