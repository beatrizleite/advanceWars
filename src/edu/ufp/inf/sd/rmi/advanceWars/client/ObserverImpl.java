package edu.ufp.inf.sd.rmi.advanceWars.client;

import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsRI;
import edu.ufp.inf.sd.rmi.advanceWars.server.State;
import engine.Game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {
    private State obsState;
    private Game game;
    private AdvanceWarsRI gameLobby;
    private String username;


    public ObserverImpl(AdvanceWarsRI gameLobby) throws RemoteException {
        super();
        this.gameLobby = gameLobby;
        this.gameLobby.attach(this);
    }

    @Override
    public void updateObsState() throws RemoteException {
        this.obsState = gameLobby.getState();
    }

    public AdvanceWarsRI getGame() {
        return gameLobby;
    }

    private State getObsState() throws RemoteException {
        return this.obsState;
    }

}
