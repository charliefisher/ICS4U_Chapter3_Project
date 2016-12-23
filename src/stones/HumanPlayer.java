package stones;

import java.awt.event.MouseEvent;

public class HumanPlayer extends Player implements general.Mouse{
	
		public HumanPlayer() {
			super();
		}

		@Override
		public void mouseClicked(MouseEvent e) {	
			if (Game.getWinner() == 0) {
				if ((e.getX() > 50 && e.getX() < 550) && (e.getY() > 35 && e.getY() < 535)) {
					byte x = (byte) ((e.getX() - 50) / 100), y = (byte) ((e.getY() - 35) / 100);
					if (Game.isAvailable(x, y) && Player.moves[4] == -1) {
						Game.setPosition(x, y);
						
						for (int i = 0; i < 6; i += 2) {
							if (Player.moves[i] == -1) {
								Player.moves[i] = x;
								Player.moves[i + 1] = y;
								break;
							}
						}
					}
				}
			}
			
			
			if (e.getY() < 570 && e.getY() > 540) {
				if (e.getX() > 200 && e.getX() < 285) {
					Game.cancelMoves();
				}
				else if (e.getX() > 320 && e.getX() < 385) {
					if (Player.moves[0] != -1) {
						Game.enterMoves();
					}
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
