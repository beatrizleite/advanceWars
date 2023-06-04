package edu.ufp.inf.sd.rmi.advanceWars.client;

import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsRI;
import edu.ufp.inf.sd.rmi.advanceWars.server.State;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {
    private State obsState;
    private AdvanceWarsRI game;

    public ObserverImpl(AdvanceWarsRI game) throws RemoteException {
        super();
        this.obsState = new State("");
        this.game = game;
        this.game.attach(this);
    }

    @Override
    public void updateObsState() throws RemoteException {
        this.obsState = game.getState();
    }

    public AdvanceWarsRI getGame() {
        return game;
    }

    private State getObsState() throws RemoteException {
        return this.obsState;
    }

}
