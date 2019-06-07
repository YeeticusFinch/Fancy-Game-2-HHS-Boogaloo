import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public abstract class Person {
	
	protected int speed;
	protected int dmg;
	protected int hp;
	protected int x;
	protected int y;
	protected int xo;
	protected int yo;
	protected PImage icon;
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
	
	public Person() {
		
	}
	
	public Person(int speed, int dmg, int hp) {
		this.speed = speed;
		this.dmg = dmg;
		this.hp = hp;
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
		
		if (icon == null)
			icon = g.loadImage("images" + FileIO.fileSep + "h" + id + ".png");
		g.fill(0);
		
		hw = g.width*0.06f;
		hh = g.width*0.09f;
		
		g.ellipseMode(PConstants.RADIUS);
		g.ellipse(x + g.width*0.03f, y + g.width*0.06f, g.width*0.01f, g.width*0.02f);
		
		g.ellipse(x+g.width*0.015f+f1x, y+g.width*0.09f+f1y, g.width*0.005f, g.width*0.005f); //FEET
		g.ellipse(x+g.width*0.045f+f2x, y+g.width*0.09f+f2y, g.width*0.005f, g.width*0.005f);
		
		g.ellipse(x+g.width*0.01f+h1x, y+g.width*0.065f+h1y, g.width*0.005f, g.width*0.005f); //HANDS
		g.ellipse(x+g.width*0.05f+h2x, y+g.width*0.065f+h2y, g.width*0.005f, g.width*0.005f);
		
		g.image(icon, x, y, g.width*0.06f, g.width*0.06f);
		
		if (keys[g.UP])
			y-=g.width*0.001*speed;
		if (keys[g.DOWN])
			y+=g.width*0.001*speed;
		if (keys[g.LEFT])
			x-=g.width*0.001*speed;
		if (keys[g.RIGHT])
			x+=g.width*0.001*speed;
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
		if (map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).charAt((int)((x+g.width*0.025f)/(g.width*0.05f))) == '1') {
			x = xo;
			y = yo;
		} else {
			xo = x;
			yo = y;
		}
		
		return map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).charAt((int)((x+g.width*0.025f)/(g.width*0.05f)));
		
	}
	
	public abstract void attack(int mx, int my);
	
	public abstract void attack2(int mx, int my);
	
}