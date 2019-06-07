import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Carl extends Person {

	private ArrayList<Projectile> thumbs = new ArrayList<Projectile>();
	private PImage thumbPic;
	
	public Carl() {
		super(5, 5, 15); //Speed, Damage, HP
	}
	
	@Override
	public void attack(int mx, int my) { //Throws thumb

		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		thumbs.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(50*mx/temp), (int)(50*my/temp), thumbPic, 0.06f));
		
		
	}
	
	@Override
	public void attack2(int mx, int my) { //Gives elbow connector
		
		
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		super.draw( g,  id, keys, map);
		
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
