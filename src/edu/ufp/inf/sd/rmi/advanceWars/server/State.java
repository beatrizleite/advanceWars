package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.io.Serializable;

public class State implements Serializable {
    private String state;

    public State(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
