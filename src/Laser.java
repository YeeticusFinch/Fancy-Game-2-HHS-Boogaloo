import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Laser {
	int x = 0, y = 0;
	int dx = 0, dy = 0;
	int t = 0;
	float size = 0;
	PImage pic;
	boolean penetration = false;
	boolean ghost = false;
	boolean halt = false;
	int blinding;
	Color color;
	float ttl;
	public Laser(int x, int y, int dx, int dy, Color c, float size, float ttl) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.size = size;
		color = c;
		this.ttl = ttl;
		//checkCollisions();
	}
	
	public Laser (int x, int y, int dx, int dy, Color c, float size, float ttl, boolean penetration) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.size = size;
		this.penetration = penetration;
		color = c;
		this.ttl = ttl;
		//checkCollisions();
	}
	
	public void checkCollisions(PApplet g, ArrayList<String> map) {
		/*int mx = (int)((float)x-dx);
		int my = (int)((float)y-dy);
		
		double temp = -Math.sqrt(mx*mx + my*my);*/
		
		int tx = x;
		int ty = y;
		
		for (int i = 0; i < 100; i++) {
			int fancy = Math.max(Math.min(1+(int)((ty+g.width*0.025f)/(g.width*0.05f)), map.size()-1), 0);
			if (map.get(fancy).charAt(Math.max(Math.min((int)((tx+g.width*0.025f)/(g.width*0.05f)), map.get(fancy).length()-1), 0)) == '1') {
				dx = tx;
				dy = ty;
				break;
			}
			tx += (dx-x)/100;
			ty += (dy-y)/100;
		}
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
		//g.image(pic, x-g.width*size*0.5f, y-g.width*size*0.5f, g.width*size, g.width*size);
		if (t < 1)
			checkCollisions(g, map);
		g.pushStyle();
		g.strokeWeight(size);
		g.stroke(color.getRed(), color.getGreen(), color.getBlue(), 100-100*(t/ttl));
		g.line(x, y, dx, dy);
		g.strokeWeight(size/3);
		g.stroke(255, 100-100*(t/ttl));
		g.line(x, y, dx, dy);
		t++;
		//System.out.println(map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).charAt((int)((x+g.width*0.025f)/(g.width*0.05f))));
		/*if (1+(int)((y+g.width*0.025f)/(g.width*0.05f)) < map.size() && 1+(int)((y+g.width*0.025f)/(g.width*0.05f)) >= 0 && (int)((x+g.width*0.025f)/(g.width*0.05f)) >= 0 && (int)((x+g.width*0.025f)/(g.width*0.05f)) < map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).length()) {
			if (map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).charAt((int)((x+g.width*0.025f)/(g.width*0.05f))) == '1') {
				if (ghost) {
					t = 0;
				} else if (halt) {
					x-=dx;
					y-=dy;
					dx = 0;
					dy = 0;
				}
				else if (!penetration) {
					x-=dx;
					y-=dy;
					t = 1000;
				} else {
					map.set(1+(int)((y+g.width*0.025f)/(g.width*0.05f)), map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(0, (int)((x+g.width*0.025f)/(g.width*0.05f))) + '0' + map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(1+(int)((x+g.width*0.025f)/(g.width*0.05f))));
				}
			}
		}*/
		g.popStyle();
		
	}
	
	public boolean collide(float ox, float oy, float width, float height) {
		return pDistance(ox+width/2, oy+height/2, x, y, dx, dy)<height*size*0.01f && ox+width > Math.min(x, dx)-width*size*0.01f && ox < Math.max(x, dx)+width*size*0.01f && oy+height > Math.min(y, dy)-height*size*0.01f && oy < Math.max(y, dy)+height*size*0.01f;
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
	
	public float pDistance(float x, float y, float x1, float y1, float x2, float y2) {

	      float A = x - x1; // position of point rel one end of line
	      float B = y - y1;
	      float C = x2 - x1; // vector along line
	      float D = y2 - y1;
	      float E = -D; // orthogonal vector
	      float F = C;

	      float dot = A * E + B * F;
	      float len_sq = E * E + F * F;

	      return (float)(Math.abs(dot) / Math.sqrt(len_sq));
	    }
}
