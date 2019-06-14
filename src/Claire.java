import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Claire extends Person {

	private ArrayList<Projectile> sparks = new ArrayList<Projectile>();
	private ArrayList<Projectile> deco = new ArrayList<Projectile>();
	private ArrayList<Projectile> foxes = new ArrayList<Projectile>();
	private ArrayList<Projectile> disks = new ArrayList<Projectile>();
	private ArrayList<Projectile> eGren = new ArrayList<Projectile>();
	private ArrayList<Projectile> hDrives = new ArrayList<Projectile>();
	private ArrayList<Laser> laser = new ArrayList<Laser>();
	private PImage[] sparkPic = new PImage[7];
	private PImage hardDrive;
	private PImage fox;
	private PImage disk;
	private int mx = 0;
	private int spark = 0;
	private int my = 0;
	private int hdc = 0;
	private int foxC = 0;
	
	public Claire() {
		super(6, 9, 16); //Speed, Damage, HP
	}
	
	@Override
	public void attack(int mx, int my) { //Electric shock
		
		spark = 30;
		this.mx = mx;
		this.my = my;
		
	}

	@Override
	public void attack2(int mx, int my) { //Gives elbow connector
		
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		if (unlocked && mode == 0 && hdc == 0) {
			hdc = 15;
			hDrives.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), hardDrive, 0.05f));
		} else if (foxC == 0){
			foxC = 15;
			foxes.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), fox, 0.1f));
		}
	}
	
	public void spark(int mx, int my) {
		mx = (int)((float)super.x*(Math.random()*2)-mx*(Math.random()+0.5f));
		my = (int)((float)super.y*(Math.random()*2)-my*(Math.random()+0.5f));
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		if (unlocked && mode == 0)
			sparks.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), sparkPic[(int)(Math.random()*7)], (float)Math.random()*0.05f+0.02f, "phantom"));
		else
			sparks.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), sparkPic[(int)(Math.random()*7)], (float)Math.random()*0.05f+0.02f, "ghost"));
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		super.draw( g,  id, keys, map);
		if (hdc > 0)
			hdc--;
		if (foxC > 0)
			foxC--;
		if (disk == null)
			disk = g.loadImage("images" + FileIO.fileSep+"disk.png");
		if (fox == null)
			fox = g.loadImage("images"+FileIO.fileSep + "fox.png");
		if (hardDrive == null)
			hardDrive = g.loadImage("images"+FileIO.fileSep+"cp0.png");
		if (sparkPic[0] == null)
			for (int i = 0; i < sparkPic.length; i++)
				sparkPic[i] = g.loadImage("images" + FileIO.fileSep + "s" + i + ".png");
		
		for (int i = 0; i < sparks.size(); i++) {
			if (sparks.get(i).t<Math.random()*5)
				sparks.get(i).draw(g, map);
			else
				sparks.remove(i);
			
		}
		for (int i = 0; i < foxes.size(); i++) {
			if (foxes.get(i).t<30)
				foxes.get(i).draw(g, map);
			else
				foxes.remove(i);
			
		}
		for (int i = 0; i < deco.size(); i++) {
			if (deco.get(i).t<15)
				deco.get(i).draw(g, map);
			else
				deco.remove(i);
			
		}
		for (int i = 0; i < laser.size(); i++) {
			if (laser.get(i).t<laser.get(i).ttl)
				laser.get(i).draw(g, map);
			else
				laser.remove(i);
			
		}
		for (int i = 0; i < eGren.size(); i++) {
			if (eGren.get(i).t<30)
				eGren.get(i).draw(g, map);
			else
				eGren.remove(i);
			
		}
		for (int i = 0; i < hDrives.size(); i++) {
			if (hDrives.get(i).t<30)
				hDrives.get(i).draw(g, map);
			else
				hDrives.remove(i);
			
		}
		for (int i = 0; i < disks.size(); i++) {
			if (disks.get(i).t<30) {
				disks.get(i).draw(g, map);
				disks.get(i).vx*=0.9f;
				disks.get(i).vy*=0.9f;
			}
			else if (disks.get(i).t<82)
				disks.get(i).draw(g, map);
			else if (disks.get(i).t >= 82 && disks.get(i).t < 84) {
				disks.get(i).draw(g, map);
				disks.get(i).vx = 2*(int)(Math.random()*40-20);
				disks.get(i).vy = 2*(int)(Math.random()*40-20);
				disks.get(i).halt = false;
				disks.get(i).penetration = true;
			}
			else if (disks.get(i).t >= 84 && disks.get(i).t < 104)
				disks.get(i).draw(g, map);
			else
				disks.remove(i);
			
		}
		
		if (spark > 0) {
			if (spark%2==0)
				spark(mx, my);
			spark-=1;
		}
		
		if (keys[32]) {
			keys[32] = false;
			if (unlocked && mode == 0) {
				int mx = (int)((float)super.x-(g.mouseX-GUI.tx));
				int my = (int)((float)super.y-(g.mouseY-GUI.ty));
				
				double temp = -Math.sqrt(mx*mx + my*my);
				
				disks.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), disk, 0.06f, "halt"));
			
			} else if (mode == 1) {
				for (int i = 0; i < Math.random()*10+10; i++) {
					int mx = (int)((float)(Math.random()*200-100));
					int my = (int)((float)(Math.random()*200-100));
					
					double temp = -Math.sqrt(mx*mx + my*my);
					deco.add(new Projectile((int)(x+this.hw/2), (int)(y+this.hh/2), (int)(50*mx/temp), (int)(50*my/temp), sparkPic[(int)(Math.random()*sparkPic.length)], (float)Math.random()*0.1f));
				}
			} else if (mode == 2) {
				int mx = (int)((float)super.x-(g.mouseX-GUI.tx));
				int my = (int)((float)super.y-(g.mouseY-GUI.ty));
				
				double temp = -Math.sqrt(mx*mx + my*my);
				
				eGren.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), disk, 0.02f, "halt"));
			
			}
		}
		
	}
	
	public void projectileCollide(PApplet g, ArrayList<Enemy> enemies) {
		for (Projectile f : sparks) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= f.size;
					g.pushStyle();
					g.image(sparkPic[(int)(Math.random()*7)], e.x, e.y, e.hw, e.hh);
					g.image(sparkPic[(int)(Math.random()*7)], e.x, e.y, e.hw, e.hh);
					g.popStyle();
					if (mode == 1 && e.blinded > 0)
						hp=Math.min(hp+1, maxHP);
				}
			}
		}
		for (Projectile f : hDrives) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= f.size;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
				}
			}
		}
		for (Projectile f : disks) {
			if (f.t >= 82) {
				for (Enemy e : enemies) {
					if (f.t < 88 && Math.sqrt((e.x-f.x)*(e.x-f.x)+(e.y-f.y)*(e.y-f.y)) < e.hh*5) {
						double mx = (int)((float)f.x-e.x);
						double my = (int)((float)f.y-e.y);
						double temp = -Math.sqrt(mx*mx + my*my);
						f.vx = (int)(35*mx/temp);
						f.vy = (int)(35*my/temp);
					}
					if (e.hp > 0 && f.collide(e)) {
						e.hp -= f.size*20;
						g.pushStyle();
						g.ellipseMode(PConstants.CORNER);
						g.fill(255, 0, 0);
						g.ellipse(e.x, e.y, e.hw, e.hh);
						g.popStyle();
					}
				}
			}
		}
		for (Projectile f : foxes) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.fox = 200;
				}
			}
		}
		for (Projectile f : deco) {
			for (Enemy e : enemies) {
				e.meme = true;
				
			}
		}
		for (Projectile f : eGren) {
			for (Enemy e : enemies) {
				if (Math.sqrt((e.x-f.x)*(e.x-f.x)+(e.y-f.y)*(e.y-f.y)) < e.hh*4 && Math.random()>0.2f) {
					laser.add(new Laser(f.x, f.y, (int)(e.x+hw/2), (int)(e.y+hh/2), new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)), 10, 20));
					laser.get(laser.size()-1).blinding = 200;
				}
			}
		}
		for (Laser f : laser) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					/*e.hp -= f.size*0.2f;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();*/
				}
			}
		}
	}
	
	public void enemyProjectileCollide(PApplet g, Person p) {
		for (Projectile f : sparks) {
				if (p.hp > 0 && f.collide(p)) {
					p.hp -= f.size;
					g.pushStyle();
					g.image(sparkPic[(int)(Math.random()*7)], p.x, p.y, p.hw, p.hh);
					g.image(sparkPic[(int)(Math.random()*7)], p.x, p.y, p.hw, p.hh);
					g.popStyle();
					if (mode == 1 && p.blinded > 0)
						hp=Math.min(hp+1, maxHP);
				}
		}
		for (Projectile f : hDrives) {
				if (p.hp > 0 && f.collide(p)) {
					p.hp -= f.size;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
				}
		}
		for (Projectile f : disks) {
			if (f.t >= 82) {
					if (f.t < 88 && Math.sqrt((p.x-f.x)*(p.x-f.x)+(p.y-f.y)*(p.y-f.y)) < p.hh*5) {
						double mx = (int)((float)f.x-p.x);
						double my = (int)((float)f.y-p.y);
						double temp = -Math.sqrt(mx*mx + my*my);
						f.vx = (int)(35*mx/temp);
						f.vy = (int)(35*my/temp);
					}
					if (p.hp > 0 && f.collide(p)) {
						p.hp -= f.size*20;
						g.pushStyle();
						g.ellipseMode(PConstants.CORNER);
						g.fill(255, 0, 0);
						g.ellipse(p.x, p.y, p.hw, p.hh);
						g.popStyle();
					}
			}
		}
		for (Projectile f : foxes) {
				if (p.hp > 0 && f.collide(p)) {
					p.fox = 200;
				}
		}
		for (Projectile f : eGren) {
				if (Math.sqrt((p.x-f.x)*(p.x-f.x)+(p.y-f.y)*(p.y-f.y)) < p.hh*4 && Math.random()>0.2f) {
					laser.add(new Laser(f.x, f.y, (int)(p.x+hw/2), (int)(p.y+hh/2), new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)), 10, 20));
					laser.get(laser.size()-1).blinding = 200;
				}
		}
		for (Laser f : laser) {
				if (p.hp > 0 && f.collide(p)) {
					/*e.hp -= f.size*0.2f;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();*/
				}
		}
	}
}
