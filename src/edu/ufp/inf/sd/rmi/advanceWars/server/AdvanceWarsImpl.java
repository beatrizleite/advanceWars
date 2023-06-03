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

    AdvanceWarsImpl(UUID id, ArrayList<GameSessionRI> listPlayers, int players, String map) throws RemoteException {
        super();
        this.state = new State("");
        this.id = id;
        this.listPlayers = listPlayers;
        this.players = players;
        this.curr_players = 0;
        this.map = map;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ArrayList<GameSessionRI> getListPlayers() {
        return listPlayers;
    }

    public void setListPlayers(ArrayList<GameSessionRI> listPlayers) {
        this.listPlayers = listPlayers;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getCurr_players() {
        return curr_players;
    }

    public void setCurr_players(int curr_players) {
        this.curr_players = curr_players;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
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
    public void setGameState(State state) throws RemoteException {
        this.state = state;
    }

    public void notifyObs() throws RemoteException {
        for(ObserverRI obs : observers) {
            try {
                obs.update();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
