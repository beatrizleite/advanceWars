package edu.ufp.inf.sd.rmi.advanceWars.server;

import edu.ufp.inf.sd.rmi.advanceWars.client.ObserverRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Observer;

public interface AdvanceWarsRI extends Remote {
    void attach(ObserverRI observerRI) throws RemoteException;
    void detach(ObserverRI observerRI) throws RemoteException;
    State getState() throws RemoteException;
    void setGameState(State state) throws RemoteException;
}
