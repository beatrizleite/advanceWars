package edu.ufp.inf.sd.rmi.advanceWars.client;

import menus.PlayerSelectionLobby;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObserverRI extends Remote {
    void updateObsState() throws RemoteException;

    public PlayerSelectionLobby getSelection() throws RemoteException;
    public void setSelection(PlayerSelectionLobby playerSelectionLobby) throws RemoteException;

    String getUsername() throws RemoteException;
}
