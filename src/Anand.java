import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Anand extends Person {

	private ArrayList<Projectile> nukes = new ArrayList<Projectile>();
	private ArrayList<Projectile> drones = new ArrayList<Projectile>();
	private ArrayList<Projectile> fire = new ArrayList<Projectile>();
	private ArrayList<Projectile> bags = new ArrayList<Projectile>();
	private ArrayList<Projectile> darts = new ArrayList<Projectile>();
	private ArrayList<Projectile> tellers = new ArrayList<Projectile>();
	private ArrayList<Projectile> canes = new ArrayList<Projectile>();
	private ArrayList<Laser> laser = new ArrayList<Laser>();
	private PImage[] personalities = new PImage[4];
	private PImage dartPic;
	private PImage canePic;
	private PImage teller;
	private PImage bagPic;
	private PImage nukePic;
	private PImage firePic;
	private PImage dronePic;
	private PImage ironPic;
	int explosions = 0;
	int throwCool = 0;
	int ex, ey;
	boolean ironman;
	int fuel = 100;
	int personality = 0;
	private int shoot = 0;
	private int tmx, tmy;
	private boolean yoink;
	
	public Anand() {
		super(3, 4, 20); //Speed, Damage, HP
	}
	
	public void shoot(int mx, int my) {
		mx += (int)(Math.random()*200-100);
		my += (int)(Math.random()*200-100);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		darts.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(40*mx/temp), (int)(40*my/temp), dartPic, 0.04f));
	
	}
	
	public boolean nukeIsClose(int mx, int my) {
		
		for (Projectile e : nukes) {
			int dx = e.x-mx;
			int dy = e.y-my;
			if (Math.sqrt(dx*dx + dy*dy) < 200)
				return true;
		}
		
		return false;
	}
	
	@Override
	public void attack(int mx, int my) { //Throws nuke
		if (throwCool == 0) {
			personality = 0;
			throwCool = 20;
			mx = (int)((float)super.x-mx);
			my = (int)((float)super.y-my);
			
			double temp = -Math.sqrt(mx*mx + my*my);
			
			if (Math.random()>0.05)
				nukes.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(10*mx/temp), (int)(10*my/temp), nukePic, 0.09f));
			else
				explosion(x, y, 25);
		}
		
	}
	
	@Override
	public void attack2(int mx, int my) { //Gives elbow connector
		if (unlocked && mode == 0 && throwCool == 0) {
			
			throwCool = 20;
			
			mx = (int)((float)super.x-mx);
			my = (int)((float)super.y-my);
			
			
			
			double temp = -Math.sqrt(mx*mx + my*my);
			drones.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(15*mx/temp), (int)(15*my/temp), dronePic, 0.09f));
		} else if (ironman && mode == 1 && fuel > 5) {
			fuel-=5;
			laser.add(new Laser((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), mx, my, new Color(100, 100, 255), 10, 50));
		} else if (mode == 2 && throwCool == 0) {
			throwCool = 15;
			if (personality == 0 || Math.random()>0.6f) {
				personality = (int)(Math.random()*4)+1;
			}
			fancyAttack(mx, my);
		}
	}
	
	private void fancyAttack(int mx, int my) {
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		switch (personality-1) {
			case 0:
				shoot = 30;
				tmx = mx;
				tmy = my;
				break;
			case 1:
				bags.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), bagPic, 0.09f));
				break;
			case 2:
				tellers.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(15*mx/temp), (int)(15*my/temp), teller, 0.09f));
				break;
			case 3:
				canes.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*(mx+(Math.random()*200-100))/temp), (int)(30*(my+(Math.random()*200-100))/temp), canePic, 0.06f));
				canes.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*(mx+(Math.random()*200-100))/temp), (int)(30*(my+(Math.random()*200-100))/temp), canePic, 0.06f));
				canes.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*(mx+(Math.random()*200-100))/temp), (int)(30*(my+(Math.random()*200-100))/temp), canePic, 0.06f));
				canes.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*(mx+(Math.random()*200-100))/temp), (int)(30*(my+(Math.random()*200-100))/temp), canePic, 0.06f));
				break;
		}
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		super.draw( g,  id, keys, map);
		
		if (canePic == null)
			canePic = g.loadImage("images"+FileIO.fileSep+"cane.png");
		if (teller == null)
			teller = g.loadImage("images"+FileIO.fileSep+"teller.png");
		if (dartPic == null)
			dartPic = g.loadImage("images" + FileIO.fileSep + "dart.png");
		if (bagPic == null) {
			bagPic = g.loadImage("images" + FileIO.fileSep + "bag.png");
		}
		
		if (shoot > 0) {
			if (shoot % 3 == 0) {
				shoot(tmx, tmy);
			}
			shoot--;
		}
		
		if (personalities[0] == null) {
			for (int i = 0; i < personalities.length; i++) {
				personalities[i] = g.loadImage("images" + FileIO.fileSep + "ap" + i + ".png");
			}
		}
		
		if (explosions > 0) {
			if (explosions %4 == 0)
				explosion(ex, ey, 10);
			explosions--;
		}
		
		if (fuel < 100 && Math.random() > 0.9f)
			fuel++;
		
		if (dronePic == null)
			dronePic = g.loadImage("images" + FileIO.fileSep + "h1m0.png");
		
		if (throwCool > 0)
			throwCool--;
		
		if (mode == 1 && ironman) {
			g.pushStyle();
			g.fill(255);
			g.text("Fuel: " + fuel, -GUI.tx+g.width*0.3f, -GUI.ty+g.height*0.04f);
			g.popStyle();
		} else if (mode == 2 && personality != 0) {
			altIcon = personalities[personality-1];
		} else if (mode == 2 && personality == 0 && altIcon != null) {
			altIcon = null;
		}
		if (!keys[32])
			yoink = false;
		
		if (keys[32]) {
			if (nukes.size()>0)
				yoink = true;
			for (int i = 0; i < nukes.size(); i++) {
				if (Math.random() > 0.1) {
					if (unlocked && mode == 0) {
						ex = nukes.get(i).x;
						ey = nukes.get(i).y;
						explosion(nukes.get(i).x, nukes.get(i).y, 10);
						explosions = 20;
					} else
						explosion(nukes.get(i).x, nukes.get(i).y, 25);
				}
				nukes.remove(i);
			}
			if (mode == 1 && ironman && fuel > 1) {
				if ((keys[g.UP] || keys[g.DOWN] || keys[g.LEFT] || keys[g.RIGHT])) {
					speed = (int)(maxSpeed*4);
					fuel--;
				} else if (fuel > 10 && !yoink) {
					this.x = (int)(g.mouseX-GUI.tx);
					this.y = (int)(g.mouseY-GUI.ty);
					fuel-=10;
				}
			} else {
				if (ironman)
					speed = maxSpeed/4;
				else
					speed = maxSpeed;
			}
		} else{
			if (ironman)
				speed = maxSpeed/3;
			else
				speed = maxSpeed;
		}
		
		if (nukePic == null)
			nukePic = g.loadImage("images" + FileIO.fileSep + "nuke.png");
		if (firePic == null)
			firePic = g.loadImage("images" + FileIO.fileSep + "fire.png");
		if (ironPic == null)
			ironPic = g.loadImage("images" + FileIO.fileSep + "h1m1.png");
		
		if (keys[16] && mode == 1) {
			keys[16] = false;
			ironman = !ironman;
		}
		
		if (ironman) {
			altIcon = ironPic;
			altColor = new Color(100, 10, 10);
			if (!Map.vr && !enemy)
				Map.vr = true;
		} else if (mode == 1 && altIcon != null) {
			if (Map.vr)
				Map.vr = false;
			altIcon = null;
			altColor = null;
		}
		
		for (int i = 0; i < nukes.size(); i++) {
			if (nukes.get(i).t<70)
				nukes.get(i).draw(g, map);
			else
				nukes.remove(i);
			
		}
		
		for (int i = 0; i < fire.size(); i++) {
			if (fire.get(i).t<10)
				fire.get(i).draw(g, map);
			else
				fire.remove(i);
			
		}
		
		for (int i = 0; i < drones.size(); i++) {
			if (drones.get(i).t<80)
				drones.get(i).draw(g, map);
			else
				drones.remove(i);
			
		}
		
		for (int i = 0; i < bags.size(); i++) {
			if (bags.get(i).t<20)
				bags.get(i).draw(g, map);
			else
				bags.remove(i);
			
		}
		
		for (int i = 0; i < canes.size(); i++) {
			if (canes.get(i).t<20)
				canes.get(i).draw(g, map);
			else
				canes.remove(i);
			
		}
		
		for (int i = 0; i < darts.size(); i++) {
			if (darts.get(i).t<15)
				darts.get(i).draw(g, map);
			else
				darts.remove(i);
			
		}
		
		for (int i = 0; i < tellers.size(); i++) {
			if (tellers.get(i).t<40)
				tellers.get(i).draw(g, map);
			else {
				explosion(tellers.get(i).x, tellers.get(i).y, 40);
				tellers.remove(i);
			}
			
		}
		
		for (int i = 0; i < laser.size(); i++) {
			if (laser.get(i).t<laser.get(i).ttl)
				laser.get(i).draw(g, map);
			else
				laser.remove(i);
			
		}
		
	}
	
	public void explosion(int x, int y, int size) {
		
		
		for (int i = 0; i < Math.random()*size+size; i++) {
			int mx = (int)((float)(Math.random()*200-100));
			int my = (int)((float)(Math.random()*200-100));
			
			double temp = -Math.sqrt(mx*mx + my*my);
			fire.add(new Projectile((int)(x+this.hw/2), (int)(y+this.hh/2), (int)(50*mx/temp), (int)(50*my/temp), firePic, (float)Math.random()*0.1f, Math.random()>0.7));
		}
	}
	
	public void projectileCollide(PApplet g, ArrayList<Enemy> enemies) {
		for (int i = 0; i < drones.size(); i++) {
			Projectile f = drones.get(i);
			for (Enemy e : enemies) {
				if (e.hp > 0 && Math.sqrt((e.x-f.x)*(e.x-f.x)+(e.y-f.y)*(e.y-f.y)) < hw*0.5f) {
					explosion(f.x, f.y, 15);
					drones.remove(i);
				}
				if (f.t > 8 && Math.sqrt((e.x-f.x)*(e.x-f.x)+(e.y-f.y)*(e.y-f.y)) < hw*5) {
					double mx = (int)((float)f.x-e.x);
					double my = (int)((float)f.y-e.y);
					double temp = -Math.sqrt(mx*mx + my*my);
					f.vx = (int)(20*mx/temp);
					f.vy = (int)(20*my/temp);
					break;
				}
			}
		}
		for (Projectile f : nukes) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= f.size*5;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
				}
			}
		}
		for (Laser f : laser) {
			for (Enemy e : enemies) {
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
		for (Projectile f : canes) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= f.size*0.5f;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
				}
			}
		}
		for (Projectile f : bags) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= 70;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
				}
			}
		}
		for (Projectile f : fire) {
			if (!ironman && f.collide(this) && ((!unlocked && Math.random()>0.6f) || (unlocked && Math.random()>0.8f))) {
				hp -= 1;
				g.pushStyle();
				g.ellipseMode(PConstants.CORNER);
				g.fill(255, 0, 0);
				g.ellipse(x, y, hw, hh);
				g.popStyle();
			}
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= f.size*10;
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
		for (int i = 0; i < drones.size(); i++) {
			Projectile f = drones.get(i);
				if (p.hp > 0 && Math.sqrt((p.x-f.x)*(p.x-f.x)+(p.y-f.y)*(p.y-f.y)) < hw*0.5f) {
					explosion(f.x, f.y, 15);
					drones.remove(i);
				}
				if (f.t > 8 && Math.sqrt((p.x-f.x)*(p.x-f.x)+(p.y-f.y)*(p.y-f.y)) < hw*5) {
					double mx = (int)((float)f.x-p.x);
					double my = (int)((float)f.y-p.y);
					double temp = -Math.sqrt(mx*mx + my*my);
					f.vx = (int)(20*mx/temp);
					f.vy = (int)(20*my/temp);
					break;
				}
		}
		for (Projectile f : nukes) {
				if (p.hp > 0 && f.collide(p)) {
					p.hp -= f.size*5;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
				}
		}
		for (Laser f : laser) {
				if (p.hp > 0 && f.collide(p)) {
					p.hp -= f.size*0.2f;
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
		for (Projectile f : canes) {
				if (p.hp > 0 && f.collide(p)) {
					p.hp -= f.size*0.5f;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
				}
		}
		for (Projectile f : bags) {
				if (p.hp > 0 && f.collide(p)) {
					p.hp -= 70;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
				}
		}
		for (Projectile f : fire) {
			if (!ironman && f.collide(this) && ((!unlocked && Math.random()>0.6f) || (unlocked && Math.random()>0.8f))) {
				hp -= 1;
				g.pushStyle();
				g.ellipseMode(PConstants.CORNER);
				g.fill(255, 0, 0);
				g.ellipse(x, y, hw, hh);
				g.popStyle();
			}
				if (p.hp > 0 && f.collide(p)) {
					p.hp -= f.size*10;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
				}
		}
	}
	
	
}
