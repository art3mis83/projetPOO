/*
 * jeu de bouton pour le HUD du menu
 * Compose que de getter et setter
 */
package projet_fin;

import org.newdawn.slick.gui.MouseOverArea;


public class setBouton {
	
	// bouton d'ajout et de retrait pour l equipe 1 avec le compteur associe
	private MouseOverArea add_unite1;
	private MouseOverArea delete_unite1;
	private int nb_unite1 = 0;
	
	// bouton d'ajout et de retrait pour l equipe 2 avec le compteur associe
	private MouseOverArea add_unite2;
	private MouseOverArea delete_unite2;
	private int nb_unite2 = 0;
	
	
	// Getter et Setter 
	public MouseOverArea getAdd_unite1() {
		return add_unite1;
	}
	public void setAdd_unite1(MouseOverArea add_unite1) {
		this.add_unite1 = add_unite1;
	}
	public MouseOverArea getDelete_unite1() {
		return delete_unite1;
	}
	public void setDelete_unite1(MouseOverArea delete_unite1) {
		this.delete_unite1 = delete_unite1;
	}
	public int getNb_unite1() {
		return nb_unite1;
	}
	public void setNb_unite1(int nb_unite1) {
		this.nb_unite1 = nb_unite1;
	}
	public MouseOverArea getAdd_unite2() {
		return add_unite2;
	}
	public void setAdd_unite2(MouseOverArea add_unite2) {
		this.add_unite2 = add_unite2;
	}
	public MouseOverArea getDelete_unite2() {
		return delete_unite2;
	}
	public void setDelete_unite2(MouseOverArea delete_unite2) {
		this.delete_unite2 = delete_unite2;
	}
	public int getNb_unite2() {
		return nb_unite2;
	}
	public void setNb_unite2(int nb_unite2) {
		this.nb_unite2 = nb_unite2;
	}

	
	
}
