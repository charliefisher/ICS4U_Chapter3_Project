package tictactoe;

import java.awt.Color;
import java.awt.Graphics;

public class TicTacToePanel extends general.GamePanel {

	public static enum GameState {
		MAIN_MENU, ONE_PLAYER, TWO_PLAYER, WINNER, RESET
	}

	public static GameState state;
	private boolean running = true;

	public TicTacToePanel() {
		state = GameState.MAIN_MENU;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		
		switch (state) {
		case MAIN_MENU: 
			break;
		case ONE_PLAYER:
			break;
		case TWO_PLAYER:
			break;
		case WINNER:
			break;
		case RESET:
			break;
		}
	}

	@Override
	public void run() {
		switch (state) {
		case MAIN_MENU: 
			break;
		case ONE_PLAYER:
			break;
		case TWO_PLAYER:
			break;
		case WINNER:
			break;
		case RESET:
			break;
		}
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}
}
