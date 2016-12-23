package stones;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;

import main.GamePanel;

public final class StonesPanel extends general.Panel {

	// initialize time storing variables for resetting
	private static long timeOver = 0, stopBoardDisplay = 0;
	
	// declare player objects
	HumanPlayer p1;
	Player p2;
	
	// declare last state enum
	public static enum LastGameType {
		ONE_PLAYER, TWO_PLAYER
	}

	// declare stae enum
	public static enum GameState {
		MAIN_MENU, CPU_SELECT, ONE_PLAYER, TWO_PLAYER, GAME_OVER
	}

	// initialize the enums and hovering booleans (same use as the main menu)
	private static GameState state,lastGame;
	private boolean hoverMainMenu[] = { false, false};
	private boolean hoverOnePlayer[] = {false, false};
	private boolean hoverGameOver[] = {false, false, false};

	public StonesPanel() throws FontFormatException, IOException {	
		// initialize the state (Main Menu)
		state = GameState.MAIN_MENU;
		// load game images
		Game.load();
		
		// initialize the first player to human
		p1 = new HumanPlayer();
	}
	

	@Override
	public void paint(Graphics g) {
		switch (state) {
		case MAIN_MENU:
			// draw our menu options
			this.handleFonts(g);
			break;
		case CPU_SELECT:
			// a secondary menu - draw those options
			this.handleFonts(g);
			break;
			// draw the game board
		case ONE_PLAYER:
		case TWO_PLAYER:
			Game.drawBoard(g);
			break;
		case GAME_OVER:
			// set the font options
			g.setFont(GamePanel.getStonesFont().deriveFont(72f));
			g.setColor(Color.BLACK);
			
			// show the board for 1/4 second
			if (System.currentTimeMillis() - timeOver < 1000) {
				Game.drawBoard(g);
				stopBoardDisplay = System.currentTimeMillis();
			}	
			
			// state the results
			else if (System.currentTimeMillis() - stopBoardDisplay < 2000) {
				if(Game.getWinner() == 1) {
					g.drawString("Player 1 Wins!", 125, 300);
				}
				else if (Game.getWinner() == 2) {
					g.drawString("Player 2 Wins!", 125, 300);
				}
			}
			// what to do next?
			else {
				// the reset menu
				Game.reset();
				this.handleFonts(g);
			}
		}
	}

	private void handleFonts(Graphics g) {
		// set font options
		g.setColor(Color.BLACK);
		
		switch(state) {
		// set each coordinate for the menu option depending on whether or not it is enlarged
		case MAIN_MENU:
			int x, y;

			// 1 Player
			if (hoverMainMenu[0]) {
				g.setFont(GamePanel.getStonesFont().deriveFont(108f));
				x = 169;
				y = 188;
			} else {
				g.setFont(GamePanel.getStonesFont().deriveFont(72f));
				x = 206;
				y = 175;
			}

			// draw 1 Player
			g.drawString("1 Player", x, y);

			// 2 Players
			if (hoverMainMenu[1]) {
				g.setFont(GamePanel.getStonesFont().deriveFont(108f));
				x = 150;
				y = 388;
			} else {
				g.setFont(GamePanel.getStonesFont().deriveFont(72f));
				x = 190;
				y = 375;
			}

			// draw 2 Players
			g.drawString("2 Players", x, y);
			break;
			// set each coordinate for the menu option depending on whether or not it is enlarged
		case CPU_SELECT:
			// Easy
			if (hoverOnePlayer[0]) {
				g.setFont(GamePanel.getStonesFont().deriveFont(108f));
				x = 210;
				y = 188;
			} else {
				g.setFont(GamePanel.getStonesFont().deriveFont(72f));
				x = 235;
				y = 175;
			}

			// Easy
			g.drawString("Easy", x, y);

			// Expert
			if (hoverOnePlayer[1]) {
				g.setFont(GamePanel.getStonesFont().deriveFont(108f));
				x = 190;
				y = 388;
			} else {
				g.setFont(GamePanel.getStonesFont().deriveFont(72f));
				x = 215;
				y = 375;
			}

			// Expert
			g.drawString("Expert", x, y);
			
			break;
			// set each coordinate for the menu option depending on whether or not it is enlarged
		case GAME_OVER:	
			// Another Game
			if (hoverGameOver[0]) {
				g.setFont(GamePanel.getStonesFont().deriveFont(65f));
				x = 145;
				y = 160;
			} 
			else {
				g.setFont(GamePanel.getStonesFont().deriveFont(50f));
				x = 180;
				y = 155;
			}

			
			g.drawString("Another Game", x, y);
			
			// Select number of players
			if (hoverGameOver[1]) {
				g.setFont(GamePanel.getStonesFont().deriveFont(65f));
				x = 20;
				y = 295;
			} 
			else {
				g.setFont(GamePanel.getStonesFont().deriveFont(50f));
				x = 80;
				y = 290;
			}
			
			g.drawString("Select Number of Players", x, y);
			
			// main menu
			if (hoverGameOver[2]) {
				g.setFont(GamePanel.getStonesFont().deriveFont(65f));
				x = 180;
				y = 440;
			} 
			else {
				g.setFont(GamePanel.getStonesFont().deriveFont(50f));
				x = 205;
				y = 435;
			}
			
			g.drawString("Main Menu", x, y);
			break;
		default:
			break;
		}
		
		
	}

