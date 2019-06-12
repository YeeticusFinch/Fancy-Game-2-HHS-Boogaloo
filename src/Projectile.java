import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Projectile {
	ArrayList<Projectile> extras = new ArrayList<Projectile>();
	PImage[] sparkPic = new PImage[7];
	int x = 0, y = 0;
	int vx = 0, vy = 0;
	int t = 0;
	float size = 0;
	PImage pic;
	boolean penetration = false;
	boolean ghost = false;
	boolean halt = false;
	int blinding;
	int phantom = -1;
	int ttl;
	boolean fade;
	boolean rotate;
	float rotateMod = 0;
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
		fancyConstruction(x, y, vx, vy, pic, size, special);
			
	}
	
	public Projectile (int x, int y, int vx, int vy, PImage pic, float size, boolean penetration, String special) {
		fancyConstruction(x, y, vx, vy, pic, size, special);
		this.penetration = penetration;
	}
	
	private void fancyConstruction(int x, int y, int vx, int vy, PImage pic, float size, String special) {
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
		else if (special.equalsIgnoreCase("phantom")) {
			phantom = 3;
			ghost = true;
			
		} else if (special.substring(0, 4).equals("fade")) {
			fade = true;
			ttl = Integer.parseInt(special.substring(special.indexOf(":")+1));
		} else if (special.equalsIgnoreCase("rotate"))
			rotate = true;
	}
	
	public void draw(PApplet g, ArrayList<String> map) {
		for (int i = 0; i < extras.size(); i++) {
			if (extras.get(i).t<Math.random()*5)
				extras.get(i).draw(g, map);
			else
				extras.remove(i);
			
		}
		for (Projectile e : extras)
			e.draw(g, map);
		if (blinding > 0 && Math.random()>0.3f) {
			g.pushStyle();
			g.ellipseMode(g.RADIUS);
			g.noStroke();
			g.fill((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
			g.ellipse(x,  y, blinding, blinding);
			g.popStyle();
		}
		if (rotate) {
			g.pushMatrix();
			g.translate(x-g.width*size*0.1f, y-g.width*size*0.1f);
			if(vx > 0)
				g.rotate((float)Math.atan((double)vy/vx)+(float)(Math.PI/2) + rotateMod);
			else {
				g.rotate((float)Math.atan((double)vy/vx)+(float)(3*Math.PI/2) + rotateMod);
			}
			g.translate(-x+g.width*size*0.1f, -y+g.width*size*0.1f);
			g.image(pic, x-g.width*size*0.5f, y-g.width*size*0.5f, g.width*size, g.width*size);
			g.popMatrix();
		}
		else if (!fade)
			g.image(pic, x-g.width*size*0.5f, y-g.width*size*0.5f, g.width*size, g.width*size);
		else {
			g.pushStyle();
			g.tint(100, 100-100*((float)t/ttl));
			g.image(pic, x-g.width*size*0.5f, y-g.width*size*0.5f, g.width*size, g.width*size);
			g.popStyle();
		}
		x+=vx;
		y+=vy;
		t++;
		//System.out.println(map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).charAt((int)((x+g.width*0.025f)/(g.width*0.05f))));
		if (1+(int)((y+g.width*0.025f)/(g.width*0.05f)) < map.size() && 1+(int)((y+g.width*0.025f)/(g.width*0.05f)) >= 0 && (int)((x+g.width*0.025f)/(g.width*0.05f)) >= 0 && (int)((x+g.width*0.025f)/(g.width*0.05f)) < map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).length()) {
			if (map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).charAt((int)((x+g.width*0.025f)/(g.width*0.05f))) == '1') {
				if (ghost) {
					t = 0;
					if (phantom > -1) {
						phantom--;
						if (sparkPic[0] == null)
							for (int i = 0; i < sparkPic.length; i++)
								sparkPic[i] = g.loadImage("images" + FileIO.fileSep + "s" + i + ".png");
						int mx = (int)((float)(Math.random()*200-100));
						int my = (int)((float)(Math.random()*200-100));
						
						double temp = -Math.sqrt(mx*mx + my*my);
						if (Math.random()>0.8f)
							extras.add(new Projectile(x, y, (int)(30*mx/temp), (int)(30*my/temp), sparkPic[(int)(Math.random()*7)], (float)Math.random()*0.05f+0.02f, "phantom"));
						else
							extras.add(new Projectile(x, y, (int)(30*mx/temp), (int)(30*my/temp), sparkPic[(int)(Math.random()*7)], (float)Math.random()*0.05f+0.02f, "ghost"));
					}
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
		boolean res = false;
		for (Projectile e : extras) {
			if (e.collide(ox, oy, width, height)) {
				res = true;
				break;
			}
		}
		
		return res || (x < ox + width && x + size > ox && y < oy + height && y + size > oy);
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
