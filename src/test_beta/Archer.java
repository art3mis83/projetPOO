/**
 * 
 */
package test_beta;

import lesson2.Constant;

public class Archer extends Unite {
	
	
	public Archer(int e, Map m) {
		super(e, m, Constant.ARCHER);
		setVie(7);
		setDistance_attaque(150);
		setDegat(0.5);
		setVitesse(0.02f);
		setVitesseAttaque((long) 500);
	}
	
	

	
}
