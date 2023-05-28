package edu.ufp.inf.sd.rmi.advanceWars.client;

import javax.swing.*;
import java.awt.*;

public class Login extends JPanel{
    private JLabel username;

    public Login() {
        this.setLayout(new GridLayout());
        this.setup();
    }

    private void setup() {
        username = new JLabel();
        username.setText("Username");
        this.add(username);
    }
}
