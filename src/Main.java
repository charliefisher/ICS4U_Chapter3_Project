
import java.awt.Color;
import java.awt.Cursor;
import java.io.IOException;

import javax.swing.JFrame;

public class Main {
	
	static JFrame window = new JFrame("Charlie and Aaron's Video Game Arcade");
	
	private static short WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
	
	// 0 - Main Menu
	// 10 - TicTacToe
	// 20 - Battleship
	// 3 - 
	private static byte gameState = 0;
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		MainMenu mm = new MainMenu();
		
//		BufferedImage jFrameIcon = new BufferedImage(null, null, false, null);
		Cursor jFrameCursor = new Cursor(1);
		
		// configure JFrame
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
//		window.setIconImage(jFrameIcon);
		window.setCursor(jFrameCursor);
		
		mm.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setContentPane(mm);
		
		mm.addMouseListener(mm);
		mm.addMouseMotionListener(mm);
		mm.requestFocus();
		
		window.setVisible(true);
		
		mm.setBackground(Color.WHITE);
		
		
		
		while (true) {
			long timeBeforeRun = System.currentTimeMillis();
				
			mm.run();
			mm.repaint();
		
			long timeRunDifference = System.currentTimeMillis() - timeBeforeRun;
			
			
			Thread.sleep(Math.max(0, 16 - timeRunDifference));  // ensures it runs at 60FPS
		}
	}
	
	
	public static short getWidth() {
		return Main.WINDOW_WIDTH;
	}
	
	public static short getHeight() {
		return Main.WINDOW_HEIGHT;
	}
	
	public static byte getGameState() {
		return gameState;
	}
	
	public static void setGameState(byte gs) {
		Main.gameState = gs;
	}
}
