/*
 * On gere ici quel carte va être chargée
 */
package projet_fin;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;


public class Map {
	private TiledMap tiledMap; // carte que l'on va utiliser
	private int num_carte;

	public void init() throws SlickException { // on attribue la bonne carte en fonction de la 
		// carte choisi dans le menu
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
		
	}
	
	// on affiche les 3 couches qui servent d arriere plan
	public void renderBackground() { 
		this.tiledMap.render(0, 0, 0);
		this.tiledMap.render(0, 0, 1);
		this.tiledMap.render(0, 0, 2);
	}

	// on affiche les 2 couches qui servent de premier plan
	public void renderForeground() { 
		this.tiledMap.render(0, 0, 3);
		this.tiledMap.render(0, 0, 4);
	}

	// test qui renvoie vraie s il y a une tuile logic en coordonne X et Y
	public boolean isCollision(double X, double Y) { // test qui 
		int tileW = this.tiledMap.getTileWidth();
		int tileH = this.tiledMap.getTileHeight();
		int logicLayer = this.tiledMap.getLayerIndex("logic");
		Image tile = this.tiledMap.getTileImage((int) X / tileW, (int) Y / tileH, logicLayer);
		boolean collision = tile != null;
		if (collision) {
			Color color = tile.getColor((int) X % tileW, (int) Y % tileH);
			collision = color.getAlpha() > 0;
		}
		return collision;
	}

	public int getNum_carte() {
		return this.num_carte;
	}

	public void setNum_carte(int num) {
		this.num_carte = num;
	}
}
