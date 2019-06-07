import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Anand extends Person {

	private ArrayList<Projectile> nukes = new ArrayList<Projectile>();
	private PImage nukePic;
	
	public Anand() {
		super(3, 4, 17); //Speed, Damage, HP
	}
	
	@Override
	public void attack(int mx, int my) { //Throws nuke

		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		nukes.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(10*mx/temp), (int)(10*my/temp), nukePic, 0.09f));
		
		
	}
	
	@Override
	public void attack2(int mx, int my) { //Gives elbow connector
		
		
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		super.draw( g,  id, keys, map);
		
		if (nukePic == null)
			nukePic = g.loadImage("images" + FileIO.fileSep + "nuke.png");
		
		for (int i = 0; i < nukes.size(); i++) {
			if (nukes.get(i).t<70)
				nukes.get(i).draw(g, map);
			else
				nukes.remove(i);
			
		}
		
		
		
	}
	
	public void projectileCollide(PApplet g, ArrayList<Enemy> enemies) {
		for (Projectile f : nukes) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= f.size*5;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
				}
			}
		}
	}
	
	
}
