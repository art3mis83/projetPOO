/**
 * 
 */
package test_beta;


public class Piquier extends Unite {
	
	
	public Piquier(int e, Map m) {
		super(e, m, Constant.PIQUIER);
		setVie(Constant.VIE_MAX_PIQ);
		setDistance_attaque(50);
		setDegat(0.8);
		setVitesse(Constant.VITESSE_DEF);
		setVitesseAttaque((long) 700);
	}
	
	

	
}
