package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class State implements Serializable {
    private String state;
    private String id;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public State(String id, String state) {
        this.state = state;
        this.id = id;
    }


}
