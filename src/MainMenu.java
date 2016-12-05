import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

public class MainMenu extends JPanel implements MouseMotionListener, MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	BufferedImage imgMainMenu;
	
//	Font titleFont = new Font(null, 0, false, null);
	
	
	public MainMenu() throws IOException {
		// MAKE IT A SINGLETON
		
//		 imgMainMenu = ImageIO.read(new File("MainMenu.png"));
	}
	
	public void run() {
		switch (Main.getGameState()) {
		case 0: 
			break;
		case 1:
		}
	}
	
	public void paint(Graphics g) {
//		g.setFont(titleFont);		
//		g.drawImage(imgMainMenu, 0, 0, null);	
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}