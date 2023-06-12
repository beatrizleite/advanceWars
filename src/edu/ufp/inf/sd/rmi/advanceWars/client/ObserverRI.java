package edu.ufp.inf.sd.rmi.advanceWars.client;

import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsRI;
import engine.Game;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public interface ObserverRI extends Remote {

        public String getUser();

        public void setUser(String user);

        public int getChr();

        public void setChr(int chr);

        public Game getGame();

        public void setGame(Game game);

        public AdvanceWarsRI getGameLobby();
        public void setGameLobby(AdvanceWarsRI gameLobby);

        void update() throws RemoteException;
}
