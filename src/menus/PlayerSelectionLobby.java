package menus;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JLabel;

import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsImpl;
import edu.ufp.inf.sd.rmi.advanceWars.server.AdvanceWarsRI;
import edu.ufp.inf.sd.rmi.advanceWars.server.GameSessionRI;
import edu.ufp.inf.sd.rmi.advanceWars.server.State;
import engine.Game;

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
	UUID id;
	String mapname;
	int player;
	String username;

	public PlayerSelectionLobby(String map, UUID id) throws RemoteException {
		mapname = map;
		this.id = id;
		this.player = Game.gameLobby.getObsId(Game.username);
		this.username = Game.username;
		npc[player] = false;

		Point size = MenuHandler.PrepMenu(400,200);

		for (int i = 0; i < 1; i++) {
			Prev[i].addActionListener(this);
			Prev[i].setBounds(size.x + 10 + 84 * i, size.y + 10, 64, 32);
			Game.gui.add(Prev[i]);
			Next[i].addActionListener(this);
			Next[i].setBounds(size.x + 10 + 84 * i, size.y + 100, 64, 32);
			Game.gui.add(Next[i]);
			ManOrMachine[i].addActionListener(this);
			ManOrMachine[i].setBounds(size.x + 12 + 84 * i, size.y + 68, 58, 24);
			Game.gui.add(ManOrMachine[i]);
			Name[i].setBounds(size.x + 10 + 84 * i, size.y + 40, 64, 32);
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
	private void AddGui() {
		Return.addActionListener(this);
		Game.gui.add(ThunderbirdsAreGo);
		try {
			//o commander pode iniciar o jogo
			if (Game.gameLobby.getObsId(username) == 0) {
				Game.gui.add(ThunderbirdsAreGo);
			}
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		Game.gui.add(Return);
	}
	private void AddListeners() {
		ThunderbirdsAreGo.addActionListener(this);
		Return.addActionListener(this);
	}

	@Override public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s == Return) {
			try{
				if (Game.gameLobby != null) {
					if (Game.gameLobby.howManyPlayers() == 1) {
						AdvanceWarsRI game = Game.session.getGame(id);
						Game.session.removeGame(game);
					}
					Game.gameLobby.detach(Game.observer);
				}
			} catch (RemoteException ex) {
				throw new RuntimeException(ex);
			}
			Game.gameLobby = null;
			Game.observer = null;
			MenuHandler.CloseMenu();
			try {
				new Join();
			} catch (RemoteException ex) {
				throw new RuntimeException(ex);
			}
		}
		else if(s == ThunderbirdsAreGo) {
			new WaitingRoom(id);
		}
		for (int i = 0; i < 1; i++) {
			if (s == Prev[i]) {
				plyer[i]--;
				if (plyer[i]<0) {plyer[i]=Game.displayC.size()-1;}
				Name[i].setText(Game.displayC.get(plyer[i]).name);
				Game.chr = plyer[i];
			}
			else if (s == Next[i]) {
				plyer[i]++;
				if (plyer[i]>Game.displayC.size()-1) {plyer[i]=0;}
				Name[i].setText(Game.displayC.get(plyer[i]).name);
				Game.chr = plyer[i];
			}
			else if (s == ManOrMachine[i]) {
				ManOrMachine[i].setText("PLY");
			}
		}
	}
}
