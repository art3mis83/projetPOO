/**
 *  Classe la plus haute, elle est extend par entite
 *  Elle represente un couple de coordonnees
 */
package projet_fin;


public class Position{
	
	// abscisse et ordonne
	private double xPos;
    private double yPos;

    
    public Position(final double xPos, final double yPos) {
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
