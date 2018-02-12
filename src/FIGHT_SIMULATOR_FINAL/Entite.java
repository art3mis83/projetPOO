/**
 * Classe mere de Unite et Gemme
 */
package projet_fin;


public class Entite {

	// abscisse et ordonne 
		private double xPos;
	    private double yPos;
	
	    /**
	     * Constructeur, on définit la position de l'entite
	     * @param xPos valeur de l'abscisse
	     * @param yPos valeur de l'ordonnée
	     */
	public Entite(double xPos, double yPos) {
		 this.xPos = xPos;
	     this.yPos = yPos;
		
	}
	
	/*** GETTER en abscisse ou ordonnee ***/
    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    /*** SETTER en abscisse ou ordonnee ***/
	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	
}
