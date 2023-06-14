package edu.ufp.inf.sd.rmi.advanceWars.client;

import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsRI;
import edu.ufp.inf.sd.rmi.advanceWars.server.State;
import engine.Battle;
import engine.Game;
import menus.MenuHandler;
import players.Base;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {
    private String user;
    private int chr;
    private Game game;
    private AdvanceWarsRI gameLobby;
    private String state;
    private boolean waiting = false;

    public ObserverImpl(AdvanceWarsRI gameLobby, String user, int chr, Game game) throws RemoteException {
        super();
        this.user = user;
        this.chr = chr;
        this.game = game;
        this.gameLobby = gameLobby;
    }

    public String getUser() throws RemoteException {
        return user;
    }

    public void setUser(String user) throws RemoteException {
        this.user = user;
    }

    public int getChr()  throws RemoteException {
        return chr;
    }

    public void setChr(int chr)  throws RemoteException{
        this.chr = chr;
    }

    public Game getGame()  throws RemoteException{
        return game;
    }

    public void setGame(Game game) throws RemoteException {
        this.game = game;
    }

    public AdvanceWarsRI getGameLobby() throws RemoteException {
        return gameLobby;
    }


    public void update() throws RemoteException {
        state = gameLobby.getState();
        String curr = Game.gameLobby.getObs().get(Game.observer.getGameLobby().getTokenHolder()).getUser();
        System.out.println("Observer "+ curr +" state updated: "+ state);
        updates(state);
    }

    public static void updates(String move) throws RemoteException {
        if(Game.GameState == Game.State.PLAYING) {
            Base ply = Game.player.get(Game.btl.currentplayer);
            switch (move) {
                case "up" -> {
                    ply.selecty--;
                    if (ply.selecty < 0) ply.selecty++;
                }
                case "down" -> {
                    ply.selecty++;
                    if (ply.selecty >= Game.map.height) ply.selecty--;
                }
                case "left" -> {
                    ply.selectx--;
                    if (ply.selectx < 0) ply.selectx++;
                }
                case "right" -> {
                    ply.selectx++;
                    if (ply.selectx > Game.map.width) ply.selectx--;
                }
                case "select" -> Game.btl.Action();
                case "cancel" -> Game.player.get(Game.btl.currentplayer).Cancle();
                case "start" -> new menus.Pause();
                case "endturn" -> {
                    MenuHandler.CloseMenu();
                    Game.btl.EndTurn();
                }
                default -> {
                    if(move.startsWith("buy")) {
                        if(Game.gameLobby.getTokenHolder() == Game.btl.currentplayer ) {
                            if (Game.gameLobby.getObsId(Game.username) == Game.btl.currentplayer) {
                                String[] params = move.split(" ");
                                Game.btl.Buyunit(Integer.parseInt(params[1]),
                                        Integer.parseInt(params[2]),
                                        Integer.parseInt(params[3]));
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public void startGame() throws RemoteException {
        System.out.println(this.gameLobby.getId());
        System.out.println(Game.observer.getUser());
        game.startGame(this.gameLobby);
    }

    @Override
    public void setWaiting(boolean waiting) throws RemoteException {
        this.waiting = waiting;
    }

    @Override
    public boolean getWaiting() throws RemoteException {
        return waiting;
    }
}
