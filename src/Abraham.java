import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Abraham extends Person {

	private ArrayList<Projectile> yeses = new ArrayList<Projectile>();
	private PImage yesPic;
	int yes = 0;
	int mx=0;
	int my=0;
	boolean throwable = true;
	
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
		
		yeses.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(40*mx/temp), (int)(40*my/temp), yesPic, 0.2f, true));
	}
	
	@Override
	public void attack2(int mx, int my) { //Gives elbow connector
		
		
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		super.draw( g,  id, keys, map);
		
		if (yesPic == null)
			yesPic = g.loadImage("images" + FileIO.fileSep + "yes.png");
		
		for (int i = 0; i < yeses.size(); i++) {
			if (yeses.get(i).t<40)
				yeses.get(i).draw(g, map);
			else
				yeses.remove(i);
			
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
	}
	
}
