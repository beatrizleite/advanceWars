package edu.ufp.inf.sd.rabbit.advanceWars.consumer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;
import edu.ufp.inf.sd.rabbit.advanceWars.producer.*;
import edu.ufp.inf.sd.rabbit.advanceWars.consumer.aw_game.engine.Game;
import edu.ufp.inf.sd.rabbit.advanceWars.consumer.aw_game.menus.MenuHandler;
import edu.ufp.inf.sd.rabbit.advanceWars.consumer.aw_game.players.Base;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {
    public transient Connection con;
    public transient Channel channel;
    private int chr;
    private Game game;
    private String user;

    public ObserverImpl(String user, int chr, Game game) throws IOException, TimeoutException {
        super();
        this.chr = chr;
        this.game = game;
        this.user = user;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        this.con = factory.newConnection();
        this.channel = con.createChannel();
        this.channel.queueDeclare("queue", false, false, false, null);
        listenQueue();
    }

    public void listenQueue() {
        try {
            this.channel.exchangeDeclare("fan_out", "fanout");
            String qname = this.channel.queueDeclare().getQueue();
            System.out.println(qname);
            channel.queueBind(qname, "fan_out", "");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String msg = new String(delivery.getBody(), "UTF-8");
                updates(msg);
            };
            this.channel.basicConsume(qname, true, deliverCallback, consumerTag -> {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public AdvanceWarsRI getGameLobby() throws RemoteException {
        return null;
    }

    @Override
    public void update() throws RemoteException {

    }

    public void updates(String move) throws RemoteException {
        System.out.println(move);

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
                case "start" -> new edu.ufp.inf.sd.rabbit.advanceWars.consumer.aw_game.menus.Pause();
                case "endturn" -> {
                    MenuHandler.CloseMenu();
                    Game.btl.EndTurn();
                }
                default -> {
                    String[] params = move.split(" ");
                    System.out.println(Arrays.toString(params));

                    Game.btl.Buyunit(Integer.parseInt(params[1]),
                                     Integer.parseInt(params[2]),
                                     Integer.parseInt(params[3]));
                }
            }
        }

    }

    @Override
    public void startGame() throws RemoteException {
        if (Game.gameFactoryRI.hasChannel()) {
            listenQueue();
        }
    }


    @Override
    public String getUser() throws RemoteException {
        return user;
    }

}

