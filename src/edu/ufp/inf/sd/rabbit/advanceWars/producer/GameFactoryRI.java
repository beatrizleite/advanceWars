package edu.ufp.inf.sd.rabbit.advanceWars.producer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameFactoryRI extends Remote {
    public GameSessionRI login(String username, String pwd) throws RemoteException;
    public boolean register(String username, String pwd) throws RemoteException;

    boolean hasChannel();
}
