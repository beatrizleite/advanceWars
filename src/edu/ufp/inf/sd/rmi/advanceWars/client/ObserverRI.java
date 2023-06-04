package edu.ufp.inf.sd.rmi.advanceWars.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObserverRI extends Remote {
    void updateObsState() throws RemoteException;
}
