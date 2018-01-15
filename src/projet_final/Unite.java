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
	private long time_last_deplace = -1; // timer pour respecter le temps entre deux attaques
	
	
	public Unite(int e, Map map, int type) {
		super(0,0); // on initialise les unites a (0,0) (modifier dans initialisation MapGameState)
		equipe = e; // correspond a l equipe de l unite
		this.map = map;

	}


	// methode redefinit dans les unites specifiques pour charger les sprites
	public abstract void config_sprite() throws SlickException; 
	
	// initialisation de l unite
	public void init(int x, int y, double f, double g) throws SlickException { 
		this.setxPos(x);
		this.setyPos(y);
		this.cibleX = f;
		this.cibleY = g;
				
		config_sprite();
	}
	
	
	// fonction mise en final pour ne pas qu'elle soit redefini
	protected final Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
		Animation animation = new Animation();
		for (int x = startX; x < endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), 100);
		}
		return animation;
	}
	
	// fonction redefini dans les classes archer, piquier, chevalier
	public abstract void afficher_barre_vie(Graphics g);
	
	// methode d affichage de l unite
	public void render(Graphics g) {
		
		if(equipe == 1) { // on change la couleur de "l ombre" en fonction de l equipe
			g.setColor(new Color(0, 0, 0, .5f));
		}else {
			g.setColor(new Color(100, 100, 100, .5f));
		}
		
		g.fillOval((int) this.getX() - 16, (int) this.getY() - 8, 32, 16); // on dessine l ombre (permet de rajouter un efet 3D)
		
		if(!placement_fini) {
			g.drawAnimation(animations[direction], (int) this.getX() - 32, (int) this.getY() - 60);
		}
		else if(moving) {
			g.drawAnimation(animations[direction + (moving ? 4 : 0)], (int) this.getX() - 32, (int) this.getY() - 60);
		}else {
			g.drawAnimation(attaque[direction + (moving ? 0 : 4)], (int) this.getX() - 32, (int) this.getY() - 60);
		}	
		
	}


	// fonction permettant de realiser une treeMap des cibles potentielles
	public abstract TreeMap<Double, Entite> treeMap_spec(List<Unite> l_ennemi, List<Gemme> bonus);
	
	// on attribue une cible en fonction de la treeMap
	public void attribuer_cible(List<Unite> l_ennemi, List<Gemme> bonus) {
		
		TreeMap<Double, Entite> tmap = treeMap_spec(l_ennemi, bonus);
		
		for(Entite p : tmap.values()) {
			this.setCible(p);
			break;
		}
	}
	
	public void update(int delta) {
		if (this.moving) {
			double futurX = getFuturX(delta);
			double futurY = getFuturY(delta);
			boolean collision = this.map.isCollision(futurX, futurY);
			if (collision) {
				this.moving = false;
			} else {
				this.setxPos(futurX);
				this.setyPos(futurY);
			}
		}
	}
	

	public double distancebtw(double x, double y, double u, double v) {
		double dx = x - u;
        double dy = y - v;
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}
	
	
	public void deplacement(double deltaX, double deltaY, List<Unite> l_allie) {
		
		Noeud objectif = new Noeud(cibleX, cibleY, 1000., 1000., null);
		Noeud depart = new Noeud(this.getX(),this.getY(), 0.,0., null);
		Noeud noeud_proche = depart.cheminPlusCourt(objectif, this.map, distance_attaque, l_allie);
				
		cibleX = noeud_proche.getX();
		cibleY = noeud_proche.getY();
			
		deltaX = Math.abs(this.getX() - cibleX);
		deltaY = Math.abs(this.getY() - cibleY);
		moving = true;
		
		if(deltaX >= 1) {
			if(this.getX() > cibleX) {
				direction = 1;
			}else {
				direction = 3;
			}
		}else if(deltaY >= 1) {
			if(this.getY() > cibleY) {
				direction = 0;
			}else {
				direction = 2;
			}
		}		
	}
	
	public void play(List<Unite> l_allie) {
		
		double deltaX = Math.abs(this.getX() - cibleX);
		double deltaY = Math.abs(this.getY() - cibleY);
		
		double dist = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

		if(cible.getClass() == Gemme.class ) {
			if(dist > 25) {
				deplacement(deltaX, deltaY, l_allie);
			}else {
				this.setDegat(this.getDegat() + 3);
				((Gemme) cible).supprimer();
			}
			
		}else if(dist > distance_attaque + 1 ) {
			if( System.currentTimeMillis() - time_last_deplace > 100 ){
				deplacement(deltaX, deltaY, l_allie);
				time_last_deplace = System.currentTimeMillis();
			}
		}else {
			moving = false;
			attaque();
		}

	}

	public void attaque() {
		Unite u = (Unite) cible;
		if( System.currentTimeMillis() - time_last_attack > vitesse_attaque ){
			if(u.getVie() > getDegat()) {
				u.setVie(u.getVie() - getDegat());
			}else {
				u.setVie(0);
			}
			
			time_last_attack = System.currentTimeMillis();
		}
		/*double reussite = Math.random()*10;
		if(reussite < 6) {*/
			
		//}
		
	}
	


	public Double getDistanceTo(Entite p) {
		final double dx = getX() - p.getX();
        final double dy = getY() - p.getY();
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}

	public double getFuturX(int delta) {
		double futurX = getX();
		switch (getDirection()) {
		case 1:
			futurX = getX() - getVitesse() * delta;
			break;
		case 3:
			futurX = getX() + getVitesse() * delta;
			break;
		}
		return futurX;
	}
	
	
	private double getFuturY(int delta) {
		double futurY = getY();
		switch (getDirection()) {
		case 0:
			futurY = getY() - getVitesse() * delta;
			break;
		case 2:
			futurY = getY() + getVitesse() * delta;
			break;
		}
		return futurY;
	}

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
