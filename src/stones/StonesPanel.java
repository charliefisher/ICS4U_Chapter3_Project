package stones;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;

import main.GamePanel;

public final class StonesPanel extends general.Panel {

	private static long timeOver = 0, stopBoardDisplay = 0;
	HumanPlayer p1;
	Player p2;
	
	public static enum LastGameType {
		ONE_PLAYER, TWO_PLAYER
	}

	public static enum GameState {
		MAIN_MENU, CPU_SELECT, ONE_PLAYER, TWO_PLAYER, GAME_OVER
	}

	private static GameState state,lastGame;
	private boolean hoverMainMenu[] = { false, false};
	private boolean hoverOnePlayer[] = {false, false};
	private boolean hoverGameOver[] = {false, false, false};

	public StonesPanel() throws FontFormatException, IOException {	
		state = GameState.MAIN_MENU;
		Game.load();
		
		p1 = new HumanPlayer();
	}
	

	@Override
	public void paint(Graphics g) {
		switch (state) {
		case MAIN_MENU:
			this.handleFonts(g);
			break;
		case CPU_SELECT:
			this.handleFonts(g);
			break;
		case ONE_PLAYER:
		case TWO_PLAYER:
			Game.drawBoard(g);
			break;
		case GAME_OVER:
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
				Game.reset();
				this.handleFonts(g);
			}
		}
	}

	private void handleFonts(Graphics g) {
		g.setColor(Color.BLACK);
		
		switch(state) {
		case MAIN_MENU:
			int x, y;

			// select font size
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

			// select font size
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
		case CPU_SELECT:
			// select font size
			if (hoverOnePlayer[0]) {
				g.setFont(GamePanel.getStonesFont().deriveFont(108f));
				x = 210;
				y = 188;
			} else {
				g.setFont(GamePanel.getStonesFont().deriveFont(72f));
				x = 235;
				y = 175;
			}

			// draw 1 Player
			g.drawString("Easy", x, y);

			// select font size
			if (hoverOnePlayer[1]) {
				g.setFont(GamePanel.getStonesFont().deriveFont(108f));
				x = 190;
				y = 388;
			} else {
				g.setFont(GamePanel.getStonesFont().deriveFont(72f));
				x = 215;
				y = 375;
			}

			// draw 2 Players
			g.drawString("Expert", x, y);
			
			break;
		case GAME_OVER:			
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
		if (state == GameState.ONE_PLAYER || state == GameState.TWO_PLAYER) {
//			Game.turn();
			if (Game.getWinner() != 0) {
				timeOver = System.currentTimeMillis();
				state = GameState.GAME_OVER;	
			}
		}
		
		switch (state) {
		case MAIN_MENU:
			break;
		case ONE_PLAYER:
			if (Game.getTurn() == 2) {
//				cp.run();
			}
			break;
		case TWO_PLAYER:
			break;	
		case GAME_OVER:
			break;
		default:
			break;
		
		}
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (state) {
		case MAIN_MENU:
			// check if we have clicked an option
			if (hoverMainMenu[0]) {
				state = GameState.CPU_SELECT;
				lastGame = GameState.ONE_PLAYER;
//				p2 = new ComputerPlayer();
			} else if (hoverMainMenu[1]) {
				state = GameState.TWO_PLAYER;
				lastGame = GameState.TWO_PLAYER;
				p2 = new HumanPlayer();
			}
			break;
		case CPU_SELECT:
			if (hoverOnePlayer[0]) {
				tictactoe.ComputerPlayer.setGameState(tictactoe.ComputerPlayer.GameState.EASY);
				tictactoe.ComputerPlayer.setLastCPU(tictactoe.ComputerPlayer.GameState.EASY);
				state = GameState.ONE_PLAYER;
			} else if (hoverOnePlayer[1]) {
				tictactoe.ComputerPlayer.setGameState(tictactoe.ComputerPlayer.GameState.EXPERT);
				tictactoe.ComputerPlayer.setLastCPU(tictactoe.ComputerPlayer.GameState.EXPERT);
				state = GameState.ONE_PLAYER;
			}
			break;
		case ONE_PLAYER:
			if (Game.getTurn() == 1) {
				p1.mouseClicked(e);
			}
			break;
		case TWO_PLAYER:
			if (Game.getTurn() == 1) {
				p1.mouseClicked(e);
			} else {
			((HumanPlayer) p2).mouseClicked(e);
			}
			break;
		case GAME_OVER:
			Game.reset();
//			cp.reset();
			
			if (hoverGameOver[0]) {
				if (lastGame == GameState.ONE_PLAYER) {
					state = GameState.ONE_PLAYER;
				}
				else {
					state = GameState.TWO_PLAYER;
				}		
			}
			
			else if (hoverGameOver[1]) {
				state = GameState.MAIN_MENU;
			}
			
			else if (hoverGameOver[2]) {
				state = GameState.MAIN_MENU;
				GamePanel.setGameState(GamePanel.GameState.MAIN_MENU);
			}
			break;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
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
		case MAIN_MENU:
			// handle selection
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
			// handle selection
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
	
	public static GameState getGameState() {
		return StonesPanel.state;
	}
}
