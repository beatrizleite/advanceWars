package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Lobby implements Serializable {
    private UUID id;
    private ArrayList<GameSessionRI> listPlayers;
    private int players;
    private int curr_players;
    private String map;

    public Lobby(UUID id, ArrayList<GameSessionRI> listPlayers, int players, String map) {
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
}
