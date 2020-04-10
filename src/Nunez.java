import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Nunez extends Enemy {

	boolean rushing = false;
	int dash = 0;
	int dvx = 0;
	int dvy = 0;
	
	ArrayList<Projectile> maths = new ArrayList<Projectile>();
	ArrayList<Projectile> phys = new ArrayList<Projectile>();
	ArrayList<Projectile> shields = new ArrayList<Projectile>();
	PImage[] mathPics = new PImage[8];
	PImage[] physPics = new PImage[9];
	PImage laserEyes;
	PImage balloonez;
	PImage moonez;
	PImage noonez;
	boolean shield;
	int shieldHP;
	public Nunez() {
		super(8, 4, 70, 9); //Speed, Damage, HP
		if (Math.random() > 0.5)
			rushing = true;
	}
	
	public void draw(PApplet g, boolean[] keys, ArrayList<String> map) {
		super.draw(g, keys, map);
		if (laserEyes == null) {
			laserEyes = g.loadImage("images" + FileIO.fileSep + "blunez.png");
		}
		if (balloonez == null) {
			balloonez = g.loadImage("images" + FileIO.fileSep + "balloonez.png");
		}
		if (moonez == null) {
			moonez = g.loadImage("images" + FileIO.fileSep + "moonez.png");
		}
		if (noonez == null) {
			noonez = g.loadImage("images" + FileIO.fileSep + "noonez.png");
		}
		if (mathPics[0] == null) {
			for (int i = 0; i < mathPics.length; i++)
				mathPics[i] = g.loadImage("images" + FileIO.fileSep + "math" + i + ".png");
			
		}
		
		if (physPics[0] == null) {
			for (int i = 0; i < physPics.length; i++)
				physPics[i] = g.loadImage("images" + FileIO.fileSep + "phys" + i + ".png");
		}
		for (int j = 0; j < maths.size(); j++) {
			if (maths.get(j).t<15)
				maths.get(j).draw(g, map);
			else
				maths.remove(j);
			
		}
		for (int j = 0; j < phys.size(); j++) {
			if (phys.get(j).t<200)
				phys.get(j).draw(g, map);
			else
				phys.remove(j);
			
		}
		
		for (int j = 0; j < shields.size(); j++) {
			if (hp > 0 && shield)
				shields.get(j).draw(g, map);
			else
				shields.remove(j);
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
		if (shield) {
			if (Math.random() > 0.93) {
				dash = 17;
				dvx = vx + (int)(Math.random()*3-1);
				dvy = vy + (int)(Math.random()*3-1);
			}
			hp = shieldHP;
			if (dash > 7) {
				x += 5 * dvx;
				y += 5 * dvy;
				dash--;
			} else {
				x += 0.5 * vx;
				y += 0.5 * vy;
			}
			rushing = true;
			if (altIcon == null)
				altIcon = laserEyes;
		} else if (altIcon != null)
			altIcon = null;
		else if (!shield) {
			x+=vx;
			y+=vy;
		}
		if (Math.random()>0.99)
			rushing = !rushing;
		
		if (fox < 1 && Math.random() > 0.990 && !shield)
			attack(px, py);
		
		if (fox < 1 && Math.random() > 0.995 && !shield)
			attack2(px, py);
		if (Math.random() > 0.995) {
			shield = !shield;
			if (shield == true) {
				shieldHP = hp;
				shields.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), 0, 0, moonez, 0.08f, true));
				shields.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), 0, 0, noonez, 0.08f, true));
				shields.get(shields.size()-1).t = 8;
				shields.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), 0, 0, balloonez, 0.08f, true));
				shields.get(shields.size()-1).t = 16;
			} else
				hp = shieldHP;
		}
		
	}

	@Override
	public void attack(int mx, int my) {
		// TODO Auto-generated method stub
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		maths.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), mathPics[(int)(Math.random()*8)], 0.06f));
		
		//System.out.println("Throwing hw");
	}

	@Override
	public void attack2(int mx, int my) {
		// TODO Auto-generated method stub
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		phys.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(10*mx/temp), (int)(10*my/temp), physPics[(int)(Math.random()*9)], 0.1f, true));
		
	}
	
	public void projectileCollide(PApplet g, Person e) {
		
		for (Projectile f : maths) {
			if (e.hp > 0 && f.collide(e)) {
				e.hp -= f.size*0.5f;
				g.pushStyle();
				g.ellipseMode(PConstants.CORNER);
				g.fill(255, 0, 0);
				g.ellipse(e.x, e.y, e.hw, e.hh);
				g.popStyle();
			}
			
		}
		
		for (Projectile f : shields) {
			f.x = x+(int)(hw/2+200*Math.cos((float)f.t/4));
			f.y = y+(int)(hh/2+200*Math.sin((float)f.t/4));
			if (e.hp > 0 && f.collide(e)) {
				e.hp -= f.size*0.5f;
				g.pushStyle();
				g.ellipseMode(PConstants.CORNER);
				g.fill(255, 0, 0);
				g.ellipse(e.x, e.y, e.hw, e.hh);
				g.popStyle();
			}
			
		}
		
		for (Projectile f : phys) {
			if (Math.random() > 0.7) {
				int mx = (int)(f.x-e.x);
				int my = (int)(f.y-e.y);
				double temp = -Math.sqrt(mx*mx + my*my);
				f.vx = (int)(10*mx/temp);
				f.vy = (int)(10*my/temp);
			}
			if (e.hp > 0 && f.collide(e)) {
				e.hp -= 9;
				g.pushStyle();
				g.ellipseMode(PConstants.CORNER);
				g.fill(0, 0, 0);
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
		maths.clear();
		phys.clear();
	}

	
	
}
