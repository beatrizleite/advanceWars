package edu.ufp.inf.sd.rabbit.advanceWars.consumer;

import com.rabbitmq.client.Channel;
import edu.ufp.inf.sd.rabbit.advanceWars.producer.AdvanceWarsRI;
import edu.ufp.inf.sd.rabbit.advanceWars.consumer.aw_game.engine.Game;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObserverRI extends Remote {
    int getChr() throws RemoteException;
    void setChr(int chr) throws RemoteException;
    Game getGame() throws RemoteException;
    void setGame(Game game) throws RemoteException;
    AdvanceWarsRI getGameLobby() throws RemoteException;
    void update() throws RemoteException;
    void updates(String move) throws RemoteException;

    void startGame() throws RemoteException;
    String getUser() throws RemoteException;

}
