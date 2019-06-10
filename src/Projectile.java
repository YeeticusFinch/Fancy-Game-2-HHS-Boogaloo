import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Projectile {
	int x = 0, y = 0;
	int vx = 0, vy = 0;
	int t = 0;
	float size = 0;
	PImage pic;
	boolean penetration = false;
	boolean ghost = false;
	boolean halt = false;
	int blinding;
	public Projectile(int x, int y, int vx, int vy, PImage pic, float size) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.pic = pic;
		this.size = size;
	}
	
	public Projectile (int x, int y, int vx, int vy, PImage pic, float size, boolean penetration) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.pic = pic;
		this.size = size;
		this.penetration = penetration;
	}
	
	public Projectile (int x, int y, int vx, int vy, PImage pic, float size, String special) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.pic = pic;
		this.size = size;
		if (special.equalsIgnoreCase("ghost"))
			ghost = true;
		else if (special.equalsIgnoreCase("halt"))
			halt = true;
		else if (special.substring(0, 3).equals("led"))
			blinding = Integer.parseInt(special.substring(special.indexOf(":")+1));
	}
	
	public void draw(PApplet g, ArrayList<String> map) {
		if (blinding > 0 && Math.random()>0.3f) {
			g.pushStyle();
			g.ellipseMode(g.RADIUS);
			g.noStroke();
			g.fill((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
			g.ellipse(x,  y, blinding, blinding);
			g.popStyle();
		}
		g.image(pic, x-g.width*size*0.5f, y-g.width*size*0.5f, g.width*size, g.width*size);
		x+=vx;
		y+=vy;
		t++;
		//System.out.println(map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).charAt((int)((x+g.width*0.025f)/(g.width*0.05f))));
		if (1+(int)((y+g.width*0.025f)/(g.width*0.05f)) < map.size() && 1+(int)((y+g.width*0.025f)/(g.width*0.05f)) >= 0 && (int)((x+g.width*0.025f)/(g.width*0.05f)) >= 0 && (int)((x+g.width*0.025f)/(g.width*0.05f)) < map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).length()) {
			if (map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).charAt((int)((x+g.width*0.025f)/(g.width*0.05f))) == '1') {
				if (ghost) {
					t = 0;
				} else if (halt) {
					x-=vx;
					y-=vy;
					vx = 0;
					vy = 0;
				}
				else if (!penetration) {
					x-=vx;
					y-=vy;
					t = 1000;
				} else {
					map.set(1+(int)((y+g.width*0.025f)/(g.width*0.05f)), map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(0, (int)((x+g.width*0.025f)/(g.width*0.05f))) + '0' + map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(1+(int)((x+g.width*0.025f)/(g.width*0.05f))));
				}
			}
		}
		
	}
	
	public boolean collide(float ox, float oy, float width, float height) {
		
		return x < ox + width && x + size > ox && y < oy + height && y + size > oy;
	}
	
	public boolean collide(Person other) {
		
		return collide(other.getX(), other.getY(), other.hw, other.hh);
	}
	
	public boolean collide(Enemy other) {
		if (blinding > 0 && Math.sqrt((other.x-x)*(other.x-x)+(other.y-y)*(other.y-y)) < blinding) {
			other.blinded = blinding;
		}
		return collide(other.getX(), other.getY(), other.hw, other.hh);
	}
}
