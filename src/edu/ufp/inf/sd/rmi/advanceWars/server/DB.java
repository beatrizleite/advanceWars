package edu.ufp.inf.sd.rmi.advanceWars.server;

import edu.ufp.inf.sd.rmi.advanceWars.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DB extends UnicastRemoteObject {
    private static DB db = null;
    private final ArrayList<User> users;
    private final HashMap<String, GameSessionRI> sessions;
    //private final HashMap<Integer, GameSessionRI> sessionTokens;
    //private final HashMap<String, ObserverRI> observers;
    private final ArrayList<AdvanceWarsRI> games;

    public DB() throws RemoteException {
        super();
        this.users = new ArrayList<>();
        this.sessions = new HashMap<>();
        //this.observers = new HashMap<>();
        this.games = new ArrayList<>();
        //this.sessionTokens = new HashMap<>();

        //para testar
        User u1 = new User("123","123");
        User u2 = new User("bea","bea");
        User u3 = new User("joana","joana");
        User u4 = new User("ines","ines");

        this.users.add(u1);
        this.users.add(u2);
        this.users.add(u3);
        this.users.add(u4);

    }

    public static synchronized DB getInstance() throws RemoteException {
        if (db == null) db = new DB();
        return db;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean userExists(String username) throws RemoteException {
        for(User u: this.users) {
            if(u.getName().compareTo(username) == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean register(String username, String pwd) throws RemoteException {
        if(!userExists(username)) {
            users.add(new User(username, pwd));
            return true;
        }
        return false;
    }

    public void addSession(String user, GameSessionRI gameSessionRI) throws RemoteException {
        sessions.put(user, gameSessionRI);
        //falta o token
    }

    public void removeSession(String user, GameSessionRI gameSessionRI) throws RemoteException {
        sessions.remove(user, gameSessionRI);
    }

    public void addGame(String user, AdvanceWarsRI advanceWarsRI) {
        games.add(advanceWarsRI);
    }

    public void removeGame(String user, AdvanceWarsRI advanceWarsRI) {
        games.remove(advanceWarsRI);
    }

    /*
    public void addObs(String user, ObserverRI observerRI) {
        observers.put(user, observerRI);
    }

    public void removeObs(String user, ObserverRI observerRI) {
        observers.remove(user, observerRI);
    }

    */

    public User getUser(String username) throws RemoteException {
        for(User u: this.users) {
            if(u.getName().compareTo(username) == 0) return u;
        }
        return null;
    }
}
