/*package edu.ufp.inf.sd.rmi.advanceWars.client;

import edu.ufp.inf.sd.rmi.advanceWars.server.*;
import edu.ufp.inf.sd.rmi.advanceWars.util.rmisetup.SetupContextRMI;

import javax.swing.*;
import engine.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdvanceWarsClient_interface extends JFrame {

    private SetupContextRMI contextRMI;
    private GameFactoryRI gameFactoryRI;
    private DB db;
    private GameSessionRI gameSessionRI;
    private AdvanceWarsRI advanceWarsRI;
    private ObserverRI observerRI;
    private String username;

    public AdvanceWarsClient_interface(String[] args) throws RemoteException {
        this.db= DB.getInstance();
        initContext(args);
        this.lookup();
        login();
        menu();
        //para mostrar utilizadores online
        //online_users();

        //initObserver(args);
    }

    private void startGame() {
        new Game(this.gameFactoryRI);
    }

    private void initContext(String args[]) {
        try {
            //List ans set args
            SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            //Create a context for RMI setup
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(AdvanceWarsClient_interface.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    private void lookup() {
        try {
            //Get proxy MAIL_TO_ADDR rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                // Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR lookup service @ {0}", serviceUrl);

                //============ Get proxy MAIL_TO_ADDR HelloWorld service ============
                this.gameFactoryRI = (GameFactoryRI) registry.lookup(serviceUrl);
            //} else {
                //Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initObserver(String args[]) {
        try {
            observerRI = new ObserverImpl(this.gameSessionRI.getUsername(), this, this.advanceWarsRI);
        } catch (RemoteException e) {
            Logger.getLogger(AdvanceWarsClient_interface.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void login() throws RemoteException {
        String choice = JOptionPane.showInputDialog("Enter 'l' for Login or 'r' for register: ");
        String username, password;
        switch (choice) {
            case "r" -> {
                try {
                    username = JOptionPane.showInputDialog("Username: ");
                    password = JOptionPane.showInputDialog("Password: ");
                    gameFactoryRI.register(username, password);
                    gameSessionRI = gameFactoryRI.login(username, password);
                    this.username = username;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "l" -> {
                try {
                    username = JOptionPane.showInputDialog("Username: ");
                    password = JOptionPane.showInputDialog("Password: ");
                    this.gameSessionRI = gameFactoryRI.login(username, password);
                    this.username = username;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            default -> {
                JOptionPane.showMessageDialog(new JFrame(), "Not a valid option!", "Dialog", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void menu() {
        String map1 = "SmallVs", map2 = "FourCorners";
        String choice = JOptionPane.showInputDialog("Write 'create' to create a new lobby or 'join' to join an existing lobby: ");
        switch(choice) {
            case "create":
                String map = JOptionPane.showInputDialog("Choose map: \n-SmallVs (2 players)-> write 'SmallVS'\n-FourCorners (4 players)-> write 'FourCorners'");
                switch (map) {
                    case "SmallVs" -> {
                        try {
                            gameSessionRI.createGame(2, map1, this.gameSessionRI);
                            startGame();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    case "FourCorners" -> {
                        try {
                            this.gameSessionRI.createGame(2, map2, this.gameSessionRI);
                            startGame();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    default ->
                            JOptionPane.showMessageDialog(new JFrame(), "Not a valid option!", "Dialog", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "join":


                break;


            default:
                JOptionPane.showMessageDialog(new JFrame(), "Not a valid option!", "Dialog", JOptionPane.ERROR_MESSAGE);
        }

    }

    private JButton refresh;
    private JList listUsers;

    private void online_users() throws RemoteException {
        listUsers = new JList(getSessions());
        refresh = new JButton("Refresh");

        setPreferredSize(new Dimension(300,400));
        setLayout(null);

        add(listUsers);
        add(refresh);

        listUsers.setBounds(30, 35, 200, 95);
        refresh.setBounds(165, 390, 100, 25);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    updateUsers();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private JButton refreshGames;
    private JList listGames;
    private void online_games() throws RemoteException {
        listGames = new JList(getGames());
        refreshGames = new JButton("Refresh");

        setPreferredSize(new Dimension(300,400));
        setLayout(null);

        add(listUsers);
        add(refresh);

        listGames.setBounds(30, 35, 200, 95);
        refreshGames.setBounds(165, 390, 100, 25);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    updateGames();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public DefaultListModel getSessions() throws RemoteException {
        DefaultListModel model = new DefaultListModel();
        ArrayList<GameSessionRI> sessions = gameFactoryRI.getSessions();
        for (GameSessionRI session : sessions) {
            if (!Objects.equals(session.getUsername(), this.username)) {
                model.addElement(session.getUsername());
            }
        }
        return model;
    }

    public DefaultListModel getGames() throws RemoteException {
        DefaultListModel model = new DefaultListModel();
        ArrayList<AdvanceWarsRI> games =this.gameSessionRI.getGames();
        for (AdvanceWarsRI game : games) {
            model.addElement(game.getId());
            model.addElement(game.getState());
        }
        return model;
    }
    //TBD INTERFACE PARA LISTAR JOGOS
    //entretanto usamos o terminal para testar

    public void updateGames() throws RemoteException {
        listGames.setModel(getGames());
    }
    public void updateUsers() throws RemoteException {
        listUsers.setModel(getSessions());
    }

    public static void main(String[] args) {
        try {
            new AdvanceWarsClient_interface(args).setVisible(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
*/