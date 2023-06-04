package menus;

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
import java.util.Objects;

public class Join implements ActionListener {
    String[] titles = {"Dev Team","TITLE HERE","Special Mentions"};
    String[] list1 = {"Serge-David"};//Main Developers
    String[] list2 = {"hithere"};//People who've helped
    String[] list3 = {"Google","Gamers like you"};

    JList<String> gamesList;
    JScrollPane gamesPane;
    DefaultListModel<String> gamesModel = new DefaultListModel<>();

    JButton Create = new JButton("Create New Game");
    JButton Join = new JButton("Join Existing Game");
    JButton Return = new JButton("Return");
    JButton Refresh = new JButton("Refresh");

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
        int max = 0;
        DefaultListModel<String> gameList = new DefaultListModel<>();
        for (AdvanceWarsRI game : Game.session.getGames()) {
            if (Objects.equals(game.getMap(), "SmallVs")) max=2;
            else if(Objects.equals(game.getMap(), "FourCorners")) max=4;
            String g = "Map: " +game.getMap() + "; State: " + game.getState() + "; Players online: " + game.getPlayers() + " out of max of " + max;
            gameList.addElement(g);
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
            //cria um novo jogo
            //new creatGame();
        } else if (s == Join) {
            int id = gamesList.getSelectedIndex();
            try {
                Game.session.getGame(id);
                //falta os observers
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
