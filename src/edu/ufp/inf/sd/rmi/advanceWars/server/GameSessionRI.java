package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GameSessionRI extends Remote {
    User getUser() throws RemoteException;
    String getUsername() throws RemoteException;
    ArrayList<AdvanceWarsRI> getGames() throws RemoteException;
    AdvanceWarsRI getGame(int id) throws RemoteException;
    AdvanceWarsRI createGame(String map, GameSessionRI gameSessionRI) throws RemoteException;
    void removeGame(AdvanceWarsRI game) throws RemoteException;
    void logout() throws RemoteException;

}
