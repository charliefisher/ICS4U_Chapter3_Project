package tictactoe;

import java.awt.event.MouseEvent;

public class HumanPlayer extends Player implements general.Mouse {

	// initialize player number
	public HumanPlayer(byte player) {
		super(player);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// if no one has won, determine the click location and set the spot (if available)
		if (Game.getWinner() == 0) {
			byte x = (byte) (e.getX() / 200), y = (byte) (e.getY() / 200);
			if (Game.isOpen(x, y)) {
				Game.setPosition(x, y, this.player);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// same as click
		mouseClicked(e);
	}
	
	// UNUSED
	// Not used for this
	@Override
	public void mouseMoved(MouseEvent e) {

	}
}
