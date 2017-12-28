
package test_beta;


import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Unite extends Entite{

	private int equipe;
	private double vie;
	private int distance_attaque;
	private double degat;
	private double vitesse;
	private long vitesse_attaque;
	
	public boolean placement_fini = false;
	private int type;
	private Map map;
	private double x, y;
	private double cibleX, cibleY;
	private Entite cible;
	private int direction = 2;
	private boolean moving = false;
	private Animation[] animations = new Animation[8];
	private Animation[] attaque = new Animation[8];
	
	private long time_last_attack = -1;
	
	
	public Unite(int e, Map map, int type) {
		super(0,0);
		equipe = e;
		this.map = map;
		this.type = type;
		
	}



	public void init(int x, int y, double f, double g) throws SlickException {
		
		this.x = x;
		this.y = y;
		this.cibleX = f;
		this.cibleY = g;
		
		switch(type) {
			case Constant.ARCHER:
				SpriteSheet spriteSheetArcher_Deplacement = new SpriteSheet("sprites/character.png", 64, 64);
				this.animations[0] = loadAnimation(spriteSheetArcher_Deplacement, 0, 1, 0);
				this.animations[1] = loadAnimation(spriteSheetArcher_Deplacement, 0, 1, 1);
				this.animations[2] = loadAnimation(spriteSheetArcher_Deplacement, 0, 1, 2);
				this.animations[3] = loadAnimation(spriteSheetArcher_Deplacement, 0, 1, 3);
				this.animations[4] = loadAnimation(spriteSheetArcher_Deplacement, 1, 9, 0);
				this.animations[5] = loadAnimation(spriteSheetArcher_Deplacement, 1, 9, 1);
				this.animations[6] = loadAnimation(spriteSheetArcher_Deplacement, 1, 9, 2);
				this.animations[7] = loadAnimation(spriteSheetArcher_Deplacement, 1, 9, 3);
				
				
				SpriteSheet spriteSheetArcher_Attaque = new SpriteSheet("sprites/archer.png", 64, 64);
				this.attaque[0] = loadAnimation(spriteSheetArcher_Attaque, 0, 1, 0);
				this.attaque[1] = loadAnimation(spriteSheetArcher_Attaque, 0, 1, 1);
				this.attaque[2] = loadAnimation(spriteSheetArcher_Attaque, 0, 1, 2);
				this.attaque[3] = loadAnimation(spriteSheetArcher_Attaque, 0, 1, 3);
				this.attaque[4] = loadAnimation(spriteSheetArcher_Attaque, 1, 13, 0);
				this.attaque[5] = loadAnimation(spriteSheetArcher_Attaque, 1, 13, 1);
				this.attaque[6] = loadAnimation(spriteSheetArcher_Attaque, 1, 13, 2);
				this.attaque[7] = loadAnimation(spriteSheetArcher_Attaque, 1, 13, 3);
				
				break;
			
			case Constant.CHEVALIER:
				SpriteSheet spriteSheetChevalier_Deplacement = new SpriteSheet("sprites/chevalier_deplacement.png", 64, 64);
				this.animations[0] = loadAnimation(spriteSheetChevalier_Deplacement, 0, 1, 0);
				this.animations[1] = loadAnimation(spriteSheetChevalier_Deplacement, 0, 1, 1);
				this.animations[2] = loadAnimation(spriteSheetChevalier_Deplacement, 0, 1, 2);
				this.animations[3] = loadAnimation(spriteSheetChevalier_Deplacement, 0, 1, 3);
				this.animations[4] = loadAnimation(spriteSheetChevalier_Deplacement, 1, 9, 0);
				this.animations[5] = loadAnimation(spriteSheetChevalier_Deplacement, 1, 9, 1);
				this.animations[6] = loadAnimation(spriteSheetChevalier_Deplacement, 1, 9, 2);
				this.animations[7] = loadAnimation(spriteSheetChevalier_Deplacement, 1, 9, 3);
				
				
				SpriteSheet spriteSheetA = new SpriteSheet("sprites/chevalier_attaque.png", 64, 64);
				this.attaque[0] = loadAnimation(spriteSheetA, 0, 1, 0);
				this.attaque[1] = loadAnimation(spriteSheetA, 0, 1, 1);
				this.attaque[2] = loadAnimation(spriteSheetA, 0, 1, 2);
				this.attaque[3] = loadAnimation(spriteSheetA, 0, 1, 3);
				this.attaque[4] = loadAnimation(spriteSheetA, 1, 5, 0);
				this.attaque[5] = loadAnimation(spriteSheetA, 1, 5, 1);
				this.attaque[6] = loadAnimation(spriteSheetA, 1, 5, 2);
				this.attaque[7] = loadAnimation(spriteSheetA, 1, 5, 3);
				
				break;
				
			case Constant.PIQUIER :
				
				SpriteSheet spriteSheetPiquier_Deplacement = new SpriteSheet("sprites/piquier_deplacement.png", 64, 64);
				this.animations[0] = loadAnimation(spriteSheetPiquier_Deplacement, 0, 1, 0);
				this.animations[1] = loadAnimation(spriteSheetPiquier_Deplacement, 0, 1, 1);
				this.animations[2] = loadAnimation(spriteSheetPiquier_Deplacement, 0, 1, 2);
				this.animations[3] = loadAnimation(spriteSheetPiquier_Deplacement, 0, 1, 3);
				this.animations[4] = loadAnimation(spriteSheetPiquier_Deplacement, 1, 9, 0);
				this.animations[5] = loadAnimation(spriteSheetPiquier_Deplacement, 1, 9, 1);
				this.animations[6] = loadAnimation(spriteSheetPiquier_Deplacement, 1, 9, 2);
				this.animations[7] = loadAnimation(spriteSheetPiquier_Deplacement, 1, 9, 3);
				
				
				SpriteSheet spriteSheetPiquier_Attaque = new SpriteSheet("sprites/piquier_attaque.png", 64, 64);
				this.attaque[0] = loadAnimation(spriteSheetPiquier_Attaque, 0, 1, 0);
				this.attaque[1] = loadAnimation(spriteSheetPiquier_Attaque, 0, 1, 1);
				this.attaque[2] = loadAnimation(spriteSheetPiquier_Attaque, 0, 1, 2);
				this.attaque[3] = loadAnimation(spriteSheetPiquier_Attaque, 0, 1, 3);
				this.attaque[4] = loadAnimation(spriteSheetPiquier_Attaque, 1, 8, 0);
				this.attaque[5] = loadAnimation(spriteSheetPiquier_Attaque, 1, 8, 1);
				this.attaque[6] = loadAnimation(spriteSheetPiquier_Attaque, 1, 8, 2);
				this.attaque[7] = loadAnimation(spriteSheetPiquier_Attaque, 1, 8, 3);
				
				break;
		}
		
		
		
	}
	
	
	
	private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
		Animation animation = new Animation();
		for (int x = startX; x < endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), 100);
		}
		return animation;
	}
	
	
	public void render(Graphics g) {
		
		if(equipe == 1) {
			g.setColor(new Color(0, 0, 0, .5f));
		}else {
			g.setColor(new Color(100, 100, 100, .5f));
		}
		
		g.fillOval((int) x - 16, (int) y - 8, 32, 16);
		
		g.setColor(new Color(0, 255, 0));
		
		switch(type) {
		case Constant.ARCHER :
			g.drawRect((float)x -5, (float)y - 60, (float)((20 * vie)/Constant.VIE_MAX_ARC), (float)5);
			break;
		case Constant.CHEVALIER :
			g.drawRect((float)x -5, (float)y - 60, (float)((20 * vie)/Constant.VIE_MAX_CHE), (float)5);
			break;
		case Constant.PIQUIER :
			g.drawRect((float)x -5, (float)y - 60, (float)((20 * vie)/Constant.VIE_MAX_PIQ), (float)5);
			break;
		}
		
		
		if(!placement_fini) {
			
			g.drawAnimation(animations[direction], (int) x - 32, (int) y - 60);
		}
		else if(moving) {
			g.drawAnimation(animations[direction + (moving ? 4 : 0)], (int) x - 32, (int) y - 60);
		}else {
			g.drawAnimation(attaque[direction + (moving ? 0 : 4)], (int) x - 32, (int) y - 60);
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
				this.x = futurX;
				this.y = futurY;
			}
		}
	}
	

	public double distancebtw(double x, double y, double u, double v) {
		double dx = x - u;
        double dy = y - v;
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}
	
	
	public void deplacement(double deltaX, double deltaY) {
		Noeud objectif = new Noeud(cibleX, cibleY, 1000., 1000., null);
		Noeud depart = new Noeud(x,y, 0.,0., null);
		Noeud noeud_proche = depart.cheminPlusCourt(objectif, this.map, distance_attaque);
		
		cibleX = noeud_proche.getX();
		cibleY = noeud_proche.getY();
		
		deltaX = Math.abs(x - cibleX);
		deltaY = Math.abs(y - cibleY);
		moving = true;
		
			if(deltaX >= 1) {
				if(x > cibleX) {
					direction = 1;
				}else {
					direction = 3;
				}
			}else if(deltaY >= 1) {
				if(y > cibleY) {
					direction = 0;
				}else {
					direction = 2;
				}
			}
	}
	
	public void play() {
		
		double deltaX = Math.abs(x - cibleX);
		double deltaY = Math.abs(y - cibleY);
		
		double dist = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

		if(cible.getClass() == Bonus.class ) {
			if(dist > 25) {
				deplacement(deltaX, deltaY);
			}else {
				this.setDegat(this.getDegat() * 3);
				((Bonus) cible).supprimer();
			}
			
		}else if(dist > distance_attaque + 1 ) {
			deplacement(deltaX, deltaY);
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
	
	
	
	
	public double getX() {
		return x;
	}


	public double getY() {
		return y;
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


	public void setX(double x) {
		this.x = x;
	}


	public void setY(double y) {
		this.y = y;
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
