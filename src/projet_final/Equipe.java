/*
 * Une equipe est compose d'une liste avec toutes les unites :
 * soit un certains nombres de chevalier(s), de piquier(s) et d'archer(s)
 */

package projet_fin;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;


public class Equipe {
	
	private List<Unite> l = new ArrayList<>(); // liste des unites
	private int nb_chevalier = 0; 
	private int nb_archer = 0;
	private int nb_piquier = 0;
	private int num_equipe;
	
	public void init(Map map, int equipe) throws SlickException {
		int i, yChe, yPiq, yArc;

		if(equipe == Constant.EQUIPE1) {
			num_equipe = equipe;
			yChe = 450;
			yPiq = 500;
			yArc = 550;
		}else {
			num_equipe = equipe;
			yChe = 200;
			yPiq = 150;
			yArc = 100;
		}
		for(i = 0; i<this.getNb_chevalier(); i++) { // on instancie le bon nombre de chevalier dans l equipe 1
			this.getL().add(new Chevalier(num_equipe, map));
			this.getL().get(i).init(200 + i*40, yChe, 0, 0);
		}
		
		for(i = 0; i<this.getNb_archer(); i++) { // on instancie le bon nombre d archer dans l equipe 1
			this.getL().add(new Archer(num_equipe, map));
			this.getL().get(i+ this.getNb_chevalier()).init(200 + i*40, yArc, 0, 0);
		}

		for(i = 0; i<this.getNb_piquier(); i++) { // on instancie le bon nombre de piquier dans l equipe 1
			this.getL().add(new Piquier(num_equipe, map));
			this.getL().get(i+ this.getNb_chevalier() + this.getNb_archer()).init(200 + i*40, yPiq, 0, 0);
		}
	}
	
	// modifie le nombre de chevalier
	public void setNb_chevalier(int nb_chevalier) {
		this.nb_chevalier = nb_chevalier;
	}
	
	// modifie le nombre d'archer
	public void setNb_archer(int nb_archer) {
		this.nb_archer = nb_archer;
	}
	
	// modifie le nombre de piquier
	public void setNb_piquier(int nb_piquier) {
		this.nb_piquier = nb_piquier;
	}
	
	// attribue une nouvelle liste d'unite
	public void setL(List<Unite> liste) {
		l = liste;
	}
	
	/*
	 * Differents getter
	 */
	public int getNb_chevalier() {
		return nb_chevalier;
	}
	public int getNb_archer() {
		return nb_archer;
	}
	public int getNb_piquier() {
		return nb_piquier;
	}
	public List<Unite> getL() {
		return l;
	}
	
	
	
	
	

}
