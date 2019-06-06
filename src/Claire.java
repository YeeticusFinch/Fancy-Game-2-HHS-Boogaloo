import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Claire extends Person {

	private ArrayList<Projectile> sparks = new ArrayList<Projectile>();
	private PImage sparkPic;
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
		mx = (int)((float)super.x-mx)+(int)(Math.random()*800-400);
		my = (int)((float)super.y-my)+(int)(Math.random()*800-400);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		sparks.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), sparkPic, 0.07f));
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		super.draw( g,  id, keys, map);
		
		if (sparkPic == null)
			sparkPic = g.loadImage("images" + FileIO.fileSep + "spark.png");
		
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
}
