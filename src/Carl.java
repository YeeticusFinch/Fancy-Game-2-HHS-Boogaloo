import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Carl extends Person {

	private ArrayList<Projectile> thumbs = new ArrayList<Projectile>();
	private ArrayList<Projectile> darts = new ArrayList<Projectile>();
	private ArrayList<Projectile> unicycles = new ArrayList<Projectile>();
	private PImage thumbPic;
	private PImage dartPic;
	private PImage blaster;
	int shoot = 0;
	int throwCool = 0;
	boolean unicycle = false;
	int uc = 0;
	PImage[] yeetcycle = new PImage[4];
	float uvx = 0;
	float uvy = 0;
	boolean nerf = false;
	int tmx;
	int tmy;
	PImage armor;
	PImage dalekPic;
	boolean dalek = false;
	int carlHP;
	int dalekHP;
	int carlMaxHP;
	int dalekMaxHP;
	
	public Carl() {
		super(5, 5, 15); //Speed, Damage, HP
		dalekHP = 45;
		dalekMaxHP = 45;
	}
	
	@Override
	public void attack(int mx, int my) { //Throws thumb

		if (!nerf && !dalek) {
		
			if (throwCool == 0) {
				throwCool = 15;
				mx = (int)((float)super.x-mx);
				my = (int)((float)super.y-my);
				
				double temp = -Math.sqrt(mx*mx + my*my);
				
				thumbs.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(50*mx/temp), (int)(50*my/temp), thumbPic, 0.06f));
			}
		
		} else if (nerf) {
			if (throwCool == 0) {
				throwCool = 40;
				shoot = 30;
				tmx = mx;
				tmy = my;
			}
		} else if (dalek) {
			if (throwCool == 0) {
				throwCool = 50;
				shoot = 20;
				tmx = mx;
				tmy = my;
			}
		}
		
	}
	
	public void shoot(int mx, int my) {
		mx = (int)((float)super.x-mx) + (int)(Math.random()*200-100);
		my = (int)((float)super.y-my) + (int)(Math.random()*200-100);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		darts.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(40*mx/temp), (int)(40*my/temp), dartPic, 0.04f));
	
	}
	
	@Override
	public void attack2(int mx, int my) { //Gives elbow connector
		
		
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		
		if (yeetcycle[0] == null) {
			for (int i = 0; i < yeetcycle.length; i++) {
				yeetcycle[i] = g.loadImage("images" + FileIO.fileSep + "uc" + i + ".png");
			}
		}
		
		if (armor == null)
			armor = g.loadImage("images" + FileIO.fileSep + "h3armor.png");
		
		super.draw( g,  id, keys, map);
		
		if (dalekPic == null) {
			dalekPic = g.loadImage("images" + FileIO.fileSep + "h3m2.png");
		}
		
		if (blaster == null) {
			blaster = g.loadImage("images" + FileIO.fileSep + "h3m1.png");
		}
		
		if (unicycle) {
			g.image(yeetcycle[uc], x-hw/2, y+hh/2, hw*2, hh);
			uc++;
			uc %= yeetcycle.length;
			x += uvx;
			y += uvy;
			if (keys[PConstants.RIGHT])
				uvx = Math.min(uvx+1, 10);
			if (keys[PConstants.LEFT])
				uvx = Math.max(uvx-1, -10);
			if (keys[PConstants.DOWN])
				uvy = Math.min(uvy+1, 10);
			if (keys[PConstants.UP])
				uvy = Math.max(uvy-1, -10);
		}
		
		if (dalek) {
			speed = 0;
			g.image(dalekPic, x-hw, y-hh/2, hw*3, hh*2);
			
			x += uvx;
			y += uvy;
			if (keys[PConstants.RIGHT])
				uvx = Math.min(uvx+0.3f, 10);
			if (keys[PConstants.LEFT])
				uvx = Math.max(uvx-0.3f, -10);
			if (keys[PConstants.DOWN])
				uvy = Math.min(uvy+0.3f, 10);
			if (keys[PConstants.UP])
				uvy = Math.max(uvy-0.3f, -10);
		} else if (mode == 2) {
			speed = 5;
		}
		
		if (dartPic == null)
			dartPic = g.loadImage("images" + FileIO.fileSep + "dart.png");
		
		if (keys[32]) {
			if (mode == 0) {
				unicycle = !unicycle;
				keys[32] = false;
				if (unlocked && !unicycle) {
					unicycles.add(new Projectile(x, y+(int)(hh*0.5f), (int)uvx*2, (int)uvy*2, yeetcycle[uc], 0.1f));
				}
				uvx = 1;
				uvy = 0;
				
			} else if (mode == 1) {
				nerf = !nerf;
				keys[32] = false;
			} else if (mode == 2) {
				if (!dalek) {
					carlMaxHP = maxHP;
					dalekMaxHP = carlMaxHP * 3;
					carlHP = hp;
					maxHP = dalekMaxHP;
					hp = dalekHP;
					uvx = 0;
					uvy = 0;
				} else {
					dalekHP = hp;
					hp = carlHP;
					dalekMaxHP = maxHP;
					maxHP = carlMaxHP;
					
				}
				
				dalek = !dalek;
				keys[32] = false;
			}
		}
		if (shoot > 0) {
			if (shoot % 3 == 0) {
				shoot(tmx, tmy);
			}
			shoot--;
		}
		
		if (throwCool > 0)
			throwCool--;
		if (thumbPic == null)
			thumbPic = g.loadImage("images" + FileIO.fileSep + "thumb.png");
		
		for (int i = 0; i < thumbs.size(); i++) {
			if (thumbs.get(i).t<40)
				thumbs.get(i).draw(g, map);
			else
				thumbs.remove(i);
			
		}
		
		for (int i = 0; i < unicycles.size(); i++) {
			if (unicycles.get(i).t<30)
				unicycles.get(i).draw(g, map);
			else
				unicycles.remove(i);
			
		}
		
		for (int i = 0; i < darts.size(); i++) {
			if (darts.get(i).t<15)
				darts.get(i).draw(g, map);
			else
				darts.remove(i);
			
		}
		
		if (nerf && altIcon == null)
			altIcon = g.loadImage("images" + FileIO.fileSep + "h3alt.png");
		else if (!nerf && altIcon != null) {
			altIcon = null;
			speed = 5;
		}
		if (nerf) {
			speed = (int)(5 * 0.8);
			g.image(armor, x-hw*0.3f, y, hw*1.5f, hh*1.5f);
			g.image(altIcon, x, y, g.width*0.06f, g.width*0.06f);
			g.image(blaster, x-hw*0.5f, y+hh*0.4f, hw*1.6f, hh*0.8f);
		}
		if (mode != 1)
			nerf = false;
		if (mode != 0)
			unicycle = false;
		if (mode != 2)
			dalek = false;
	}
	
	public void projectileCollide(PApplet g, ArrayList<Enemy> enemies) {
		if (mode == 0 && unlocked && unicycle) {
			for (Enemy e : enemies) {
				if ((Math.abs(uvx) > 8 || Math.abs(uvy) > 8) && Math.random()>0.9f && Math.abs(e.x-x) < hw && Math.abs(e.y-y) < hh) {
					e.hp -= 1;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
				}
			}
		}
		for (Projectile f : thumbs) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= f.size*1.1f;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
				}
			}
		}
		for (Projectile f : darts) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= f.size*0.3f;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
				}
			}
		}
		for (Projectile f : unicycles) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= 0.5f*Math.sqrt(f.vx*f.vx+f.vy*f.vy);
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
				}
			}
		}
	}

	@Override
	public void enemyProjectileCollide(PApplet g, Person p) {
		// TODO Auto-generated method stub
		if (mode == 0 && unlocked && unicycle) {
				if ((Math.abs(uvx) > 8 || Math.abs(uvy) > 8) && Math.random()>0.9f && Math.abs(p.x-x) < hw && Math.abs(p.y-y) < hh) {
					p.hp -= 1;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
				}
		}
		for (Projectile f : thumbs) {
				if (p.hp > 0 && f.collide(p)) {
					p.hp -= f.size*1.1f;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
				}
		}
		for (Projectile f : darts) {
				if (p.hp > 0 && f.collide(p)) {
					p.hp -= f.size*0.3f;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
				}
		}
		for (Projectile f : unicycles) {
				if (p.hp > 0 && f.collide(p)) {
					p.hp -= 0.5f*Math.sqrt(f.vx*f.vx+f.vy*f.vy);
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
				}
		}
	}
	
}
