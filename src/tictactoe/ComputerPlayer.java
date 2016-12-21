package tictactoe;

import java.awt.event.MouseEvent;

public class ComputerPlayer extends HumanPlayer {
	
	private byte x, y, move = 1;
	private boolean centre;
	private byte[] hpMoves = new byte[5];
	boolean vertical = false;

	public static enum GameState {
		EASY, EXPERT
	}

	private static GameState state;

	public ComputerPlayer(boolean easy) {
		super((byte) 2);

		if (easy) {
			ComputerPlayer.state = GameState.EASY;
		} else {
			ComputerPlayer.state = GameState.EXPERT;
		}
	}

	public void run() {
		switch (state) {
		case EASY:
			do {
				generateCordinates();
			}
			while(!Game.isOpen(x,y));

			Game.setPosition(x, y, this.player);

			break;
		case EXPERT:
			makeMove();
			this.move++;
			break;
		}

	}
	
	private void generateCordinates() {
		x = (byte) (Math.random() * 3);
		y = (byte) (Math.random() * 3);
	}

	private void makeMove() {
		byte[][] board = Game.getBoard();
		byte playerSquare = 0;

		// check for an empty position
		for (byte row = 0; row < 3; row++) {
			for (byte column = 0; column < 3; column++) {
				if (board[row][column] == 1) {
					playerSquare = (byte)((row * 3 + column) + 1);
					
					for (int i = 0; i < hpMoves.length; i++) {
						if (hpMoves[i] != playerSquare) {
							hpMoves[i] = playerSquare;
							break;
						}
					}
				}
			}
		}
		
		switch (this.move) {
		case 1:
			switch(hpMoves[0]) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 6:
			case 7:
			case 8:
			case 9:
				Game.setPosition((byte)1, (byte)1, this.player);
				this.centre = true;
				break;
			case 5:
				Game.setPosition((byte)0, (byte)0, this.player);
				this.centre = false;
				break;
			}
			break;
		case 2:
			if (centre) {
				// if they are not about to win
				if (!aboutToWin((byte) 1)) {
					// checks for open vertical centre line
					if (!Game.checkPosition((byte) 1, (byte) 2, (byte) 1) && Game.isOpen((byte) 1, (byte) 0)) {
						Game.setPosition((byte) 1, (byte) 0, this.player);
						vertical = true;
					}
					// checks for open horizontal centre line
					else if (!Game.checkPosition((byte) 2, (byte) 1, (byte) 1) && Game.isOpen((byte) 0, (byte) 1)) {
						Game.setPosition((byte) 0, (byte) 1, this.player);
						vertical = false;
					}
				}		
			}
			else {
				// checks top horizontal column
				if (!Game.checkPosition((byte) 1, (byte) 0, (byte) 1) && Game.isOpen((byte) 2, (byte) 0)) {
					Game.setPosition((byte) 2, (byte) 0, this.player);
				}
				// checks left vertical column
				else if (!Game.checkPosition((byte) 0, (byte) 1, (byte) 1) && Game.isOpen((byte) 0, (byte) 2)) {
					Game.setPosition((byte) 0, (byte) 2, this.player);
				}
			}
			break;
		case 3:
			// if we are not about to win
			if (!aboutToWin(this.player)) {
				// if they are not about to win
				if (!aboutToWin((byte)1)) {
					if (centre) {
						// checks top horizontal column
						if (!Game.checkPosition((byte) 1, (byte) 0, (byte) 1) && Game.isOpen((byte) 2, (byte) 0)) {
							Game.setPosition((byte) 2, (byte) 0, this.player);
						}
						// checks left vertical column
						else if (!Game.checkPosition((byte) 0, (byte) 1, (byte) 1) && Game.isOpen((byte) 0, (byte) 2)) {
							Game.setPosition((byte) 0, (byte) 2, this.player);
						}
						// if neither are open player in the bottom right corner
						else if (Game.isOpen((byte) 2, (byte) 2)) {
							Game.setPosition((byte) 2, (byte) 2, this.player);
						}							
					}
					else {
						// check for diagonal
						if (!Game.checkPosition((byte) 0, (byte) 2, (byte) 1) && Game.isOpen((byte) 2, (byte) 0)) {
							Game.setPosition((byte) 2, (byte) 0, this.player);
						}
						if (vertical) {
							// check horizontal
							if (!Game.checkPosition((byte) 2, (byte) 1, (byte) 1) && Game.isOpen((byte) 0, (byte) 1)) {
								Game.setPosition((byte) 0, (byte) 1, this.player);
							}
						}
						else {
							// check vertical
							if (!Game.checkPosition((byte) 1, (byte) 2, (byte) 1) && Game.isOpen((byte) 1, (byte) 0)) {
								Game.setPosition((byte) 1, (byte) 0, this.player);
							}
						}
					}
				}
			}
			break;
		case 4:
			// since the four move is irrelevant if neither play can we, revert to randomly generating coordinants
			if (!aboutToWin(this.player)) {
				if (!aboutToWin((byte) 1)) {
					ComputerPlayer.state = GameState.EASY;
					this.run();
				}
			}	
		}
	}
		
	
	private boolean aboutToWin(byte player) {
		// horizontal wins 
		for (byte i = 0; i < 3; i++) {
			if (Game.checkPosition((byte)0, i, player) && Game.checkPosition((byte)1, i, player)) {
				if (Game.isOpen((byte)2, (byte)i)) {
					Game.setPosition((byte)2, (byte)i, this.player);
					return true;
				}
			}
			else if (Game.checkPosition((byte)0, i, player) && Game.checkPosition((byte)2, i, player)) {
				if (Game.isOpen((byte)1, (byte)i)) {
					Game.setPosition((byte)1, (byte)i, this.player);
					return true;
				}
			}
			else if (Game.checkPosition((byte)1, i, player) && Game.checkPosition((byte)2, i, player)) {
				if (Game.isOpen((byte)0, (byte)i)) {
					Game.setPosition((byte)0, (byte)i, this.player);
					return true;
				}
			}
		}
		
		// vertical wins 
		for (byte i = 0; i < 3; i++) {
			if (Game.checkPosition(i, (byte)0, player) && Game.checkPosition(i, (byte)1, player)) {
				if (Game.isOpen(i, (byte)2)) {
					Game.setPosition(i, (byte)2, this.player);
					return true;
				}
			}
			else if (Game.checkPosition(i, (byte)0, player) && Game.checkPosition(i, (byte)2, player)) {
				if (Game.isOpen(i, (byte)1)) {
					Game.setPosition(i, (byte)1, this.player);
					return true;
				}
			}
			else if (Game.checkPosition(i, (byte)1, player) && Game.checkPosition(i, (byte)2, player)) {
				if (Game.isOpen(i, (byte)0)) {
					Game.setPosition(i, (byte)0, this.player);
					return true;
				}
			}
		}
		
		
		
		// diagonal wins
		for (byte i = 0; i < 2; i++) {
			if (Game.checkPosition((byte) (2 * i), (byte)0, player) && Game.checkPosition((byte)1, (byte)1, player)) {
				if (Game.isOpen((byte) (2 - 2 * i), (byte)2)) {
					Game.setPosition((byte) (2 - 2 * i), (byte)2, this.player);
					return true;
				}
			}
			else if (Game.checkPosition((byte)(2 * i), (byte)0, player) && Game.checkPosition((byte) (2 - 2 * i), (byte)2, player)) {
				if (Game.isOpen((byte)1, (byte)1)) {
					Game.setPosition((byte)1, (byte)1, this.player);
					return true;
				}
			}
			else if (Game.checkPosition((byte)1, (byte)1, player) && Game.checkPosition((byte) (2 - 2 * i), (byte)2, player)) {
				if (Game.isOpen((byte) (2 * i), (byte)0)) {
					Game.setPosition((byte) (2 * i), (byte)0, this.player);
					return true;
				}
			}
		}	
		
		return false;
	}
	
	
	public void reset() {
		for (byte i = 0; i < hpMoves.length; i++) {
			hpMoves[i] = 0;
		}
		
		move = 1;
		vertical = false;
		
		state = GameState.EXPERT;
	}
	
	// do not want the computer to have clicking capability
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseDragged(MouseEvent e) {
	}
	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
