/**
 * Classe representant des bonnus, ils doublent les degats de l'unite qui collecte le boost
 * 
 * Un bonus est une entite
 */
package projet_fin;



import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Gemme extends Entite{
	

	private Image gemme; // image que l'on affiche sur la carte
	private Map map; // map sur laquelle on interagit
	
	// bornes entre lesquelles les bonus peuvent apparaitre
	private float xMin = 50;
	private float yMin = 50;

	
	public Gemme(Map m) throws SlickException {
		super(1000,1000);
		this.map = m;
		gemme = new Image("sprites/gemme_rouge.png"); // on definit l'image du bonud
	}
	
	/**
	 *  On place les bonus aléatoirement sur la carte en fonction des bornes definit en attributs
	 * Si on est sur une zone non accessible pour une unite (zone de collision), 
	 * on recommence jusqu a trouve une bonne position
	 * @param xMax borne maximal pour les abscisses
	 * @param yMax borne maximal pour les ordonnees
	 */
	public void init(float xMax, float yMax) throws SlickException {
		
		/*
		 * Pour obtenir un nombre aléatoire entre a et b on fait :
		 *  Nb_aléatoire * (b-a) + a
		 */
		
		this.setxPos((Math.random()* (xMax - xMin) + xMin)); // abscisse aleatoire
		this.setyPos((Math.random()* (yMax - yMin) + yMin)); // ordonnee aleatoire
		  
		while(map.isCollision(this.getX(), this.getY())) { // tant que la position donnée n'est pas authorisee, on recommence le tirage
			this.setxPos((Math.random()* (xMax - xMin) + xMin)); // abscisse aleatoire
			this.setyPos((Math.random()* (yMax - yMin) + yMin)); // ordonnee aleatoire
		  
		}
	}
	
	/**
	 * On affiche le bonus, soit l'image de la gemme
	 * @param g composant graphique sur lequelle on affiche
	 */
	public void render(Graphics g) {
		  
		gemme.draw((float)this.getX() - 10 ,(float)this.getY() - 10);
	}

	
	/**
	 * on simule une suppression en placant le bonus a l'autre bout
	 *  de la carte
	 */
	public void supprimer() {
		this.setxPos(1000);
		this.setyPos(1000);
		
	}
	
	
}
