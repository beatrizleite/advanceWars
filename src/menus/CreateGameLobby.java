package menus;

import edu.ufp.inf.sd.rmi.advanceWars.client.ObserverImpl;
import engine.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.UUID;


public class CreateGameLobby implements ActionListener {
    public JButton map1 = new JButton("SmallVs(2 players)");
    public JButton map2 = new JButton("FourCorners(4 players)");
    public JButton Return = new JButton("Return");

    public CreateGameLobby() {
        Point size = MenuHandler.PrepMenu(280,280);
        MenuHandler.HideBackground();
        SetBounds(size);
        AddGui();
        AddListeners();
    }

    private void SetBounds(Point size) {
        map1.setBounds(new Rectangle(size.x, size.y + 10, 200, 32));
        map2.setBounds(new Rectangle(size.x, size.y + 10 + 38, 200, 32));
        Return.setBounds(new Rectangle(size.x, size.y + 10 + 38*2, 100, 32));
    }

    private void AddGui() {
        Game.gui.add(map1);
        Game.gui.add(map2);
        Game.gui.add(Return);
    }

    private void AddListeners() {
        map1.addActionListener(this);
        map2.addActionListener(this);
        Return.addActionListener(this);

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object s = actionEvent.getSource();
        if (s == map1) {
            try {
                Game.gameLobby = Game.session.createGame("SmallVs", Game.session);
                UUID id = Game.gameLobby.getId();
                Game.observer = new ObserverImpl(Game.gameLobby, Game.username, Game.chr, Game.game);
                Game.gameLobby.attach(Game.observer);
                new PlayerSelectionLobby("SmallVs", id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (s == map2) {
            try {
                Game.gameLobby = Game.session.createGame("FourCorners", Game.session);
                UUID id = Game.gameLobby.getId();
                Game.observer = new ObserverImpl(Game.gameLobby, Game.username, Game.chr, Game.game);
                Game.gameLobby.attach(Game.observer);
                new PlayerSelectionLobby("FourCorners", id);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else if (s == Return) {
            try {
                new Join();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
