package edu.ufp.inf.sd.rabbit.advanceWars.producer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DB extends UnicastRemoteObject {
    private static DB db = null;
    private final ArrayList<User> users;
    private final HashMap<String, GameSessionRI> sessions;
    //private final HashMap<Integer, GameSessionRI> sessionTokens;
    //private final HashMap<String, ObserverRI> observers;
    private final HashMap<UUID, AdvanceWarsRI> games;

    public DB() throws RemoteException {
        super();
        this.users = new ArrayList<>();
        this.sessions = new HashMap<>();
        this.games = new HashMap<>();


        //para testar
        User t1 = new User("1","1");
        User t2 = new User("2","2");
        User t3 = new User("3", "3");
        User u1 = new User("bea","bea");
        User u2 = new User("joana","joana");
        User u3 = new User("ines","ines");

        this.users.add(t1);
        this.users.add(t2);
        this.users.add(t3);
        this.users.add(u1);
        this.users.add(u2);
        this.users.add(u3);

        /*
        try {
            AdvanceWarsRI game1 = new AdvanceWarsImpl("SmallVs", t1.getName());
            AdvanceWarsRI game2 = new AdvanceWarsImpl("FourCorners", t2.getName());
            AdvanceWarsRI game3 = new AdvanceWarsImpl("SmallVs", t3.getName());
            games.put(game1.getId(), game1);
            games.put(game2.getId(), game2);
            games.put(game3.getId(), game3);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        */

    }

    public static synchronized DB getInstance() throws RemoteException {
        if (db == null) db = new DB();
        return db;
    }

    public void addSession(String user, GameSessionRI gameSessionRI) throws RemoteException {
        this.sessions.put(user, gameSessionRI);
        //falta o token
    }

    public void removeSession(String user) throws RemoteException {
        this.sessions.remove(user);
    }

    public boolean sessionExists(String user) {
        return this.sessions.containsKey(user);
    }

    public void addGame(AdvanceWarsRI game) throws RemoteException {
        games.put(game.getId(), game);
    }

    public void removeGame(AdvanceWarsRI game) throws RemoteException{
        games.remove(game.getId());
    }

    public GameSessionRI getSession(User user) {
        return this.sessions.get(user);
    }

    public AdvanceWarsRI getGame(UUID id) {
        return games.get(id);
    }

    public void updateGame(UUID id, AdvanceWarsRI game) {
        games.put(id, game);
    }

    public ArrayList<AdvanceWarsRI> getGames() {
        return new ArrayList<>(games.values());
    }
    public User getUser(String username) throws RemoteException {
        for(User u: this.users) {
            if(u.getName().compareTo(username) == 0) return u;
        }
        return null;
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

}
