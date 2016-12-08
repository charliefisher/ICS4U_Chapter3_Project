package Main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JFrame;

public class Main {
	
	static JFrame window = new JFrame("Charlie and Aaron's Video Game Arcade");
	
	private static short WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;	
	
	public static void main(String[] args) throws IOException, InterruptedException, FontFormatException {
		
		GamePanel panel = new GamePanel();
		
//		BufferedImage jFrameIcon = new BufferedImage(null, null, false, null);
		Cursor jFrameCursor = new Cursor(1);
		
		// configure JFrame
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
//		window.setIconImage(jFrameIcon);
		window.setCursor(jFrameCursor);
		
		panel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setContentPane(panel);
		
		panel.addMouseListener(panel);
		panel.addMouseMotionListener(panel);
		panel.requestFocus();
		
		window.setVisible(true);
		
		panel.setBackground(Color.WHITE);
		
		
		
		while (true) {
			if (GamePanel.getGameState() == GamePanel.GameState.TICTACTOE) {
				window.setSize(WINDOW_HEIGHT, WINDOW_HEIGHT);
				panel.setSize(WINDOW_HEIGHT, WINDOW_HEIGHT);
			}
			
			
			long timeBeforeRun = System.currentTimeMillis();
				
	
			panel.run();
			panel.repaint();
			
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
}
