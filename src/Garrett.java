import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Garrett extends Enemy {

	boolean rushing = false;
	
	ArrayList<Projectile> cards = new ArrayList<Projectile>();
	PImage[] cardPics = new PImage[5];
	//PImage[] frPics = new PImage[4];
	int card = 0;
	
	public Garrett() {
		super(5, 6, 17, 2); //Speed, Damage, HP, ID
		if (Math.random() > 0.5)
			rushing = true;
	}
	
	public void draw(PApplet g, boolean[] keys, ArrayList<String> map) {
		super.draw( g, keys, map);
		if (cardPics[0] == null) {
			for (int i = 0; i < cardPics.length; i++)
				cardPics[i] = g.loadImage("images" + FileIO.fileSep + "card" + i + ".png");
			
			//System.out.println("Loaded Darius images");
		}
		
		System.out.println("Drawing Garrett");
		
		for (int j = 0; j < cards.size(); j++) {
			if (cards.get(j).t<22)
				cards.get(j).draw(g, map);
			else
				cards.remove(j);
			
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
		
		if (card > 0 ) {
			if (card%4 == 0) {
				attack(px+(int)(Math.random()*200-100), py+(int)(Math.random()*200-100));
			}
			card--;
		}
		
		if (fox < 1 && Math.random() > 0.99)
			card = 20+(int)(Math.random()*20);
		
	}

	@Override
	public void attack(int mx, int my) {
		// TODO Auto-generated method stub
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		cards.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), cardPics[(int)(Math.random()*4)], 0.06f));
		
		//System.out.println("Throwing hw");
	}

	@Override
	public void attack2(int mx, int my) {
		
	}
	
	public void projectileCollide(PApplet g, Person e) {
		for (Projectile f : cards) {
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
		cards.clear();
	}

	
	
}
