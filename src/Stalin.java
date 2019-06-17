import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Stalin extends Enemy {

	boolean rushing = false;
	
	ArrayList<Projectile> russians = new ArrayList<Projectile>();
	ArrayList<Projectile> gulags = new ArrayList<Projectile>();
	ArrayList<Projectile> communism = new ArrayList<Projectile>();
	PImage commie;
	PImage[] russianPics = new PImage[6];
	PImage[] gulagPics = new PImage[4];
	PImage bearPic;
	boolean bear;
	
	public Stalin() {
		super(4, 4, 120, 8); //Speed, Damage, HP
		if (Math.random() > 0.5)
			rushing = true;
	}
	
	public void draw(PApplet g, boolean[] keys, ArrayList<String> map) {
		super.draw( g, keys, map);
		if (commie == null)
			commie = g.loadImage("images" + FileIO.fileSep + "commie.png");
		if (bearPic == null) {
			bearPic = g.loadImage("images" + FileIO.fileSep + "bear.png");
		}
		if (russianPics[0] == null) {
			for (int i = 0; i < russianPics.length; i++)
				russianPics[i] = g.loadImage("images" + FileIO.fileSep + "rs" + i + ".png");
			
			//System.out.println("Loaded Darius images");
		}
		
		if (gulagPics[0] == null) {
			for (int i = 0; i < gulagPics.length; i++)
				gulagPics[i] = g.loadImage("images" + FileIO.fileSep + "gulag" + i + ".png");
		}
		for (int j = 0; j < russians.size(); j++) {
			if (russians.get(j).t<15)
				russians.get(j).draw(g, map);
			else
				russians.remove(j);
			
		}
		for (int j = 0; j < gulags.size(); j++) {
			if (gulags.get(j).t<22)
				gulags.get(j).draw(g, map);
			else
				gulags.remove(j);
			
		}
		
		for (int j = 0; j < communism.size(); j++) {
			if (communism.get(j).t<5)
				communism.get(j).draw(g, map);
			else
				communism.remove(j);
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
		if (bear) {
			x += 2 * vx;
			y += 2 * vy;
			rushing = true;
			if (altIcon == null)
				altIcon = bearPic;
		} else if (altIcon != null)
			altIcon = null;
		else if (!bear) {
			x+=vx;
			y+=vy;
		}
		if (Math.random()>0.99)
			rushing = !rushing;
		
		if (fox < 1 && Math.random() > 0.991 && !bear)
			attack(px, py);
		
		if (fox < 1 && Math.random() > 0.995 && !bear)
			attack2(px, py);
		if (Math.random() > 0.995)
			bear = !bear;
		if (bear && Math.random() > 0.4)
			communism.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(Math.random()*30-15), (int)(Math.random()*30-15), commie, (float)Math.random() * 0.03f, Math.random()>0.6f));
		
	}

	@Override
	public void attack(int mx, int my) {
		// TODO Auto-generated method stub
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		russians.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), russianPics[(int)(Math.random()*4)], 0.06f));
		
		//System.out.println("Throwing hw");
	}

	@Override
	public void attack2(int mx, int my) {
		// TODO Auto-generated method stub
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		gulags.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(40*mx/temp), (int)(40*my/temp), gulagPics[(int)(Math.random()*4)], 0.22f, true));
		
	}
	
	public void projectileCollide(PApplet g, Person e) {
		
		for (Projectile f : russians) {
			if (e.hp > 0 && f.collide(e)) {
				e.hp -= f.size*0.5f;
				g.pushStyle();
				g.ellipseMode(PConstants.CORNER);
				g.fill(255, 0, 0);
				g.ellipse(e.x, e.y, e.hw, e.hh);
				g.popStyle();
			}
			
		}
		
		for (Projectile f : communism) {
			if (e.hp > 0 && f.collide(e)) {
				e.hp -= f.size*0.5f;
				g.pushStyle();
				g.ellipseMode(PConstants.CORNER);
				g.fill(255, 0, 0);
				g.ellipse(e.x, e.y, e.hw, e.hh);
				g.popStyle();
			}
			
		}
		
		for (Projectile f : gulags) {
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
		russians.clear();
		gulags.clear();
	}

	
	
}
