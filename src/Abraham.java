import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Abraham extends Person {

	private ArrayList<Projectile> yeses = new ArrayList<Projectile>();
	private ArrayList<Projectile> yeets = new ArrayList<Projectile>();
	private ArrayList<Projectile> blocks = new ArrayList<Projectile>();
	private ArrayList<Projectile> rockets = new ArrayList<Projectile>();
	private ArrayList<Projectile> smoke = new ArrayList<Projectile>();
	private ArrayList<Projectile> redApples = new ArrayList<Projectile>();
	private ArrayList<Projectile> greenApples = new ArrayList<Projectile>();
	private PImage smokePic;
	private PImage yesPic;
	private PImage blockPic;
	private PImage redApple;
	private PImage greenApple;
	private PImage[] yeet = new PImage[8];
	private PImage[] yoinks = new PImage[5];
	int yes = 0;
	private PImage rocket;
	int mx=0;
	int my=0;
	boolean throwable = true;
	int rocketCool = 0;
	int yoink = 0;
	boolean yoinking;
	
	public Abraham() {
		super(4, 10, 25); //Speed, Damage, HP
	}
	
	@Override
	public void attack(int mx, int my) { //YES YES YES
		if (throwable) {
			yes = 30;
			this.mx = mx;
			this.my = my;
			throwable = false;
		}
	}
	
	public void yes(int mx, int my) {
		mx = (int)((float)super.x-mx)+(int)(Math.random()*400-200);
		my = (int)((float)super.y-my)+(int)(Math.random()*400-200);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		yeses.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(40*mx/temp), (int)(40*my/temp), yesPic, 0.2f, true, "rotate"));
		yeses.get(yeses.size()-1).rotateMod = -(float)(Math.PI/2);
	}
	
	@Override
	public void attack2(int mx, int my) { //Gives elbow connector
		if (mode == 0 && !unlocked) {
			mx = (int)((float)super.x-mx);
			my = (int)((float)super.y-my);
			
			double temp = -Math.sqrt(mx*mx + my*my);
			
			blocks.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), blockPic, 0.05f, "halt"));
		} else if (unlocked && mode == 0) {
			mx = (int)((float)super.x-(mx));
			my = (int)((float)super.y-(my));
			
			double temp = -Math.sqrt(mx*mx + my*my);
			
			yeets.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*(mx + (int)(Math.random()*300-150))/temp), (int)(30*(my + (int)(Math.random()*200-100))/temp), yeet[(int)(Math.random()*yeet.length)], (float)Math.random()*0.2f+0.03f, Math.random()>0.6f));
			yeets.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*(mx + (int)(Math.random()*300-150))/temp), (int)(30*(my + (int)(Math.random()*200-100))/temp), yeet[(int)(Math.random()*yeet.length)], (float)Math.random()*0.2f+0.03f, Math.random()>0.6f));
			yeets.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*(mx + (int)(Math.random()*300-150))/temp), (int)(30*(my + (int)(Math.random()*200-100))/temp), yeet[(int)(Math.random()*yeet.length)], (float)Math.random()*0.2f+0.03f, Math.random()>0.6f));
			yeets.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*(mx + (int)(Math.random()*300-150))/temp), (int)(30*(my + (int)(Math.random()*200-100))/temp), yeet[(int)(Math.random()*yeet.length)], (float)Math.random()*0.2f+0.03f, Math.random()>0.6f));
			yeets.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*(mx + (int)(Math.random()*300-150))/temp), (int)(30*(my + (int)(Math.random()*200-100))/temp), yeet[(int)(Math.random()*yeet.length)], (float)Math.random()*0.2f+0.03f, Math.random()>0.6f));
			yeets.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*(mx + (int)(Math.random()*300-150))/temp), (int)(30*(my + (int)(Math.random()*200-100))/temp), yeet[(int)(Math.random()*yeet.length)], (float)Math.random()*0.2f+0.03f, Math.random()>0.6f));
			
		} else if (mode == 1 && rocketCool == 0) {
			rocketCool = 20;
			mx = (int)((float)super.x-mx);
			my = (int)((float)super.y-my);
			
			double temp = -Math.sqrt(mx*mx + my*my);
			
			rockets.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(10*mx/temp), (int)(10*my/temp), rocket, 0.05f, "rotate"));
		
		} else if (mode == 2) {
			mx = (int)((float)super.x-mx);
			my = (int)((float)super.y-my);
			
			double temp = -Math.sqrt(mx*mx + my*my);
			
			greenApples.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp), greenApple, 0.05f, "halt"));
			greenApples.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*(mx + (int)(Math.random()*300-150))/temp), (int)(20*(mx + (int)(Math.random()*300-150))/temp), greenApple, 0.05f, "halt"));
			greenApples.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*(mx + (int)(Math.random()*300-150))/temp), (int)(20*(mx + (int)(Math.random()*300-150))/temp), greenApple, 0.05f, "halt"));
			
		}
		
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		super.draw( g,  id, keys, map);
		
		if (redApple == null)
			redApple = g.loadImage("images"+FileIO.fileSep+"h2m2.png");
		if (greenApple == null)
			greenApple = g.loadImage("images"+FileIO.fileSep+"greenApple.png");
		if (yoinks[0] == null)
			for (int i = 0; i < yoinks.length; i++)
				yoinks[i] = g.loadImage("images"+FileIO.fileSep+"yoink"+i+".png");
		if (rocketCool > 0)
			rocketCool--;
		if (smokePic == null)
			smokePic = g.loadImage("images"+FileIO.fileSep+"smoke.png");
		if (rocket == null)
			rocket = g.loadImage("images"+FileIO.fileSep+"h2m1.png");
		
		if (yeet[0] == null)
			for (int i = 0; i < yeet.length; i++)
				yeet[i] = g.loadImage("images" + FileIO.fileSep + "yeet" + i + ".png");
		if (yesPic == null)
			yesPic = g.loadImage("images" + FileIO.fileSep + "yes.png");
		if (blockPic == null)
			blockPic = g.loadImage("images" + FileIO.fileSep + "block.png");
		
		for (int i = 0; i < yeses.size(); i++) {
			if (yeses.get(i).t<40)
				yeses.get(i).draw(g, map);
			else
				yeses.remove(i);
			
		}
		for (int i = 0; i < yeets.size(); i++) {
			if (yeets.get(i).t<12)
				yeets.get(i).draw(g, map);
			else
				yeets.remove(i);
			
		}
		for (int i = 0; i < smoke.size(); i++) {
			if (smoke.get(i).t<smoke.get(i).ttl)
				smoke.get(i).draw(g, map);
			else
				smoke.remove(i);
			
		}
		for (int i = 0; i < redApples.size(); i++) {
			if (redApples.get(i).t<50)
				redApples.get(i).draw(g, map);
			else
				redApples.remove(i);
			
		}
		for (int i = 0; i < greenApples.size(); i++) {
			if (greenApples.get(i).t<20)
				greenApples.get(i).draw(g, map);
			else
				greenApples.remove(i);
			
		}
		for (int i = 0; i < rockets.size(); i++) {
			if (rockets.get(i).t<20) {
				rockets.get(i).draw(g, map);
				rockets.get(i).vx*=1.2f;
				rockets.get(i).vy*=1.2f;
				if (rockets.get(i).t > 5) {
					smoke.add(new Projectile((int)(rockets.get(i).x + rockets.get(i).size/2), (int)(rockets.get(i).y + rockets.get(i).size/2), -(int)(rockets.get(i).vx/10) + (int)(Math.random()*10-5), -(int)(rockets.get(i).vy/10) + (int)(Math.random()*10-5), smokePic, 0.05f, "fade:40"));
				}
			} else {
				explosion(rockets.get(i).x, rockets.get(i).y, 20);
				rockets.remove(i);
			}
			
		}
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).t<10)
				blocks.get(i).draw(g, map);
			else {
				int x = blocks.get(i).x;
				int y = blocks.get(i).y;
				map.set(1+(int)((y+g.width*0.025f)/(g.width*0.05f)), map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(0, (int)((x+g.width*0.025f)/(g.width*0.05f))) + '1' + map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(1+(int)((x+g.width*0.025f)/(g.width*0.05f))));
				
				blocks.remove(i);
			}
			
		}
		
		if(yoink>0) 
			yoink--;
		
		if (yoinking && yoink < 30) {
			speed = (int)(maxSpeed*6);
			
			smoke.add(new Projectile((int)(x + hw/2), (int)(y + hh/2), (int)(Math.random()*10-5), (int)(Math.random()*10-5), yoinks[Math.min(yoink/6, yoinks.length-1)], 0.05f, "fade:20"));
			yoink+=2;
		} else {
			speed = maxSpeed;
			yoinking = false;
		}
		
		if (keys[32]) {
			if (unlocked && mode == 0 && (keys[g.UP] || keys[g.DOWN] || keys[g.LEFT] || keys[g.RIGHT]) && yoink < 1) {
				
				yoinking = true;
			}
		}
		
		if (yes > 0) {
			if (yes%10==0)
				yes(mx, my);
			yes-=1;
		}
		if (yes == 0) {
			throwable = true;
		}
		
	}
	
	public void explosion(int x, int y, int size) {
		
		
		for (int i = 0; i < Math.random()*size+size; i++) {
			int mx = (int)((float)(Math.random()*200-100));
			int my = (int)((float)(Math.random()*200-100));
			
			double temp = -Math.sqrt(mx*mx + my*my);
			yeses.add(new Projectile((int)(x+this.hw/2), (int)(y+this.hh/2), (int)(50*mx/temp), (int)(50*my/temp), yeet[(int)(Math.random()*yeet.length)], (float)Math.random()*0.1f, Math.random()>0.7));
			yeses.get(yeses.size()-1).t = 20 + (int)(Math.random()*15);
		}
	}
	
	public void projectileCollide(PApplet g, ArrayList<Enemy> enemies) {
		for (Projectile f : yeses) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= f.size*4;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
				}
			}
		}
		for (int i = 0; i < greenApples.size(); i++) {
			Projectile f = greenApples.get(i);
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= 2;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
					greenApples.remove(i);
					int mx = (int)((float)e.x-x);
					int my = (int)((float)e.y-y);
					
					double temp = -Math.sqrt(mx*mx + my*my);
					redApples.add(new Projectile((int)(e.x+e.hw/2), (int)(e.y+e.hh/2), (int)(25*mx/temp), (int)(25*my/temp), redApple, 0.05f, "halt"));
					break;
				}
			}
		}
		
		for (int i = 0; i < redApples.size(); i++) {
			Projectile f = redApples.get(i);
			if (hp > 0 && f.collide(this)) {
				hp = (int)Math.min(hp + Math.random()*2+2, maxHP);
				g.pushStyle();
				g.ellipseMode(PConstants.CORNER);
				g.fill(0, 255, 0);
				g.ellipse(x, y, hw, hh);
				g.popStyle();
				redApples.remove(i);
			}
		}
		
		for (Projectile f : yeets) {
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
		for (int i = 0; i < rockets.size(); i++) {
			Projectile f = rockets.get(i);
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= f.size;
					explosion(f.x, f.y, 20);
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
					rockets.remove(i);
				}
			}
		}
		
	}

	@Override
	public void enemyProjectileCollide(PApplet g, Person p) {
		// TODO Auto-generated method stub
		for (Projectile f : yeses) {
				if (p.hp > 0 && f.collide(p)) {
					p.hp -= f.size*4;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
				}
		}
		for (int i = 0; i < greenApples.size(); i++) {
			Projectile f = greenApples.get(i);
				if (p.hp > 0 && f.collide(p)) {
					p.hp -= 2;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
					greenApples.remove(i);
					int mx = (int)((float)p.x-x);
					int my = (int)((float)p.y-y);
					
					double temp = -Math.sqrt(mx*mx + my*my);
					redApples.add(new Projectile((int)(p.x+p.hw/2), (int)(p.y+p.hh/2), (int)(25*mx/temp), (int)(25*my/temp), redApple, 0.05f, "halt"));
					break;
				}
		}
		
		for (int i = 0; i < redApples.size(); i++) {
			Projectile f = redApples.get(i);
			if (hp > 0 && f.collide(this)) {
				hp = (int)Math.min(hp + Math.random()*2+2, maxHP);
				g.pushStyle();
				g.ellipseMode(PConstants.CORNER);
				g.fill(0, 255, 0);
				g.ellipse(x, y, hw, hh);
				g.popStyle();
				redApples.remove(i);
			}
		}
		
		for (Projectile f : yeets) {
				if (p.hp > 0 && f.collide(p)) {
					p.hp -= f.size*5;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
				}
		}
		for (int i = 0; i < rockets.size(); i++) {
			Projectile f = rockets.get(i);
				if (p.hp > 0 && f.collide(p)) {
					p.hp -= f.size;
					explosion(f.x, f.y, 20);
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
					rockets.remove(i);
				}
		}
	}
	
}
