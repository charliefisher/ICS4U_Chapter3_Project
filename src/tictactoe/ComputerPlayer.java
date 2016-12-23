package tictactoe;

public class ComputerPlayer extends Player {
	
	private byte x, y, move = 1;
	private boolean centre;
	private byte[] hpMoves = new byte[5];
	boolean vertical = false;

	// create the states of the AI
	public static enum GameState {
		EASY, EXPERT
	}
	
	
	private static GameState state, lastCPU;

	public ComputerPlayer() {
		super((byte) 2);
	}

	public void run() {	
		// if it is easy, use random coordinates (no strategy)
		switch (state) {
		case EASY:
			do {
				generateCordinates();
			}
			while(!Game.isOpen(x,y));

			Game.setPosition(x, y, this.player);

			break;
		case EXPERT:
			// if it is expert (break it down by move to determine the best option)
			makeMove();
			this.move++;
			break;
		}
	}
	
	// random x and y cordnates for easy
	private void generateCordinates() {
		x = (byte) (Math.random() * 3);
		y = (byte) (Math.random() * 3);
	}


	private void makeMove() {
		// get the board
		byte[][] board = Game.getBoard();
		byte playerSquare = 0;

		// get the square where the hp made their first move and put that into a 1D array
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
		// chose the center if it is available, if not chose top left
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
					// checks for open vertical center line
					if (!Game.checkPosition((byte) 1, (byte) 2, (byte) 1) && Game.isOpen((byte) 1, (byte) 0)) {
						Game.setPosition((byte) 1, (byte) 0, this.player);
						vertical = true;
					}
					// checks for open horizontal center line
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
							// check horizontal center
							if (!Game.checkPosition((byte) 2, (byte) 1, (byte) 1) && Game.isOpen((byte) 0, (byte) 1)) {
								Game.setPosition((byte) 0, (byte) 1, this.player);
							}
						}
						else {
							// check vertical center
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
	
	// reset all instance variables
	public void reset() {
		for (byte i = 0; i < hpMoves.length; i++) {
			hpMoves[i] = 0;
		}
		
		move = 1;
		vertical = false;
		
		state = lastCPU;
	}
	
	// mutator game state (done through the input, not passed into the constructor)
	public static void setGameState(GameState newState) {
		ComputerPlayer.state = newState;
	}
	
	// mutator last game state 
	public static void setLastCPU(GameState newState) {
		ComputerPlayer.lastCPU = newState;
	}
}