	@Override
	public void run() {
		// set the time when a winner is declared 
		// we want to draw the screen for a bit before going to the next menu
		if (state == GameState.ONE_PLAYER || state == GameState.TWO_PLAYER) {
			if (Game.getWinner() != 0) {
				timeOver = System.currentTimeMillis();
				state = GameState.GAME_OVER;	
			}
		}
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (state) {
		// check if we have clicked on a menu option
		case MAIN_MENU:
			// one player
			if (hoverMainMenu[0]) {
				state = GameState.CPU_SELECT;
				lastGame = GameState.ONE_PLAYER;
				// there is no implementation code for a computer player, but this is how to declare it
//				p2 = new ComputerPlayer();
			} 
			// two players
			else if (hoverMainMenu[1]) {
				state = GameState.TWO_PLAYER;
				lastGame = GameState.TWO_PLAYER;
				p2 = new HumanPlayer();
			}
			break;
			// go to secondary menu
		case CPU_SELECT:
			// easy
			if (hoverOnePlayer[0]) {
				tictactoe.ComputerPlayer.setGameState(tictactoe.ComputerPlayer.GameState.EASY);
				tictactoe.ComputerPlayer.setLastCPU(tictactoe.ComputerPlayer.GameState.EASY);
				state = GameState.ONE_PLAYER;
			} 
			// expert
			else if (hoverOnePlayer[1]) {
				tictactoe.ComputerPlayer.setGameState(tictactoe.ComputerPlayer.GameState.EXPERT);
				tictactoe.ComputerPlayer.setLastCPU(tictactoe.ComputerPlayer.GameState.EXPERT);
				state = GameState.ONE_PLAYER;
			}
			break;
		case ONE_PLAYER:
			// if it is one player (only click for the human player)
			if (Game.getTurn() == 1) {
				p1.mouseClicked(e);
			}
			break;
			// determine which player to click for (p1 or p2)
		case TWO_PLAYER:
			if (Game.getTurn() == 1) {
				p1.mouseClicked(e);
			} 
			else {
			((HumanPlayer) p2).mouseClicked(e);
			}
			break;
		case GAME_OVER:
			Game.reset();
			// you would need to reset the computer here but there is no implementation
//			cp.reset();
			
			// another game (reset to the last game type)
			if (hoverGameOver[0]) {
				if (lastGame == GameState.ONE_PLAYER) {
					state = GameState.ONE_PLAYER;
				}
				else {
					state = GameState.TWO_PLAYER;
				}		
			}
			// go to the player number select
			else if (hoverGameOver[1]) {
				state = GameState.MAIN_MENU;
			}
			// go to the main menu
			else if (hoverGameOver[2]) {
				state = GameState.MAIN_MENU;
				GamePanel.setGameState(GamePanel.GameState.MAIN_MENU);
			}
			break;
		}
	}

	
	@Override
	public void mouseDragged(MouseEvent e) {
		// dragged is the same as clicked for our purposes here
		switch (state) {
		case MAIN_MENU:
			mouseClicked(e);
			break;
		case ONE_PLAYER:
			mouseClicked(e);
			break;
		case TWO_PLAYER:
			mouseClicked(e);
			break;
		case GAME_OVER:
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (state) {
		// For menu options:
		// check if user is hovering over the font
		// from this flip a boolean indicating whether the using is hovering
		// if so, enlarge the font
		// if it is enlarged, change our values and see if we are still hovering over it
		case MAIN_MENU:
			// Player 1
			if (hoverMainMenu[0]) {
				if ((e.getX() > 175 && e.getX() < 460) && (e.getY() > 120 && e.getY() < 185)) {
					hoverMainMenu[0] = true;
				} else {
					hoverMainMenu[0] = false;
				}
			} 
			else {
				if ((e.getX() > 210 && e.getX() < 400) && (e.getY() > 130 && e.getY() < 175)) {
					hoverMainMenu[0] = true;
				} else {
					hoverMainMenu[0] = false;
				}
			}
			// Player 2
			if (hoverMainMenu[1]) {
				if ((e.getX() > 150 && e.getX() < 475) && (e.getY() > 320 && e.getY() < 385)) {
					hoverMainMenu[1] = true;
				} else {
					hoverMainMenu[1] = false;
				}
			} 
			else {
				if ((e.getX() > 190 && e.getX() < 410) && (e.getY() > 330 && e.getY() < 375)) {
					hoverMainMenu[1] = true;
				} else {
					hoverMainMenu[1] = false;
				}
			}			
			break;
		case CPU_SELECT:			
			// Easy
			if (hoverOnePlayer[0]) {
				if ((e.getX() > 215 && e.getX() < 375) && (e.getY() > 120 && e.getY() < 185)) {
					hoverOnePlayer[0] = true;
				} 
				else {
					hoverOnePlayer[0] = false;
				}
			} 
			else {
				if ((e.getX() > 235 && e.getX() < 350) && (e.getY() > 130 && e.getY() < 175)) {
					hoverOnePlayer[0] = true;
				} 
				else {
					hoverOnePlayer[0] = false;
				}
			}
			// Expert
			if (hoverOnePlayer[1]) {
				if ((e.getX() > 190 && e.getX() < 410) && (e.getY() < 400 && e.getY() > 325)) {
					hoverOnePlayer[1] = true;
				} 
				else {
					hoverOnePlayer[1] = false;
				}
			} 
			else {
				if ((e.getX() > 215 && e.getX() < 370) && (e.getY() < 390 && e.getY() > 325)) {
					hoverOnePlayer[1] = true;
				} 
				else {
					hoverOnePlayer[1] = false;
				}
			}
			break;
		case GAME_OVER:
			// Another Game
			if (hoverGameOver[0]) {
				if ((e.getX() > 145 && e.getX() < 455) && (e.getY() > 110 && e.getY() < 160)) {
					hoverGameOver[0] = true;
				} else {
					hoverGameOver[0] = false;
				}
			}
			else {
				
				if ((e.getX() > 180 && e.getX() < 420) && (e.getY() > 115 && e.getY() < 155)) {
					hoverGameOver[0] = true;
				} else {
					hoverGameOver[0] = false;
				}
			}
			// Player number select
			if (hoverGameOver[1]) {
				if ((e.getX() > 20 && e.getX() < 580) && (e.getY() > 250 && e.getY() < 300)) {
					hoverGameOver[1] = true;
				} else {
					hoverGameOver[1] = false;
				}
			}
			else {
				if ((e.getX() > 80 && e.getX() < 520) && (e.getY() > 255 && e.getY() < 295)) {
					hoverGameOver[1] = true;
				} else {
					hoverGameOver[1] = false;
				}	
			}
			
			// go to main menu
			if (hoverGameOver[2]) {
				if ((e.getX() > 180 && e.getX() < 420) && (e.getY() > 395 && e.getY() < 440)) {
					hoverGameOver[2] = true;
				} else {
					hoverGameOver[2] = false;
				}
			}
			else {
				if ((e.getX() > 205 && e.getX() < 395) && (e.getY() > 400 && e.getY() < 435)) {
					hoverGameOver[2] = true;
				} else {
					hoverGameOver[2] = false;
				}	
			}
			break;
		default:
			break;
		}
	}
	
	// state accessor
	public static GameState getGameState() {
		return StonesPanel.state;
	}
}
