package stones;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Game {

		// 0 - free
		// 1 - used
		private static boolean[][] board = new boolean[5][5];
		private static byte numStones = 25;
		
		// 0 - undefined
		// 1 - player one 
		// 2 - player two 
		private static byte winner = 0;

		// 1 - player one 
		// 2 - player two 
		private static byte turn = 1;
		
		
		private static BufferedImage imgStone;

		public Game() {
			
		}
		
		public static void load() throws IOException, FontFormatException {				
			for (int row = 0; row < 5; row++) {
				for (int column = 0; column < 5; column++) {
					board[row][column] = true;
				}
			}
			
			URL fileURL;
			
			fileURL = Game.class.getResource("/stones/images/Stone.png");
			Game.imgStone = ImageIO.read(fileURL);
		}

		public static void setPosition(byte x, byte y) {
			board[x][y] = false;
			numStones--;
		}
		
		public static void cancelMoves() {
			for (byte i = 0; i < 6; i += 2)
			{
				if (HumanPlayer.getMoves(i) != -1) {
					board[HumanPlayer.getMoves(i)][HumanPlayer.getMoves((byte)(i + 1))] = true;
					numStones++;
				}
				else {
					break;
				}
			}
			
			HumanPlayer.resetMoves();
		}
		
		public static void enterMoves() {
			turn++;
			
			if (Game.turn == 3) {
				turn = 1;
			}
			
			HumanPlayer.resetMoves();
		}

		public static boolean isAvailable(byte x, byte y) {
			if (board[x][y]) {
				return true;
			}
			
			return false;
		}
		
		
		public static void drawBoard(Graphics g) {

			if (StonesPanel.getGameState() == StonesPanel.GameState.TWO_PLAYER) {
				Game.printTurn(g);
			}
			
			Game.printMoveOptions(g);

			for (byte row = 0; row < 5; row++) {
				for (byte column = 0; column < 5; column++) {
					if (board[row][column] == true) {
						g.drawImage(imgStone, row * 100 + 50, column * 100 + 50 - 15, null);
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
		
		private static void printMoveOptions(Graphics g) {
			g.setFont(GamePanel.getTTTFont().deriveFont(36f));
			g.setColor(Color.BLACK);

			g.drawString("Cancel", 200, 570);
			
			g.drawString("Enter", 320, 570);
		}

		public static byte getWinner() {
			if (numStones == 0) {
				Game.winner = Game.turn;
			}
			
			return Game.winner;
		}
		
		public static void reset() {
			for (int row = 0; row < 5; row++) {
				for (int column = 0; column < 5; column++) {
					board[row][column] = true;
				}
			}
			
			Game.numStones = 25;
			Game.turn = 1;
			Game.winner = 0;
			
			HumanPlayer.resetMoves();
		}
	
		public static byte getTurn() {
			return Game.turn;
		}
}
