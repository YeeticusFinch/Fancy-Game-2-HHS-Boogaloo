import java.io.IOException;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;

public class Map {

	private ArrayList<ArrayList<String>> map = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<Integer>> mDet = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> maxX = new ArrayList<Integer>();
	private ArrayList<Integer> maxY = new ArrayList<Integer>();
	private int m;

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
			for (int j = 0; j < map.get(map.size() - 1).size(); j++)
				if (maxX.get(maxX.size() - 1) < map.get(map.size() - 1).get(j).length())
					maxX.set(maxX.size() - 1, map.get(map.size() - 1).get(j).length());
			maxY.add(map.get(map.size() - 1).size());
			System.out.println("This map is " + maxX.get(maxX.size() - 1) + " by " + maxY.get(maxY.size() - 1));
			GUI.enemies.clear();
			for (int j = 0; j < mDet.get(mDet.size()-1).size(); j++) {
				for (int k = 0; k < mDet.get(mDet.size()-1).get(j); k++) {
					switch(j) {
						case 0:
							GUI.enemies.add(new Darius());
							break;
					}
				}
			}
			
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
		g.pushStyle();
		for (int i = 1; i < map.get(m).size(); i++) {
			for (int j = 0; j < map.get(m).get(i).length(); j++) {
				switch ((char)map.get(m).get(i).charAt(j)) {
					case '0':
						g.fill(220);
						g.rect(g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '1':
						g.fill(100);
						g.rect(g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '2':
						g.fill(100, 255, 100);
						g.rect(g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '3':
						g.fill(255, 255, 100);
						g.rect(g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
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
		g.popStyle();
	}
	
	public int getMap() {
		return m;
	}

	public void setMap(int m) {
		this.m = m;
	}

}