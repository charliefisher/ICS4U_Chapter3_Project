package tictactoe;

import java.awt.event.MouseEvent;

public class HumanPlayer extends Player implements general.Mouse {

	public HumanPlayer(byte player) {
		super(player);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (Game.getWinner() == 0) {
			byte x = (byte) (e.getX() / 200), y = (byte) (e.getY() / 200);
			if (Game.isOpen(x, y)) {
				Game.setPosition(x, y, this.player);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseClicked(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
}
