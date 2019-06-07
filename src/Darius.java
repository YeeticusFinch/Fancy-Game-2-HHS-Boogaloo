
public class Darius extends Enemy {

	boolean rushing = false;
	
	public Darius() {
		super(5, 4, 10); //Speed, Damage, HP
		if (Math.random() > 0.5)
			rushing = true;
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
			float temp = (float)Math.sqrt( (px-x)*(px-x) + (py-y)*(py-y) );
			vx = (int)(speed*(px-x)/temp);
			vy = (int)(speed*(py-y)/temp);
		}
		x+=vx;
		y+=vy;
		if (Math.random()>0.99)
			rushing = !rushing;
	}

	@Override
	public void attack(int mx, int my) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attack2(int mx, int my) {
		// TODO Auto-generated method stub
		
	}

	
	
}
