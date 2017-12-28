package test_beta;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 * 
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y-
 */
public class Map {
	private TiledMap tiledMap;

	public void init(int num_carte) throws SlickException {
		switch(num_carte) {
		case Constant.Carte1:
			this.tiledMap = new TiledMap("map/carte_perso.tmx");
			break;
		case Constant.Carte2 :
			this.tiledMap = new TiledMap("map/exemple.tmx");
			break;
		case Constant.Carte3 :
			this.tiledMap = new TiledMap("map/myDesert.tmx");
			break;
		}
		//this.tiledMap = new TiledMap("map/carte_perso.tmx");
	}

	public void renderBackground() {
		this.tiledMap.render(0, 0, 0);
		this.tiledMap.render(0, 0, 1);
		this.tiledMap.render(0, 0, 2);
	}

	public void renderForeground() {
		this.tiledMap.render(0, 0, 3);
		this.tiledMap.render(0, 0, 4);
	}

	public boolean isCollision(double futurX, double futurY) {
		int tileW = this.tiledMap.getTileWidth();
		int tileH = this.tiledMap.getTileHeight();
		int logicLayer = this.tiledMap.getLayerIndex("logic");
		Image tile = this.tiledMap.getTileImage((int) futurX / tileW, (int) futurY / tileH, logicLayer);
		boolean collision = tile != null;
		if (collision) {
			Color color = tile.getColor((int) futurX % tileW, (int) futurY % tileH);
			collision = color.getAlpha() > 0;
		}
		return collision;
	}

	public void changeMap(String file) throws SlickException {
		this.tiledMap = new TiledMap(file);
	}

	public int getObjectCount() {
		return this.tiledMap.getObjectCount(0);
	}

	public String getObjectType(int objectID) {
		return this.tiledMap.getObjectType(0, objectID);
	}

	public float getObjectX(int objectID) {
		return this.tiledMap.getObjectX(0, objectID);
	}

	public float getObjectY(int objectID) {
		return this.tiledMap.getObjectY(0, objectID);
	}

	public float getObjectWidth(int objectID) {
		return this.tiledMap.getObjectWidth(0, objectID);
	}

	public float getObjectHeight(int objectID) {
		return this.tiledMap.getObjectHeight(0, objectID);
	}

	public String getObjectProperty(int objectID, String propertyName, String def) {
		return this.tiledMap.getObjectProperty(0, objectID, propertyName, def);
	}

}
