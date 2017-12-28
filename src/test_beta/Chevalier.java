/**
 * 
 */
package test_beta;


public class Chevalier extends Unite {
	
	
	public Chevalier(int equipe, Map m) {
		super(equipe, m, Constant.CHEVALIER);
		setVie(Constant.VIE_MAX_CHE);
		setDistance_attaque(30);
		setDegat(1);
		setVitesse(0.02f);
		setVitesseAttaque(1000);
	}
	
	

	
}
