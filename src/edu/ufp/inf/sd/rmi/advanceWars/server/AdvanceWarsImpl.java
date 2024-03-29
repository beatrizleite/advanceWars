package edu.ufp.inf.sd.rmi.advanceWars.server;

import edu.ufp.inf.sd.rmi.advanceWars.client.ObserverRI;
import engine.Game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class AdvanceWarsImpl extends UnicastRemoteObject implements AdvanceWarsRI {
    private String state;
    private List<ObserverRI> observers;
    private UUID id;
    private String map;
    private String username;
    private int max;
    private boolean running;
    private TokenRing tokenRing;
    private boolean waiting = false;

    public AdvanceWarsImpl(String map) throws RemoteException {
        super();
        this.id = UUID.randomUUID();
        this.map = map;
        this.observers = Collections.synchronizedList(new ArrayList<>());
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
    public List<ObserverRI> getObs() throws RemoteException {
        return observers;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public void attach(ObserverRI observerRI) throws RemoteException {
        if(this.howManyPlayers() < max && !this.getObs().contains(observerRI)) {
            this.getObs().add(observerRI);
        }
        if (this.howManyPlayers() == max) {
            startGame();
        }
    }

    @Override
    public void startGame() throws RemoteException {
        this.tokenRing = new TokenRing(max);
        for (ObserverRI obs : this.observers) {
            try {
                obs.startGame();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void detach(ObserverRI observerRI) throws RemoteException {
        this.getObs().remove(observerRI);
    }

    @Override
    public String getState() throws RemoteException {
        return state;
    }

    @Override
    public void setState(String state, ObserverRI obs) throws RemoteException {
            if(this.tokenRing.currentHolder() == this.observers.indexOf(obs)) {
                this.state = state;
                this.notifyObs();
            }
            if(state.compareTo("endturn") == 0) {
                this.tokenRing.nextHolder();
            }
    }

    public int getTokenHolder() throws RemoteException{
        return this.tokenRing.currentHolder();
    }
    @Override
    public void setGameState(String state) throws RemoteException {
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
    public String getMap() throws RemoteException {
        return this.map;
    }

    @Override
    public void setMap(String map) throws RemoteException{
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
