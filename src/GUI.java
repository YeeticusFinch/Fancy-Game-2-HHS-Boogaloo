import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class GUI extends PApplet {
	
	private String[] charNames = {"Smallboi", "Rocket Man", "Yes Yes Yes Man", "Fancy Boi", "EEEEEEEEEEEEEE", "Glasses Geek"};
	private String[] desc = {"Very small and fast, passes people elbow connectors with or without them noticing\nLeftClick to pass elbow connector and generate health packs, Space+arrowkey to dodge",
			"Slowmoving with an obsession with nukes, throws mini nukes at people\nLeftClick to throw nuke, Space to detonate nuke",
			"Medium speed, yells \"Yes Yes Yes\" at people, destroys terrain\nLeftClick to yell \"Yes Yes Yes\", RightClick to place walls",
			"Medium speed, throws fancy thumbs at a long range\nLeftClick to throw thumb, Space to mount/dismount unicycle",
			"High speed, shoots really low range electric sparks, but they travel further through walls\nLeftClick to zap, RightClick to throw fox which freezes people",
			"Can move fast if he's late for the bus, tosses computer parts that he doesn't need,\nglasses, and oranges because why not\nLeftClick to toss comp part, Right click to toss glasses"
	};
	
	Map map = new Map();
	
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	private boolean[] keys = new boolean[300];
	
	private Person player;
	
	public static float tx = 0;
	public static float ty = 0;
	public static float zoom = 1;
	
	private final int SELECTION = 0;
	private final int PLAY = 1;
	private final int DEAD = 2;
	
	public static int selected = -1;
	
	private PImage[] sel = new PImage[6];
	
	private int phase = 0;
	
	public void setup() {
		for (int i = 0; i < sel.length; i++) {
			sel[i] = loadImage("images" + System.getProperty("file.separator") + "sel" + i + ".png");
		}
		//for (int i = 0; i < 19; i++) {
		//	map.loadMap(i);
		//}
		map.loadMap(0);
		map.setMap(0);
		
	}
	
	public void draw() {
		if (phase == SELECTION) {
			drawSelection();
		} else if (phase == PLAY) {
			drawPlay();
		} else if (phase == DEAD) {
			drawDead();
		}
		
	}
	
	
	public void drawDead() {
		clear();
		textAlign(LEFT);
		textSize(width*0.02f);
		text("HA, YOU DIED!!!", width*0.01f, height*0.04f);
		textAlign(CENTER);
		text("Press SPACE to start from the previous level", width*0.5f, height*0.9f);
		image(sel[selected], width*0.2f, height*0.2f, width*0.5f, height*0.6f);
	}
	
	public void drawPlay() {
		if (map.noPerson) {
			map.draw(this, tx, ty);
			if (keys[32]) {
				keys[32] = false;
				if (!map.isLoaded(map.m+1))
					map.loadMap(map.m+1);
				map.setMap(map.m+1);
				player.spawn(map.getCurrentMap(), this);
			}
		}
		else {
			clear();
			scale(zoom);
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
				
			char fancy = player.collide(this, map.getCurrentMap());
			
			translate(tx, ty);
			map.draw(this, tx, ty);
			if (fancy == '2' && 0 == enemies.size()) {
				if (!map.isLoaded(map.m+1))
					map.loadMap(map.m+1);
				map.setMap(map.m+1);
				player.spawn(map.getCurrentMap(), this);
			} else if (fancy == ')') {
				player.mode = 0;
				player.unlocked = true;
			} else if (fancy == '!') {
				player.mode = 1;
			} else if (fancy == '@') {
				player.mode = 2;
			}
			
			if (player.hp > 0)
				player.draw(this, selected, keys, map.getCurrentMap());
			else
				phase = DEAD;
			
			for (Enemy e : enemies) {
				if (e.hp > 0) {
					e.draw(this, keys, map.getCurrentMap());
					if (Map.vr) {
						pushStyle();
						fill(0);
						stroke(255, 100, 10);
						rect(e.x, e.y, e.hw, e.hh);
						popStyle();
					}
					if (e.ox == 0 && e.oy == 0)
						e.move(player.getX(), player.getY());
					else {
						e.move(e.ox, e.oy);
						System.out.println("yoink");
					}
					e.collide(this, map.getCurrentMap());
				} else {
					e.deleteProjectiles();
				}
				e.projectileCollide(this, player);
			}
			
			for (int i = 0; i < enemies.size(); i++) {
				if (enemies.get(i).hp <=0) {
					enemies.remove(i);
				}
			}
			player.projectileCollide(this, enemies);
			textAlign(LEFT);
			translate(-tx, -ty);
			text("HP: " + player.hp, width*0.2f, height*0.03f);
			//text("YEET", mouseX-tx, mouseY-ty);
		}
	}
	
	public void drawSelection() {
		clear();
		background((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
		if (selected == -1) {
			textAlign(LEFT);
			textSize(width*0.02f);
			text("Choose your character", width*0.01f, height*0.04f);
			
			for (int i = 0; i < Math.min(sel.length, 6); i++)
				image(sel[i], width*0.2f*i, 1*height*0.25f, width*0.2f, width*0.2f);
			for (int i = 5; i < Math.min(sel.length, 12); i++)
				image(sel[i], width*0.2f*(i-5), 2*height*0.3f, width*0.2f, width*0.2f);
		} else {
			textAlign(LEFT);
			textSize(width*0.02f);
			text("Character Chosen: " + charNames[selected] + "\nDescription: " + desc[selected], width*0.01f, height*0.04f);
			textAlign(CENTER);
			text("Press SPACE to start\nOr press BACKSPACE to go back", width*0.5f, height*0.9f);
			image(sel[selected], width*0.2f, height*0.2f, width*0.5f, height*0.6f);
		}
		
	}
	
	public void keyPressed() {
		//System.out.println(keyCode);
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
					case 5:
						player = new Anton();
						break;
				}
				player.setLoc((int)(width*0.2), (int)(height*0.2));
				player.spawn(map.getCurrentMap(), this);
			} else if (keyCode == 8) {
				selected = -1;
			}
		} else if (phase == PLAY && player.fox < 1) { // W = 87, A = 65, S = 83, D = 68, Q = 81, E = 69
			
			keys[keyCode] = true;
			if (keyCode == 192) { // REEE CHEATING
				enemies.clear();
			}
		} else if (phase == DEAD) {
			if (keyCode == 32) {
				enemies.clear();
				player.hp = player.maxHP;
				map.setMap(Math.max(map.m-1, 0));
				player.spawn(map.getCurrentMap(), this);
				phase = PLAY;
			}
		}
	}
	
	public void keyReleased() {
		if (phase == PLAY) {
			keys[keyCode] = false;
		}
	}

	public void mouseReleased() {
    	if (mouseButton == CENTER)
    		keys[32] = false;
    	}
	
	public void mousePressed() { //Yeet, I like them triangles
		if (phase == SELECTION) {
			if (mouseButton == LEFT) {
				for (int i = 0; i < sel.length; i++) {
					if (mouseX > width*0.2f*i && mouseX < width*0.2f*i+width*0.2f && mouseY > height*0.25f && mouseY < height*0.25f+width*0.2f) {
						selected = i;
						System.out.println("Selected " + i);
						//phase = 1;
					} else if (mouseX > width*0.2f*(i-5) && mouseX < width*0.2f*i+width*0.2f && mouseY > height*0.6f && mouseY < height*0.6f+width*0.2f) {
						selected = i;
						System.out.println("Selected " + i);
						//phase = 1;
					}
				}
			}
		} else if (phase == PLAY) {
			if (mouseButton == LEFT && player.fox < 1) {
				player.attack(mouseX-(int)tx, mouseY-(int)ty);
			} else if (mouseButton == RIGHT && player.fox < 1) {
				player.attack2(mouseX-(int)tx, mouseY-(int)ty);
			} else if (mouseButton == CENTER && player.fox < 1) {
				keys[32] = true;
			}
		}
	}
	
}
