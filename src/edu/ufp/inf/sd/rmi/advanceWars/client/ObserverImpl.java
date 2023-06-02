package edu.ufp.inf.sd.rmi.advanceWars.client;

import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsRI;
import edu.ufp.inf.sd.rmi.advanceWars.server.State;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {
    private String id;
    private AdvanceWarsClient advanceWarsClient;
    private State obsState;
    private AdvanceWarsRI advanceWarsRI;

    public ObserverImpl(String id, AdvanceWarsClient advanceWarsClient, AdvanceWarsRI advanceWarsRI) throws RemoteException {
        super();
        this.advanceWarsClient = advanceWarsClient;
        this.id = id;
        this.obsState = new State(id,"");
        this.advanceWarsRI = advanceWarsRI;
        this.advanceWarsRI.attach(this);
    }

    @Override
    public void update() throws RemoteException {
        this.obsState = advanceWarsRI.getState();
    }

    private State getObsState() {
        return this.obsState;
    }

}
