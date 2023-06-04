package menus;

import engine.Game;
import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsRI;
import edu.ufp.inf.sd.rmi.advanceWars.server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.GlyphMetrics;
import java.rmi.RemoteException;
import java.util.Objects;


public class Login implements ActionListener {

    private JButton Login = new JButton("Log In");
    private JButton Register = new JButton("Register");
    private JButton Exit = new JButton("Exit");
    private JTextField Username = new JTextField("Username");
    private JTextField Pwd = new JTextField("Password");


    public Login() {
        Point size = MenuHandler.PrepMenu(280, 280);
        MenuHandler.HideBackground();
        setBounds(size);
        AddListeners();
        AddGui();
    }

    private void setBounds(Point size) {
        Username.setBounds(size.x,size.y+10+38, 200, 32);
        Pwd.setBounds(size.x,size.y+10+38*2, 200, 32);
        Login.setBounds(size.x,size.y+10+38*3, 200, 32);
        Register.setBounds(size.x,size.y+10+38*4, 200, 32);
        Exit.setBounds(size.x,size.y+10+38*5, 200, 32);
    }
    private void AddGui() {
        Game.gui.add(Username);
        Game.gui.add(Pwd);
        Game.gui.add(Login);
        Game.gui.add(Register);
        Game.gui.add(Exit);
    }
    private void AddListeners() {
        Username.addActionListener(this);
        Username.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                if (Username.getText().equals("Username")) {
                    Username.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (Username.getText().equals("")) {
                    Username.setText("Username");
                }
            }
        });

        Pwd.addActionListener(this);
        Pwd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                if (Pwd.getText().equals("Password")) {
                    Pwd.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (Pwd.getText().equals("")) {
                    Pwd.setText("Password");
                }
            }
        });
        Login.addActionListener(this);
        Register.addActionListener(this);
        Exit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s==Login) {
            String un = Username.getText();
            String pw = Pwd.getText();
            try {
                Game.session = Game.gameFactoryRI.login(un, pw);
                Game.username = un;
                new StartMenu();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        } else if (s == Register) {
            String un = Username.getText();
            String pw = Pwd.getText();
            try {
                if (Game.gameFactoryRI.register(un, pw)) {
                    Game.session = Game.gameFactoryRI.login(un, pw);
                    Game.username = un;
                    new StartMenu();
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        } else if (s == Exit) {
            System.exit(0);
        }
    }
}
