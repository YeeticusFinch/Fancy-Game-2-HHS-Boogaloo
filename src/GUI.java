import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class GUI extends PApplet {
	
	private String[] charNames = {"Smallboi", "Rocket Man", "Yes Yes Yes Man", "Fancy Boi", "EEEEEEEEEEEEEE"};
	
	Map map = new Map();
	
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	private boolean[] keys = new boolean[300];
	
	private Person player;
	
	private float tx = 0;
	private float ty = 0;
	
	private final int SELECTION = 0;
	private final int PLAY = 1;
	private final int END = 2;
	
	private int selected = -1;
	
	private PImage[] sel = new PImage[5];
	
	private int phase = 0;
	
	public void setup() {
		for (int i = 0; i < sel.length; i++) {
			sel[i] = loadImage("images" + System.getProperty("file.separator") + "sel" + i + ".png");
		}
		map.loadMap(0);
		
	}
	
	public void draw() {
		if (phase == SELECTION) {
			drawSelection();
		} else if (phase == PLAY) {
			drawPlay();
		}
		
	}
	
	public void drawPlay() {
		clear();
		if(player.getX() >= width*0.5f && player.getX() <= map.maxX()*width*0.05f-width*0.5f)
			tx= -player.getX()+width*0.5f;
		else if(player.getX() < width*0.5f)
			tx = 0;
		else if (player.getX() > map.maxX()*width*0.05f-width*0.5f)
			tx = -(map.maxX()*width*0.05f-width);
		
		if(player.getY() >= height*0.5f && player.getY() <= (map.maxY()-1)*width*0.05f-height*0.5f)
			ty= -player.getY()+height*0.5f;
		else if(player.getY() < height*0.5f)//ahem osman
			ty = 0;
		else if(player.getY() > (map.maxY()-1)*width*0.05f-height*0.5f)
			ty = -((map.maxY()-1)*width*0.05f-height);
		//System.out.println("player.getX() = " + player.getX() + "player.getY() = " + player.getY());
		//System.out.println("tx = " + tx);
		//System.out.println("ty = " + ty);
				
		translate(tx, ty);
		map.draw(this, tx, ty);
		if (player.collide(this, map.getCurrentMap()) == '2') {
			map.loadMap(map.getMap()+1);
			map.setMap(map.getMap()+1);
			player.spawn(map.getCurrentMap(), this);
		}
		player.draw(this, selected, keys, map.getCurrentMap());
		for (Enemy e : enemies) {
			e.draw(this, keys, map.getCurrentMap());
			e.move(player.getX(), player.getY());
			e.collide(this, map.getCurrentMap());
		}
		//text("YEET", mouseX-tx, mouseY-ty);
	}
	
	public void drawSelection() {
		clear();
		background((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
		if (selected == -1) {
			textAlign(LEFT);
			textSize(width*0.02f);
			text("Choose your character", width*0.01f, height*0.04f);
			for (int i = 0; i < sel.length; i++)
				image(sel[i], width*0.2f*i, height*0.3f, width*0.2f, width*0.2f);
		} else {
			textAlign(LEFT);
			textSize(width*0.02f);
			text("Character Chosen: " + charNames[selected], width*0.01f, height*0.04f);
			textAlign(CENTER);
			text("Press SPACE to start", width*0.5f, height*0.9f);
			image(sel[selected], width*0.2f, height*0.2f, width*0.6f, height*0.6f);
		}
		
	}
	
	public void keyPressed() {
		if (phase == SELECTION && selected != -1) {
			if (keyCode == 32) {
				phase = PLAY;
				switch (selected) {
					case 0:
						player = new Osman();
						break;
					case 1:
						player = new Anand();
						break;
					case 2:
						player = new Abraham();
						break;
					case 3:
						player = new Carl();
						break;
					case 4:
						player = new Claire();
						break;
				}
				player.setLoc((int)(width*0.2), (int)(height*0.2));
				player.spawn(map.getCurrentMap(), this);
			}
		} else if (phase == PLAY) { // W = 87, A = 65, S = 83, D = 68, Q = 81, E = 69
			
			keys[keyCode] = true;
		}
	}
	
	public void keyReleased() {
		if (phase == PLAY) {
			keys[keyCode] = false;
		}
	}
	
	public void mousePressed() { //Yeet, I like them triangles
		if (phase == SELECTION) {
			if (mouseButton == LEFT) {
				for (int i = 0; i < sel.length; i++) {
					if (mouseX > width*0.2f*i && mouseX < width*0.2f*i+width*0.2f && mouseY > height*0.3f && mouseY < height*0.3f+width*0.2f) {
						selected = i;
						System.out.println("Selected " + i);
						//phase = 1;
					}
				}
			}
		} else if (phase == PLAY) {
			if (mouseButton == LEFT) {
				player.attack(mouseX-(int)tx, mouseY-(int)ty);
			} else if (mouseButton == RIGHT) {
				player.attack2(mouseX-(int)tx, mouseY-(int)ty);
			}
		}
	}
	
}
