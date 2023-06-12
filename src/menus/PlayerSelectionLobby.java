package menus;

import edu.ufp.inf.sd.rmi.advanceWars.client.ObserverImpl;
import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsImpl;
import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsRI;
import edu.ufp.inf.sd.rmi.advanceWars.server.GameSessionRI;
import engine.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 * This deals with player and battle options setup (might split it) such as npc, team, commander, starting money, turn money, fog, etc.
 * @author SergeDavid
 * @version 0.2
 */
public class PlayerSelectionLobby implements ActionListener {

	//Commander Selection
	JButton[] Prev = {new JButton("Prev"),new JButton("Prev"),new JButton("Prev"),new JButton("Prev")};
	JButton[] Next = {new JButton("Next"),new JButton("Next"),new JButton("Next"),new JButton("Next")};
	JLabel[] Name = {new JLabel("Andy"),new JLabel("Andy"),new JLabel("Andy"),new JLabel("Andy")};
	int[] plyer = {0,0,0,0};

	//NPC Stuff
	JButton[] ManOrMachine = {new JButton("PLY"),new JButton("NPC"),new JButton("NPC"),new JButton("NPC")};
	boolean[] npc = {false,false,false,false};

	//Other
	JButton Return = new JButton("Return");
	JButton StartMoney = new JButton ("$ 100");int start = 100;
	JButton CityMoney = new JButton ("$ 50");int city = 50;
	JButton ThunderbirdsAreGo = new JButton ("Start");

	String mapname;
	GameSessionRI session;
	UUID uuid;
	ObserverImpl observer;
	AdvanceWarsRI game;
	int id;


	public PlayerSelectionLobby(String map, GameSessionRI session, UUID id) throws RemoteException {

		try {
			if (id == null) {
				UUID new_id = UUID.randomUUID();
				game.setId(new_id);

			} else {

			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		mapname = map;
		Point size = MenuHandler.PrepMenu(400,200);
		for (int i = 0; i < 4; i++) {
			Prev[i].addActionListener(this);
			Prev[i].setBounds(size.x+10+84*i, size.y+10, 64, 32);
			Game.gui.add(Prev[i]);
			Next[i].addActionListener(this);
			Next[i].setBounds(size.x+10+84*i, size.y+100, 64, 32);
			Game.gui.add(Next[i]);
			ManOrMachine[i].addActionListener(this);
			ManOrMachine[i].setBounds(size.x+12+84*i, size.y+68, 58, 24);
			Game.gui.add(ManOrMachine[i]);
			Name[i].setBounds(size.x+10+84*i, size.y+40, 64, 32);
			Game.gui.add(Name[i]);
		}
		SetBounds(size);
		AddGui();
		AddListeners();
	}
	private void SetBounds(Point size) {
		ThunderbirdsAreGo.setBounds(size.x+200, size.y+170, 100, 24);
		Return.setBounds(size.x+20, size.y+170, 100, 24);
	}
	private void AddGui() throws RemoteException {
		Return.addActionListener(this);
		Game.gui.add(ThunderbirdsAreGo);
		Game.gui.add(Return);
	}
	private void AddListeners() {
		ThunderbirdsAreGo.addActionListener(this);
		Return.addActionListener(this);
	}
	
	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s == Return) {
			if(Game.gameLobby != null) {
				try {
					Game.session.removeGame(Game.gameLobby);
				} catch (RemoteException ex) {
					throw new RuntimeException(ex);
				}
				try {
					Game.gameLobby.detach(Game.observer);
				} catch (RemoteException ex) {
					throw new RuntimeException(ex);
				}
			}
			Game.gameLobby = null;
			Game.observer = null;
			MenuHandler.CloseMenu();
			//Game.gui.LoginScreen();
			try {
				new Join();
			} catch (RemoteException ex) {
				throw new RuntimeException(ex);
			}
		}
		else if(s == ThunderbirdsAreGo) {


		}
		for (int i = 0; i < 4; i++) {
			if (s == Prev[i]) {
				plyer[i]--;
				if (plyer[i] < 0) {
					plyer[i] = Game.displayC.size() - 1;
				}
				Name[i].setText(Game.displayC.get(plyer[i]).name);
			} else if (s == Next[i]) {
				plyer[i]++;
				if (plyer[i] > Game.displayC.size() - 1) {
					plyer[i] = 0;
				}
				Name[i].setText(Game.displayC.get(plyer[i]).name);
			} else if (s == ManOrMachine[i]) {
				npc[i] = !npc[i];
				if (npc[i]) {
					ManOrMachine[i].setText("NPC");
				} else {
					ManOrMachine[i].setText("PLY");
				}
			}
		}
	}
}
