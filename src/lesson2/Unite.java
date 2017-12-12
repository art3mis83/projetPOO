/**
 * 
 */
package lesson2;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 * 
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y-
 */
public class Unite {

	private int equipe;
	private int vie;
	private int distance_attaque;
	private int degat;
	private float vitesse;
	
	
	public Unite(int e, int v) {
		equipe = e;
		vie = v;
		distance_attaque = 30;
		degat = 1;
		vitesse = 0.05f;
	}


	public int getEquipe() {
		return equipe;
	}


	public int getVie() {
		return vie;
	}


	public int getDistance_attaque() {
		return distance_attaque;
	}


	public int getDegat() {
		return degat;
	}


	public float getVitesse() {
		return vitesse;
	}


	public void setEquipe(int equipe) {
		this.equipe = equipe;
	}


	public void setVie(int vie) {
		this.vie = vie;
	}


	public void setDistance_attaque(int distance_attaque) {
		this.distance_attaque = distance_attaque;
	}


	public void setDegat(int degat) {
		this.degat = degat;
	}


	public void setVitesse(float vitesse) {
		this.vitesse = vitesse;
	}
	
	
	
	
}
