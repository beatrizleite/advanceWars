package edu.ufp.inf.sd.rmi.advanceWars.server;

import edu.ufp.inf.sd.rmi.advanceWars.client.ObserverRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

public interface AdvanceWarsRI extends Remote {
    void attach(ObserverRI observerRI) throws RemoteException;
    void detach(ObserverRI observerRI) throws RemoteException;


    State getState() throws RemoteException;
    void setGameState(State state, ObserverRI observer) throws RemoteException;
    void setGameState(State state) throws RemoteException;
    UUID getId() throws RemoteException;
    void setId(UUID id) throws RemoteException;
    ArrayList<GameSessionRI> getListPlayers() throws RemoteException;
    void setListPlayers(ArrayList<GameSessionRI> listPlayers) throws RemoteException;
    int getPlayers() throws RemoteException;
    void setPlayers(int players) throws RemoteException;
    int getCurr_players() throws RemoteException;
    void setCurr_players(int curr_players) throws RemoteException;
    String getMap() throws RemoteException;
    void setMap(String map) throws RemoteException;
    void notifyObs() throws RemoteException;
    public int getObsId(String user) throws RemoteException;
}
