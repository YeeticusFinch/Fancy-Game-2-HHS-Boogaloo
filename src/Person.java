import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public abstract class Person {
	
	protected int speed;
	protected int maxSpeed;
	protected int dmg;
	protected int hp;
	protected int maxHP;
	protected int x;
	protected int y;
	protected int xo;
	protected int yo;
	protected PImage icon;
	protected PImage altIcon;
	protected PImage foxIcon;
	protected PImage slinkyIcon;
	protected PImage smallSlinkyIcon;
	protected PImage bigSlinkyIcon;
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
	public int mode = 0;
	public boolean unlocked = false;
	protected float modX = 1, modY = 1;
	public int fox = 0;
	public boolean slinky;
	public boolean smallSlinky;
	public boolean bigSlinky;
	public boolean glass;
	public int blinded;
	public int ox = 0, oy = 0;
	public boolean enemy = false;
	
	protected java.awt.Color altColor;
	
	public Person() {
		
	}
	
	public Person(int speed, int dmg, int hp) {
		this.speed = speed;
		this.dmg = dmg;
		this.hp = hp;
		maxHP = hp;
		maxSpeed = speed;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void spawn(ArrayList<String> map, PApplet g) {
		for (int i = 0; i < map.size(); i++) {
			for (int j = 0; j < map.get(i).length(); j++) {
				if (map.get(i).charAt(j) == '3') {
					x = (int)(g.width*0.05f*(j));
					y = (int)(g.width*0.05f*(i-1.5f));
					System.out.println("Placing at spawnpoint " + x + ", " + y);
					xo = x;
					yo = y;
				}
			}
		}
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) { // W = 87, A = 65, S = 83, D = 68, Q = 81, E = 69
		g.pushStyle();
		/*if (keys[192]) { //Cheat (for debugging purposes)
			maxHP *= 10;
			hp *= 10;
		}*/
		if (foxIcon == null)
			foxIcon = g.loadImage("images" + FileIO.fileSep + "fox.png");
		if (slinkyIcon == null)
			slinkyIcon = g.loadImage("images" + FileIO.fileSep + "h0m1.png");
		if (smallSlinkyIcon == null)
			smallSlinkyIcon = g.loadImage("images" + FileIO.fileSep + "plastics.png");
		if (bigSlinkyIcon == null)
			bigSlinkyIcon = g.loadImage("images" + FileIO.fileSep + "metals.png");
		if (icon == null)
			icon = g.loadImage("images" + FileIO.fileSep + "h" + id + ".png");
		if (altColor == null)
			g.fill(0);
		else 
			g.fill(altColor.getRed(), altColor.getGreen(), altColor.getBlue());
		hw = g.width*0.06f;
		hh = g.width*0.09f;
		
		g.ellipseMode(PConstants.RADIUS);
		g.ellipse(x + g.width*0.03f, y + g.width*0.06f, g.width*0.01f, g.width*0.02f);
		
		g.ellipse(x+g.width*0.015f+f1x, y+g.width*0.09f+f1y, g.width*0.005f, g.width*0.005f); //FEET
		g.ellipse(x+g.width*0.045f+f2x, y+g.width*0.09f+f2y, g.width*0.005f, g.width*0.005f);
		
		g.ellipse(x+g.width*0.01f+h1x, y+g.width*0.065f+h1y, g.width*0.005f, g.width*0.005f); //HANDS
		g.ellipse(x+g.width*0.05f+h2x, y+g.width*0.065f+h2y, g.width*0.005f, g.width*0.005f);
		
		if (altIcon == null)
			g.image(icon, x, y, g.width*0.06f, g.width*0.06f);
		else
			g.image(altIcon, x, y, g.width*0.06f, g.width*0.06f);
		
		if (fox > 0) {
			fox-=8;
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
		else if (fox < 1) {
			slinky = false;
			glass = false;
			smallSlinky = false;
			bigSlinky = false;
			if (keys[g.UP])
				y-=g.width*0.001*speed*modY;
			if (keys[g.DOWN])
				y+=g.width*0.001*speed*modY;
			if (keys[g.LEFT])
				x-=g.width*0.001*speed*modX;
			if (keys[g.RIGHT])
				x+=g.width*0.001*speed*modX;
			if ((modX != 1 && modY != 1) && (keys[g.UP] || keys[g.DOWN] || keys[g.LEFT] || keys[g.RIGHT])) {
				y+=speed*(modY-0.5f);
				x+=speed*(modX-0.5f);
			}
		}
		if (ox != 0 && oy != 0) {
			int vx = x-ox;
			int vy = y-oy;
			int temp = (int)(Math.sqrt(vx*vx + vy*vy));
			x+=speed*vx/temp;
			y+=speed*vy/temp;
		}
		if (blinded > 0) {
			blinded--;
			System.out.println("BLINDED");
			g.fill(255);
			g.rect(GUI.tx, GUI.ty, g.width, g.height);
		}
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
		char fancyChar = map.get(fancy).charAt(Math.max(Math.min((int)((x+g.width*0.025f)/(g.width*0.05f)),map.get(fancy).length()-1), 0));
		if (fancyChar == '1') {
			x = xo;
			y = yo;
		} else if (fancyChar == '5') {
			if (hp < maxHP) {
				map.set(1+(int)((y+g.width*0.025f)/(g.width*0.05f)), map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(0, (int)((x+g.width*0.025f)/(g.width*0.05f))) + '0' + map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(1+(int)((x+g.width*0.025f)/(g.width*0.05f))));
				hp = Math.min(hp+maxHP/2, maxHP);
			}
		} else {
			xo = x;
			yo = y;
		}
		
		return fancyChar;
		
	}
	
	public abstract void attack(int mx, int my);
	
	public abstract void attack2(int mx, int my);
	
	public abstract void projectileCollide(PApplet g, ArrayList<Enemy> enemies);

	public abstract void enemyProjectileCollide(PApplet g, Person p);

	public boolean nukeIsClose(int mx, int my) {
		// TODO Auto-generated method stub
		return false;
	}
	
}