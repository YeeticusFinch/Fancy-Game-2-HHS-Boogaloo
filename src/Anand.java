import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Anand extends Person {

	private ArrayList<Projectile> nukes = new ArrayList<Projectile>();
	private ArrayList<Projectile> fire = new ArrayList<Projectile>();
	private PImage nukePic;
	private PImage firePic;
	
	public Anand() {
		super(3, 4, 20); //Speed, Damage, HP
	}
	
	@Override
	public void attack(int mx, int my) { //Throws nuke

		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		if (Math.random()>0.1)
			nukes.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(10*mx/temp), (int)(10*my/temp), nukePic, 0.09f));
		else
			explosion(x, y);
		
	}
	
	@Override
	public void attack2(int mx, int my) { //Gives elbow connector
		
		
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		super.draw( g,  id, keys, map);
		
		if (keys[32]) {
			for (int i = 0; i < nukes.size(); i++) {
				if (Math.random() > 0.2)
					explosion(nukes.get(i).x, nukes.get(i).y);
				nukes.remove(i);
			}
		}
		
		if (nukePic == null)
			nukePic = g.loadImage("images" + FileIO.fileSep + "nuke.png");
		if (firePic == null)
			firePic = g.loadImage("images" + FileIO.fileSep + "fire.png");
		
		for (int i = 0; i < nukes.size(); i++) {
			if (nukes.get(i).t<70)
				nukes.get(i).draw(g, map);
			else
				nukes.remove(i);
			
		}
		
		for (int i = 0; i < fire.size(); i++) {
			if (fire.get(i).t<10)
				fire.get(i).draw(g, map);
			else
				fire.remove(i);
			
		}
		
		
		
	}
	
	public void explosion(int x, int y) {
		
		
		for (int i = 0; i < Math.random()*20+20; i++) {
			int mx = (int)((float)(Math.random()*200-100));
			int my = (int)((float)(Math.random()*200-100));
			
			double temp = -Math.sqrt(mx*mx + my*my);
			fire.add(new Projectile((int)(x+this.hw/2), (int)(y+this.hh/2), (int)(50*mx/temp), (int)(50*my/temp), firePic, (float)Math.random()*0.1f, Math.random()>0.7));
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
		for (Projectile f : fire) {
			if (f.collide(this) && Math.random()>0.6f) {
				hp -= 1;
				g.pushStyle();
				g.ellipseMode(PConstants.CORNER);
				g.fill(255, 0, 0);
				g.ellipse(x, y, hw, hh);
				g.popStyle();
			}
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= f.size*10;
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
