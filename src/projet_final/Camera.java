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

	private float xCamera, yCamera; // coordonne de la camera
	private int orientationCam = 0; // direction de la camera
	private float xCamMax, yCamMax;

	
	public Camera() {
		// on initialise la camera en haut à gauche de la carte
		// soit, la ou se site les unites lors de leur initialisation
		xCamera = Constant.XOrigine; 
		yCamera = Constant.YOrigine;
	}

	public void init(Map map) {
		switch(map.getNum_carte()) {
		case Constant.Carte1:
			this.setxCamMax(1000);
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
	/*
	 * On place la camera en fonction des coordonnes de la camera et de la fenetre
	 */
	public void place(GameContainer container, Graphics g) {
		g.translate(container.getWidth() / 2 - (int) this.xCamera, 
					container.getHeight() / 2 - (int) this.yCamera);
	}

	// retourne l'abscisse de la camera
	public float getX() {
		return xCamera;
	}
		
	// retourne l'ordonnée de la camera
	public float getY() {
		return yCamera;
	}
	
	public float getxCamMax() {
		return xCamMax;
	}

	public void setxCamMax(float xCamMax) {
		this.xCamMax = xCamMax;
	}

	public float getyCamMax() {
		return yCamMax;
	}

	public void setyCamMax(float yCamMax) {
		this.yCamMax = yCamMax;
	}

	
	public void update() {
		switch (this.orientationCam) {
		case 1: 
			if(yCamera > 350) { // on bloque la montee pour ne pas etre hors cadre
				this.yCamera = this.yCamera- 10;
			}
			break;
		case 2:
			if(xCamera > 500) { // on bloque le deplacement gauche pour ne pas etre hors cadre
				this.xCamera = this.xCamera- 10;
			}
			break;
		case 3:
			if(yCamera < this.yCamMax) {
				this.yCamera = this.yCamera + 10;
			}
			break;
		case 4:
			if(xCamera < this.xCamMax) { // on bloque le deplacement gauche pour ne pas etre hors cadre
				this.xCamera = this.xCamera + 10;
			}
			
			break;
	 }
	}
		
	// deplace la camera en fonction des fleches directionnelles cliquees
	public void keyPressed(int key, char c) {
		// TODO trouver ou bloquer le deplacement bas et droit
		switch (key) {
			case Input.KEY_UP: 
				this.orientationCam = 1;
				break;
			case Input.KEY_LEFT:
				this.orientationCam = 2;
				break;
			case Input.KEY_DOWN:
				this.orientationCam = 3;
				break;
			case Input.KEY_RIGHT:
				this.orientationCam = 4;
				break;
		 }
	}


		
	public void setInput(Input input) {	}
	
	public boolean isAcceptingInput() {return true; }
		
	public void inputEnded() {	}

	public void inputStarted() { }
	
	public void keyReleased(int key, char c) {	
		if(key == Input.KEY_RIGHT || key == Input.KEY_DOWN || key == Input.KEY_LEFT || key == Input.KEY_UP ) {
			this.orientationCam = 0;
		}
	}
	
}
