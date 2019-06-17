import java.io.IOException;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Map {

	private ArrayList<ArrayList<String>> map = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<Integer>> mDet = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> maxX = new ArrayList<Integer>();
	private ArrayList<Integer> maxY = new ArrayList<Integer>();
	private PImage hPack;
	public int m;
	private PImage[] modes = new PImage[3];
	public static boolean vr = false;
	private PImage fancyImage;
	public boolean noPerson = false;
	
	public void loadMap(int i) {
		try {
			map.add(FileIO.reeeeeeeeeeeeeeeeeead("maps" + FileIO.fileSep + "m" + i));
			ArrayList<Integer> yoink = new ArrayList<Integer>();
			for (int j = 0; j < map.get(map.size() - 1).get(0).length(); j++) {
				if (map.get(map.size() - 1).get(0).indexOf(j + ":") != -1)
					yoink.add(Integer.parseInt(map.get(map.size() - 1).get(0)
							.substring(map.get(map.size() - 1).get(0).indexOf(j + ":") + 2, map.get(map.size() - 1)
									.get(0).indexOf(",", map.get(map.size() - 1).get(0).indexOf(j + ":") + 2))));
			}
			mDet.add(yoink);
			maxX.add(0);
			for (int j = 1; j < map.get(map.size() - 1).size(); j++)
				if (maxX.get(maxX.size() - 1) < map.get(map.size() - 1).get(j).length())
					maxX.set(maxX.size() - 1, map.get(map.size() - 1).get(j).length());
			maxY.add(map.get(map.size() - 1).size());
			System.out.println("This map is " + maxX.get(maxX.size() - 1) + " by " + maxY.get(maxY.size() - 1));
			
			
		} catch (IOException e) {
			System.out.println("Ruh Roh");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> getCurrentMap() {
		return map.get(m);
	}

	public int maxX() {
		return maxX.get(m);
	}

	public int maxY() {
		return maxY.get(m);
	}

	public void draw(PApplet g, float tx, float ty) {
		if (hPack == null)
			hPack = g.loadImage("images" + FileIO.fileSep + "hPack.png");
		g.pushStyle();
		if (map.get(m).get(0).substring(0,3).equals("!!!")) {
			if (fancyImage == null) {
				if (map.get(m).get(2).equalsIgnoreCase("player"))
					fancyImage = g.loadImage("images" + FileIO.fileSep + "sel" + GUI.selected + ".png");
				else
					fancyImage = g.loadImage("images"+FileIO.fileSep+map.get(m).get(2));
			}
			g.background((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
			g.imageMode(PConstants.CENTER);
			g.image(fancyImage, g.width/2, g.height/2, g.width*0.6f, g.height*0.8f);
			g.textAlign(PConstants.CENTER);
			g.fill(255);
			g.textSize(g.width*0.02f);
			g.text(map.get(m).get(1), g.width/2, g.height*0.05f);
			g.text("Press SPACE to continue", g.width/2, g.height*0.95f);
		} else {
			for (int i = 1; i < map.get(m).size(); i++) {
				for (int j = 0; j < map.get(m).get(i).length(); j++) {
					g.stroke(0);
					switch ((char)map.get(m).get(i).charAt(j)) {
						case '0': //Empty
							if (vr) {
								g.fill(0);
								g.stroke(100, 50, 0);
							}
							else
								g.fill(220);
							g.rect(g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
							break;
						case '1': //Wall
							if (vr) {
								g.noFill();
								g.stroke(255, 100, 10);
							} else
								g.fill(100);
							g.rect(g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
							break;
						case '2': //Door to next map
							if (GUI.enemies.size()>0)
								g.fill(50, 100, 50);
							else
								g.fill(100, 255, 100);
							g.rect(g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
							break;
						case '3': //Player spawn
							g.fill(255, 255, 100);
							g.rect(g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
							break;
						case '4': //Enemy Spawn
							g.fill(255, 100, 100);
							g.rect(g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
							break;
						case '5': //Health Pack
							g.fill(220);
							g.rect(g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
							g.fill(255);
							g.rect(g.width*0.05f*j+g.width*0.01f, g.width*0.05f*(i-1)+g.width*0.01f, g.width*0.05f-g.width*0.02f, g.width*0.05f-g.width*0.02f);
							g.image(hPack, g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
							break;
						case ')': //Mode 0 upgrade
							g.fill(255, 255, 0);
							g.rect(g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
							if (modes[0] == null)
								modes[0] = g.loadImage("images" + FileIO.fileSep + "h" + GUI.selected + "m0.png");
							g.image(modes[0], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
							break;
						case '!': //Mode 1 upgrade
							g.fill(255, 255, 0);
							g.rect(g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
							if (modes[1] == null)
								modes[1] = g.loadImage("images" + FileIO.fileSep + "h" + GUI.selected + "m1.png");
							g.image(modes[1], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
							break;
						case '@': //Mode 2 upgrade
							g.fill(255, 255, 0);
							g.rect(g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
							if (modes[2] == null)
								modes[2] = g.loadImage("images" + FileIO.fileSep + "h" + GUI.selected + "m2.png");
							g.image(modes[2], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
							break;
					}
				}
			}
			g.pushMatrix();
			g.pushStyle();
			g.translate(-tx, -ty);
			g.textAlign(PConstants.LEFT);
			g.fill(255);
			g.text(map.get(m).get(0).substring(map.get(m).get(0).indexOf('\"')+1, map.get(m).get(0).indexOf('\"',map.get(m).get(0).indexOf('\"')+1)), 0, g.height*0.03f);
			g.popMatrix();
			g.popStyle();
		}
		g.popStyle();
	}
	
	public int getMap() {
		return m;
	}
	
	public boolean isLoaded(int m) {
		return (m < map.size());
	}

	public void setMap(int m) {
		this.m = m;
		GUI.enemies.clear();
		if (map.get(m).get(0).substring(0,3).equals("!!!")) {
			fancyImage = null;
			noPerson = true;
		} else {
			noPerson = false;
			for (int j = 0; j < mDet.get(m).size(); j++) {
				for (int k = 0; k < mDet.get(m).get(j); k++) {
					switch(j) {
						case 0:
							GUI.enemies.add(new Darius());
							break;
						case 1:
							GUI.enemies.add(new VonStein());
							break;
						case 2:
							GUI.enemies.add(new Garrett());
							break;
						case 3:
							GUI.enemies.add(new Joe());
							break;
						case 4:
							GUI.enemies.add(new Henry());
							break;
						case 5:
							GUI.enemies.add(new Shelby());
							break;
						case 6:
							GUI.enemies.add(new Antagonist());
							break;
						case 7:
							GUI.enemies.add(new Antagonist(true));
							break;
						case 8:
							GUI.enemies.add(new Stalin());
							break;
					}
				}
			}
		}
	}

}
