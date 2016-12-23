package tictactoe;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import main.GamePanel;

public final class Game {
	/*
	 * Important to Note:
	 * I originally started by drawing strings them decided to change it to images
	 * I have left the working string code uncommented out as I thought it may be valuable in the future
	 */
	
	// 0 - empty
	// 1 - player one (X)
	// 2 - player two (O)
	private static byte[][] board = new byte[3][3];

	// 0 - undefined
	// 1 - player one (X)
	// 2 - player two (O)
	// 3 = tie
	private static byte winner = 0;

	// 1 - player one (X)
	// 2 - player two (O)
	private static byte turn = 1;
	
	/*	  CREATE FONT FOR STRING GAME PIECE METHOD
	 * 
	 * private static Font gamePiece;
	 */
	private static BufferedImage imgBoard, imgX, imgO;

	public Game() {
	}
	
	public static void load() throws IOException, FontFormatException {		
		URL fileURL;
		
		// load baord image
		fileURL = Game.class.getResource("/tictactoe/images/Board.png");
		Game.imgBoard = ImageIO.read(fileURL);

		// load x image
		fileURL = Game.class.getResource("/tictactoe/images/X.png");
		Game.imgX = ImageIO.read(fileURL);

		// load o image
		fileURL = Game.class.getResource("/tictactoe/images/O.png");
		Game.imgO = ImageIO.read(fileURL);		
		
		/*	  LOAD FONT FOR STRING GAME PIECES
		 * 
		 * InputStream is = Game.class.getResourceAsStream("/general_resources/print_dashed_tt.ttf");
		 * Game.gamePiece = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(160f);
		 */
	}

	// set the position to the player number
	public static void setPosition(byte x, byte y, byte player) {
		board[x][y] = player;
		turn++;
	}

	
	public static void drawBoard(Graphics g) {

		// print the turn if there are two players
		if (TicTacToePanel.getGameState() == TicTacToePanel.GameState.TWO_PLAYER) {
			Game.printTurn(g);
		}

		// draw the board
		g.drawImage(imgBoard, 50, 35, null);
		
		/* set the font options (for string option)
		 * g.setFont(GamePanel.getTTTFont().deriveFont(160f));
		 */

		// draw the x and o image
		for (byte row = 0; row < 3; row++) {
			for (byte column = 0; column < 3; column++) {
				if (board[row][column] == 1) {
					g.drawImage(imgX, row * 167 + 50, column * 167 + 35, null);
					
					/*	  ALTERNATE METHOD USING STRINGS	
					 * 
					 * g.setColor(Color.BLUE);
					 * g.drawString("X", row * 200 + 55, column * 200 + 150);
					 */	
				} else if (board[row][column] == 2) {
					g.drawImage(imgO, row * 167 + 50, column * 167 + 35, null);

					/*	  ALTERNATE METHOD USING STRINGS
					 * 				
					 * g.setColor(Color.RED);
					 * g.drawString("O", row * 200 + 55, column * 200 + 150);
					 */
				}
			}
		}
	}

	private static void printTurn(Graphics g) {
		// set font options
		g.setFont(GamePanel.getTTTFont().deriveFont(36f));
		g.setColor(Color.BLACK);

		byte x = 5, y = 30;
		
		// Player 1
		if (turn == 1) {
			g.drawString("Player 1", x, y);
		}
		// Player 2
		else {
			g.drawString("Player 2", x, y);
		}
	}

	// reset the turn
	public static void turn() {
		if (Game.turn == 3) {
			turn = 1;
		}
	}

	public static byte getWinner() {
		// checks horizontal wins (a horizontal line)
		for (byte row = 0; row < 3; row++) {
			if ((board[row][0] == 1 && board[row][1] == 1) && board[row][2] == 1) {
				Game.winner = 1;
				break;
			} else if ((board[row][0] == 2 && board[row][1] == 2) && board[row][2] == 2) {
				Game.winner = 2;
				break;
			}
		}

		// checks vertical wins (a vertical line)
		for (byte column = 0; column < 3; column++) {
			if ((board[0][column] == 1 && board[1][column] == 1) && board[2][column] == 1) {
				Game.winner = 1;
				break;
			} else if ((board[0][column] == 2 && board[1][column] == 2) && board[2][column] == 2) {
				Game.winner = 2;
				break;
			}
		}

		// checks diagonal wins (corner to corner)
		if ((board[0][0] == 1 && board[1][1] == 1) && board[2][2] == 1) {
			Game.winner = 1;
		} else if ((board[0][2] == 1 && board[1][1] == 1) && board[2][0] == 1) {
			Game.winner = 1;
		} else if ((board[0][0] == 2 && board[1][1] == 2) && board[2][2] == 2) {
			Game.winner = 2;
		} else if ((board[0][2] == 2 && board[1][1] == 2) && board[2][0] == 2) {
			Game.winner = 2;
		} 
		
		else if (winner == 0 && isEntireBoardFull()) {
			Game.winner = 3;
		}

		return Game.winner;
	}

	// check if a location is not taken
	static boolean isOpen(byte x, byte y) {
		if (board[x][y] == 0) {
			return true;
		}

		return false;
	}

	// see if it is a tie (no wins but all spots taken)
	private static boolean isEntireBoardFull() {
		boolean full = true;

		// check for an empty position
		for (byte row = 0; row < 3; row++) {
			for (byte column = 0; column < 3; column++) {
				if (board[row][column] == 0) {
					full = false;
				}
			}
		}

		// if the entire board is full (no empty positions), return true
		// if there is an empty position, return false
		return full;
	}

	// reset instance variables to start value
	public static void reset() {
		// initialize all of the 2D array to 0 (empty)
		for (byte row = 0; row < 3; row++) {
			for (byte column = 0; column < 3; column++) {
				Game.board[row][column] = 0;
			}
		}
		
		Game.winner = 0;
		Game.turn = 1;
	}

	// accessor for turn
	public static byte getTurn() {
		return Game.turn;
	}

	// accessor for board array
	public static byte[][] getBoard() {
		return Game.board;
	}
	
	// for AI, see who occupies a spot
	public static boolean checkPosition(byte x, byte y, byte player) {
		if (Game.board[x][y] == player) {
			return true;
		}
		
		return false;
	}
}
