/**
 * Classe qui represent le selecteur d'unite qui apparait lorsque l'on maintient le clic de la souris
 * On dessine un rectangle pour symboliser la zone cliquée
 **/

package projet_fin;

import java.util.ArrayList;
import java.util.List;


import org.newdawn.slick.geom.Rectangle;


public class Mouse{
	
	private double XMouse, YMouse; // coordonne du rectangle
	private List<Unite> l = new ArrayList<>(); // liste des unites dans le rectangle
	private boolean selection = false; // de base on a selectionne aucune unté
	private Rectangle rec = new Rectangle(1000,1000,0,0); // on place le retangle a l'autre bout de la carte
	private boolean premsel = false; // de base, il n'y a pas de selection
	
	 
	
	/**
	 * Lorsque la souris est relaché, on regarde si le rectangle dessine contient des unites
	 * @param e1 équipe 1
	 * @param e2 équipe 2
	 */
	public void mouseReleased(Equipe e1, Equipe e2) {
		if(premsel && !selection) { // si c'est notre premiere selection et que le rectangle n'est pas déja dessine
				
			for(Unite u : e1.getL()) {
				if(rec.contains((float) u.getX(),(float) u.getY())) {
					l.add(u);
					selection = true;
				}
			}
			for(Unite u : e2.getL()) {
				if(rec.contains((float) u.getX(),(float) u.getY())) {
					l.add(u);
					selection = true;
				}
			}
				rec.setSize(0, 0); // on "supprime" le rectangle
		}
		
	}
	
	/**
	 * Lorsque l'on deplace la souris on met à jour le rectangle
	 * @param newx
	 * @param newy
	 */
	public void mouseDragged( int newx, int newy) {
				
		rec = new Rectangle((float) XMouse, (float) YMouse, (float) (newx - XMouse ), (float) (newy - YMouse));
		
	}
	
	/**
	 * On sauvegarde les coordonnées du début du rectangle et on déclare que la selection est active
	 * @param x abscisse du debut du rectangle
	 * @param y ordonne du debut du rectangle
	 */
	public void mousePressed(int x, int y) {
		XMouse = x;
		YMouse = y;
		premsel = true;
		
	}

	
	/**
	 * Si des unités ont été selectionnées, lors d'un clic, on place les unités
	 * @param x abscisse du clic
	 * @param y ordonnee du clic
	 */
	public void mouseClicked(int x, int y) {
		if(selection ) { // si on a bien selectionne des unites
			int ligne = 0, colonne = 0;
			for(int i = 0; i< l.size(); i++) { // on place les unités en formation de 6 de large
				l.get(i).setxPos(x + 50*colonne);
				l.get(i).setyPos(y + 50*ligne);
				colonne++;
				if(i%6 == 5) {
					colonne = 0;
					ligne++;
				}
			}
			l.clear(); // on efface le contenu de la liste des unite selectionne
			selection = false; // plus aucune unité est selectionnee, donc on repasse a faux
			rec.setSize(0, 0); // on "supprime" le rectangle
			
		}
		
	}

	/**
	 * Getter qui permet d'avoir le rectangle
	 * @return le rectangle de selection
	 */
	public Rectangle getRec() {
		return rec;
	}

	/**
	 * On definit les coordoonées du rectangle en haut de la carte
	 * Sert juste pour atribuer une valeur a ces attributs
	 */
	public void init() {
		this.XMouse = 0;
		this.YMouse = 0;
	}

	
}
