package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JPanel;

import tictactoe.TicTacToePanel;

public class GamePanel extends JPanel implements MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	
	TicTacToePanel ttt = new TicTacToePanel();
//	battleship.MainMenu battleshipMainMenu = new battleship.MainMenu();
	
	public static enum GameState {
		MAIN_MENU, BATTLESHIP, TICTACTOE, RESET
	}

	public static GameState state;
	BufferedImage imgMainMenu;
	Font[] titleFont = new Font[2];
	boolean hover[] = { false, false };

	public GamePanel() throws IOException, FontFormatException {
		// MAKE IT A SINGLETON

		// we are in the main menu
		state = GameState.MAIN_MENU;

		// URL fileURL =
		// getClass().getResource("General_Resources/MainMenu.png");
		// imgMainMenu = ImageIO.read(fileURL);

		// load our custom font
		InputStream is = getClass().getResourceAsStream("/General_Resources/print_clearly_tt.ttf");
		Font tempFont = Font.createFont(Font.TRUETYPE_FONT, is);

		// create two fonts
		titleFont[0] = tempFont.deriveFont(72f);
		titleFont[1] = tempFont.deriveFont(108f);

		// imgMainMenu = ImageIO.read(new File("MainMenu.png"));
	}

	public void paint(Graphics g) {
		switch (state) {
		case MAIN_MENU:
			// draw the font (menu options)
			handleFonts(g);

			// g.drawImage(imgMainMenu, 0, 0, null);
			break;
		case BATTLESHIP:
//			battleshipMainMenu.paint(g);
			break;
		case TICTACTOE:
			ttt.paint(g);
			break;
		case RESET: 
			
			break;
		}
	}

	private void handleFonts(Graphics g) {
		// set font color
		g.setColor(Color.BLACK);

		// select font size
		if (hover[0]) {
			g.setFont(titleFont[1]);
		}
		else {
			g.setFont(titleFont[0]);
		}

		// draw the tic tac toe
		g.drawString("TicTacToe", 300, 200);

		// select font size
		if (hover[1]) {
			g.setFont(titleFont[1]);
		}
		else {
			g.setFont(titleFont[0]);
		}

		// draw battleship
		g.drawString("Battleship", 300, 400);
	}

	public void run() {		
		switch (state) {
		case MAIN_MENU:
			
			break;
		case BATTLESHIP:
//			battleshipMainMenu.run();
			break;
		case TICTACTOE:
			if (ttt.isRunning()) {
				ttt.run();
			}
			break;
		case RESET: 
			
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (state) {
		case MAIN_MENU:
			// check if we have clicked an option
			if (hover[0]) {
				if ((e.getX() > 300 && e.getX() < 400) && (e.getY() > 200 && e.getY() < 275)) {
					state = GameState.TICTACTOE;
				} 
				else if((e.getX() > 300 && e.getX() < 400) && (e.getY() > 300 && e.getY() < 375)) {
					state = GameState.BATTLESHIP;
				}
			}
			else {
				if ((e.getX() > 250 && e.getX() < 450) && (e.getY() > 150 && e.getY() < 300)) {
					state = GameState.TICTACTOE;
				} 
				else if((e.getX() > 250 && e.getX() < 450) && (e.getY() > 250 && e.getY() < 400)) {
					state = GameState.BATTLESHIP;
				}
			}
			break;
		case BATTLESHIP:
			
			break;
		case TICTACTOE:
			
			break;
		case RESET: 
			
			break;
		}	
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseClicked(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (state) {
		case MAIN_MENU:
			// handle selection
			if ((e.getX() > 300 && e.getX() < 400) && (e.getY() > 200 && e.getY() < 275)) {
				hover[0] = true;
			} 
			else {
				hover[0] = false;
			}
				
			if((e.getX() > 300 && e.getX() < 400) && (e.getY() > 300 && e.getY() < 375)) {
				hover[1] = true;
			} 
			else {
				hover[1] = false;
			}
			break;
		case BATTLESHIP:
			
			break;
		case TICTACTOE:
			
			break;
		case RESET: 
			
			break;
		}	
	}

	public static GameState getGameState() {
		return GamePanel.state;
	}

	// unused methods
	@Override
	public void mousePressed(MouseEvent e) {

	}
	@Override
	public void mouseReleased(MouseEvent e) {

	}
	@Override
	public void mouseEntered(MouseEvent e) {

	}
	@Override
	public void mouseExited(MouseEvent e) {

	}
}