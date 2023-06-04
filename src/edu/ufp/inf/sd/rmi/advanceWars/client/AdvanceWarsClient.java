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

    public AdvanceWarsClient(String[] args) throws RemoteException {
        this.db= DB.getInstance();
        initContext(args);
        this.lookup();
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
            observerRI = new ObserverImpl(this.advanceWarsRI);
        } catch (RemoteException e) {
            Logger.getLogger(AdvanceWarsClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void main(String[] args) {
        try {
            new AdvanceWarsClient(args).setVisible(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
