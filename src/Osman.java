import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Osman extends Person {

	private float tx = 0;
	private float ty = 0;
	private int oldHP;
	
	private ArrayList<Projectile> elbows = new ArrayList<Projectile>();
	
	private ArrayList<Projectile> hPacks = new ArrayList<Projectile>();
	
	private PImage elbowPic;
	private PImage hPackPic;
	
	public Osman() {
		super(8, 7, 15); //Speed, Damage, HP
		oldHP = hp;
		
	}
	
	@Override
	public void attack(int mx, int my) { //Gives elbow connector
		
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		elbows.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), elbowPic, 0.03f));
		
	}
	
	@Override
	public void attack2(int mx, int my) { //Gives elbow connector
		
		tx = 0.3f*((float)super.x-mx);
		ty = 0.3f*((float)super.y-my);
		
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		super.draw( g,  id, keys, map);
		if (keys[32]) {
			if (keys[PConstants.UP])
				attack2(x, y-g.width/10);
			if (keys[PConstants.DOWN])
				attack2(x, y+g.width/10);
			if (keys[PConstants.LEFT])
				attack2(x-g.width/10, y);
			if (keys[PConstants.RIGHT])
				attack2(x+g.width/10, y);
		}
		if (elbowPic == null) {
			elbowPic = g.loadImage("images" + FileIO.fileSep + "elbow.png");
		}
		if (hPackPic == null) {
			hPackPic = g.loadImage("images" + FileIO.fileSep + "hPack.png");
		}
		if (Math.abs(tx) > 0 || Math.abs(ty) > 0) {
			super.x -= tx;
			super.y -= ty;
			tx*=0.7;
			ty*=0.7;
			if (Math.abs(tx) < 1)
				tx = 0;
			if (Math.abs(ty) < 1)
				ty = 0;
			hp = oldHP;
		}	
		for (int i = 0; i < elbows.size(); i++) {
			if (elbows.get(i).t<10)
				elbows.get(i).draw(g, map);
			else
				elbows.remove(i);
			
		}
		
		for (int i = 0; i < hPacks.size(); i++) {
			if (hPacks.get(i).t<200) {
				hPacks.get(i).draw(g, map);
				hPacks.get(i).vx *= 0.8f;
				hPacks.get(i).vy *= 0.8f;
			}
			else
				hPacks.remove(i);
			
		}
		
		oldHP = hp;
		
	}
	
	public void projectileCollide(PApplet g, ArrayList<Enemy> enemies) {
		for (Projectile f : elbows) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					
					if (Math.random()>((float)hp/18	)) {
						int mx = (int)((float)(Math.random()*200-100));
						int my = (int)((float)(Math.random()*200-100));
					
						double temp = -Math.sqrt(mx*mx + my*my);
						hPacks.add(new Projectile((int)(f.x+this.hw/2), (int)(f.y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), hPackPic, 0.1f, "halt"));
					}
					e.hp -= f.size*8;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
				}
			}
		}
		
		for (int i = 0; i < hPacks.size(); i++) {
			if (hPacks.get(i).collide(this)) {
				if (hp < 15) {
					hp++;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(0, 255, 0);
					g.stroke(0, 255, 0);
					g.ellipse(x, y, hw, hh);
					g.popStyle();
				}
				
				hPacks.remove(i);
				
			}
		}
	}
	
}
