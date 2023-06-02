package edu.ufp.inf.sd.rmi.advanceWars.server;

import edu.ufp.inf.sd.rmi.advanceWars.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class AdvanceWarsImpl extends UnicastRemoteObject implements AdvanceWarsRI {
    private State state;
    private ArrayList<ObserverRI> observers = new ArrayList<>();

    AdvanceWarsImpl() throws RemoteException {
        super();
        this.state = new State("","");
    }

    @Override
    public void attach(ObserverRI observerRI) throws RemoteException {
        if(!this.observers.contains(observerRI)) this.observers.add(observerRI);
    }

    @Override
    public void detach(ObserverRI observerRI) throws RemoteException {
        this.observers.remove(observerRI);
    }

    @Override
    public State getState() throws RemoteException {
        return this.state;
    }

    @Override
    public void setGameState(State state) throws RemoteException {
        this.state = state;
    }

    public void notifyObs() throws RemoteException {
        for(ObserverRI obs : observers) {
            try {
                obs.update();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
