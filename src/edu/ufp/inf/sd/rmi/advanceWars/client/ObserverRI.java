package edu.ufp.inf.sd.rmi.advanceWars.client;

import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsRI;
import engine.Battle;
import engine.Game;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public interface ObserverRI extends Remote {

        String getUser() throws RemoteException;
        void setUser(String user) throws RemoteException;
        int getChr() throws RemoteException;
        void setChr(int chr) throws RemoteException;
        Game getGame() throws RemoteException;
        void setGame(Game game) throws RemoteException;
        AdvanceWarsRI getGameLobby() throws RemoteException;
        void update() throws RemoteException;
        void startGame() throws RemoteException;
        void setWaiting(boolean waiting) throws RemoteException;
        boolean getWaiting() throws RemoteException;
}
