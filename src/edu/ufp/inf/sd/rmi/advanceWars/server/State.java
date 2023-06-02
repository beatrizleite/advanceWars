package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.io.Serializable;

public class State implements Serializable {
    private String info;
    private String id;

    public State(String id, String info) {
        this.id = id;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}