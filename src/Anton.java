import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Anton extends Person {

	private ArrayList<Projectile> comps = new ArrayList<Projectile>();
	private ArrayList<Projectile> glass = new ArrayList<Projectile>();
	private ArrayList<Projectile> oranges = new ArrayList<Projectile>();
	private ArrayList<Projectile> led = new ArrayList<Projectile>();
	private ArrayList<Projectile> maths = new ArrayList<Projectile>();
	private PImage glasses;
	private PImage orange;
	private PImage noGlasses;
	private boolean noGlass;
	private PImage ledPic;
	int shoot = 0;
	int throwCool = 0;
	boolean unicycle = false;
	int uc = 0;
	PImage[] compParts = new PImage[18];
	PImage[] math = new PImage[8];
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
	int compBurst = 0;
	int tx, ty;
	int noGlassTimer = 0;
	PImage vr;
	boolean dashable = true;
	int dashCool = 40;
	float dx, dy;
	
	public Anton() {
		super(6, 6, 18); //Speed, Damage, HP
		dalekHP = 45;
		dalekMaxHP = 45;
	}
	
	@Override
	public void attack(int mx, int my) { //Throws thumb
		if (throwCool < 1) {
			throwCool = 15;
			compBurst = 30;
			tx = mx;
			ty = my;
		}
	}
	
	public void compThrow() {
		int mx = (int)((float)super.x-tx)+(int)(Math.random()*400-200);
		int my = (int)((float)super.y-ty)+(int)(Math.random()*400-200);
		
		
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		comps.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), compParts[(int)(Math.random()*compParts.length)], 0.09f));
	
	}
	
	@Override
	public void attack2(int mx, int my) { //Gives elbow connector
		if (!noGlass && mode == 0) {
			tx = (int)((float)super.x-mx);
			ty = (int)((float)super.y-my);
			
			double temp = -Math.sqrt(tx*tx + ty*ty);
			
			glass.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(70*tx/temp), (int)(70*ty/temp), glasses, 0.06f, "halt"));
			noGlass = true;
			noGlassTimer = 100;
			if (unlocked && mode == 0)
				altIcon = vr;
			else
				altIcon = noGlasses;
		} else if (mode == 1 && throwCool < 1) {
			tx = (int)((float)super.x-mx);
			ty = (int)((float)super.y-my);
			
			double temp = -Math.sqrt(tx*tx + ty*ty);
			
			oranges.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(70*tx/temp), (int)(70*ty/temp), orange, 0.06f, "halt"));
			
		}
	}
	
	public void dash(int mx, int my) { //Dashes
		
		if (dashCool > 35)
			dashable = true;
		
		if (dashable && dashCool > 0) {
			dx = 0.3f*((float)super.x-mx);
			dy = 0.3f*((float)super.y-my);
			
			dashCool-=5;
			
			if (dashCool < 4)
				dashable = false;
			
			mathExplosion();
		}
		
	}
	
	public void mathExplosion() {
		for (int i = 0; i < Math.random()*10+5; i++) {
			int mx = (int)((float)(Math.random()*200-100));
			int my = (int)((float)(Math.random()*200-100));
			
			double temp = -Math.sqrt(mx*mx + my*my);
			maths.add(new Projectile((int)(x+this.hw/2), (int)(y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), math[(int)(Math.random()*math.length)], (float)Math.random()*0.1f));
		}
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		
		if (dashCool < 40)
			dashCool++;
		
		if (math[0] == null) 
			for (int i = 0; i < math.length; i++)
				math[i] = g.loadImage("images" + FileIO.fileSep + "math" + i + ".png");
		
		if (noGlass && !(unlocked && mode == 0) && Math.random() > 0.3f) {
			modX = (float)(Math.random()*4-1.5);
			modY = (float)(Math.random()*4-1.5);
		} else {
			modX = 1;
			modY = 1;
		}
		
		if (vr == null) {
			vr = g.loadImage("images" + FileIO.fileSep + "h5vr.png");
		}
		
		if (ledPic == null) {
			ledPic = g.loadImage("images" + FileIO.fileSep + "h5m0.png");
		}
		
		if (noGlasses == null) {
			noGlasses = g.loadImage("images" + FileIO.fileSep + "h5ng.png");
		}
		
		if (Math.abs(dx) > 0 || Math.abs(dy) > 0) {
			super.x -= dx;
			super.y -= dy;
			dx*=0.7;
			dy*=0.7;
			if (Math.abs(dx) < 1)
				dx = 0;
			if (Math.abs(dy) < 1)
				dy = 0;
		}
		
		if (compParts[0] == null) {
			for (int i = 0; i < compParts.length; i++) {
				compParts[i] = g.loadImage("images" + FileIO.fileSep + "cp" + i + ".png");
			}
		}
		
		if (unlocked && mode == 0 && keys[32] && throwCool < 1) {
			throwCool = 20;
			int tx = (int)((float)super.x-(g.mouseX-GUI.tx));
			int ty = (int)((float)super.y-(g.mouseY-GUI.ty));
			
			double temp = -Math.sqrt(tx*tx + ty*ty);
			
			led.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(70*tx/temp), (int)(70*ty/temp), ledPic, 0.06f, "led:200"));
			led.get(led.size()-1).halt = true;
			
		}
		
		super.draw( g,  id, keys, map);
		
		if (mode == 2 && keys[32]) {
			if (keys[PConstants.UP])
				dash(x, y-g.width/10);
			if (keys[PConstants.DOWN])
				dash(x, y+g.width/10);
			if (keys[PConstants.LEFT])
				dash(x-g.width/10, y);
			if (keys[PConstants.RIGHT])
				dash(x+g.width/10, y);
		}
		
		if (compBurst > 0) {
			if (compBurst % 3 == 0) {
				compThrow();
			}
			compBurst--;
		}
		
		if (orange == null) {
			orange = g.loadImage("images" + FileIO.fileSep + "h5m1.png");
		}
		
		
		if (glasses == null)
			glasses = g.loadImage("images" + FileIO.fileSep + "glasses.png");
		
		if (throwCool > 0)
			throwCool--;
		for (int i = 0; i < comps.size(); i++) {
			if (comps.get(i).t<20)
				comps.get(i).draw(g, map);
			else
				comps.remove(i);
			
		}
		
		for (int i = 0; i < glass.size(); i++) {
			if (glass.get(i).t<100) {
				glass.get(i).draw(g, map);
				glass.get(i).vx *= 0.9f;
				glass.get(i).vy *= 0.9f;
			} else {
				glass.remove(i);
			}
			
		}
		
		if (noGlassTimer > 0) {
			noGlassTimer --;
			if (unlocked && mode == 0) {
				if (noGlassTimer > 2) {GUI.zoom = 0.8f; Map.vr = true;}
				else {GUI.zoom = 1; Map.vr = false;}
				
			}
			
		} else {
			noGlass = false;
		}
		
		for (int i = 0; i < oranges.size(); i++) {
			if (oranges.get(i).t<150) {
				oranges.get(i).draw(g, map);
				oranges.get(i).vx *= 0.9f;
				oranges.get(i).vy *= 0.9f;
			} else
				oranges.remove(i);
			
		}
		for (int i = 0; i < led.size(); i++) {
			if (led.get(i).t<100) {
				led.get(i).draw(g, map);
				led.get(i).vx *= 0.9f;
				led.get(i).vy *= 0.9f;
			} else
				led.remove(i);
			
		}
		for (int i = 0; i < maths.size(); i++) {
			if (maths.get(i).t<100) {
				maths.get(i).draw(g, map);
			} else
				maths.remove(i);
			
		}
		if (!noGlass) {
			altIcon = null;
		}
		
	}
	
	public void projectileCollide(PApplet g, ArrayList<Enemy> enemies) {
		
		for (Projectile f : comps) {
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
		for (Projectile f : maths) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= f.size*0.4f;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
				}
			}
		}
		for (int i = 0; i < glass.size(); i++) {
			Projectile f = glass.get(i);
			for (Enemy e : enemies) {
				if (e.fox < 1 && e.hp > 0 && f.collide(e) && glass.size() > 0) {
					e.hp -= f.size*0.4f;
					e.fox = 500;
					e.glass = true;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
					f.vx *= 0.5;
					f.vy *= 0.5;
				}
			}
		}
		for (Projectile f : oranges) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && Math.sqrt((e.x-f.x)*(e.x-f.x)+(e.y-f.y)*(e.y-f.y)) < hw*5) {
					e.ox = f.x;
					e.oy = f.y;
				} else {
					e.ox = 0;
					e.oy = 0;
				}
			}
		}
		if (oranges.size() == 0 && mode == 1) {
			for (Enemy e : enemies) {
				e.ox = 0;
				e.oy = 0;
			}
		}
		for (Projectile f : led) {
			for (Enemy e : enemies) {
				f.collide(e);
			}
		}
	}
	
}
