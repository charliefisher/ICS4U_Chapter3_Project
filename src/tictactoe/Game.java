package tictactoe;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import general.Grid;
import main.GamePanel;

public final class Game implements Grid {
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
	
	
	private static Font gamePiece;
	private static BufferedImage imgBoard, imgX, imgO;

	public Game() {
	}
	
	public static void load() throws IOException, FontFormatException {		
		URL fileURL;
		
		fileURL = Game.class.getResource("/tictactoe/images/Board.png");
		Game.imgBoard = ImageIO.read(fileURL);

		fileURL = Game.class.getResource("/tictactoe/images/X.png");
		Game.imgX = ImageIO.read(fileURL);

		fileURL = Game.class.getResource("/tictactoe/images/O.png");
		Game.imgO = ImageIO.read(fileURL);		
		
		InputStream is = Game.class.getResourceAsStream("/general_resources/print_dashed_tt.ttf");
		Game.gamePiece = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(160f);
	}

	public static void setPosition(byte x, byte y, byte player) {
		board[x][y] = player;
		turn++;
	}

	public static void drawBoard(Graphics g) {

		if (TicTacToePanel.getGameState() == TicTacToePanel.GameState.TWO_PLAYER) {
			Game.printTurn(g);
		}

		g.drawImage(imgBoard, 0, 0, null);
		g.setFont(GamePanel.getTTTFont().deriveFont(160f));

		for (byte row = 0; row < 3; row++) {
			for (byte column = 0; column < 3; column++) {
				if (board[row][column] == 1) {
					g.setColor(Color.BLUE);
//					 g.drawString("X", row * 200 + 55, column * 200 + 150);
					g.drawImage(imgX, row * 200, column * 200, null);
				} else if (board[row][column] == 2) {
					g.setColor(Color.RED);
//					g.drawString("O", row * 200 + 55, column * 200 + 150);
					g.drawImage(imgO, row * 200, column * 200, null);
				}
			}
		}
	}

	private static void printTurn(Graphics g) {
		g.setFont(GamePanel.getTTTFont().deriveFont(36f));
		g.setColor(Color.BLACK);

		byte x = 5, y = 30;

		if (turn == 1) {
			g.drawString("Player 1", x, y);
		}
		// player Os turn
		else {
			g.drawString("Player 2", x, y);
		}
	}

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

	static boolean isOpen(byte x, byte y) {
		if (board[x][y] == 0) {
			return true;
		}

		return false;
	}

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

	public static byte getTurn() {
		return Game.turn;
	}

	public static byte[][] getBoard() {
		return Game.board;
	}
	
	public static boolean checkPosition(byte x, byte y, byte player) {
		if (Game.board[x][y] == player) {
			return true;
		}
		
		return false;
	}
}
