import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public abstract class Enemy {
	
	protected int speed;
	protected int dmg;
	protected int hp;
	protected int x;
	protected int y;
	protected int xo;
	protected int yo;
	protected PImage icon;
	protected PImage foxIcon;
	protected PImage slinkyIcon;
	protected PImage smallSlinkyIcon;
	protected PImage bigSlinkyIcon;
	protected PImage memeIcon;
	protected int f1x;
	protected int f1y;
	protected int f2x;
	protected int f2y;
	protected int h1x;
	protected int h1y;
	protected int h2x;
	protected int h2y;
	protected float hw;
	protected float hh;
	protected int id;
	protected int vx;
	protected int vy;
	public int fox = 0;
	public boolean slinky = false;
	public boolean glass = false;
	protected int maxSpeed;
	protected PImage altIcon = null;
	public int ox = 0, oy = 0;
	public int blinded = 0;
	public boolean meme = false;
	public boolean smallSlinky = false;
	public boolean bigSlinky = false;
	
	public Enemy() {
		
	}
	
	public Enemy(int speed, int dmg, int hp, int id) {
		this.speed = speed;
		maxSpeed = speed;
		this.dmg = dmg;
		this.hp = hp;
		this.id = id;
		x = -1;
		y = -1;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void spawn(ArrayList<String> map, PApplet g) {
		for (int i = 1; i < map.size(); i++) {
			for (int j = 0; j < map.get(i).length(); j++) {
				if (map.get(i).charAt(j) == '4' && Math.random()>0.6) {
					x = (int)(g.width*0.05f*(j));
					y = (int)(g.width*0.05f*(i-1.5f));
					System.out.println("Placing " + this.getClass() + " at spawnpoint " + x + ", " + y);
					xo = x;
					yo = y;
				}
			}
		}
	}
	
	public void draw(PApplet g, boolean[] keys, ArrayList<String> map) { // W = 87, A = 65, S = 83, D = 68, Q = 81, E = 69
		g.pushStyle();
		
		g.fill(0);
		g.textSize(g.width*0.01f);
		g.text("HP: " + hp, x, y-g.width*0.01f);
		
		while (x == -1 && y == -1) {
			spawn(map, g);
		}
		
		
		if (memeIcon == null)
			memeIcon = g.loadImage("images" + FileIO.fileSep + "e" + id + "m.png");
		if (icon == null)
			icon = g.loadImage("images" + FileIO.fileSep + "e" + id + ".png");
		if (foxIcon == null)
			foxIcon = g.loadImage("images" + FileIO.fileSep + "fox.png");
		if (slinkyIcon == null)
			slinkyIcon = g.loadImage("images" + FileIO.fileSep + "h0m1.png");
		if (smallSlinkyIcon == null)
			smallSlinkyIcon = g.loadImage("images" + FileIO.fileSep + "plastics.png");
		if (bigSlinkyIcon == null)
			bigSlinkyIcon = g.loadImage("images" + FileIO.fileSep + "metals.png");
		
		g.fill(0);
		
		hw = g.width*0.06f;
		hh = g.width*0.09f;
		
		g.ellipseMode(PConstants.RADIUS);
		g.ellipse(x + g.width*0.03f, y + g.width*0.06f, g.width*0.01f, g.width*0.02f);
		
		g.ellipse(x+g.width*0.015f+f1x, y+g.width*0.09f+f1y, g.width*0.005f, g.width*0.005f); //FEET
		g.ellipse(x+g.width*0.045f+f2x, y+g.width*0.09f+f2y, g.width*0.005f, g.width*0.005f);
		
		g.ellipse(x+g.width*0.01f+h1x, y+g.width*0.065f+h1y, g.width*0.005f, g.width*0.005f); //HANDS
		g.ellipse(x+g.width*0.05f+h2x, y+g.width*0.065f+h2y, g.width*0.005f, g.width*0.005f);
		
		if (meme) {
			g.image(memeIcon, x, y, g.width*0.06f, g.width*0.06f);
			if (blinded < 2 && Math.random()>0.98f)
				blinded += Math.random()*50;
		} else if (altIcon == null)
			g.image(icon, x, y, g.width*0.06f, g.width*0.06f);
		else
			g.image(altIcon, x, y, g.width*0.06f, g.width*0.06f);
		
		g.popStyle();
		
		if (Math.abs(f1x) > 0) {
			f1x*=0.8;
		}
		if (Math.abs(f2x) > 0) {
			f2x*=0.8;
		}
		if (Math.abs(f1y) > 0) {
			f1y*=0.8;
		}
		if (Math.abs(f2y) > 0) {
			f2y*=0.8;
		}
		
		if (blinded > 1) {
			blinded--;
			ox = x +(int)( Math.random()*200-100);
			oy = y +(int)( Math.random()*200-100);
			g.text("BLINDED", x, y);
		} else if (blinded > 0){
			ox = 0;
			oy = 0;
			blinded--;
		}
		
		if (fox>0) {
			speed = maxSpeed/3;
			if (fox>0) {
				speed = maxSpeed/3;
				fox--;
			}
			else
				speed = maxSpeed;
			fox--;
			if (glass) {
				g.pushStyle();
				g.textSize(g.width*0.03f);
				g.fill(255, 0, 0);
				g.text("OW", x+g.width*0.04f*(float)Math.random()-0.02f, y+g.width*0.04f*(float)Math.random()-0.02f);
				g.popStyle();
			} else if (slinky) {
				g.image(slinkyIcon, x, y, hw, hh/2);
			}
			else if (smallSlinky)
				g.image(smallSlinkyIcon, x, y, hw, hh/2);
			else if (bigSlinky)
				g.image(bigSlinkyIcon, x, y, hw, hh/2);
			else
				g.image(foxIcon, x, y, hw, hh/2);
		}
		else {
			speed = maxSpeed;
			glass = false;
			slinky = false;
			smallSlinky = false;
			bigSlinky = false;
		}
		
		
	}
	
	public void setLoc(int x, int y) {
		this.x = x;
		this.y = y;
		this.xo = x;
		this.yo = y;
	}
	
	public char collide(PApplet g, ArrayList<String> map) {
		if (xo != x) {
			f1x = xo-x;
			f2x = x-xo;
			f1x *= 2*Math.sin(1.5*x);
			f2x *= 2*Math.sin(1.5*x);
		} if (yo != y) {
			f1y = yo-y;
			f2y = y-yo;
			f1y *= 2*Math.sin(1.5*y);
			f2y *= 2*Math.sin(1.5*y);
		}
		//g.rect(g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
		int fancy = Math.max(Math.min(1+(int)((y+g.width*0.025f)/(g.width*0.05f)), map.size()-1), 0);
		if (map.get(fancy).charAt(Math.max(Math.min((int)((x+g.width*0.025f)/(g.width*0.05f)), map.get(fancy).length()-1), 0)) == '1') {
			x = xo;
			y = yo;
		} else {
			xo = x;
			yo = y;
		}
		
		return map.get(fancy).charAt(Math.max(Math.min((int)((x+g.width*0.025f)/(g.width*0.05f)), map.get(fancy).length()-1), 0));
		
	}
	
	public abstract void deleteProjectiles();
	
	public abstract void move(int px, int py);
	
	public abstract void attack(int mx, int my);
	
	public abstract void attack2(int mx, int my);

	public abstract void projectileCollide(PApplet g, Person p);
	
}