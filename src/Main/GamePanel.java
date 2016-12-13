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

public final class GamePanel extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;

	TicTacToePanel ttt;
	// BattleshipPanel battleship;

	public static enum GameState {
		MAIN_MENU, BATTLESHIP, TICTACTOE, RESET
	}

	private static GameState state;
	private BufferedImage imgMainMenu;
	private static Font tttFont, battleshipFont;
	private boolean hover[] = { false, false };

	public GamePanel() throws IOException, FontFormatException {
		// we are in the main menu
		state = GameState.MAIN_MENU;

		// URL fileURL =
		// getClass().getResource("/general_resources/MainMenu.png");
		// imgMainMenu = ImageIO.read(fileURL);

		// load tictactoe font
		InputStream is = getClass().getResourceAsStream("/general_resources/tictactoe_font.ttf");
		tttFont = Font.createFont(Font.TRUETYPE_FONT, is);

		// load battleship font
		is = getClass().getResourceAsStream("/general_resources/tictactoe_font.ttf");
		battleshipFont = Font.createFont(Font.TRUETYPE_FONT, is);

		ttt = new TicTacToePanel();
		// battleship = new battleship.BattleshipPanel();

	}

	public void paint(Graphics g) {
		switch (state) {
		case MAIN_MENU:
			// draw the font (menu options)
			handleFonts(g);

			// g.drawImage(imgMainMenu, 0, 0, null);
			break;
		case BATTLESHIP:
			// battleship.paint(g);
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
		int x, y;

		// select font size
		if (hover[0]) {
			g.setFont(tttFont.deriveFont(108f));
			x = 225;
			y = 188;
		} else {
			g.setFont(tttFont.deriveFont(72f));
			x = 275;
			y = 175;
		}

		// draw the tic tac toe
		g.drawString("TicTacToe", x, y);

		// select font size
		if (hover[1]) {
			g.setFont(battleshipFont.deriveFont(108f));
			x = 225;
			y = 388;
		} else {
			g.setFont(battleshipFont.deriveFont(72f));
			x = 275;
			y = 375;
		}

		// draw battleship
		g.drawString("Battleship", x, y);
	}

	public void run() {
		switch (state) {
		case MAIN_MENU:

			break;
		case BATTLESHIP:
			// battleship.run();
			break;
		case TICTACTOE:
			ttt.run();
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
				state = GameState.TICTACTOE;
			} 
			else if (hover[1]) {
				state = GameState.BATTLESHIP;
			}
			break;
		case BATTLESHIP:

			break;
		case TICTACTOE:
			ttt.mouseClicked(e);
			break;
		case RESET:

			break;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch (state) {
		case MAIN_MENU:
			mouseClicked(e);
			break;
		case BATTLESHIP:

			break;
		case TICTACTOE:
			ttt.mouseDragged(e);
			break;
		case RESET:

			break;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (state) {
		case MAIN_MENU:
			// handle selection
			if (hover[0]) {
				if ((e.getX() > 225 && e.getX() < 585) && (e.getY() > 115 && e.getY() < 185)) {
					hover[0] = true;
				} else {
					hover[0] = false;
				}
			} else {
				if ((e.getX() > 275 && e.getX() < 525) && (e.getY() > 130 && e.getY() < 175)) {
					hover[0] = true;
				} else {
					hover[0] = false;
				}
			}

			if (hover[1]) {
				if ((e.getX() > 225 && e.getX() < 585) && (e.getY() > 315 && e.getY() < 385)) {
					hover[1] = true;
				} else {
					hover[1] = false;
				}
			} else {
				if ((e.getX() > 275 && e.getX() < 525) && (e.getY() > 330 && e.getY() < 375)) {
					hover[1] = true;
				} else {
					hover[1] = false;
				}
			}
			break;
		case BATTLESHIP:

			break;
		case TICTACTOE:
			ttt.mouseMoved(e);
			break;
		case RESET:

			break;
		}
	}

	public static GameState getGameState() {
		return GamePanel.state;
	}
	
	public static void setGameState(GameState newState) {
		GamePanel.state = newState;
	}

	public static Font getTTTFont() {
		return tttFont;
	}

	public static Font getBattleshipFont() {
		return battleshipFont;
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