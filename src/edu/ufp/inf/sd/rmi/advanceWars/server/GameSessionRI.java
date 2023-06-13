package edu.ufp.inf.sd.rmi.advanceWars.server;

import edu.ufp.inf.sd.rmi.advanceWars.client.ObserverRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

public interface GameSessionRI extends Remote {
    User getUser() throws RemoteException;
    String getUsername() throws RemoteException;
    ArrayList<AdvanceWarsRI> getGames() throws RemoteException;
    AdvanceWarsRI getGame(UUID id) throws RemoteException;
    AdvanceWarsRI createGame(String map, GameSessionRI gameSessionRI) throws RemoteException;
    void removeGame(AdvanceWarsRI game) throws RemoteException;
    void logout() throws RemoteException;

    void leaveGame(UUID id, ObserverRI observer) throws RemoteException;
}
