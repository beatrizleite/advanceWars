package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface GameSessionRI extends Remote {
    User getUser() throws RemoteException;
    String getUsername() throws RemoteException;
    HashMap getGames() throws RemoteException;
    AdvanceWarsRI createGame() throws RemoteException;
}
