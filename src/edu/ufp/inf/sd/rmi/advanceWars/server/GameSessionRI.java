package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface GameSessionRI extends Remote {
    User getUser() throws RemoteException;
    String getUsername() throws RemoteException;
    ArrayList<AdvanceWarsRI> getGames() throws RemoteException;
    AdvanceWarsRI createGame() throws RemoteException;
}
