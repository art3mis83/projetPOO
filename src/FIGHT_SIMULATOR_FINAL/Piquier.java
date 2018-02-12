/**
 *  Cette classe est la représentation d'un archer
 *  Elle est fille de Unite
 */
package projet_fin;

import java.util.List;
import java.util.TreeMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Piquier extends Unite {
	
	
	/**
	 * Constructeur d'un piquier, on lui donne toutes ses caractéristiques
	 * @param e représente l'équipe
	 * @param m définit la map sur laquelle le personnage évolue
	 */
	public Piquier(int e, Map m) {
		super(e, m, Constant.PIQUIER);
		setVie(Constant.VIE_MAX_PIQ); // on définit la vie
		setDistance_attaque(Constant.DIST_PIQ); // la distance à laquelle il peut attaquer
		setDegat(Constant.ATQ_PIQ); // les dégats qu'il va causer
		setVitesse(Constant.VITESSE_DEF); // vitesse de déplacement (soit la vitesse par défaut)
		setVitesseAttaque(Constant.VIT_ATQ_PIQ); // la période de temps entre deux attaques
	}

	/**
	 * Fonction herite de la classe Unite, on charge les sprites associes a l'archer
	 */
	public void config_sprite() throws SlickException {
		
		SpriteSheet spriteSheetPiquier_Deplacement = new SpriteSheet("sprites/piquier_deplacement.png", 64, 64); // on recupere la feuille des sprites
		// sprite pour les deplacements
		this.animations[0] = loadAnimation(spriteSheetPiquier_Deplacement, 0, 1, 0);
		this.animations[1] = loadAnimation(spriteSheetPiquier_Deplacement, 0, 1, 1);
		this.animations[2] = loadAnimation(spriteSheetPiquier_Deplacement, 0, 1, 2);
		this.animations[3] = loadAnimation(spriteSheetPiquier_Deplacement, 0, 1, 3);
		this.animations[4] = loadAnimation(spriteSheetPiquier_Deplacement, 1, 9, 0);
		this.animations[5] = loadAnimation(spriteSheetPiquier_Deplacement, 1, 9, 1);
		this.animations[6] = loadAnimation(spriteSheetPiquier_Deplacement, 1, 9, 2);
		this.animations[7] = loadAnimation(spriteSheetPiquier_Deplacement, 1, 9, 3);
		
		
		SpriteSheet spriteSheetPiquier_Attaque = new SpriteSheet("sprites/piquier_attaque.png", 64, 64);// on recupere la feuille des sprites
		// sprite pour les attaques
		this.attaque[0] = loadAnimation(spriteSheetPiquier_Attaque, 0, 1, 0);
		this.attaque[1] = loadAnimation(spriteSheetPiquier_Attaque, 0, 1, 1);
		this.attaque[2] = loadAnimation(spriteSheetPiquier_Attaque, 0, 1, 2);
		this.attaque[3] = loadAnimation(spriteSheetPiquier_Attaque, 0, 1, 3);
		this.attaque[4] = loadAnimation(spriteSheetPiquier_Attaque, 1, 8, 0);
		this.attaque[5] = loadAnimation(spriteSheetPiquier_Attaque, 1, 8, 1);
		this.attaque[6] = loadAnimation(spriteSheetPiquier_Attaque, 1, 8, 2);
		this.attaque[7] = loadAnimation(spriteSheetPiquier_Attaque, 1, 8, 3);
		
	}

	/**
	 * on affiche un rectangle vert correspondant à la vie de l'unite (herite de Unite)
	 *  la taille par défaut est 20px, et elle diminue en fonction des degat subis
	 */
	public void afficher_barre_vie(Graphics g) {
		g.setColor(new Color(0, 255, 0)); 
		g.fillRect((float)this.getX() -5, (float)this.getY() - 60, (float)((20 * vie)/Constant.VIE_MAX_PIQ), (float)5);
		
	}

	/**
	 * on dresse une treeMap en fonction des distances de chaque entite de la carte et de ce piquier
	 * @param l_ennemi représente la liste des unites ennemie
	 * @param bonus représente la liste des bonus sur la carte
	 */
	public TreeMap<Double, Entite> treeMap_spec(List<Unite> l_ennemi, List<Gemme> bonus) {
		
		TreeMap<Double, Entite> tmap = new TreeMap<>();
		for(Unite p : l_ennemi)
			tmap.put(this.getDistanceTo(p), p);
		
		for(Gemme b : bonus) {
			tmap.put(this.getDistanceTo(b), b);
		}
		return tmap;
		
		
	}

	/**
	 * fonction qui permet de savoir si le piquier va toucher sa cible
	 * Constant.CHANCE_PIQ vaut 5 : il a donc 50% de chance de toucher
	 */
	public boolean toucher() {
		if(Math.random()*10 > Constant.CHANCE_PIQ)
			return true;
		else
			return false;
	}
	
	

	
}
