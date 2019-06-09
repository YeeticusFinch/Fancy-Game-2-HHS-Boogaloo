import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Joe extends Enemy {

	boolean rushing = false;
	
	ArrayList<Projectile> darts = new ArrayList<Projectile>();
	PImage dartPic;
	PImage[] frPics = new PImage[4];
	int shooting = 0;
	PImage blaster;
	
	public Joe() {
		super(6, 4, 10, 3); //Speed, Damage, HP
		if (Math.random() > 0.5)
			rushing = true;
	}
	
	public void draw(PApplet g, boolean[] keys, ArrayList<String> map) {
		super.draw( g, keys, map);
		if (dartPic == null) {
			dartPic = g.loadImage("images" + FileIO.fileSep + "dart.png");
		}
		
		if (frPics[0] == null) {
			for (int i = 0; i < frPics.length; i++)
				frPics[i] = g.loadImage("images" + FileIO.fileSep + "fr" + i + ".png");
		}
		for (int j = 0; j < darts.size(); j++) {
			if (darts.get(j).t<22)
				darts.get(j).draw(g, map);
			else
				darts.remove(j);
			
		}
		if (blaster == null)
			blaster = g.loadImage("images" + FileIO.fileSep + "jnerf.png");
		g.image(blaster, x-hw*0.5f, y+hh*0.4f, hw*1.6f, hh*0.8f);
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
		
		if (fox < 1 && Math.random() > 0.992)
			shooting = 30;
		
		if (shooting > 0) {
			if (shooting %2 == 0)
				attack(px+(int)(Math.random()*200-100), py+(int)(Math.random()*200-100));
			shooting--;
		}
		
		if (fox < 1 && Math.random() > 0.995)
			attack2(px, py);
		
	}

	@Override
	public void attack(int mx, int my) {
		// TODO Auto-generated method stub
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		darts.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), dartPic, 0.03f));
		
		//System.out.println("Throwing hw");
	}

	@Override
	public void attack2(int mx, int my) {
		// TODO Auto-generated method stub
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		darts.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), frPics[(int)(Math.random()*4)], 0.22f, true));
		
	}
	
	public void projectileCollide(PApplet g, Person e) {
		for (Projectile f : darts) {
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
		darts.clear();
	}

	
	
}
