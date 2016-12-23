package stones;

public class Player {
	
	protected static byte[] moves = new byte[6];

	public Player() {
		for (byte i = 0; i < 6; i++) {
			moves[i] = -1;
		}
	}
	
	public static byte getMoves(byte index) {
		return moves[index];
	}
	
	public static void resetMoves() {
		for (int i = 0; i < 6; i ++) {
			moves[i] = -1;
		}
	}
}
