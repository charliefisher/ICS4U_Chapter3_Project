package general;

import java.awt.Graphics;

public abstract class GamePanel implements Grid{

//	public static enum GameState {
//		MAINMENU, RESET
//	}
//
//	public static GameState state;

//	public MainMenu() {
//		state = GameState.MAINMENU;
//	}
	
	public abstract void paint(Graphics g);
	public abstract void run();
	public abstract boolean isRunning();
}
