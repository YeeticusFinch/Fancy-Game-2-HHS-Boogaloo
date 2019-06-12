import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Shelby extends Enemy {

	boolean rushing = false;
	
	ArrayList<Projectile> jar = new ArrayList<Projectile>();
	ArrayList<Projectile> code = new ArrayList<Projectile>();
	ArrayList<Projectile> miniCode = new ArrayList<Projectile>();
	ArrayList<Laser> laser = new ArrayList<Laser>();
	PImage[] codePics = new PImage[7];
	PImage jarFile;
	boolean laserMode;
	PImage laserFace;
	
	public Shelby() {
		super(5, 4, 80, 5); //Speed, Damage, HP
		if (Math.random() > 0.5)
			rushing = true;
	}
	
	public void draw(PApplet g, boolean[] keys, ArrayList<String> map) {
		super.draw( g, keys, map);
		if (laserFace == null)
			laserFace = g.loadImage("images"+FileIO.fileSep+"e5alt.png");
		if (laserMode && altIcon == null)
			altIcon = laserFace;
		else if (!laserMode && altIcon != null)
			altIcon = null;
		if (jarFile == null) {
			jarFile = g.loadImage("images"+FileIO.fileSep+"jar.png");
		}
		
		if (codePics[0] == null) {
			for (int i = 0; i < codePics.length; i++)
				codePics[i] = g.loadImage("images" + FileIO.fileSep + "code" + i + ".png");
		}
		for (int j = 0; j < code.size(); j++) {
			if (code.get(j).t<9)
				code.get(j).draw(g, map);
			else
				code.remove(j);
			
		}
		for (int j = 0; j < miniCode.size(); j++) {
			if (miniCode.get(j).t<5)
				miniCode.get(j).draw(g, map);
			else
				miniCode.remove(j);
			
		}
		for (int j = 0; j < laser.size(); j++) {
			if (laser.get(j).t<laser.get(j).ttl)
				laser.get(j).draw(g, map);
			else
				laser.remove(j);
			
		}
		for (int j = 0; j < jar.size(); j++) {
			if (jar.get(j).t<22)
				jar.get(j).draw(g, map);
			else {
				explosion(jar.get(j).x, jar.get(j).y, 20);
				jar.remove(j);
			}
			
		}
		
	}
	
	@Override
	public void move(int px, int py) {
		// TODO Auto-generated method stub
		
		if (rushing) {
			float temp = (float)Math.sqrt( (px-x)*(px-x) + (py-y)*(py-y) );
			vx = (int)(speed*(px-x)/temp);
			vy = (int)(speed*(py-y)/temp);
		} else if (Math.random()>0.8){
			px = (int)(Math.random()*200-100);
			py = (int)(Math.random()*200-100);
			float temp = (float)Math.sqrt( (px)*(px) + (py)*(py) );
			vx = (int)(speed*(px)/temp);
			vy = (int)(speed*(py)/temp);
		}
		if (fox < 1 && laserMode) {
			x+=2*vx;
			y+=2*vy;
		}
		else {
			x+=vx;
			y+=vy;
		}
		if (Math.random()>0.99)
			rushing = !rushing;
		
		if (fox < 1 && !laserMode && Math.random() > 0.991)
			attack(px, py);
		
		if (fox < 1 && laserMode && Math.random() > 0.92)
			attack2(px, py);
		
		if (fox < 1 && laserMode && Math.random() > 0.92)
			miniExplosion(x, y, 4);
		
		if (fox < 1 && Math.random() > 0.996)
			laserMode = !laserMode;
		if (laserMode && Math.random() > 0.97)
			rushing = true;
		
	}

	@Override
	public void attack(int mx, int my) {
		// TODO Auto-generated method stub
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		jar.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), jarFile, 0.06f));
		
		//System.out.println("Throwing hw");
	}

	@Override
	public void attack2(int mx, int my) {
		// TODO Auto-generated method stub
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		laser.add(new Laser((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), x+(int)(400*mx/temp)+(int)(Math.random()*200-100), y+(int)(400*my/temp)+(int)(Math.random()*200-100), new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)), 15, 30));
		
	}
	
	public void explosion(int x, int y, int size) {
		
		
		for (int i = 0; i < Math.random()*size+size; i++) {
			int mx = (int)((float)(Math.random()*200-100));
			int my = (int)((float)(Math.random()*200-100));
			
			double temp = -Math.sqrt(mx*mx + my*my);
			code.add(new Projectile((int)(x+this.hw/2), (int)(y+this.hh/2), (int)(50*mx/temp), (int)(50*my/temp), codePics[(int)(Math.random()*4)], (float)Math.random()*0.1f, Math.random()>0.7));
		}
	}
	
	public void miniExplosion(int x, int y, int size) {
		
		
		for (int i = 0; i < Math.random()*size+size; i++) {
			int mx = (int)((float)(Math.random()*200-100));
			int my = (int)((float)(Math.random()*200-100));
			
			double temp = -Math.sqrt(mx*mx + my*my);
			miniCode.add(new Projectile((int)(x+this.hw/2), (int)(y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), codePics[(int)(Math.random()*4)], (float)Math.random()*0.08f, Math.random()>0.4));
		}
	}
	
	public void projectileCollide(PApplet g, Person e) {
		for (int i = 0; i < jar.size(); i++) {
			Projectile f = jar.get(i);
			if (e.hp > 0 && f.collide(e)) {
				explosion(f.x, f.y, 20);
				e.hp -= f.size*0.5f;
				g.pushStyle();
				g.ellipseMode(PConstants.CORNER);
				g.fill(255, 0, 0);
				g.ellipse(e.x, e.y, e.hw, e.hh);
				g.popStyle();
				jar.remove(i);
			}
			
		}
		for (Projectile f : code) {
			if (e.hp > 0 && f.collide(e)) {
				e.hp -= f.size*0.5f;
				g.pushStyle();
				g.ellipseMode(PConstants.CORNER);
				g.fill(255, 0, 0);
				g.ellipse(e.x, e.y, e.hw, e.hh);
				g.popStyle();
			}
			
		}
		for (Laser f : laser) {
			if (f.collide(e)) {
				e.hp -= 1;
				g.pushStyle();
				g.ellipseMode(PConstants.CORNER);
				g.fill(255, 0, 0);
				g.ellipse(e.x, e.y, e.hw, e.hh);
				g.popStyle();
			}
		}
	}
	
	public char collide(PApplet g, ArrayList<String> map) {
		char result = super.collide(g, map);
		if (result == '1')
			rushing = false;
		return result;
	}

	@Override
	public void deleteProjectiles() {
		// TODO Auto-generated method stub
		code.clear();
	}

	
	
}
