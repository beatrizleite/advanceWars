package menus;

import edu.ufp.inf.sd.rmi.advanceWars.server.State;
import engine.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

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

	JButton Select = new JButton("Select");

	//NPC Stuff
	JButton[] ManOrMachine = {new JButton("PLY"),new JButton("NPC"),new JButton("NPC"),new JButton("NPC")};
	boolean[] npc = {false,true,true,true};

	//Other
	JButton Return = new JButton("Return");
	JButton StartMoney = new JButton ("$ 100");int start = 100;
	JButton CityMoney = new JButton ("$ 50");int city = 50;
	JButton ThunderbirdsAreGo = new JButton ("Start");

	String mapname;
	int id;

	public PlayerSelectionLobby(int id) throws RemoteException {
		Game.observer.setSelection(this);
		int n = Game.gameLobby.getObsId(Game.username);
		ManOrMachine[n].setText(Game.username);
		npc[n] = false;
		mapname = Game.gameLobby.getMap();
		Point size = MenuHandler.PrepMenu(400,200);
		/*for (int i = 0; i < 4; i++) {
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
		}*/
		ArrayList<Integer> ids = new ArrayList<>(Arrays.asList(null, null, null, null));
		ids.set(n, n);
		ArrayList<String> users = new ArrayList<>(Arrays.asList(null, null, null, null));
		users.set(n, Game.username);
		this.id = id;
		SetBounds(size, n);
		AddGui(n);
		AddListeners(n);
	}
	private void SetBounds(Point size, int n) {
		ThunderbirdsAreGo.setBounds(size.x+200, size.y+170, 100, 24);
		Return.setBounds(size.x+20, size.y+170, 100, 24);
		Name[n].setBounds(new Rectangle(size.x + 10 + 90 * n, size.y + 40, 64, 32));
		ManOrMachine[n].setBounds(new Rectangle(size.x + 10 + 90 * n, size.y + 68, 58, 24));
		Next[n].setBounds(new Rectangle(size.x + 10 + 84 * n, size.y + 100, 64, 32));
		Select.setBounds(new Rectangle(size.x + 10 + 84 * n, size.y + 10, 64, 32));
	}
	private void AddGui(int n) throws RemoteException {
		Return.addActionListener(this);
		Game.gui.add(ManOrMachine[n]);
		Game.gui.add(Name[n]);
		Game.gui.add(Next[n]);
		Game.gui.add(Select);
		if(Game.gameLobby.getObsId(Game.username) == 0) {
			Game.gui.add(ThunderbirdsAreGo);
		}
		Game.gui.add(Return);
	}
	private void AddListeners(int n) {
		ThunderbirdsAreGo.addActionListener(this);
		Return.addActionListener(this);
		Next[n].addActionListener(this);
		Select.addActionListener(this);
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

			try {
				if(Game.gameLobby.getPlayers() == 4 && Objects.equals(Game.gameLobby.getMap(), "FourCorners")) {
					MenuHandler.CloseMenu();
					Game.btl.NewGame(mapname);
					Game.btl.AddCommanders(plyer, npc, 100, 50);
					Game.gameLobby.setGameState(new State("playing"));
					Game.gui.InGameScreen();
				} else if(Game.gameLobby.getPlayers() == 2 && Objects.equals(Game.gameLobby.getMap(), "SmallVs")) {
					MenuHandler.CloseMenu();
					Game.btl.NewGame(mapname);
					Game.btl.AddCommanders(plyer, npc, 100, 50);
					Game.gameLobby.setGameState(new State("playing"));
					Game.gui.InGameScreen();
				}
			} catch (RemoteException ex) {
				throw new RuntimeException(ex);
			}
		} else if(s == Select) {
			try {
				int n = Game.gameLobby.getObsId(Game.username);
				ArrayList<Integer> ids = new ArrayList<>(Arrays.asList(null, null, null, null));
				ids.set(n,n);
				ArrayList<String> users =new ArrayList<>(Arrays.asList(null, null, null, null));
				users.set(n, Game.username);


			} catch (RemoteException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			for (int i = 0; i < 4; i++) {
				if (s == Prev[i]) {
					plyer[i]--;
					if (plyer[i]<0) {plyer[i]=Game.displayC.size()-1;}
					Name[i].setText(Game.displayC.get(plyer[i]).name);
				}
				else if (s == Next[i]) {
					plyer[i]++;
					if (plyer[i]>Game.displayC.size()-1) {plyer[i]=0;}
					Name[i].setText(Game.displayC.get(plyer[i]).name);
				}
				else if (s == ManOrMachine[i]) {
					npc[i]=!npc[i];
					if (npc[i]) {ManOrMachine[i].setText("NPC");}
					else {ManOrMachine[i].setText("PLY");}
				}
			}
		}
		for (int i = 0; i < 4; i++) {
			Next[i].setEnabled(true);
		}
	}
}
