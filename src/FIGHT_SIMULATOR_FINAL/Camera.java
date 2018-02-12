/**
 * Classe qui va gerer le placement de la camera sur la carte, soit,
 * ce qu'on affiche à l'écran
 */
package projet_fin;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;


public class Camera implements KeyListener {

	private float xCamera, yCamera; // coordonne de la camera actuel
	private int orientationCam = 0; // direction de la camera
	private float xCamMax, yCamMax; // borne maximal que la camera peut atteindre

	
	public Camera() {
		// on initialise la camera en haut à gauche de la carte
		// soit, la ou se site les unites lors de leur initialisation
		xCamera = Constant.XOrigine; 
		yCamera = Constant.YOrigine;
	}

	/**
	 * Initialisation de la camera sur la map, on définit les bornes max en fonction de la carte
	 * @param map carte sur laquelle on se trouve
	 */
	public void init(Map map) {
		// les cartes ayant des tailles différentes, on définit les bornes en fonction de ces dernieres
		switch(map.getNum_carte()) { 
		case Constant.Carte1:
			this.setxCamMax(1150);
			this.setyCamMax(800);
			break;
		case Constant.Carte2:
			this.setxCamMax(1000);
			this.setyCamMax(1000);
			break;
		case Constant.Carte3:
			this.setxCamMax(1800);
			this.setyCamMax(900);
			break;
	}
	}
	

	/**
	 * On place la camera en fonction des coordonnes de la camera et de la fenetre

	 * @param container represente la fenetre de l'application
	 * @param g graphic sur lequel on fait l'affichage
	 */
	public void place(GameContainer container, Graphics g) {
		// translate permet de repositionner la camera
		g.translate(container.getWidth() / 2 - (int) this.xCamera, 
					container.getHeight() / 2 - (int) this.yCamera);
	}
	
	
	/**
	 * permet de faire l'actualisation du placement de la camera en fonction 
	 * de l'orientation de la camera qui est définit par la touche fléchée pressée
	 */
	public void update() {
		switch (this.orientationCam) {
		case Constant.HAUT: 
			if(yCamera > 350) { // on bloque la montee pour ne pas etre hors cadre
				this.yCamera = this.yCamera- 10;
			}
			break;
		case Constant.GAUCHE:
			if(xCamera > 500) { // on bloque le deplacement gauche pour ne pas etre hors cadre
				this.xCamera = this.xCamera- 10;
			}
			break;
		case Constant.BAS:
			if(yCamera < this.yCamMax) { // on bloque la descente pour ne pas sortir des limites de la carte
				this.yCamera = this.yCamera + 10;
			}
			break;
		case Constant.DROITE:
			if(xCamera < this.xCamMax) { // on bloque le deplacement droit pour ne pas sortir des limites de la carte
				this.xCamera = this.xCamera + 10;
			}
			
			break;
	 }
	}
	
	
	/**
	 * attribue une orientation a la camera en fonction de la touche fléchée pressée
	 * @param key c'est la touche qui a été pressée
	 * 
	 **/
	public void keyPressed(int key, char c) {
		switch (key) {
			case Input.KEY_UP: 
				this.orientationCam = Constant.HAUT;
				break;
			case Input.KEY_LEFT:
				this.orientationCam = Constant.GAUCHE;
				break;
			case Input.KEY_DOWN:
				this.orientationCam = Constant.BAS;
				break;
			case Input.KEY_RIGHT:
				this.orientationCam = Constant.DROITE;
				break;
		}
	}
	
	
	/**
	 * rend la camera imobile lorsque l'on relache une des fleches
	 * @param key c'est la touche que l'on vient de relacher
	 */
	public void keyReleased(int key, char c) {	
		if(key == Input.KEY_RIGHT || key == Input.KEY_DOWN || key == Input.KEY_LEFT || key == Input.KEY_UP ) {
			this.orientationCam = 0;
		}
	}

	
	/**
	 * @return l'abscisse de la camera
	 */
	public float getX() {
		return xCamera;
	}
		
	
	/**
	 * @return l'ordonnée de la camera
	 */
	public float getY() {
		return yCamera;
	}
	
	
	/**
	 * @return l'abscisse maximal de la camera
	 */
	public float getxCamMax() {
		return xCamMax;
	}

	
	/**
	 * Definit l'abscisse maximal de la camera
	 * @param xCamMax borne maximal sur x
	 */
	public void setxCamMax(float xCamMax) {
		this.xCamMax = xCamMax;
	}

	
	/**
	 * @return l'ordonnée maximal de la camera
	 */
	public float getyCamMax() {
		return yCamMax;
	}

	
	/**
	 * Definit l'ordonne maximal de la camera
	 * @param yCamMax borne maximal sur y
	 */
	public void setyCamMax(float yCamMax) {
		this.yCamMax = yCamMax;
	}


	/**
	 * Fonction herite de KeyListener mais non utilisées
	 */
	public void setInput(Input input) {	}
	
	public boolean isAcceptingInput() {return true; }
		
	public void inputEnded() {	}

	public void inputStarted() { }
	
	
	
}
