package edu.ufp.inf.sd.rmi.advanceWars.client;

import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsRI;
import engine.Battle;
import engine.Game;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public interface ObserverRI extends Remote {

        public String getUser() throws RemoteException;

        public void setUser(String user) throws RemoteException;

        public int getChr() throws RemoteException;

        public void setChr(int chr) throws RemoteException;

        public Game getGame() throws RemoteException;

        public void setGame(Game game) throws RemoteException;

        public AdvanceWarsRI getGameLobby() throws RemoteException;
        public void setGameLobby(AdvanceWarsRI gameLobby) throws RemoteException;

        void update() throws RemoteException;

        void setBattle(Battle btl) throws RemoteException;
        void startGame() throws RemoteException;
}
