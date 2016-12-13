package general;

import java.awt.Graphics;

public abstract class Panel implements Grid, Mouse {
	
	public abstract void paint(Graphics g);
	public abstract void run();
	
}
