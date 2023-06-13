package edu.ufp.inf.sd.rmi.advanceWars.server;

import edu.ufp.inf.sd.rmi.advanceWars.client.ObserverRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface AdvanceWarsRI extends Remote {
    void attach(ObserverRI observerRI) throws RemoteException;
    void detach(ObserverRI observerRI) throws RemoteException;
    State getState() throws RemoteException;
    void setGameState(State state) throws RemoteException;
    UUID getId() throws RemoteException;
    void setId(UUID id) throws RemoteException;
    void notifyObs() throws RemoteException;
    int getObsId(String user) throws RemoteException;

    String getMap();
    void setMap(String map);

    boolean isFull() throws RemoteException;
    boolean isRunning() throws RemoteException;
    int howManyPlayers() throws RemoteException;
}
