/*
 * Classe qui herite de la classe entite et qui est mere de unite specifique (archer, chevalier, piquier)
 * ELle comporte toutes les methodes permettant de modifier de maniere generale les unites
 */
package projet_fin;


import java.util.List;
import java.util.TreeMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public abstract class Unite extends Entite{

	// attribut propre a une unite
	private int equipe;
	protected double vie;
	private int distance_attaque;
	private double degat, vitesse;
	private long vitesse_attaque;
	
	public boolean placement_fini = false; 
	private Map map; // carte sur laquelle l unite se deplace
	private Entite cible; // cible vers laquelle l unite doit aller
	private double cibleX, cibleY; // coordonne de la cible
	private int direction = 2; // par defaut, les unites nous font face
	private boolean moving = false; // par defaut les unites sont imobiles
	protected Animation[] animations = new Animation[8]; // regroupe les sprites pour les deplacements
	protected Animation[] attaque = new Animation[8]; // regroupe les sprites pour les animations d'attaque
	
	private long time_last_attack = -1; // timer pour respecter le temps entre deux attaques
	private long time_last_deplace = -1; // timer pour laisser le temps a une unite d'atteindre la position
	
	
	public Unite(int e, Map map, int type) {
		super(0,0); // on initialise les unites a (0,0) (modifier dans initialisation MapGameState)
		equipe = e; // correspond a l equipe de l unite
		this.map = map;

	}


	/**
	 *  Methode redefinit dans les classes filles qui permet de charger les sprites
	 */
	public abstract void config_sprite() throws SlickException; 
	
	/**
	 *  Initialisation de l unite
	 * @param x abscisse de l'unite
	 * @param y ordonnee de l'unite
	 * @param f abscisse de sa cible (par defaut 0)
	 * @param g ordonnee de sa cible (par defaut 0)
	 */
	public void init(int x, int y, double f, double g) throws SlickException { 
		this.setxPos(x);
		this.setyPos(y);
		this.cibleX = f;
		this.cibleY = g;
				
		// un fois la position definit, on configue les sprites
		config_sprite();
	}
	
	
	/**
	 * Fonction mise en final pour ne pas qu'elle soit redefini
	 * Elle permet de charger les sprites de l animation dans le bon ordre
	 * @param spriteSheet : feuille de sprites 
	 * @param startX : début du premier sprite
	 * @param endX : debut du dernier sprite
	 * @param y : ordonnee sur laquelle se trouve les sprites
	 * @return les animations
	 */
	protected final Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
		Animation animation = new Animation();
		for (int x = startX; x < endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), 100);
		}
		return animation;
	}
	
	/**
	 * Fonction redefini dans les classes filles (archer, piquier, chevalier)
	 * On dessine les barre de vie proportionnelement au point de vie de l unite
	 * @param g graphic sur lequel on dessine les barre de vie
	 */
	public abstract void afficher_barre_vie(Graphics g);
	
	/**
	 *  methode d affichage de l unite
	 * @param g graphic sur lequel on dessine l'unite
	 */
	public void render(Graphics g) {
		
		if(equipe == 1) { // on change la couleur de "l ombre" en fonction de l equipe
			g.setColor(new Color(0, 0, 0, .5f));
		}else {
			g.setColor(new Color(100, 100, 100, .5f));
		}
		
		g.fillOval((int) this.getX() - 16, (int) this.getY() - 8, 32, 16); // on dessine l ombre (permet de rajouter un efet 3D)
		
		if(!placement_fini) { // tant que le placement n'est pas fini, les unites sont faces à nous
			g.drawAnimation(animations[direction], (int) this.getX() - 32, (int) this.getY() - 60);
		}
		else if(moving) {
			// animation de deplacement
			g.drawAnimation(animations[direction + (moving ? 4 : 0)], (int) this.getX() - 32, (int) this.getY() - 60);
		}else {
			// animation de combat
			g.drawAnimation(attaque[direction + (moving ? 0 : 4)], (int) this.getX() - 32, (int) this.getY() - 60);
		}	
		
		afficher_barre_vie(g); // on affiche la barre de vie ensuite
		
	}


	/**
	 *  fonction permettant de realiser une treeMap des cibles potentielles
	 * @param l_ennemi : liste des ennemis
	 * @param bonus : liste des bonus
	 * @return : donne une treemap comportant les entites et leur distances
	 */
	public abstract TreeMap<Double, Entite> treeMap_spec(List<Unite> l_ennemi, List<Gemme> bonus);
	
	/**
	 * on attribue une cible en fonction de la treeMap genere
	 * @param l_ennemi : liste des ennemis
	 * @param bonus : liste des bonus
	 */
	public void attribuer_cible(List<Unite> l_ennemi, List<Gemme> bonus) {
		
		TreeMap<Double, Entite> tmap = treeMap_spec(l_ennemi, bonus);
		
		for(Entite p : tmap.values()) {
			this.setCible(p); // on recupere le premier element de la treemap (soit le plus proche)
			break;
		}
	}
	
	/**
	 * fonction permettant de calculer les nouvelles coordonnees de l'unite
	 * @param delta : periode de temps durant laquelle on n'a pas fait de mise
	 */
	public void update(int delta) {
		if (this.moving) { // si elle se deplace
			// on calcule ses nouvelles coordoonees
			double futurX = getFuturX(delta); 
			double futurY = getFuturY(delta);
			
			boolean collision = this.map.isCollision(futurX, futurY); // on verifie qu'il n'y a pas de collision
			if (collision) {
				this.moving = false; // si une collision risque d'arriver, on stop l'unite, elle trouvera un nouveau chemin prochainement
			} else {
				// on lui attribue de nouvelles coordonnees
				this.setxPos(futurX);
				this.setyPos(futurY);
			}
		}
	}
	

	/**
	 * 
	 * @param x premier abscisse
	 * @param y premier ordonnee
	 * @param u deuxieme abscisse
	 * @param v deuxieme ordonnee
	 * @return Distance euclidenne entre le couple (x,y) et (u,v)
	 */
	public double distancebtw(double x, double y, double u, double v) {
		double dx = x - u;
        double dy = y - v;
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}
	
	/**
	 * Realise le deplacement, c'est a dire, oriente l'unite et calcule un chemin
	 * @param deltaX
	 * @param deltaY
	 * @param l_allie
	 */
	public void deplacement(double deltaX, double deltaY, List<Unite> l_allie) {
		
		Noeud objectif = new Noeud(cibleX, cibleY, 1000., 1000., null);
		Noeud depart = new Noeud(this.getX(),this.getY(), 0.,0., null);
		Noeud noeud_proche;
		// on calcule un chemin jusqu'a la cible (unite ou gemme)
		// si c'est une unite, la distance d'attaque est la distance de recuperation d'un bonus
		if(cible.getClass() == Gemme.class) { 
			noeud_proche = depart.cheminPlusCourt(objectif, this.map, Constant.RECUP_GEMME, l_allie);
		}else {
			noeud_proche = depart.cheminPlusCourt(objectif, this.map, distance_attaque, l_allie);
		}
				
		// on definit les coordonnes de la cible
		cibleX = noeud_proche.getX();
		cibleY = noeud_proche.getY();
			
		// on calcule les delta en abscisse et en ordonnes
		deltaX = Math.abs(this.getX() - cibleX);
		deltaY = Math.abs(this.getY() - cibleY);
		moving = true;
		
		if(deltaX >= 1) { // on oriente pour s'aligner en X
			if(this.getX() > cibleX) {
				direction = 1;
			}else {
				direction = 3;
			}
		}else if(deltaY >= 1) { // si on est bon sur X, on oriente pour s'aligner en Y
			if(this.getY() > cibleY) {
				direction = 0;
			}else {
				direction = 2;
			}
		}		
	}
	
	/**
	 * Donne un ordre à l'unite en fonction de sa cible
	 * Si c'est un bonus, soit on se déplace dans la direction, soit on le recupere
	 * Idem si c'est un ennemi
	 * Un deplacement, donc une recherche de chemin est déclenché uniquement si ca fait plus de 0.5 seconde
	 * que nous n'avons pas mis à jour le chemin
	 * 
	 * @param l_allie : liste des alliés utilisé dans le calcul d'un chemin
	 */
	public void play(List<Unite> l_allie) {
		
		double deltaX = Math.abs(this.getX() - cibleX);
		double deltaY = Math.abs(this.getY() - cibleY);
		
		double dist = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		
		if(cible.getClass() == Gemme.class ) { // si c'est un bonus
			
			if(dist > Constant.RECUP_GEMME) { // si on est trop loin, on se déplace
				deplacement(deltaX, deltaY, l_allie);
			}else {
				this.setDegat(this.getDegat() + 3); // sinon on recupere et on augmente les dégats
				((Gemme) cible).supprimer();
			}
			
		}else if(dist > distance_attaque + 1 ) { // sinon c'sest un enemi
			if( System.currentTimeMillis() - time_last_deplace > 500 ){
				deplacement(deltaX, deltaY, l_allie); // on calcule le chemin si ca fait plus de 0.5s que la mise a jour a ete faite
				time_last_deplace = System.currentTimeMillis(); // on met a jour le timer
			}
		}else { // on est a distance pour attaquer
			moving = false;
			attaque();
		}

	}

	/**
	 * Fonction redefinit dans les classes filles
	 * @return vrai si l'unite touche son enemi, faux sinon
	 */
	public abstract boolean toucher();
	
	
	/**
	 * Une unité attaque seulement si le temps entre deux attaques est respecté
	 * et qu'il réussit a toucher son enemi
	 */
	public void attaque() {
		Unite u = (Unite) cible; // la cible est une unite
		if( System.currentTimeMillis() - time_last_attack > vitesse_attaque ){ // temps entre deux attaques
			if(toucher()) {  // chance de toucher
				if(u.getVie() > getDegat()) { // on enleve des points de vie à la cible
					u.setVie(u.getVie() - getDegat());
				}else {
					u.setVie(0);
				}
			}
			time_last_attack = System.currentTimeMillis(); // on met a jour le timer 
		}
	}
	

	/**
	 * Permet de calculer la distance euclidienne entre l'unite et l'entite p
	 * @param p : entite dont on veut on connaitre la distance
	 * @return la distance qui separe l'unite de p
	 */
	public Double getDistanceTo(Entite p) {
		final double dx = getX() - p.getX();
        final double dy = getY() - p.getY();
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}

	/**
	 * Calcule la nouvelle abscisse
	 * @param delta : temps écoulé depuis le dernier calcul
	 * @return le nouveau X
	 */
	public double getFuturX(int delta) {
		double futurX = getX();
		switch (getDirection()) {
		case 1: // deplacement a gauche
			futurX = getX() - getVitesse() * delta; // x = x0 - v*t
			break;
		case 3: // deplacement a droite
			futurX = getX() + getVitesse() * delta; // x = x0 + v*t
			break;
		}
		return futurX;
	}
	
	
	/**
	 * Calcule la nouvelle ordonnee
	 * @param delta : temps écoulé depuis le dernier calcul
	 * @return le nouveau Y
	 */
	private double getFuturY(int delta) {
		double futurY = getY();
		switch (getDirection()) {
		case 0: // deplacement vers le haut
			futurY = getY() - getVitesse() * delta; // y = y0 - v*t
			break;
		case 2: // deplacement vers le bas
			futurY = getY() + getVitesse() * delta; // y = y0 + v*t
			break;
		}
		return futurY;
	}

	
	/*** GETTER ***/
	public double getCibleX() {
		return cibleX;
	}

	public double getCibleY() {
		return cibleY;
	}

	public Entite getCible() {
		return cible;
	}

	public int getDirection() {
		return direction;
	}

	public boolean isMoving() {
		return moving;
	}
	
	public int getEquipe() {
		return equipe;
	}


	public double getVie() {
		return vie;
	}


	public int getDistance_attaque() {
		return distance_attaque;
	}


	public double getDegat() {
		return degat;
	}


	public double getVitesse() {
		return vitesse;
	}



	/*** SETTER ***/
	public void setDegat(double degat) {
		this.degat = degat;
	}

	public void setCibleX(double cibleX) {
		this.cibleX = cibleX;
	}


	public void setCibleY(double cibleY) {
		this.cibleY = cibleY;
	}


	public void setCible(Entite cible) {
		this.cible = cible;
		this.cibleX = cible.getX();
		this.cibleY = cible.getY();
	}


	public void setDirection(int direction) {
		this.direction = direction;
	}


	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public void setVitesseAttaque(long time) {
		this.vitesse_attaque = time;
	}

	

	public void setEquipe(int equipe) {
		this.equipe = equipe;
	}


	public void setVie(double vie) {
		this.vie = vie;
	}


	public void setDistance_attaque(int distance_attaque) {
		this.distance_attaque = distance_attaque;
	}


	public void setDegat(int degat) {
		this.degat = degat;
	}


	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}
	
		
	
}
