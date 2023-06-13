package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class State implements Serializable {
    private String state;
    private UUID id;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public State(UUID id, String state) {
        this.state = state;
        this.id = id;
    }

    public State(String state) {
        this.state = state;
    }


}
