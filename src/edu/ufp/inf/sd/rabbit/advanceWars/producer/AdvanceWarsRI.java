package edu.ufp.inf.sd.rabbit.advanceWars.producer;

import edu.ufp.inf.sd.rabbit.advanceWars.consumer.ObserverRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface AdvanceWarsRI extends Remote {
    void attach(ObserverRI observerRI) throws RemoteException;
    void detach(ObserverRI observerRI) throws RemoteException;
    String getState() throws RemoteException;
    void setGameState(String state) throws RemoteException;
    void setState(String state, ObserverRI obs) throws RemoteException;
    UUID getId() throws RemoteException;
    void setId(UUID id) throws RemoteException;
    void notifyObs() throws RemoteException;
    int getObsId(String user) throws RemoteException;
    String getMap() throws RemoteException;
    void setMap(String map) throws RemoteException;
    boolean isFull() throws RemoteException;
    boolean isRunning() throws RemoteException;
    int howManyPlayers() throws RemoteException;
    List<ObserverRI> getObs() throws RemoteException;
    void startGame() throws RemoteException;
    int getMax() throws RemoteException;
    int getTokenHolder() throws RemoteException;
}
