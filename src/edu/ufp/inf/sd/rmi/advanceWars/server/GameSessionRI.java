package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameSessionRI extends Remote {
    User getUser() throws RemoteException;
    String getUsername() throws RemoteException;

}
