/*
 * Classe qui permet d'afficher les choix et message de fin de partie
 */
package projet_fin;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;


public class FinHud implements ComponentListener{


	private Image parchemin; // image servant de fond pour notre message
								// de fin de partie
	private Camera camera; // on recupere la camera afin de bouger notre hud 
								// en meme temps que la camera
	private int equipe_gagnante; // 
	

	public FinHud(Camera camera) throws SlickException { // on a juste besoin de la camera
		this.camera = camera;
		this.parchemin = new Image("hud/parchemin.png"); // on definit l image servant de hud
	}
	
	
	public void render(GameContainer container, Graphics g) { 
	  //g.setColor(Color.black);
	  
	  g.drawImage(parchemin, camera.getX() + 55, camera.getY() - 300); // on affiche d'abord notre fond
	  g.setColor(Color.blue);
	  g.drawString("Equipe " + this.equipe_gagnante + " a gagné", camera.getX() + 120, camera.getY() - 245); // puis l equipe gagnante
	  g.setColor(Color.black);
	  g.drawString("Appuyer sur R pour \nrelancer la bataille", camera.getX() + 100, camera.getY() - 200);
	  // puis les options
	  g.drawString("Appuyer sur M pour \nrevenir au Menu", camera.getX() + 100, camera.getY() - 150);
	  g.drawString("Appuyer sur Echapp \npour quitter", camera.getX() + 100, camera.getY() - 100);
	  
	}
	
	
	public void setGagnant(int e) { // definit l equipe gagante
		this.equipe_gagnante = e;
	}
	
	
	public void componentActivated(AbstractComponent source) {	}
	
}
