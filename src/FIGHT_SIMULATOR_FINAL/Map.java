/*
 * On gere ici quel carte va être chargée
 * Une carte est un ensemble de tuile que nous avons positionne a l'aide de TiledMap
 * On a 3 couches d'arriere plan, 2 d'avant plan, 
 * et une couche dite logique qui permet de definir les zones interdites
 * On note qu'une tuile n'est pas forcement pleine
 */
package projet_fin;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;


public class Map {
	
	private TiledMap tiledMap; // carte que l'on va utiliser
	private int num_carte; // indice de la carte

	/**
	 * on attribue a tiledMap la carte choisi par l'utilisateur dans le menu
	 */
	public void init() throws SlickException { 
		switch(num_carte) {// le choix se fait en fonction de l'indice de la carte
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
	
	/**
	 * Notre carte se compose de 3 couches d arriere plan
	 * On les affiche
	 */
	public void renderBackground() { 
		this.tiledMap.render(0, 0, 0);
		this.tiledMap.render(0, 0, 1);
		this.tiledMap.render(0, 0, 2);
	}

	/**
	 * Notre carte se compose de 2 couches en avant plan
	 * On les affiche
	 */
	public void renderForeground() { 
		this.tiledMap.render(0, 0, 3);
		this.tiledMap.render(0, 0, 4);
	}

	/**
	 * On verifie que pour une position (X, Y) donnée, il n'y a pas d'obstacle 
	 * @param X abscisse
	 * @param Y ordonnee
	 * @return vrai si la position est dite interdite, faux si le position est autorisée
	 */
	public boolean isCollision(double X, double Y) { 
		int tileW = this.tiledMap.getTileWidth(); // largeur d'une tuile
		int tileH = this.tiledMap.getTileHeight(); // hauteur d'une tuile
		int logicLayer = this.tiledMap.getLayerIndex("logic"); // on recupere la couche "logic"
		Image tile = this.tiledMap.getTileImage((int) X / tileW, (int) Y / tileH, logicLayer); // on recupere la tuile en X et Y
		boolean collision = tile != null; // s'il y a rien, c'est une zone autorise (collision = faux), 
											// sinon c'est une zone interdite (collision = true)
		
		if (collision) { // on regarde s'il y a un pixel non transparent dans la tuile 
			Color color = tile.getColor((int) X % tileW, (int) Y % tileH);
			collision = color.getAlpha() > 0; // si le pixel est transparent, collision vaut faux, vrai sinon
		}
		return collision;
	}

	/*** GETTER qui retourne le numero de la carte ***/
	public int getNum_carte() {
		return this.num_carte;
	}

	/*** SETTER qui attribue un numero de carte ***/
	public void setNum_carte(int num) {
		this.num_carte = num;
	}
}
