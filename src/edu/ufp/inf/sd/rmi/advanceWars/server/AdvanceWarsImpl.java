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
    private String map;
    private String username;
    private int max;
    private boolean running;

    public AdvanceWarsImpl(String map, String username) throws RemoteException {
        super();
        this.id = UUID.randomUUID();
        this.map = map;
        this.username = username;
        if (this.map.compareTo("SmallVs") == 0) {
            this.max = 2;
        } else if (this.map.compareTo("FourCorners") == 0) {
            this.max = 4;
        }
        this.running = false;
    }

    public UUID getId() throws RemoteException {
        return id;
    }

    public void setId(UUID id) throws RemoteException {
        this.id = id;
    }

    @Override
    public void attach(ObserverRI observerRI) throws RemoteException {
        if(!this.observers.contains(observerRI)) observers.add(observerRI);
    }

    @Override
    public void detach(ObserverRI observerRI) throws RemoteException {
        observers.remove(observerRI);
    }

    @Override
    public State getState() throws RemoteException {
        return state;
    }

    @Override
    public void setGameState(State state) throws RemoteException {
        this.state = state;
        notifyObs();
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

    @Override
    public int getObsId(String user) throws RemoteException {
        for (int i = 0; i < observers.size(); i++) {
            if(observers.get(i).getUser().equals(user)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public String getMap() {
        return map;
    }

    @Override
    public void setMap(String map) {
        this.map = map;
    }

    @Override
    public boolean isFull() throws RemoteException {
        return this.observers.size() >= this.max;
    }

    @Override
    public boolean isRunning() throws RemoteException {
        return this.running;
    }

    @Override
    public int howManyPlayers() {
        return observers.size();
    }


}
