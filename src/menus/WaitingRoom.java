package menus;

import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsRI;
import engine.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.UUID;

public class WaitingRoom implements ActionListener {
    JButton Leave = new JButton("Leave");
    JLabel Waiting = new JLabel("Waiting for players...");
    UUID id;
    public WaitingRoom(UUID id) {
        this.id = id;
        Point size = MenuHandler.PrepMenu(400, 200);
        MenuHandler.HideBackground();
        Leave.setFont(new Font("Courier", Font.BOLD, 20));
        SetBounds(size);
        AddListeners();
        AddGui();
    }

    private void SetBounds(Point size) {
        Waiting.setBounds(170, 100, 380, 20);
        Leave.setBounds(size.x + 110, size.y + 170, 150, 32);
    }

    private void AddListeners() {
        Leave.addActionListener(this);
    }

    private void AddGui() {
        Game.gui.add(Waiting);
        Game.gui.add(Leave);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s == Leave) {
            try {
                Game.session.leaveGame(id, Game.observer);
                new Join();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
