import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Anand extends Person {

	private ArrayList<Projectile> nukes = new ArrayList<Projectile>();
	private ArrayList<Projectile> drones = new ArrayList<Projectile>();
	private ArrayList<Projectile> fire = new ArrayList<Projectile>();
	private PImage nukePic;
	private PImage firePic;
	private PImage dronePic;
	private PImage ironPic;
	int explosions = 0;
	int throwCool = 0;
	int ex, ey;
	
	public Anand() {
		super(3, 4, 20); //Speed, Damage, HP
	}
	
	@Override
	public void attack(int mx, int my) { //Throws nuke
		if (throwCool == 0) {
			throwCool = 20;
			mx = (int)((float)super.x-mx);
			my = (int)((float)super.y-my);
			
			double temp = -Math.sqrt(mx*mx + my*my);
			
			if (Math.random()>0.05)
				nukes.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(10*mx/temp), (int)(10*my/temp), nukePic, 0.09f));
			else
				explosion(x, y, 25);
		}
		
	}
	
	@Override
	public void attack2(int mx, int my) { //Gives elbow connector
		if (unlocked && mode == 0 && throwCool == 0) {
			
			throwCool = 20;
			
			mx = (int)((float)super.x-mx);
			my = (int)((float)super.y-my);
			
			
			
			double temp = -Math.sqrt(mx*mx + my*my);
			drones.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(15*mx/temp), (int)(15*my/temp), dronePic, 0.09f));
		}
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		super.draw( g,  id, keys, map);
		
		
		if (explosions > 0) {
			if (explosions %4 == 0)
				explosion(ex, ey, 10);
			explosions--;
		}
		
		if (dronePic == null)
			dronePic = g.loadImage("images" + FileIO.fileSep + "h1m0.png");
		
		if (throwCool > 0)
			throwCool--;
		
		if (keys[32]) {
			for (int i = 0; i < nukes.size(); i++) {
				if (Math.random() > 0.1) {
					if (unlocked && mode == 0) {
						ex = nukes.get(i).x;
						ey = nukes.get(i).y;
						explosion(nukes.get(i).x, nukes.get(i).y, 10);
						explosions = 20;
					} else
						explosion(nukes.get(i).x, nukes.get(i).y, 25);
				}
				nukes.remove(i);
			}
		}
		
		if (nukePic == null)
			nukePic = g.loadImage("images" + FileIO.fileSep + "nuke.png");
		if (firePic == null)
			firePic = g.loadImage("images" + FileIO.fileSep + "fire.png");
		if (ironPic == null)
			ironPic = g.loadImage("images" + FileIO.fileSep + "h1m1.png");
		
		if (keys[16] && mode == 1) {
			keys[16] = false;
		}
		
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
		
		for (int i = 0; i < drones.size(); i++) {
			if (drones.get(i).t<80)
				drones.get(i).draw(g, map);
			else
				drones.remove(i);
			
		}
		
	}
	
	public void explosion(int x, int y, int size) {
		
		
		for (int i = 0; i < Math.random()*size+size; i++) {
			int mx = (int)((float)(Math.random()*200-100));
			int my = (int)((float)(Math.random()*200-100));
			
			double temp = -Math.sqrt(mx*mx + my*my);
			fire.add(new Projectile((int)(x+this.hw/2), (int)(y+this.hh/2), (int)(50*mx/temp), (int)(50*my/temp), firePic, (float)Math.random()*0.1f, Math.random()>0.7));
		}
	}
	
	public void projectileCollide(PApplet g, ArrayList<Enemy> enemies) {
		for (int i = 0; i < drones.size(); i++) {
			Projectile f = drones.get(i);
			for (Enemy e : enemies) {
				if (e.hp > 0 && Math.sqrt((e.x-f.x)*(e.x-f.x)+(e.y-f.y)*(e.y-f.y)) < hw*0.5f) {
					explosion(f.x, f.y, 15);
					drones.remove(i);
				}
				if (f.t > 8 && Math.sqrt((e.x-f.x)*(e.x-f.x)+(e.y-f.y)*(e.y-f.y)) < hw*5) {
					double mx = (int)((float)f.x-e.x);
					double my = (int)((float)f.y-e.y);
					double temp = -Math.sqrt(mx*mx + my*my);
					f.vx = (int)(20*mx/temp);
					f.vy = (int)(20*my/temp);
					break;
				}
			}
		}
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
			if (f.collide(this) && ((!unlocked && Math.random()>0.6f) || (unlocked && Math.random()>0.8f))) {
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
