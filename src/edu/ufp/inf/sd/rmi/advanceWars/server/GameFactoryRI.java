package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface GameFactoryRI extends Remote {
    public GameSessionRI login(String username, String pwd) throws RemoteException;
    public boolean register(String username, String pwd) throws RemoteException;
    ArrayList<GameSessionRI> getSessions() throws RemoteException;
    void setSession(ArrayList<GameSessionRI> sessions) throws RemoteException;

}
