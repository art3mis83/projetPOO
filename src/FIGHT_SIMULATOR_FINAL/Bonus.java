/**
 * Classe qui repertorie toutes les gemmes qui sont sur la carte
 * 
 */
package projet_fin;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Bonus {
	

	public int nb_bonus; // nombre de bonus sur la carte
	private List<Gemme> list = new ArrayList<>();
	
	// bornes maximal sur lesquels les bonus peuvent apparaitre
	private float xMax, yMax;


	
	/**
	 * On initialise chaque Gemme, c est a dire, on ajoute nb_bonus dans notre list
	 * et on les initialise. On definit les bornes max d apparition des gemmes en fonction
	 * des cartes
	 * @param map carte sur laquelle on se trouve, permet de définir les bornes max des apparitions des gemmes
	 */
	public void init(Map map) throws SlickException {
		
		switch(map.getNum_carte()) { // on définit les bornes max (les cartes ont des tailles différentes
			case Constant.Carte1:
				this.xMax = 1000;
				this.yMax = 750;
				break;
			case Constant.Carte2:
				this.xMax = 950;
				this.yMax = 750;
				break;
			case Constant.Carte3:
				this.xMax = 1750;
				this.yMax = 850;
				break;
		}
		
		for(int i = 0; i < this.nb_bonus; i++) {
			list.add(new Gemme(map)); // on ajoute une gemme
			list.get(i).init(xMax, yMax); // puis on l'initialise en precisant les bornes max
		}
		  
	}
	
	/**
	 * On affiche chaque gemme qui se trouve dans la liste
	 * @param g graphic sur lequel on doit afficher
	 */
	public void render(Graphics g) {
		  
		for(Gemme gemme : list) {
			gemme.render(g);
		}
		
	}
	
	public void update() {
		Iterator<Gemme> iterator = list.iterator(); // on place un iterateur afin de pouvoir interagir sur la liste directement
		while(iterator.hasNext()) { // on parcourt toute la liste
			Gemme g = iterator.next();
			
			// si on a "supprimer" une gemme, soit ses coordonnee sont (1000, 1000)
			// alors on la retire de la liste
			if (g.getX() == 1000 && g.getY() == 1000 ) { 
				 iterator.remove(); 
			}
		}	
	}

	/**
	 * Setter permettant de donner les bornes max
	 * @param la borne maximal en x ou en y
	 */
	public void setxMax(float xMax) {
		this.xMax = xMax;
	}

	public void setyMax(float yMax) {
		this.yMax = yMax;
	}
	
	/**
	 * Donne le nombre de bonus que l'on va avoir
	 * @param nb est le nombre de bonus voulu
	 */
	public void setNb_bonus(int nb) {
		this.nb_bonus = nb;
	}
	
	/**
	 * Getter donnant le nombre de bonus
	 * @return l'attribut nombre de bonus
	 */
	public int getNb_bonus() {
		return this.nb_bonus;
	}
	
	/**
	 * Getter donnant la liste des gemmes
	 * @return l'attribut liste de gemme
	 */
	public List<Gemme> getList(){
		return this.list;
	}
	
}
