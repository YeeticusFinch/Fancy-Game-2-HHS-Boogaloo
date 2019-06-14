import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;

public class Antagonist extends Enemy {

	Person yoink;
	int fID;
	boolean[] fKeys = new boolean[300];
	boolean rushing;
	
	public Antagonist() {
		super(5, 4, 80, 0); //Speed, Damage, HP
		
		if (Math.random() > 0.5)
			rushing = true;
		switch ((int)(Math.random()*6)) {
			case 0:
				yoink = new Osman();
				fID = 0;
				break;
			case 1:
				yoink = new Anand();
				fID = 1;
				break;
			case 2:
				yoink = new Abraham();
				fID = 2;
				break;
			case 3:
				yoink = new Carl();
				fID = 3;
				break;
			case 4:
				yoink = new Claire();
				fID = 4;
				break;
			case 5:
				yoink = new Anton();
				fID = 5;
				break;
		}
		speed = yoink.speed;
		hp = yoink.hp;
		dmg = yoink.dmg;
		yoink.enemy = true;
	}
	
	public Antagonist(boolean yeet) {
		super(5, 4, 80, 0); //Speed, Damage, HP
		
		if (Math.random() > 0.5)
			rushing = true;
		switch ((int)(Math.random()*6)) {
			case 0:
				yoink = new Osman();
				fID = 0;
				break;
			case 1:
				yoink = new Anand();
				fID = 1;
				break;
			case 2:
				yoink = new Abraham();
				fID = 2;
				break;
			case 3:
				yoink = new Carl();
				fID = 3;
				break;
			case 4:
				yoink = new Claire();
				fID = 4;
				break;
			case 5:
				yoink = new Anton();
				fID = 5;
				break;
		}
		if (yeet) {
			switch ((int)(Math.random()*3)) {
				case 0:
					yoink.mode = 0;
					yoink.unlocked = true;
					break;
				case 1:
					yoink.mode = 1;
					break;
				case 2:
					yoink.mode = 2;
					break;
			}
		}
		speed = yoink.speed;
		hp = yoink.hp;
		dmg = yoink.dmg;
		yoink.enemy = true;
	}
	
	public void draw(PApplet g, boolean[] keys, ArrayList<String> map) {
		g.pushStyle();
		g.fill(0);
		g.textSize(g.width*0.01f);
		g.text("HP: " + hp, x, y-g.width*0.01f);
		g.popStyle();
		hw = g.width*0.06f;
		hh = g.width*0.09f;
		hp = Math.min(hp, yoink.hp);
		yoink.hp = hp;
		while (x == -1 && y == -1) {
			spawn(map, g);
			if (x != -1 && y != -1) {
				yoink.x = x;
				yoink.y = y;
				yoink.xo = x;
				yoink.yo = y;
			}
		}
		x = yoink.x;
		y = yoink.y;
		xo = yoink.xo;
		yo = yoink.yo;
		yoink.collide(g, map);
		yoink.draw(g, fID, fKeys, map);
	}
	
	@Override
	public void deleteProjectiles() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(int px, int py) {
		// TODO Auto-generated method stub
		if (rushing) {
			float temp = (float)Math.sqrt( (px-x)*(px-x) + (py-y)*(py-y) );
			vx = (int)(speed*(px-x)/temp);
			vy = (int)(speed*(py-y)/temp);
		} else if (Math.random()>0.8){
			int ppx = (int)(Math.random()*200-100);
			int ppy = (int)(Math.random()*200-100);
			float temp = (float)Math.sqrt( (ppx)*(ppx) + (ppy)*(ppy) );
			vx = (int)(speed*(ppx)/temp);
			vy = (int)(speed*(ppy)/temp);
		}
		if (vy < 0)
			fKeys[PConstants.UP] = true;
		else
			fKeys[PConstants.UP] = false;;
		if (vy > 0)
			fKeys[PConstants.DOWN] = true;
		else
			fKeys[PConstants.DOWN] = false;;
		if (vx > 0)
			fKeys[PConstants.RIGHT] = true;
		else
			fKeys[PConstants.RIGHT] = false;;
		if (vx > 0)
			fKeys[PConstants.LEFT] = true;
		else
			fKeys[PConstants.LEFT] = false;;
		yoink.x+=vx;
		yoink.y+=vy;
		
		if (Math.random()>0.992)
			rushing = !rushing;
		
		if (fox < 1) {
			if (Math.random()>0.94f)
				attack(px, py);
			if (Math.random()>0.94f)
				attack2(px, py);
			if (Math.random()>0.94f && fID != 1)
				fKeys[32] = !fKeys[32];
			if (Math.random()>0.92f)
				fKeys[16] = !fKeys[16];
			if (fID == 1 && yoink.nukeIsClose(px, py)) {
				fKeys[32] = true;
			}
		}
	}
	
	public char collide(PApplet g, ArrayList<String> map) {
		return yoink.collide(g, map);
	}

	@Override
	public void attack(int mx, int my) {
		// TODO Auto-generated method stub
		yoink.attack(mx, my);
	}

	@Override
	public void attack2(int mx, int my) {
		// TODO Auto-generated method stub
		yoink.attack2(mx, my);
	}

	@Override
	public void projectileCollide(PApplet g, Person p) {
		// TODO Auto-generated method stub
		yoink.enemyProjectileCollide(g, p);
	}

}
