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
		super(0,0);
		this.map = m;
		gemme = new Image("sprites/gemme_rouge.png"); // on definit l'image du bonud
	}
	
	/*
	 * On place les bonus sur la carte en fonction des bornes definit en attributs
	 * Si on est sur une zone non accessible pour une unite (zone de collision), 
	 * on recommence jusqu a trouve une bonne position
	 */
	public void init(float xMax, float yMax) throws SlickException {
		
		
		this.setxPos((Math.random()* (xMax - xMin) + xMin));
		this.setyPos((Math.random()* (yMax - yMin) + yMin)); 
		  
		while(map.isCollision(this.getX(), this.getY())) {
			this.setxPos((Math.random()* (xMax - xMin) + xMin));
			this.setyPos((Math.random()* (yMax - yMin) + yMin)); 
		  
		}
	}
	
	/*
	 * On affiche le bonus
	 */
	public void render(Graphics g) {
		  
		gemme.draw((float)this.getX() ,(float)this.getY());
	}

	
	/*
	 *  on simule une suppression en placant le bonus a l'autre bout
	 *  de la carte
	 */
	public void supprimer() {
		this.setxPos(1000);
		this.setyPos(1000);
		
	}
	
	
}
