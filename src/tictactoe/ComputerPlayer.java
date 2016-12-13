package tictactoe;

public class ComputerPlayer extends HumanPlayer {
	
	private byte x, y, move = 1;
	private boolean centre;
	private byte[] hpMoves = new byte[5];

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
			generateCordinate();
			this.move++;
			break;
		}

	}
	
	private void generateCordinates() {
		x = (byte) (Math.random() * 3);
		y = (byte) (Math.random() * 3);
	}

	private void generateCordinate() {
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
			if (!aboutToWin()) {
			}
			break;
		case 3:
			break;
		case 4: 
			break;	
		}
	}
	
	private boolean aboutToWin() {
		// horizontal wins 
		for (int i = 0; i < 3; i++) {
			if (findCord((byte) (1 + i * 3)) && findCord((byte) (2 + i * 3))){
				if (Game.isOpen((byte)3, (byte)i)) {
					Game.setPosition((byte)3, (byte)i, this.player);
					return true;
				}
			}
			else if (findCord((byte) (2 + i * 3)) && findCord((byte) (3 + i * 3))){
				if (Game.isOpen((byte)0, (byte)0)) {
					Game.setPosition((byte)1, (byte)i, this.player);
					return true;
				}
			}
			else if (findCord((byte) (1 + i * 3)) && findCord((byte) (3 + i * 3))){
				if (Game.isOpen((byte)i, (byte)1)) {
					Game.setPosition((byte)2, (byte)i, this.player);
					return true;
				}
			}
		}
		
		// vertical wins 
		for (int i = 0; i < 3; i++) {
			if (findCord((byte) (1 + i * 1)) && findCord((byte) (4 + i * 1))){
				if (Game.isOpen((byte)2, (byte)0)) {
					Game.setPosition((byte)2, (byte)0, this.player);
					return true;
				}
			}
			else if (findCord((byte) (4 + i * 1)) && findCord((byte) (7 + i * 1))){
				if (Game.isOpen((byte)0, (byte)0)) {
					Game.setPosition((byte)0, (byte)0, this.player);
					return true;
				}
			}
			else if (findCord((byte) (1 + i * 1)) && findCord((byte) (7 + i * 1))){
				if (Game.isOpen((byte)1, (byte)0)) {
					Game.setPosition((byte)1, (byte)0, this.player);
					return true;
				}
			}
		}	
		
		// diagonal wins
		for (int i = 0; i < 2; i++) {
			if (findCord((byte) (2 * i)) && findCord((byte) 5)){
				if (Game.isOpen((byte) (2 - 2 * i), (byte)2)) {
					Game.setPosition((byte) (2 - 2 * i), (byte)2, this.player);
				}
			}
			
			else if (findCord((byte) 5) && findCord((byte) (9 - 2 * 1))){
				if (Game.isOpen((byte) (2 * i), (byte)0)) {
					Game.setPosition((byte) (2 * i), (byte) 0, this.player);
				}
			}
				
			else if (findCord((byte) (2 * i)) && findCord((byte) (9 - 2 * i))){
				if (Game.isOpen((byte) (1), (byte)1)) {
					Game.setPosition((byte) 1, (byte)1, this.player);
				}
			}
		}		
		
		return false;
	}
	
	private boolean findCord(byte check) {
		for (int i = 0; i < hpMoves.length; i++) {
			if (hpMoves[i] == check) {
				return true;
			}
		}
		
		return false;
	}
	
}
