import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Carl extends Person {

	private ArrayList<Projectile> thumbs = new ArrayList<Projectile>();
	private PImage thumbPic;
	int throwCool = 0;
	boolean unicycle = false;
	int uc = 0;
	PImage[] yeetcycle = new PImage[4];
	int uvx = 0;
	int uvy = 0;
	
	public Carl() {
		super(5, 5, 15); //Speed, Damage, HP
	}
	
	@Override
	public void attack(int mx, int my) { //Throws thumb

		if (throwCool == 0) {
			throwCool = 15;
			mx = (int)((float)super.x-mx);
			my = (int)((float)super.y-my);
			
			double temp = -Math.sqrt(mx*mx + my*my);
			
			thumbs.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(50*mx/temp), (int)(50*my/temp), thumbPic, 0.06f));
		}
		
	}
	
	@Override
	public void attack2(int mx, int my) { //Gives elbow connector
		
		
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		
		if (yeetcycle[0] == null) {
			for (int i = 0; i < yeetcycle.length; i++) {
				yeetcycle[i] = g.loadImage("images" + FileIO.fileSep + "uc" + i + ".png");
			}
		}
		
		
		
		super.draw( g,  id, keys, map);
		
		if (unicycle) {
			g.image(yeetcycle[uc], x-hw/2, y+hh/2, hw*2, hh);
			uc++;
			uc %= yeetcycle.length;
			x += uvx;
			y += uvy;
			if (keys[PConstants.RIGHT])
				uvx = Math.min(uvx+1, 10);
			if (keys[PConstants.LEFT])
				uvx = Math.max(uvx-1, -10);
			if (keys[PConstants.DOWN])
				uvy = Math.min(uvy+1, 10);
			if (keys[PConstants.UP])
				uvy = Math.max(uvy-1, -10);
		}
		
		if (keys[32]) {
			unicycle = !unicycle;
			keys[32] = false;
			uvx = 1;
			uvy = 0;
		}
			
		if (throwCool > 0)
			throwCool--;
		if (thumbPic == null)
			thumbPic = g.loadImage("images" + FileIO.fileSep + "thumb.png");
		
		for (int i = 0; i < thumbs.size(); i++) {
			if (thumbs.get(i).t<40)
				thumbs.get(i).draw(g, map);
			else
				thumbs.remove(i);
			
		}
		
	}
	
	public void projectileCollide(PApplet g, ArrayList<Enemy> enemies) {
		for (Projectile f : thumbs) {
			for (Enemy e : enemies) {
				if (e.hp > 0 && f.collide(e)) {
					e.hp -= f.size*0.8f;
					g.pushStyle();
					g.ellipseMode(PConstants.CORNER);
					g.fill(255, 0, 0);
					g.ellipse(e.x, e.y, e.hw, e.hh);
					g.popStyle();
				}
			}
		}
	}
}
