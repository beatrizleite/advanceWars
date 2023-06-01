package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {
    public GameFactoryImpl() throws RemoteException {
        super();
    }

    @Override
    public void test() throws RemoteException {
        System.out.println("Game Factory Implementation");
    }
}
