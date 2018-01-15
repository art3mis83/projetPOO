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


public class DebutHud implements ComponentListener{


	private Image parchemin; // image servant de fond pour notre message
								// de fin de partie
	private Camera camera; // on recupere la camera afin de bouger notre hud 
								// en meme temps que la camera
	private boolean erreur_placement;
	

	public DebutHud(Camera camera) throws SlickException { // on a juste besoin de la camera
		this.camera = camera;
		this.parchemin = new Image("hud/parchemin.png"); // on definit l image servant de hud
	}
	
	
	public void render(GameContainer container, Graphics g) { 
	  g.setColor(Color.black);
	  
	  g.drawImage(parchemin, camera.getX() + 55, camera.getY() - 300); // on affiche d'abord notre fond
	  if(erreur_placement) {
		  g.setColor(Color.red);
		  g.drawString("Erreur de placement \nVerifier placement", camera.getX() + 120, camera.getY() - 245); // puis l equipe g
		  g.setColor(Color.black);
	  }
	  g.drawString("Cliquer sur une unite\npour la deplacer", camera.getX() + 100, camera.getY() - 200);
	  // puis les options
	  g.drawString("Appuyer sur Entre \n pour lancer la partie", camera.getX() + 100, camera.getY() - 150);
	  g.drawString("Appuyer sur Echapp \npour quitter", camera.getX() + 100, camera.getY() - 100);
	  
	}
	
	public void setErreur(boolean val) {
		erreur_placement = val;
	}
	public void componentActivated(AbstractComponent source) {	}
	
}
