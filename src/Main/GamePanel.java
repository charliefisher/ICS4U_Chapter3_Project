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

import stones.StonesPanel;
import tictactoe.TicTacToePanel;

public final class GamePanel extends JPanel implements MouseListener, MouseMotionListener {

	// fixes an error with JPanel
	private static final long serialVersionUID = 1L;

	// declare our objects of each game
	TicTacToePanel ttt;
	StonesPanel stones;
	
	// create our states
	public static enum GameState {
		MAIN_MENU, STONES, TICTACTOE, RESET
	}

	// instantiate our state
	private static GameState state;


	public static BufferedImage imgBoard;
	private static Font tttFont, StonesFont;
	private boolean hover[] = { false, false };

	public GamePanel() throws IOException, FontFormatException {
		// we are in the main menu
		state = GameState.MAIN_MENU;		

		// load tictactoe font
		InputStream is = getClass().getResourceAsStream("/general_resources/tictactoe_font.ttf");
		tttFont = Font.createFont(Font.TRUETYPE_FONT, is);

		// load stones font
		is = getClass().getResourceAsStream("/general_resources/tictactoe_font.ttf");
		StonesFont = Font.createFont(Font.TRUETYPE_FONT, is);

		// instantiate our objects 
		// (better to do one of each than instantiate one every time we start a new game)
		ttt = new TicTacToePanel();
		stones = new StonesPanel();

	}

	public void paint(Graphics g) {
		switch (state) {
		case MAIN_MENU:
			// draw the menu options
			handleFonts(g);
			break;
		case STONES:
			// call the paint method for stones
			stones.paint(g);
			break;
		case TICTACTOE:
			// call the paint method for tic tac toe
			ttt.paint(g);
			break;
		case RESET:

			break;
		}
	}

	private void handleFonts(Graphics g) {
		// set font color
		g.setColor(Color.BLACK);
		
		// create a variable to store the coordinates at which to draw our menu options
		int x, y;

		// select the right coordinate depending on font size
		if (hover[0]) {
			g.setFont(tttFont.deriveFont(108f));
			x = 125;
			y = 188;
		} else {
			g.setFont(tttFont.deriveFont(72f));
			x = 175;
			y = 175;
		}

		// draw the tic tac toe option
		g.drawString("TicTacToe", x, y);

		// select the right coordinate depending on font size
		if (hover[1]) {
			g.setFont(StonesFont.deriveFont(108f));
			x = 150;
			y = 388;
		} else {
			g.setFont(StonesFont.deriveFont(72f));
			x = 200;
			y = 375;
		}

		// draw the stones option
		g.drawString("Stones", x, y);
	}

	public void run() {
		// call each games run method (let each game handle its own game logic)
		if (state == GameState.STONES) {
			stones.run();
		}
		else if (state == GameState.TICTACTOE) {
			ttt.run();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (state) {
		case MAIN_MENU:
			// check if we have clicked on an option (either tic tac toe or stones)
			// if we have set our state to the appropriate game
			if (hover[0]) {
				state = GameState.TICTACTOE;
			} 
			else if (hover[1]) {
				state = GameState.STONES;
			}
			break;
			
			// call each games mouse clicked method (let each game handle its own game logic)
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
			// we do not have any specific drag uses, so whenever we drag, assume it is the same as a click
		case MAIN_MENU:
			mouseClicked(e);
			break;
			// call each games mouse dragged (let each game handle its own game logic)
		case STONES:
			stones.mouseDragged(e);
			break;
		case TICTACTOE:
			ttt.mouseDragged(e);
			break;
		default:
				break;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (state) {
		case MAIN_MENU:
			// For both menu options:
			// check if user is hovering over the font
			// from this flip a boolean indicating whether the using is hovering
			// if so, enlarge the font
			// if it is enlarged, change our values and see if we are still hovering over it
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
			// call each games mouse moved method (let each game handle its own game logic)
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
	
	// accessor for state
	public static GameState getGameState() {
		return GamePanel.state;
	}
	
	// mutator for state
	public static void setGameState(GameState newState) {
		GamePanel.state = newState;
	}

	// accessor for tic tac toe font
	public static Font getTTTFont() {
		return tttFont;
	}

	// accessor for stones font
	public static Font getStonesFont() {
		return StonesFont;
	}

	// UNUSED METHODS
	// JPanel makes you implement MouseListener and MouseMotionLister in order to get mouse input
	// Cannot just use specific motion events
	// in other class the do not extend JPanel, you can use listener methods on their own
	// other classes use a custom interface with only the mouse listener methods it needs
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