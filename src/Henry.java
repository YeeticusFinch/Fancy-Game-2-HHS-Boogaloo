import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Henry extends Enemy {

	boolean rushing = false;
	
	ArrayList<Projectile> swords = new ArrayList<Projectile>();
	PImage[] swordPics = new PImage[4];
	
	ArrayList<Projectile> music = new ArrayList<Projectile>();
	PImage[] musicPics = new PImage[4];
	
	public Henry() {
		super(8, 4, 8, 4); //Speed, Damage, HP
		if (Math.random() > 0.5)
			rushing = true;
	}
	
	public void draw(PApplet g, boolean[] keys, ArrayList<String> map) {
		super.draw( g, keys, map);
		if (swordPics[0] == null)
			for (int i = 0; i < swordPics.length; i++)
			swordPics[i] = g.loadImage("images" + FileIO.fileSep + "sword" + i + ".png");
		
		for (int j = 0; j < swords.size(); j++) {
			if (swords.get(j).t<12)
				swords.get(j).draw(g, map);
			else
				swords.remove(j);
			
		}
		if (musicPics[0] == null)
			for (int i = 0; i < musicPics.length; i++)
			musicPics[i] = g.loadImage("images" + FileIO.fileSep + "music" + i + ".png");
		
		for (int j = 0; j < music.size(); j++) {
			if (music.get(j).t<30)
				music.get(j).draw(g, map);
			else
				music.remove(j);
			
		}
		
	}
	
	@Override
	public void move(int px, int py) {
		// TODO Auto-generated method stub
		
		if (rushing) {
			float temp = (float)Math.sqrt( (px-x)*(px-x) + (py-y)*(py-y) );
			vx = (int)(speed*(px-x)/temp);
			vy = (int)(speed*(py-y)/temp);
		} else if (Math.random()>0.8){
			px = (int)(Math.random()*200-100);
			py = (int)(Math.random()*200-100);
			float temp = (float)Math.sqrt( (px)*(px) + (py)*(py) );
			vx = (int)(speed*(px)/temp);
			vy = (int)(speed*(py)/temp);
		}
		x+=vx;
		y+=vy;
		if (Math.random()>0.992)
			rushing = !rushing;
		
		if (fox < 1 && Math.random() > 0.99)
			attack(px, py);
		if (fox < 1 && Math.random() > 0.993)
			attack2(px, py);
	}

	@Override
	public void attack(int mx, int my) {
		// TODO Auto-generated method stub
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		swords.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), swordPics[(int)(Math.random()*swordPics.length)], 0.08f));
		
		//System.out.println("Throwing hw");
	}

	@Override
	public void attack2(int mx, int my) {
		// TODO Auto-generated method stub
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		music.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), musicPics[(int)(Math.random()*musicPics.length)], 0.1f));
		
	}
	
	public void projectileCollide(PApplet g, Person e) {
		for (Projectile f : swords) {
			if (e.hp > 0 && f.collide(e)) {
				e.hp -= f.size*0.5f;
				g.pushStyle();
				g.ellipseMode(PConstants.CORNER);
				g.fill(255, 0, 0);
				g.ellipse(e.x, e.y, e.hw, e.hh);
				g.popStyle();
			}
			
		}
		for (Projectile f : music) {
			if (e.hp > 0 && f.collide(e)) {
				e.hp -= f.size*0.2f;
				g.pushStyle();
				g.ellipseMode(PConstants.CORNER);
				g.fill(255, 0, 0);
				g.ellipse(e.x, e.y, e.hw, e.hh);
				g.popStyle();
			}
			
		}
	}
	
	public char collide(PApplet g, ArrayList<String> map) {
		char result = super.collide(g, map);
		if (result == '1')
			rushing = false;
		return result;
	}

	@Override
	public void deleteProjectiles() {
		// TODO Auto-generated method stub
		swords.clear();
	}

	
	
}
