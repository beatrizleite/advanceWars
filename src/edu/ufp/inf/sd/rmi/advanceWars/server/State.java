package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class State implements Serializable {
    private String state;
    ArrayList id;
    int[] chr;
    ArrayList users;
    int ply;
    int x; int y; String s;

    public State(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public State(ArrayList id, int[] chr, ArrayList users) {
        this.id = id;
        this.chr = chr;
    }

    public void currPlay(int ply, int x, int y, String s) {
        this.ply = ply;
        this.x = x;
        this.y = y;
        this.s = s;
    }

    public ArrayList getId() {
        return id;
    }

    public void setId(ArrayList id) {
        this.id = id;
    }

    public int[] getChr() {
        return chr;
    }

    public void setChr(int[] chr) {
        this.chr = chr;
    }

    public ArrayList getUsers() {
        return users;
    }

    public void setUsers(ArrayList users) {
        this.users = users;
    }

    public int getPly() {
        return ply;
    }

    public void setPly(int ply) {
        this.ply = ply;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
