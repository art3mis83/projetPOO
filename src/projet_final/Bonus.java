/**
 * Classe representant des bonnus, ils doublent les degats de l'unite qui collecte le boost
 * 
 * Un bonus est une entite
 */
package projet_fin;


import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Bonus {
	

	public int nb_bonus; // nombre de bonus sur la carte
	private List<Gemme> list = new ArrayList<>();
	
	// bornes entre lesquelles les bonus peuvent apparaitre
	private float xMax, yMax;


	
	/*
	 * On initialise chaque Gemme, c est a dire, on ajoute nb_bonus dans notre list
	 * et on les initialise. On definit les bornes max d apparition des gemmes en fonction
	 * des cartes
	 */
	public void init(Map map) throws SlickException {
		
		switch(map.getNum_carte()) {
			case Constant.Carte1:
				this.xMax = 950;
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
			list.add(new Gemme(map));
			list.get(i).init(xMax, yMax);			
		}
		  
	}
	
	/*
	 * On affiche le bonus
	 */
	public void render(Graphics g) {
		  
		for(Gemme gemme : list) {
			gemme.render(g);
		}
		
	}

	public void setxMax(float xMax) {
		this.xMax = xMax;
	}

	public void setyMax(float yMax) {
		this.yMax = yMax;
	}
	
	public void setNb_bonus(int nb) {
		this.nb_bonus = nb;
	}
	
	public int getNb_bonus() {
		return this.nb_bonus;
	}
	
	public List<Gemme> getList(){
		return this.list;
	}
	
}
