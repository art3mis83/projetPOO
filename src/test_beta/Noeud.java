
package test_beta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class Noeud implements Comparable<Noeud>{

	private double x;
	private double y;
	private double cout;
	private double H;
	private Noeud pere;
	
	private static double c = 15;
	
	public Noeud(double x, double y, double cout, double H, Noeud pere) {
		this.x = x;
		this.y = y;
		this.cout = cout;
		this.H = H;
		this.pere = pere;
	}
	
	public double dist(Noeud n1, Noeud n2) {
		double dx = Math.abs(n1.x - n2.x);
        double dy = Math.abs(n1.y - n2.y);
        return dx + dy;
	}
	
	
	@Override
	public String toString() {
		return "Noeud [x=" + x + ", y=" + y + ", cout=" + cout + ", H=" + H + "]";
	}

	public Noeud cheminPlusCourt(Noeud objectif, Map map, int distance_attaque) {
		
		Noeud depart = new Noeud(this.x, this.y, 0.,0., null);
		
		List<Noeud> closeList = new ArrayList<>();
		List<Noeud> openList = new ArrayList<>();
		List<Noeud> voisin = new ArrayList<>();
		
		double distance;
		boolean tmp;
		openList.add(depart);
		
		while(openList.size() > 0) {
			Collections.sort(openList);
			Noeud u = openList.remove(0);
			
			
			distance = Math.abs(u.x - objectif.x) + Math.abs(u.y - objectif.y); 
			//tmp = noObstacle(map, u.x, u.y, objectif.x, objectif.y);
			tmp = true;
			if(distance < distance_attaque && tmp) {
				return get_racine(u);
				//return node_to_pass(closeList);
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
				
				
				if(v.est_pere(u)) {
					continue;
				}
				
				int indexO = index(openList, v);
				int indexC = index(closeList, v);
				
				if(indexO != -1 && openList.get(indexO).cout <= v.cout 
						|| indexC != -1 && closeList.get(indexC).cout <= v.cout) {
					continue;
				}else {
					if(indexO != -1)
						openList.remove(indexO);
					v.H = dist(v, objectif);
					openList.add(v);
				}
				
				
				
				
				
				
			}
			

			
		}
	
		return null;
	}

	public Noeud get_racine(Noeud u) {
		while(u.pere.pere != null) {
			u = u.pere;
		}
		return u;
	}

	/*public Noeud node_to_pass(List<Noeud> closeList) {
		int taille = closeList.size();
		Noeud n = closeList.get(taille - 1);
		
		while(n.pere != null) {
			
		}
		return null;
	}*/

	public boolean contient(List<Noeud> l, Noeud n) {
		for(Noeud t : l) {
			if(t.x == n.x && t.y == n.y) {
				return true;
			}
		}
		
		return false;
	}
	
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
	}

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
	
	@Override
	public int compareTo(Noeud n) {
		
		if(this.H > n.H) {
			return 1;
		}else if(this.H == n.H) {
			return 0;
		}else {
			return -1;
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	
	
}
