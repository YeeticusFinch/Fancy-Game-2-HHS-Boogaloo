import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Osman extends Person {

	private float tx = 0;
	private float ty = 0;
	
	private ArrayList<Projectile> elbows = new ArrayList<Projectile>();
	
	private PImage elbowPic;
	
	public Osman() {
		super(8, 7, 15); //Speed, Damage, HP
		
	}
	
	@Override
	public void attack(int mx, int my) { //Gives elbow connector
		
		mx = (int)((float)super.x-mx);
		my = (int)((float)super.y-my);
		
		double temp = -Math.sqrt(mx*mx + my*my);
		
		elbows.add(new Projectile((int)(super.x+this.hw/2), (int)(super.y+this.hh/2), (int)(30*mx/temp), (int)(30*my/temp), elbowPic, 0.03f));
		
	}
	
	@Override
	public void attack2(int mx, int my) { //Gives elbow connector
		
		tx = 0.3f*((float)super.x-mx);
		ty = 0.3f*((float)super.y-my);
		
	}
	
	public void draw(PApplet g, int id, boolean[] keys, ArrayList<String> map) {
		super.draw( g,  id, keys, map);
		if (elbowPic == null) {
			elbowPic = g.loadImage("images" + FileIO.fileSep + "elbow.png");
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
		}	
		for (int i = 0; i < elbows.size(); i++) {
			if (elbows.get(i).t<10)
				elbows.get(i).draw(g, map);
			else
				elbows.remove(i);
			
		}
		
	}
	
}
