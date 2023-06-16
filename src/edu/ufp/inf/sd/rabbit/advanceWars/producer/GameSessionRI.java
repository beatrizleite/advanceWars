package edu.ufp.inf.sd.rabbit.advanceWars.producer;

import edu.ufp.inf.sd.rabbit.advanceWars.consumer.ObserverRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

public interface GameSessionRI extends Remote {
    ArrayList<AdvanceWarsRI> getGames() throws RemoteException;
    AdvanceWarsRI getGame(UUID id) throws RemoteException;
    AdvanceWarsRI createGame(String map, GameSessionRI gameSessionRI) throws RemoteException;
    void removeGame(AdvanceWarsRI game) throws RemoteException;
    void logout() throws RemoteException;
    void leaveGame(UUID id, ObserverRI observer) throws RemoteException;
    void deleteGame() throws RemoteException;
}
