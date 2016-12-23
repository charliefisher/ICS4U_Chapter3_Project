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
		
		// count used to determine when there is a winner
		private static byte numStones = 25;
		
		// 0 - undefined
		// 1 - player one 
		// 2 - player two 
		private static byte winner = 0;

		// 1 - player one 
		// 2 - player two 
		private static byte turn = 1;
		
		// create an object for the stone
		private static BufferedImage imgStone;

		// since this class is final, it is not instantiated and thus its constructor is not run (like the Math class)
		// it is here to show it does nothing
		public Game() {
			
		}
		
		// used to load images and initialize (in the place of a constructor
		public static void load() throws IOException, FontFormatException {
			// set the board array all to true (since the stones are all present)
			for (int row = 0; row < 5; row++) {
				for (int column = 0; column < 5; column++) {
					board[row][column] = true;
				}
			}
			
			// load the stone image
			URL fileURL = Game.class.getResource("/stones/images/Stone.png");
			Game.imgStone = ImageIO.read(fileURL);
		}

		// remove a stone (set it to false)
		// decrement the stone count (one was taken away)
		public static void setPosition(byte x, byte y) {
			board[x][y] = false;
			numStones--;
		}
		
		// if the user has not submitted their moves and wants to undo it, this sets the removed stones back to true
		public static void cancelMoves() {
			// cycle through its moves this turn (stored in an array in the HumanPlayer class
			for (byte i = 0; i < 6; i += 2)
			{
				// if they have made a move, set each coordinate back to true
				// increase the stone count (they exist again)
				if (HumanPlayer.getMoves(i) != -1) {
					board[HumanPlayer.getMoves(i)][HumanPlayer.getMoves((byte)(i + 1))] = true;
					numStones++;
				}
				// if we have hit an empty move (unused), stop looking through the array
				else {
					break;
				}
			}
			
			// reset the players moves (they have been undone)
			HumanPlayer.resetMoves();
		}
		
		// set the turn to the next player (since the current player is now done
		public static void enterMoves() {
			turn++;
			
			if (Game.turn == 3) {
				turn = 1;
			}
			
			// reset the moves, as they are now for a new player
			HumanPlayer.resetMoves();
		}

		// check if a stone exists (true) or if it does not (false)
		public static boolean isAvailable(byte x, byte y) {
			if (board[x][y]) {
				return true;
			}
			
			return false;
		}
		
		
		public static void drawBoard(Graphics g) {	
			// only print the turn when there are two players
			if (StonesPanel.getGameState() == StonesPanel.GameState.TWO_PLAYER) {
				Game.printTurn(g);
			}
			
			// Handle the enter and cancel buttons
			Game.printMoveOptions(g);

			// draw the board (where the is a stone (true), draw one)
			for (byte row = 0; row < 5; row++) {
				for (byte column = 0; column < 5; column++) {
					if (board[row][column] == true) {
						g.drawImage(imgStone, row * 100 + 50, column * 100 + 50 - 15, null);
					}
				}
			}
		}

		private static void printTurn(Graphics g) {
			// set the font options
			g.setFont(GamePanel.getStonesFont().deriveFont(36f));
			g.setColor(Color.BLACK);

			// temporily store the coordinates of turn counter
			byte x = 5, y = 30;

			// draw the turn (player 1 or player 2)
			if (turn == 1) {
				g.drawString("Player 1", x, y);
			}
			else {
				g.drawString("Player 2", x, y);
			}
		}
		
		private static void printMoveOptions(Graphics g) {
			// set font options
			g.setFont(GamePanel.getTTTFont().deriveFont(36f));
			g.setColor(Color.BLACK);

			// draw the cancel and enter buttons
			g.drawString("Cancel", 200, 570);
			g.drawString("Enter", 320, 570);
		}

		// if there are no more stones (you took the last one), you have won!
		public static byte getWinner() {
			if (numStones == 0) {
				Game.winner = Game.turn;
			}
			
			return Game.winner;
		}
		
		// reset the entire board to true and all other instance variables to their starting values
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
	
		// accessor for turn
		public static byte getTurn() {
			return Game.turn;
		}
}
