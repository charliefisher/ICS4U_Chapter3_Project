package stones;

public class Player {
	
	protected static byte[] moves = new byte[6];

	// initialize the moves array to empty (-1)
	public Player() {
		for (byte i = 0; i < 6; i++) {
			moves[i] = -1;
		}
	}
	
	// accessor for each move
	public static byte getMoves(byte index) {
		return moves[index];
	}
	
	// reset the moves array
	public static void resetMoves() {
		for (int i = 0; i < 6; i ++) {
			moves[i] = -1;
		}
	}
}
