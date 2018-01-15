/*
 * fonction mettant en place le pathfinding grace a l algo A*
 */
package projet_fin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class Noeud implements Comparable<Noeud>{

	// un noeud est caracterise par ses coordonnes, son cout, la distance par rapport a la cible et son pere
	private double x;
	private double y;
	private double cout;
	private double H;
	private Noeud pere;
	
	private static double c = 15; // represente le pas
	
	// on cree un noeud
	public Noeud(double x, double y, double cout, double H, Noeud pere) {
		this.x = x;
		this.y = y;
		this.cout = cout;
		this.H = H;
		this.pere = pere;
	}
	
	// retourne la distance entre deux noeuds
	public double dist(Noeud n1, Noeud n2) {
		double dx = Math.abs(n1.x - n2.x);
        double dy = Math.abs(n1.y - n2.y);
        return dx + dy;
	}
	
	// retourne la distance entre deux noeuds
	public double distreelle(Noeud n1, Noeud n2) {
		double dx = Math.abs(n1.x - n2.x);
	    double dy = Math.abs(n1.y - n2.y);
	    return Math.sqrt(dx*dx + dy*dy);
	}
	
	// reecriture de l algorithme A*
	public Noeud cheminPlusCourt(Noeud objectif, Map map, int distance_attaque, List<Unite> l_allie) {
		
		// le noeud de depart est celui sur lequel on lance l algo
		Noeud depart = new Noeud(this.x, this.y, 0.,0., null);
		
		List<Noeud> listeNoeudAllie = new ArrayList<>();
		for(Unite u : l_allie) {
			listeNoeudAllie.add(new Noeud(u.getX(), u.getY(), 0, 0, null));
		}
		
		List<Noeud> closeList = new ArrayList<>();
		List<Noeud> openList = new ArrayList<>();
		List<Noeud> voisin = new ArrayList<>();
		
		double distance;
		openList.add(depart);
		
		while(openList.size() > 0) {
			Collections.sort(openList);
			Noeud u = openList.remove(0);
			
			
			distance = Math.abs(u.x - objectif.x) + Math.abs(u.y - objectif.y); 
			if(distance < distance_attaque) {
				return get_racine(u); // on renvoie le noeud le plus proche vers lequel on doit se diriger
			}
			
			closeList.add(u);
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

			for(Noeud v : voisin) {
					
				if(v.est_pere(u)) { // on ne prend pas le pere en compte pour ne pas boucler
					continue;
				}else if(allie(listeNoeudAllie, v)) {
					continue;
				}else {
					int indexO = index(openList, v); // index du noeud v dans la openlist
					int indexC = index(closeList, v); // index du noeud v dans la closelist
					
					// si on est deja passe par ce noeud on passe
					if(indexO != -1 && openList.get(indexO).cout <= v.cout 
							|| indexC != -1 && closeList.get(indexC).cout <= v.cout) {
						continue;
					}else {
						if(indexO != -1) {
							openList.remove(indexO); // on supprime le noeud qui est moins interessant
						}
						v.H = dist(v, objectif); // on met a jour la distance
						openList.add(v); // on ajoute le noeud dans notre liste
					}
				}				
			}		
		}
	
		return null;
	}

	// renvoie le premier ou second noeud apres le point de depart
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

	// dit si oui on non u est le pere du noeud sur lequel on lance la fonction
	public boolean est_pere(Noeud u) {
		Noeud n;
		
		if(u.pere == null) {
			return false;
		}else {
			n = u.pere;
		}
		
		if(n.x == this.x && n.y == this.y) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean allie(List<Noeud> l, Noeud n) {
		
		for(Noeud t : l) {
			if(distreelle(n,t) < 10) {
				return true;
			}
		}
		
		return false;
	}
	
	// retourne l index du noeud n dans la liste l
	// rentourne -1 si il n y est pas
	// on a du redefinir cette fonction qui existe deja car on voulait verifier les coordonnes
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

	// retourne le X du noeud
	public double getX() {
		return x;
	}
	
	// retourne le Y du noeud
	public double getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "Noeud [x=" + x + ", y=" + y + ", cout=" + cout + ", H=" + H + "]";
	}
	/*
	public boolean noObstacle(Map map, double x1, double y1, double x2, double y2) {
		double y, a, b, depart, fin, i;
		if(Math.abs(x1 - x2) < 1 ) {
			depart = (y1 < y2) ? y1 : y2;
			fin = (y1 < y2) ? y2 : y1;
			
			for(i = depart; i<= fin; i++) {
				if(map.isCollision(x1, i)) {
					return false;
				}
			}
		}else {
			a = (y2 - y1)/ (x2 - x1);
			b = y2 - 2*x2;
			
			depart = (x1 < x2) ? x1 : x2;
			fin = (x1 < x2) ? x2 : x1;
			
			for(i = depart; i<= fin; i++ ) {
				y = a*i + b;
				if(map.isCollision(i, y)) {
					System.out.println("Il y a un obstacle");
					return false;
				}
					
			}
		}
		
		
		return true;
	}*/

	
	
}
