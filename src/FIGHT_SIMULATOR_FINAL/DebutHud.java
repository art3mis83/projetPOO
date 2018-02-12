/*
 * Classe qui permet d'afficher les choix et message de début de partie de partie sur notre parchemin
 */
package projet_fin;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class DebutHud {


	private Image parchemin; // image servant de fond pour notre message
								// de debut de partie
	private Camera camera; // on recupere la camera afin de bouger notre hud 
								// en meme temps que la camera
	private boolean erreur_placement; // boolean signalant une erreur de placement
	

	/**
	 * Constructeur, on récupère l'image servant de corps a notre HUD
	 * @param camera on  recupere la camera afin de bouger notre hud 
	 * 				en meme temps que la camera
	 */
	public DebutHud(Camera camera) throws SlickException { // on a juste besoin de la camera
		this.camera = camera;
		this.parchemin = new Image("hud/parchemin.png"); // on definit l image servant de hud
	}
	
	/**
	 * Fonction gérant l'affichage de notre HUD
	 * @param container fenetre dans laquelle on fait l'affichage
	 * @param g composant graphique sur lequelle on affiche
	 */
	public void render(GameContainer container, Graphics g) { 
	  g.setColor(Color.black); // on veut ecrire en noir
	  
	  g.drawImage(parchemin, camera.getX() + 105, camera.getY() - 300); // on affiche d'abord notre fond
	  if(erreur_placement) { // si on detecte une erreur, on la signale
		  g.setColor(Color.red); // message d'erreur en rouge
		  g.drawString("Erreur de placement \nVerifier placement", camera.getX() + 170, camera.getY() - 245); 
		  g.setColor(Color.black); // on repasse en noir
	  }
	  // on précise les options possible
	  g.drawString("Cliquer sur une unite\npour la deplacer", camera.getX() + 150, camera.getY() - 200);
	  g.drawString("Appuyer sur Entre \n pour lancer la partie", camera.getX() + 150, camera.getY() - 150);
	  g.drawString("Appuyer sur Echapp \npour quitter", camera.getX() + 150, camera.getY() - 100);
	  
	}
	
	/**
	 * Setter pour signaler une erreur de placement d'une des unités (ou que tout est bon)
	 * @param val valeur que l'on attribue a notre booleen erreur_placement
	 * Valant true si une unité est mal placée, faux sinon
	 */
	public void setErreur(boolean val) {
		erreur_placement = val;
	}
	
	
}
