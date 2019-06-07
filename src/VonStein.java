import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class VonStein extends Enemy {

	boolean rushing = false;
	
	ArrayList<Projectile> homework = new ArrayList<Projectile>();
	PImage[] hwPics = new PImage[4];
	PImage[] frPics = new PImage[4];
	
	public VonStein() {
		super(4, 4, 30, 1); //Speed, Damage, HP
		if (Math.random() > 0.5)
			rushing = true;
	}
	
	public void draw(PApplet g, boolean[] keys, ArrayList<String> map) {
		super.draw( g, keys, map);
		if (hwPics[0] == null) {
			for (int i = 0; i < hwPics.length; i++)
				hwPics[i] = g.loadImage("images" + FileIO.fileSep + "hw" + i + ".png");
			
			//System.out.println("Loaded Darius images");
		}
		
		if (frPics[0] == null) {
			for (int i = 0; i < frPics.length; i++)
				frPics[i] = g.loadImage("images" + FileIO.fileSep + "fr" + i + ".png");
		}
		for (int j = 0; j < homework.size(); j++) {
			if (homework.get(j).t<22)
				homework.get(j).draw(g, map);
			else
				homework.remove(j);
			
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
		x+=vx;
		y+=vy;
		if (Math.random()>0.99)
			rushing = !rushing;
		
		if (Math.random() > 0.992)
			attack(px, py);
		
		if (Math.random() > 0.995)
			attack2(px, py);
	}

	@Override
	public void attack(int mx, int my) {
		// TODO Auto-generated method stub
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		homework.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), hwPics[(int)(Math.random()*4)], 0.06f));
		
		//System.out.println("Throwing hw");
	}

	@Override
	public void attack2(int mx, int my) {
		// TODO Auto-generated method stub
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		homework.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), frPics[(int)(Math.random()*4)], 0.22f, true));
		
	}
	
	public void projectileCollide(PApplet g, Person e) {
		for (Projectile f : homework) {
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
	
	public char collide(PApplet g, ArrayList<String> map) {
		char result = super.collide(g, map);
		if (result == '1')
			rushing = false;
		return result;
	}

	@Override
	public void deleteProjectiles() {
		// TODO Auto-generated method stub
		homework.clear();
	}

	
	
}
