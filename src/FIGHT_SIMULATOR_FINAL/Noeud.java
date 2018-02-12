/**
 * Classe mettant en place le pathfinding grace a l algo A*
 */
package projet_fin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Noeud /*implements Comparable<Noeud>*/{

	// un noeud est caracterise par ses coordonnes, son cout, la distance par rapport a la cible et son pere
	private double x;
	private double y;
	private double cout;
	private double H;
	private Noeud pere;
	private double time; // on rajoute un timer pour éviter de tomber dans une boucle infinie
	
	private static double c = 15; // represente le pas
	
	/**
	 * on cree un noeud
	 * @param x abscisse
	 * @param y ordonnee
	 * @param cout nombre de saut depuis l'origine
	 * @param H distance qu'il reste a parcourir
	 * @param pere noeud précédent
	 */
	public Noeud(double x, double y, double cout, double H, Noeud pere) {
		this.x = x;
		this.y = y;
		this.cout = cout;
		this.H = H;
		this.pere = pere;
	}
	
	/**
	 *  Calcul la distance de manathan entre 2 noeuds
	 * @param n1 noeud 1
	 * @param n2 noeud 2
	 * @return distance entre les noeuds
	 */
	public double distManathan(Noeud n1, Noeud n2) {
		double dx = Math.abs(n1.x - n2.x);
        double dy = Math.abs(n1.y - n2.y);
        return dx + dy;
	}
	
	/**
	 * retourne la distance euclidenne entre deux noeuds
	 * @param n1 noeud 1
	 * @param n2 noeud 2
	 * @return distance entre les noeuds
	 */
	public double distreelle(Noeud n1, Noeud n2) {
		double dx = Math.abs(n1.x - n2.x);
	    double dy = Math.abs(n1.y - n2.y);
	    return Math.sqrt(dx*dx + dy*dy);
	}
	
	
	/**
	 * On applique l algorithme A* afin de trouver un chemin
	 * @param objectif : noeud que l'on veut atteindre
	 * @param map : carte sur laquelle on est (permet de calculer les collisions)
	 * @param distance_attaque : la distance d'attaque de l'unite qui cherche son chemin
	 * @param l_allie : liste des allies
	 * @return le noeud le plus proche a atteindre sur le chemin jusqu'à l objectif
	 */
	public Noeud cheminPlusCourt(Noeud objectif, Map map, int distance_attaque, List<Unite> l_allie) {
		
		// le noeud de depart est celui sur lequel on lance l algo
		Noeud depart = new Noeud(this.x, this.y, 0.,1000., null);
		
		List<Noeud> listeNoeudAllie = new ArrayList<>();
		for(Unite u : l_allie) { // on ajoute les unités alliees imobile afin de les eviter
			if(!u.isMoving()) {
				listeNoeudAllie.add(new Noeud(u.getX(), u.getY(), 0, 0, null));
			}
			
		}
		
		List<Noeud> closeList = new ArrayList<>();
		List<Noeud> openList = new ArrayList<>();
		List<Noeud> voisin = new ArrayList<>();
		
		openList.add(depart);
		double distance;
		time = System.currentTimeMillis();
		// tant que l'on a des noeuds a traiter 
		// ou bien que l'on a pas exceder 30 millisecondes de recherche
		while(openList.size() > 0 && System.currentTimeMillis() - time < 30) { 
			
			
			Noeud u = get_distMin(openList); // on recupere le noeud le plus proche de notre arrive
			openList.remove(u);
			
			distance = Math.abs(u.x - objectif.x) + Math.abs(u.y - objectif.y); 
			if(distance < distance_attaque) { // si on est a porte d'attaque
				return get_racine(u); // on renvoie le noeud le plus proche vers lequel on doit se diriger
			}
			
			closeList.add(u); // on ajoute le noeud au chemin
			
			// on recupere les voisins possible du noeud 
			voisin.clear();
			
			if(!map.isCollision(u.x+c, u.y)) {
				voisin.add(new Noeud(u.x + c, u.y, u.cout+1, 0., u));
			}
			
			if(!map.isCollision(u.x-c, u.y)) {
				voisin.add(new Noeud(u.x - c, u.y, u.cout+1, 0., u));
			}
			
			if(!map.isCollision(u.x, u.y+c)) {
				voisin.add(new Noeud(u.x, u.y + c, u.cout+1, 0., u));
			}
			
			if(!map.isCollision(u.x, u.y-c)) {
				voisin.add(new Noeud(u.x, u.y - c, u.cout+1, 0, u));
			}

			// pour chaque voisin, on regarde s'ils sont interessants à suivre
			for(Noeud v : voisin) {
					
				if(v.est_pere(u)) { // on ne prend pas le pere en compte pour ne pas boucler
					continue;
				}else if(allie(listeNoeudAllie, v)) {// si on a un allie sur cette position on ne la prend pas en compte
					continue;
				}else {
					int indexO = index(openList, v); // index du noeud v dans la openlist
					int indexC = index(closeList, v); // index du noeud v dans la closelist
					
					// si le noeud correspond a un chemin plus court on passe
					if(indexO != -1 && openList.get(indexO).cout <= v.cout 
							|| indexC != -1 && closeList.get(indexC).cout <= v.cout) {
						continue;
					}else {// sinon on l'ajoute dans notre liste de noeud a traiter
						if(indexO != -1) {
							openList.remove(indexO); // on supprime le noeud qui est moins interessant
						}
						v.H = distManathan(v, objectif); // on met a jour la distance
						openList.add(v); // on ajoute le noeud dans notre liste
					}
				}				
			}		
		}
		return get_racine(get_distMin(closeList)); // on arrive ici si on a depasse les 30 ms
	}

	/**
	 * Donne le noeud le plus proche de l'objectif
	 * @param l : liste de noeud à traiter
	 * @return le noeud le plus proche
	 */
	public Noeud get_distMin(List<Noeud> l) {
		double min = 10000;
		Noeud petit = null;
		for(Noeud n : l) {
			if(n.H < min) {
				petit = n;
				min = n.H;
			}
		}
		
		return petit;
	}
	
	
	/**
	 * Donne le noeud sur lequel on doit se rendre pour atteindre la cible
	 * @param u : on part de ce noeud et on remonte jusqu'au point de depart
	 * @return le noeud sur lequel l'unite doit aller
	 */
	public Noeud get_racine(Noeud u) {
		if(u.pere != null) {
			while(u.pere.pere != null) {
				u = u.pere;
			}
			return u;
		}else {
			return u;
		}
		
	}

	/**
	 * Permet de savoir si u est le fils du noeud sur lequel on lance la fonction 
	 * @param u
	 * @return
	 */
	public boolean est_pere(Noeud u) {
		Noeud n;
		
		if(u.pere == null) {
			return false; // si u n'a pas de pere, on ne peut pas etre son pere
		}else {
			n = u.pere;
		}
		
		if(n.x == this.x && n.y == this.y) { // si on a les meme coordonnee que le pere de u, alors on est son pere
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Verifie qu'il n'y a pas d allie dans un certain perimetre pour un noeud donné
	 * Si on a un allie, on ne pourra pas prendre ce noeud en compte dans notre chemin
	 * @param l : liste des positions des allies (à l arret) de notre unite
	 * @param n : noeud à l'étude
	 * @return vrai si un allie est dans le perimetre, faux sinon
	 */
	public boolean allie(List<Noeud> l, Noeud n) {
		
		for(Noeud t : l) {
			if(distreelle(n,t) < 10) { // si un allie et le noeud a l'étude sont trop proche on renvoie vrai 
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 *  Permet d'obtenir le rang d'un noeud n dans une liste l 
	 *  on a du redefinir cette fonction qui existe deja car on voulait verifier les coordonnes
	 * @param l : liste dans lequel on cherche le noeud
	 * @param n : le noeud à l'étude
	 * @return l'indice du rang de n dans l
	 * 			-1 s'il n'y est pas
	 */
	public int index(List<Noeud> l, Noeud n) {

		Noeud t;
		Iterator<Noeud> iterator = l.iterator();
		while(iterator.hasNext()) {
			t = iterator.next();
			if(t.x == n.x && t.y == n.y) { // le noeud n est deja present 
					return l.indexOf(t);
			}
		}
		
		return -1;
	}
	
	/*
	// on definit compareTo pour le tri les listes
		public int compareTo(Noeud n) {
			
			if(this.H > n.H) {
				return 1;
			}else if(this.H == n.H) {
				return 0;
			}else {
				return -1;
			}
		}
*/
	
	
	/**
	 * retourne le X du noeud
	 */
	public double getX() {
		return x;
	}
	
	/**
	 *  retourne le Y du noeud
	 */
	public double getY() {
		return y;
	}
	
	
	
}
