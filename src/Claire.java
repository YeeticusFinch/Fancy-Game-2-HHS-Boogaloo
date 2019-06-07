import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Claire extends Person {

	private ArrayList<Projectile> sparks = new ArrayList<Projectile>();
	private PImage[] sparkPic = new PImage[7];
	private int mx = 0;
	private int spark = 0;
	private int my = 0;
	
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
		
		
	}
	
	public void spark(int mx, int my) {
		mx = (int)((float)super.x*(Math.random()*2)-mx*(Math.random()+0.5f));
		my = (int)((float)super.y*(Math.random()*2)-my*(Math.random()+0.5f));
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		sparks.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), sparkPic[(int)(Math.random()*7)], (float)Math.random()*0.05f+0.02f));
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		super.draw( g,  id, keys, map);
		
		if (sparkPic[0] == null)
			for (int i = 0; i < sparkPic.length; i++)
				sparkPic[i] = g.loadImage("images" + FileIO.fileSep + "s" + i + ".png");
		
		for (int i = 0; i < sparks.size(); i++) {
			if (sparks.get(i).t<Math.random()*15)
				sparks.get(i).draw(g, map);
			else
				sparks.remove(i);
			
		}
		
		if (spark > 0) {
			if (spark%2==0)
				spark(mx, my);
			spark-=1;
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
				}
			}
		}
	}
}
