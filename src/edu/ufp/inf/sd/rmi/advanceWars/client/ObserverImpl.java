package edu.ufp.inf.sd.rmi.advanceWars.client;

import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsRI;
import edu.ufp.inf.sd.rmi.advanceWars.server.State;
import engine.Battle;
import engine.Game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {
    private String user;
    private int chr;
    private Game game;
    private AdvanceWarsRI gameLobby;
    private String state;
    private Battle btl;
    private boolean waiting = false;

    public ObserverImpl(AdvanceWarsRI gameLobby, String user, int chr, Game game) throws RemoteException {
        super();
        this.user = user;
        this.chr = chr;
        this.game = game;
        this.gameLobby = gameLobby;
        this.gameLobby.attach(this);
    }

    public String getUser() throws RemoteException {
        return user;
    }

    public void setUser(String user) throws RemoteException {
        this.user = user;
    }

    public int getChr()  throws RemoteException {
        return chr;
    }

    public void setChr(int chr)  throws RemoteException{
        this.chr = chr;
    }

    public Game getGame()  throws RemoteException{
        return game;
    }

    public void setGame(Game game) throws RemoteException {
        this.game = game;
    }

    public AdvanceWarsRI getGameLobby() throws RemoteException {
        return gameLobby;
    }


    public void update() throws RemoteException {
        state = gameLobby.getState();
        System.out.println("Observer state updated: "+ state);
        Game.updates(state);
    }

    @Override
    public void startGame() throws RemoteException {
        game.startGame(this.gameLobby);
    }

    @Override
    public void setWaiting(boolean waiting)throws RemoteException {
        this.waiting = waiting;
    }

    @Override
    public boolean getWaiting()throws RemoteException {
        return waiting;
    }
}
