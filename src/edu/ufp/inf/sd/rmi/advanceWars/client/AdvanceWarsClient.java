package edu.ufp.inf.sd.rmi.advanceWars.client;

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

public class AdvanceWarsClient extends JFrame {

    private SetupContextRMI contextRMI;
    private GameFactoryRI gameFactoryRI;
    private DB db;
    private GameSessionRI gameSessionRI;
    private AdvanceWarsRI advanceWarsRI;
    private ObserverRI observerRI;
    private String username;
    Scanner sc = new Scanner(System.in);

    public AdvanceWarsClient(String[] args) throws RemoteException {
        this.db= DB.getInstance();
        initContext(args);
        this.lookup();
        new Game();

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
            Logger.getLogger(AdvanceWarsClient.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(AdvanceWarsClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void login() throws RemoteException {
        System.out.println("Enter 'l' for Login or 'r' for register: ");
        String choice = sc.nextLine();
        String username, password;
        switch (choice) {
            case "r" -> {
                try {
                    System.out.println("Registration!");
                    System.out.println("Username?");
                    username = sc.nextLine();
                    System.out.println("Password?");
                    password = sc.nextLine();
                    gameFactoryRI.register(username, password);
                    gameSessionRI = gameFactoryRI.login(username, password);
                    this.username = username;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "l" -> {
                try {
                    System.out.println("Login!");
                    System.out.println("Username?");
                    username = sc.nextLine();
                    System.out.println("Password?");
                    password = sc.nextLine();
                    this.gameSessionRI = gameFactoryRI.login(username, password);
                    this.username = username;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            default -> {
                System.out.println("Not a valid option!");
            }
        }
    }

    private void menu() {
        String map1 = "SmallVs", map2 = "FourCorners";
        System.out.println("Write 'create' to create a new lobby or 'join' to join an existing lobby: ");
        String choice = sc.nextLine();
        switch(choice) {
            case "create":
                System.out.println("Choose map: \n-SmallVs (2 players)-> write 'SmallVS'\n-FourCorners (4 players)-> write 'FourCorners'");
                String map = sc.nextLine();
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

    //entretanto usamos o terminal para testar
    public void updateUsers() throws RemoteException {
        listUsers.setModel(getSessions());
    }

    public static void main(String[] args) {
        try {
            new AdvanceWarsClient(args).setVisible(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
