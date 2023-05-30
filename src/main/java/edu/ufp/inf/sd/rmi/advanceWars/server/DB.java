package edu.ufp.inf.sd.rmi.advanceWars.server;

import edu.ufp.inf.sd.rmi.advanceWars.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DB extends UnicastRemoteObject {

    private static DB db = null;
    private final Set<User> users;
    private final HashMap<String, GameSessionRI> sessions;
    private final HashMap<Token, GameSessionRI> session_tokens;
    private final HashMap<String, ObserverRI> observers;
    private final HashMap<String, AdvanceWarsRI> games;
    public DB() throws RemoteException {
        super();
        this.users = new HashSet<>();
        this.sessions = new HashMap<>();
        this.observers = new HashMap<>();
        this.games = new HashMap<>();
        this.session_tokens = new HashMap<>();

        //para testar
        User u1 = new User("123","123");
        User u2 = new User("ines", "ines");
        User u3 = new User("bea", "bea");
        User u4 = new User("joana", "joana");

        //adicionar os users ao HashMap
        this.users.add(u1);
        this.users.add(u2);
        this.users.add(u3);
        this.users.add(u4);

    }

    public static synchronized DB getInstance() throws RemoteException {
        if (db == null)
            db = new DB();
        return db;
    }
    public Set<User> getUsers() {
        return users;
    }
    public boolean userExists(String username, String pwd) throws RemoteException {
        for (User user: this.users) {
            if(user.getName().compareTo(username) == 0 && user.getPwd().compareTo(pwd) == 0)
                return true;
        }
        return false;
    }

    public boolean register(String username, String pwd) throws RemoteException {
        if(!userExists(username, pwd)) {
            users.add(new User(username, pwd));
            return true;
        }
        return false;
    }

    public void addSession(String user, GameSessionRI session) throws RemoteException {
        sessions.put(user, session);
        User u = getUser(user);
        Token tok;
        tok = new Token(user);
        u.setToken(tok);
        session_tokens.put(tok, session);
    }

    public void removeSession(String user, GameSessionRI session) throws RemoteException {
        sessions.remove(user, session);
        User u = getUser(user);
        Token tok = u.getToken();
        session_tokens.remove(tok, session);
    }

    public void addGame(String user, AdvanceWarsRI advanceWarsRI) {
        games.put(user, advanceWarsRI);
    }
    public void removeGame(String user, AdvanceWarsRI advanceWarsRI) {
        games.remove(user, advanceWarsRI);
    }

    public void addObs(String user, ObserverRI observerRI) {
        observers.put(user, observerRI);
    }

    public void removeObs(String user, ObserverRI observerRI) {
        observers.remove(user, observerRI);
    }

    public User getUser(String username) throws RemoteException {
        for (User user : this.users) {
            if(user.getName().compareTo(username) == 0)
                return user;
        }
        return null;
    }
}
