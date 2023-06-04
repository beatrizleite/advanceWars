package edu.ufp.inf.sd.rmi.advanceWars.server;

import edu.ufp.inf.sd.rmi.advanceWars.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

public class AdvanceWarsImpl extends UnicastRemoteObject implements AdvanceWarsRI {
    private State state;
    private ArrayList<ObserverRI> observers = new ArrayList<>();

    private UUID id;
    private ArrayList<GameSessionRI> listPlayers;
    private int players;
    private int curr_players;
    private String map;
    private String firstPlayer;
    private TokenRing tr;

    AdvanceWarsImpl(String map, String firstPlayer) throws RemoteException {
        super();
        this.state = new State("");
        this.id = UUID.randomUUID();
        this.map = map;
        this.firstPlayer = firstPlayer;
        this.listPlayers = new ArrayList<>();
        this.curr_players = 0;
    }

    public UUID getId() throws RemoteException {
        return id;
    }

    public void setId(UUID id) throws RemoteException {
        this.id = id;
    }

    public ArrayList<GameSessionRI> getListPlayers()throws RemoteException {
        return listPlayers;
    }

    public void setListPlayers(ArrayList<GameSessionRI> listPlayers) throws RemoteException {
        this.listPlayers = listPlayers;
    }

    public int getPlayers() throws RemoteException {
        return players;
    }

    public void setPlayers(int players) throws RemoteException {
        this.players = players;
    }

    public int getCurr_players() throws RemoteException {
        return curr_players;
    }

    public void setCurr_players(int curr_players) throws RemoteException {
        this.curr_players = curr_players;
    }

    public String getMap() throws RemoteException {
        return map;
    }

    public void setMap(String map) throws RemoteException {
        this.map = map;
    }

    @Override
    public void attach(ObserverRI observerRI) throws RemoteException {
        if(!this.observers.contains(observerRI)) this.observers.add(observerRI);
    }

    @Override
    public void detach(ObserverRI observerRI) throws RemoteException {
        this.observers.remove(observerRI);
    }

    @Override
    public State getState() throws RemoteException {
        return this.state;
    }

    @Override
    public void setGameState(State state, ObserverRI observer) throws RemoteException {
        if (this.tr.currentHolder() == this.observers.indexOf(observer)) {
            this.state = state;
            this.notifyObs();
        }
        if (state.toString().compareTo("endturn") == 0) {
            this.tr.nextHolder();
        }
    }

    @Override
    public void setGameState(State state) throws RemoteException {
        this.state = state;
        this.notifyObs();
    }

    public void notifyObs() throws RemoteException {
        for(ObserverRI obs : observers) {
            try {
                obs.updateObsState();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getObsId(String user) throws RemoteException {
        for (int i = 0; i < observers.size(); i++) {
            if(observers.get(i).getUsername().equals(user)) {
                return i;
            }
        }
        return 0;
    }

}
