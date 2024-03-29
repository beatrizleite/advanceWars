package menus;

import edu.ufp.inf.sd.rmi.advanceWars.client.ObserverImpl;
import edu.ufp.inf.sd.rmi.advanceWars.client.ObserverRI;
import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsImpl;
import edu.ufp.inf.sd.rmi.advanceWars.server.GameSessionRI;
import engine.Game;
import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsRI;
import edu.ufp.inf.sd.rmi.advanceWars.server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.GlyphMetrics;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Join implements ActionListener {
    JList<String> gamesList;
    JScrollPane gamesPane;
    DefaultListModel<String> gamesModel = new DefaultListModel<>();

    JButton Create = new JButton("Create New Game");
    JButton Join = new JButton("Join Existing Game");
    JButton Return = new JButton("Return");
    JButton Refresh = new JButton("Refresh");

    private ArrayList<AdvanceWarsRI> games = new ArrayList<>();
    private Game game;


    public Join() throws RemoteException {
        Point size = MenuHandler.PrepMenu(280,280);
        MenuHandler.HideBackground();
        SetBounds(size);
        AddGui();
        AddListeners();
        gameList(size);
    }
    private void SetBounds(Point size) {
        Create.setBounds(size.x,size.y+10+38, 200, 32);
        Join.setBounds(size.x,size.y+10+38*2, 200, 32);
        Refresh.setBounds(size.x,size.y+10+38*3, 200, 32);
        Return.setBounds(size.x,size.y+10+38*4, 200, 32);
    }
    private void AddGui() {
        Game.gui.add(Create);
        Game.gui.add(Join);
        Game.gui.add(Refresh);
        Game.gui.add(Return);
    }
    private void AddListeners() {
        Return.addActionListener(this);
        Create.addActionListener(this);
        Refresh.addActionListener(this);
        Join.addActionListener(this);
    }

    private void gameList(Point size) throws RemoteException {
        gamesModel = getGames();
        gamesPane = new JScrollPane(gamesList = new JList<>(gamesModel));
        gamesPane.setBounds(size.x + 220, size.y + 10, 140, 260);
        Game.gui.add(gamesPane);
        gamesList.setBounds(new Rectangle(0, 0, 140, 260));
        gamesList.setSelectedIndex(0);
    }

    DefaultListModel<String> getGames() throws RemoteException {
        DefaultListModel<String> gameList = new DefaultListModel<>();
        int i = 0;
        String g;
        for (AdvanceWarsRI game : Game.session.getGames()) {
            if (game.howManyPlayers() != 0) {
                this.games.add(i, game);
                i++;
                if (game.howManyPlayers() == game.getMax()) {
                    g = "!!ONGOING!! "+game.getMap()+": "+ game.howManyPlayers() + "/"+game.getMax();
                    gameList.addElement(g);
                } else {
                    g = game.getMap()+": "+ game.howManyPlayers() + "/"+game.getMax();
                    gameList.addElement(g);
                }

            }

        }
        return gameList;
    }

    @Override public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s==Return) {
            new StartMenu();
        } else if (s == Refresh) {
            try {
                new Join();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        } else if (s == Create) {
            new CreateGameLobby();
        } else if (s == Join) {
            try {
                int chosen = gamesList.getSelectedIndex();
                UUID id = this.games.get(chosen).getId();
                AdvanceWarsRI gameLobby = Game.session.getGame(id);
                if (!gameLobby.isFull() && !gameLobby.isRunning()) {
                    Game.gameLobby = Game.session.getGame(id);
                    for (ObserverRI obs : Game.gameLobby.getObs()) {
                        if(Game.observer != null) {
                            if(obs.getUser().compareTo(Game.observer.getUser()) == 0) {
                                new WaitingRoom(Game.observer.getGameLobby().getId());
                            } else {
                                if (gameLobby.howManyPlayers()+1 == gameLobby.getMax()) {
                                    new PlayerSelectionLobby(Game.gameLobby.getMap(), id, true);
                                } else {
                                    Game.observer = new ObserverImpl(Game.gameLobby, Game.username, Game.chr, Game.game);
                                    Game.gameLobby.attach(Game.observer);
                                    new PlayerSelectionLobby(Game.gameLobby.getMap(), id, false);
                                }
                            }
                        } else {
                            if (gameLobby.howManyPlayers()+1 == gameLobby.getMax()) {
                                new PlayerSelectionLobby(Game.gameLobby.getMap(), id, true);
                            } else {
                                Game.observer = new ObserverImpl(Game.gameLobby, Game.username, Game.chr, Game.game);
                                Game.gameLobby.attach(Game.observer);
                                new PlayerSelectionLobby(Game.gameLobby.getMap(), id, false);
                            }
                        }

                    }



                } else {
                    Game.error.ShowError("Lobby is already full!");
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
