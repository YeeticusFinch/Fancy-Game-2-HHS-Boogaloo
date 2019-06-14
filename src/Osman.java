import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Osman extends Person {

	private float tx = 0;
	private float ty = 0;
	private int oldHP;
	
	private ArrayList<Projectile> elbows = new ArrayList<Projectile>();
	
	private ArrayList<Projectile> hPacks = new ArrayList<Projectile>();
	
	private ArrayList<Projectile> bois = new ArrayList<Projectile>();
	
	private ArrayList<Projectile> slinkies = new ArrayList<Projectile>();
	
	private ArrayList<Projectile> bigSlinkies = new ArrayList<Projectile>();
	
	private ArrayList<Projectile> smallSlinkies = new ArrayList<Projectile>();
	
	private ArrayList<Projectile> trees = new ArrayList<Projectile>();
	
	private ArrayList<Projectile> thumbs = new ArrayList<Projectile>();
	
	private PImage slinky;
	
	private PImage smallSlinky;
	
	private PImage bigSlinky;
	
	private PImage thumb;
	
	private PImage tree;
	
	private PImage[] boiPics = new PImage[3];
	
	private PImage elbowPic;
	private PImage hPackPic;
	
	boolean dashable = true;
	int dashCool = 40;
	int boi = 0;
	
	public Osman() {
		super(8, 7, 15); //Speed, Damage, HP
		oldHP = hp;
		
	}
	
	@Override
	public void attack(int mx, int my) { //Gives elbow connector
		
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		elbows.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), elbowPic, 0.03f));
		
	}
	
	public void dash(int mx, int my) { //Dashes
		
		if (dashCool > 35)
			dashable = true;
		
		if (dashable && dashCool > 0) {
			tx = 0.3f*((float)super.x-mx);
			ty = 0.3f*((float)super.y-my);
			
			dashCool-=5;
			
			if (dashCool < 4)
				dashable = false;
			
			if (mode == 2)
				thumbExplosion();
			else if (mode == 1 && Math.random() > 0.5 && dashCool > 20)
				slinky((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(Math.random()*10-5), (int)(Math.random()*10-5));
			}
		
	}
	
	private void slinky(int x, int y, int vx, int vy) {
		
		switch ((int)(Math.random()*3)) {
			case 0:
				slinkies.add(new Projectile(x, y, vx, vy, slinky, 0.05f, "halt"));
				break;
			case 1:
				smallSlinkies.add(new Projectile(x, y, 2*vx, 2*vy, smallSlinky, 0.02f, "halt"));
				break;
			case 2:
				bigSlinkies.add(new Projectile(x, y, vx/2, vy/2, bigSlinky, 0.08f, "halt"));
				break;
		}
		
	}
	
	public void thumbExplosion() {
		for (int i = 0; i < Math.random()*10+5; i++) {
			int mx = (int)((float)(Math.random()*200-100));
			int my = (int)((float)(Math.random()*200-100));
			
			double temp = -Math.sqrt(mx*mx + my*my);
			thumbs.add(new Projectile((int)(x+this.hw/2), (int)(y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), thumb, (float)Math.random()*0.1f));
		}
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		super.draw( g,  id, keys, map);
		if (keys[32]) {
			if (keys[PConstants.UP])
				dash(x, y-g.width/10);
			if (keys[PConstants.DOWN])
				dash(x, y+g.width/10);
			if (keys[PConstants.LEFT])
				dash(x-g.width/10, y);
			if (keys[PConstants.RIGHT])
				dash(x+g.width/10, y);
		}
		if (dashCool < 40)
			dashCool++;
		if (boiPics[0] == null) {
			
			boiPics[0] = g.loadImage("images" + FileIO.fileSep + "B.png");
			boiPics[1] = g.loadImage("images" + FileIO.fileSep + "O.png");
			boiPics[2] = g.loadImage("images" + FileIO.fileSep + "I.png");
		}
		if (elbowPic == null) {
			elbowPic = g.loadImage("images" + FileIO.fileSep + "elbow.png");
		}
		if (hPackPic == null) {
			hPackPic = g.loadImage("images" + FileIO.fileSep + "hPack.png");
		}
		if (slinky == null) {
			slinky = g.loadImage("images" + FileIO.fileSep + "h0m1.png");
		}
		if (smallSlinky == null)
			smallSlinky = g.loadImage("images"+FileIO.fileSep+"plastics.png");
		if (bigSlinky == null)
			bigSlinky = g.loadImage("images"+FileIO.fileSep+"metals.png");
		if (tree == null) {
			tree = g.loadImage("images" + FileIO.fileSep + "h0m2.png");
		}
		if (thumb == null) {
			thumb = g.loadImage("images" + FileIO.fileSep + "thumb.png");
		}
		if (Math.abs(tx) > 0 || Math.abs(ty) > 0) {
			super.x -= tx;
			super.y -= ty;
			tx*=0.7;
			ty*=0.7;
			if (Math.abs(tx) < 1)
				tx = 0;
			if (Math.abs(ty) < 1)
				ty = 0;
			hp = oldHP;
		}	
		
		if (boi > 0) {
			if (boi > 30) {
				int mx = (int)((float)(Math.random()*200-100));
				int my = (int)((float)(Math.random()*200-100));
			
				double temp = -Math.sqrt(mx*mx + my*my);
				bois.add(new Projectile((int)(x+this.hw/2), (int)(y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), boiPics[0], (float)Math.random()*0.1f+0.05f, Math.random()>0.7));
			} else if (boi > 20) {
				int mx = (int)((float)(Math.random()*200-100));
				int my = (int)((float)(Math.random()*200-100));
			
				double temp = -Math.sqrt(mx*mx + my*my);
				bois.add(new Projectile((int)(x+this.hw/2), (int)(y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), boiPics[1], (float)Math.random()*0.1f+0.05f, Math.random()>0.7));
			} else if (boi > 10) {
				int mx = (int)((float)(Math.random()*200-100));
				int my = (int)((float)(Math.random()*200-100));
			
				double temp = -Math.sqrt(mx*mx + my*my);
				bois.add(new Projectile((int)(x+this.hw/2), (int)(y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), boiPics[2], (float)Math.random()*0.1f+0.05f, Math.random()>0.7));
			}
			boi--;
		}
		
		for (int i = 0; i < elbows.size(); i++) {
			if (elbows.get(i).t<10)
				elbows.get(i).draw(g, map);
			else
				elbows.remove(i);
			
		}
		
		for (int i = 0; i < bois.size(); i++) {
			if (bois.get(i).t<10)
				bois.get(i).draw(g, map);
			else
				bois.remove(i);
			
		}
		
		
		for (int i = 0; i < hPacks.size(); i++) {
			if (hPacks.get(i).t<200) {
				hPacks.get(i).draw(g, map);
				hPacks.get(i).vx *= 0.8f;
				hPacks.get(i).vy *= 0.8f;
			}
			else
				hPacks.remove(i);
			
		}
		
		for (int i = 0; i < slinkies.size(); i++) {
			if (slinkies.get(i).t<200) {
				slinkies.get(i).draw(g, map);
				slinkies.get(i).vx *= 0.98f;
				slinkies.get(i).vy *= 0.98f;
			}
			else
				slinkies.remove(i);
			
		}
		
		for (int i = 0; i < smallSlinkies.size(); i++) {
			if (smallSlinkies.get(i).t<300) {
				smallSlinkies.get(i).draw(g, map);
				smallSlinkies.get(i).vx *= 0.99f;
				smallSlinkies.get(i).vy *= 0.99f;
			}
			else
				smallSlinkies.remove(i);
			
		}
		
		for (int i = 0; i < bigSlinkies.size(); i++) {
			if (bigSlinkies.get(i).t<100) {
				bigSlinkies.get(i).draw(g, map);
				bigSlinkies.get(i).vx *= 0.9f;
				bigSlinkies.get(i).vy *= 0.9f;
			}
			else
				bigSlinkies.remove(i);
			
		}
		
		for (int i = 0; i < thumbs.size(); i++) {
			if (thumbs.get(i).t<10)
				thumbs.get(i).draw(g, map);
			else
				thumbs.remove(i);
			
		}
		
		for (Projectile e : trees) {
			e.draw(g, map);
		}
		
		if (trees.size() > 0 && trees.get(trees.size()-1).t < 2) {
			int x = trees.get(trees.size()-1).x;
			int y = trees.get(trees.size()-1).y+(int)((g.width*0.05f));
			map.set(1+(int)((y+g.width*0.025f)/(g.width*0.05f)), map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(0, (int)((x+g.width*0.025f)/(g.width*0.05f))) + '1' + map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(1+(int)((x+g.width*0.025f)/(g.width*0.05f))));
			y-=g.width*0.05f;
			map.set(1+(int)((y+g.width*0.025f)/(g.width*0.05f)), map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(0, (int)((x+g.width*0.025f)/(g.width*0.05f))) + '1' + map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(1+(int)((x+g.width*0.025f)/(g.width*0.05f))));
			y-=g.width*0.05f;
			map.set(1+(int)((y+g.width*0.025f)/(g.width*0.05f)), map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(0, (int)((x+g.width*0.025f)/(g.width*0.05f))) + '1' + map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(1+(int)((x+g.width*0.025f)/(g.width*0.05f))));
			y-=g.width*0.05f;
			map.set(1+(int)((y+g.width*0.025f)/(g.width*0.05f)), map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(0, (int)((x+g.width*0.025f)/(g.width*0.05f))) + '1' + map.get(1+(int)((y+g.width*0.025f)/(g.width*0.05f))).substring(1+(int)((x+g.width*0.025f)/(g.width*0.05f))));
		}
		
		oldHP = hp;
		
	}
	
	public void spawn(ArrayList<String> map, PApplet g) {
		super.spawn(map, g);
		trees.clear();
	}
	
	public void projectileCollide(PApplet g, ArrayList<Enemy> enemies) {
		for (Projectile f : elbows) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					
					if (Math.random()>((float)hp/maxHP*1.2f	)) {
						int mx = (int)((float)(Math.random()*200-100));
						int my = (int)((float)(Math.random()*200-100));
					
						double temp = -Math.sqrt(mx*mx + my*my);
						hPacks.add(new Projectile((int)(f.x+this.hw/2), (int)(f.y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), hPackPic, 0.1f, "halt"));
					}
					e.hp -= f.size*8;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
				}
			}
		}
		
		for (Projectile f : bois) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					
					e.hp -= f.size*0.9;
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
					
					e.hp -= f.size*0.9;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
				}
			}
		}
		
		for (int i = 0; i < hPacks.size(); i++) {
			if (hPacks.get(i).collide(this)) {
				if (hp < maxHP) {
					hp++;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(0, 255, 0);
					g.stroke(0, 255, 0);
					g.ellipse(x, y, hw, hh);
					g.popStyle();
				}
				
				hPacks.remove(i);
				
			}
		}
		
		for (Projectile f : slinkies) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.fox = 400;
					e.slinky = true;
				}
			}
		}
		
		for (Projectile f : smallSlinkies) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.fox = 200;
					e.smallSlinky = true;
				}
			}
		}
		
		for (int i = 0; i < bigSlinkies.size(); i++) {
			Projectile f = bigSlinkies.get(i);
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e) && i < bigSlinkies.size()) {
					e.fox = 800;
					e.bigSlinky = true;
					e.hp -= 3;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
					bigSlinkies.remove(i);
					break;
				}
			}
		}
	}

	@Override
	public void attack2(int mx, int my) {
		// TODO Auto-generated method stub
		if (mode == 0 && unlocked && boi == 0) {
			boi = 40;
		} else if (mode == 1) {
			mx = (int)((float)super.x-mx);
			my = (int)((float)super.y-my);
			
			double temp = -Math.sqrt(mx*mx + my*my);
			
			slinky((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(20*mx/temp), (int)(20*my/temp));
			
		} else if (mode == 2) {
			trees.add(new Projectile(mx, my, 0, 0, tree, 0.25f));
		}
	}
	
	public void enemyProjectileCollide(PApplet g, Person p) {
		for (Projectile f : elbows) {
				if (p.hp > 0 && f.collide(p)) {
					
					if (Math.random()>((float)hp/maxHP*1.2f	)) {
						int mx = (int)((float)(Math.random()*200-100));
						int my = (int)((float)(Math.random()*200-100));
					
						double temp = -Math.sqrt(mx*mx + my*my);
						hPacks.add(new Projectile((int)(f.x+this.hw/2), (int)(f.y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), hPackPic, 0.1f, "halt"));
					}
					p.hp -= f.size*8;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
				}
		}
		
		for (Projectile f : bois) {
				if (p.hp > 0 && f.collide(p)) {
					
					p.hp -= f.size*0.9;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
				}
		}
		
		for (Projectile f : thumbs) {
				if (p.hp > 0 && f.collide(p)) {
					p.hp -= f.size*0.9;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
				}
		}
		
		for (int i = 0; i < hPacks.size(); i++) {
			if (hPacks.get(i).collide(this)) {
				if (hp < maxHP) {
					hp++;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(0, 255, 0);
					g.stroke(0, 255, 0);
					g.ellipse(x, y, hw, hh);
					g.popStyle();
				}
				
				hPacks.remove(i);
				
			}
		}
		
		for (Projectile f : slinkies) {
				if (p.hp > 0 && f.collide(p)) {
					p.fox = 400;
					p.slinky = true;
				}
		}
		
		for (Projectile f : smallSlinkies) {
				if (p.hp > 0 && f.collide(p)) {
					p.fox = 200;
					p.smallSlinky = true;
				}
		}
		
		for (int i = 0; i < bigSlinkies.size(); i++) {
			Projectile f = bigSlinkies.get(i);
				if (p.hp > 0 && f.collide(p) && i < bigSlinkies.size()) {
					p.fox = 800;
					p.bigSlinky = true;
					p.hp -= 3;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(p.x, p.y, p.hw, p.hh);
					g.popStyle();
					bigSlinkies.remove(i);
					break;
				}
		}
	}
	
}
