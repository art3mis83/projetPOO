/*
 * Classe qui permet d'afficher les choix et message de fin de partie
 */
package projet_fin;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class FinHud{


	private Image parchemin; // image servant de fond pour notre message
								// de fin de partie
	private Camera camera; // on recupere la camera afin de bouger notre hud 
								// en meme temps que la camera
	private int equipe_gagnante; // on recupere le numero de l'équipe qui a gagné
	

	/**
	 * Constructeur, on récupère l'image servant de corps a notre HUD
	 * @param camera on  recupere la camera afin de bouger notre hud 
	 * 				en meme temps que la camera
	 */
	public FinHud(Camera camera) throws SlickException { // on a juste besoin de la camera
		this.camera = camera;
		this.parchemin = new Image("hud/parchemin.png"); // on definit l image servant de hud
	}
	
	/**
	 * Fonction gérant l'affichage de notre HUD
	 * @param container fenetre dans laquelle on fait l'affichage
	 * @param g composant graphique sur lequelle on affiche
	 */
	public void render(GameContainer container, Graphics g) { 
	  
	  g.drawImage(parchemin, camera.getX() + 55, camera.getY() - 300); // on affiche d'abord notre fond
	  g.setColor(Color.blue); // le nom du vainqueur est ecrit en bleu
	  g.drawString("Equipe " + this.equipe_gagnante + " a gagné", camera.getX() + 120, camera.getY() - 245); // puis l equipe gagnante
	  g.setColor(Color.black); // on veut ecrire en noir
	  
	  // on précise les options possible
	  g.drawString("Appuyer sur R pour \nrelancer la bataille", camera.getX() + 100, camera.getY() - 200);
	  g.drawString("Appuyer sur M pour \nrevenir au Menu", camera.getX() + 100, camera.getY() - 150);
	  g.drawString("Appuyer sur Echapp \npour quitter", camera.getX() + 100, camera.getY() - 100);
	  
	}
	
	/**
	 * Setter pour définir l'equipe gagnante 
	 * @param e indice de l'équipe gagnante
	 */
	public void setGagnant(int e) { 
		this.equipe_gagnante = e;
	}
	
}
