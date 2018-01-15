/**
 * Classe mere de Unite et Bonus
 * Herite de position
 */
package projet_fin;


public class Entite {

	// abscisse et ordonne
		private double xPos;
	    private double yPos;
	
	public Entite(double xPos, double yPos) {
		 this.xPos = xPos;
	     this.yPos = yPos;
		
	}
	
	// Getter
    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    // Setter
	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	
}
