import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class ShadyMain {
	public static void main(String args[]) {
		GUI drawing = new GUI();
		PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setLocation(screenSize.width/2-window.getSize().width/2, screenSize.height/2-window.getSize().height/2);
	
		window.setVisible(true);
	}
}
