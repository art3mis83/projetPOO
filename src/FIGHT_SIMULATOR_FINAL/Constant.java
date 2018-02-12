/**
 *  Classe repertoriant les constantes
 */
package projet_fin;


public class Constant {

	// TYPE DES UNITES + ATTRIBUTS
	
			/*** CHEVALIER ***/
	public static final int CHEVALIER = 1; // indice représentant le type d'unité
	
	public static final double VIE_MAX_CHE = 10; // vie par défaut
	
	public static final double CHANCE_CHE = 4; // chance (sur 10) de manquer une cible
	
	public static final double ATQ_CHE = 1; // dégat
	
	public static final int DIST_CHE = 30; // distance d'attaque
	
	public static final long VIT_ATQ_CHE = 1000; // temps entre deux attaques
	
	
			/*** PIQUIER ***/
	public static final int PIQUIER = 3;// indice représentant le type d'unité
	
	public static final double VIE_MAX_PIQ = 8; // vie par défaut
	
	public static final double CHANCE_PIQ = 5; // chance (sur 10) de manquer une cible
	
	public static final double ATQ_PIQ = 0.8; // dégat
	
	public static final int DIST_PIQ = 50; // distance d'attaque
	
	public static final long VIT_ATQ_PIQ = 700; // temps entre deux attaques
	
	
			/*** ARCHER ***/
	public static final int ARCHER = 2; // indice représentant le type d'unité
	
	public static final double VIE_MAX_ARC = 7; // vie par défaut
	
	public static final double CHANCE_ARC = 6; // chance (sur 10) de manquer une cible
	
	public static final double ATQ_ARC = 0.5; // dégat
	
	public static final int DIST_ARC = 150; // distance d'attaque
	
	public static final long VIT_ATQ_ARC = 500; // temps entre deux attaques
	
	
	public static final int RECUP_GEMME = 20; // distance pour recuperer un bonus
	
	
	// VITESSE PAR DEFAUT
	public static final double VITESSE_DEF = 0.02f;
	
	
	// NUMERO EQUIPE 
	public static final int EQUIPE1 = 1;
	
	public static final int EQUIPE2 = 2;
	
	
	// ORIGINE CAMERA + ORIENTATION
	public static final float XOrigine = 500;
	
	public static final float YOrigine = 350;
	
	public static final int HAUT = 1;
	
	public static final int BAS = 3;
	
	public static final int GAUCHE = 2;
	
	public static final int DROITE = 4;
	
	
	// INDICE DES CARTES 
	public static final int Carte1 = 0;
	
	public static final int Carte2 = 1;
	
	public static final int Carte3 = 2;
	

}
