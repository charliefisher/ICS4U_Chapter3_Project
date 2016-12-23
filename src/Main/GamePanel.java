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
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import stones.StonesPanel;
import tictactoe.TicTacToePanel;

public final class GamePanel extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;

	TicTacToePanel ttt;
	StonesPanel stones;

	public static enum GameState {
		MAIN_MENU, STONES, TICTACTOE, RESET
	}

	private static GameState state;
//	private BufferedImage imgMainMenu;

	public static BufferedImage imgBoard;
	private static Font tttFont, StonesFont;
	private boolean hover[] = { false, false };

	public GamePanel() throws IOException, FontFormatException {
		// we are in the main menu
		state = GameState.MAIN_MENU;
		
		URL fileURL;
		
		fileURL = getClass().getResource("/tictactoe/images/Board.png");
		imgBoard = ImageIO.read(fileURL);

		// load tictactoe font
		InputStream is = getClass().getResourceAsStream("/general_resources/tictactoe_font.ttf");
		tttFont = Font.createFont(Font.TRUETYPE_FONT, is);

		// load STONES font
		is = getClass().getResourceAsStream("/general_resources/tictactoe_font.ttf");
		StonesFont = Font.createFont(Font.TRUETYPE_FONT, is);

		ttt = new TicTacToePanel();
		stones = new StonesPanel();

	}

	public void paint(Graphics g) {
		switch (state) {
		case MAIN_MENU:
			// draw the font (menu options)
			handleFonts(g);

			// g.drawImage(imgMainMenu, 0, 0, null);
			break;
		case STONES:
			stones.paint(g);
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
			x = 125;
			y = 188;
		} else {
			g.setFont(tttFont.deriveFont(72f));
			x = 175;
			y = 175;
		}

		// draw the tic tac toe
		g.drawString("TicTacToe", x, y);

		// select font size
		if (hover[1]) {
			g.setFont(StonesFont.deriveFont(108f));
			x = 150;
			y = 388;
		} else {
			g.setFont(StonesFont.deriveFont(72f));
			x = 200;
			y = 375;
		}

		// draw STONES
		g.drawString("Stones", x, y);
	}

	public void run() {
		switch (state) {
		case MAIN_MENU:

			break;
		case STONES:
			stones.run();
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
				state = GameState.STONES;
			}
			break;
		case STONES:
			stones.mouseClicked(e);
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
		case STONES:
			stones.mouseDragged(e);
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
				if ((e.getX() > 125 && e.getX() < 485) && (e.getY() > 115 && e.getY() < 185)) {
					hover[0] = true;
				} else {
					hover[0] = false;
				}
			} else {
				if ((e.getX() > 175 && e.getX() < 425) && (e.getY() > 130 && e.getY() < 175)) {
					hover[0] = true;
				} else {
					hover[0] = false;
				}
			}

			if (hover[1]) {
				if ((e.getX() > 150 && e.getX() < 400) && (e.getY() > 315 && e.getY() < 385)) {
					hover[1] = true;
				} else {
					hover[1] = false;
				}
			} else {
				if ((e.getX() > 200 && e.getX() < 365) && (e.getY() > 330 && e.getY() < 375)) {
					hover[1] = true;
				} else {
					hover[1] = false;
				}
			}
			break;
		case STONES:
			stones.mouseMoved(e);
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

	public static Font getStonesFont() {
		return StonesFont;
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