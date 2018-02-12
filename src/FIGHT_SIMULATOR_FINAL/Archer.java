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


public class Archer extends Unite {
	
	/**
	 * Constructeur d'un archer, on lui donne toutes ses caractéristiques
	 * @param e représente l'équipe
	 * @param m définit la map sur laquelle le personnage évolue
	 */
	public Archer(int e, Map m) {
		super(e, m, Constant.ARCHER);
		setVie(Constant.VIE_MAX_ARC); // on définit la vie
		setDistance_attaque(Constant.DIST_ARC); // la distance à laquelle il peut attaquer
		setDegat(Constant.ATQ_ARC); // les dégats qu'il va causer
		setVitesse(Constant.VITESSE_DEF); // vitesse de déplacement (soit la vitesse par défaut)
		setVitesseAttaque(Constant.VIT_ATQ_ARC); // la période de temps entre deux attaques
	}


	/**
	 * Fonction herite de la classe Unite, on charge les sprites associes a l'archer
	 */
	public void config_sprite() throws SlickException {
		
		SpriteSheet spriteSheetArcher_Deplacement = new SpriteSheet("sprites/character.png", 64, 64); // on recupere la feuille des sprites
		// sprite pour les deplacements
		this.animations[0] = loadAnimation(spriteSheetArcher_Deplacement, 0, 1, 0);
		this.animations[1] = loadAnimation(spriteSheetArcher_Deplacement, 0, 1, 1);
		this.animations[2] = loadAnimation(spriteSheetArcher_Deplacement, 0, 1, 2);
		this.animations[3] = loadAnimation(spriteSheetArcher_Deplacement, 0, 1, 3);
		this.animations[4] = loadAnimation(spriteSheetArcher_Deplacement, 1, 9, 0);
		this.animations[5] = loadAnimation(spriteSheetArcher_Deplacement, 1, 9, 1);
		this.animations[6] = loadAnimation(spriteSheetArcher_Deplacement, 1, 9, 2);
		this.animations[7] = loadAnimation(spriteSheetArcher_Deplacement, 1, 9, 3);
		
		
		SpriteSheet spriteSheetArcher_Attaque = new SpriteSheet("sprites/archer.png", 64, 64);
		// sprite pour les attaques
		this.attaque[0] = loadAnimation(spriteSheetArcher_Attaque, 0, 1, 0);
		this.attaque[1] = loadAnimation(spriteSheetArcher_Attaque, 0, 1, 1);
		this.attaque[2] = loadAnimation(spriteSheetArcher_Attaque, 0, 1, 2);
		this.attaque[3] = loadAnimation(spriteSheetArcher_Attaque, 0, 1, 3);
		this.attaque[4] = loadAnimation(spriteSheetArcher_Attaque, 1, 13, 0);
		this.attaque[5] = loadAnimation(spriteSheetArcher_Attaque, 1, 13, 1);
		this.attaque[6] = loadAnimation(spriteSheetArcher_Attaque, 1, 13, 2);
		this.attaque[7] = loadAnimation(spriteSheetArcher_Attaque, 1, 13, 3);
		
	}


	/**
	 * on affiche un rectangle vert correspondant à la vie de l'unite (herite de Unite)
	 *  la taille par défaut est 20px, et elle diminue en fonction des degat subis
	 */
	public void afficher_barre_vie(Graphics g) {
		g.setColor(new Color(0, 255, 0)); 
		g.fillRect((float)this.getX() -5, (float)this.getY() - 60, (float)((20 * vie)/Constant.VIE_MAX_ARC), (float)5);
	}


	/**
	 * on dresse une treeMap en fonction des distances de chaque entite de la carte et de cet archer
	 * @param l_ennemi représente la liste des unites ennemie
	 * @param bonus représente la liste des bonus sur la carte
	 */
	public TreeMap<Double, Entite> treeMap_spec(List<Unite> l_ennemi, List<Gemme> bonus) {
		
		TreeMap<Double, Entite> tmap = new TreeMap<>();
		for(Unite p : l_ennemi)// on ajoute dans la treemap tous les ennemis
			tmap.put(this.getDistanceTo(p), p);
		
		for(Gemme b : bonus) { // on ajoute dans la treemap tous les bonus disponibles
			tmap.put(this.getDistanceTo(b), b);
		}
		return tmap;
	}


	/**
	 * fonction qui permet de savoir si l'archer va toucher sa cible
	 * Constant.CHANCE_ARC vaut 6 : il a donc 40% de chance de toucher
	 */
	public boolean toucher() {
		if(Math.random()*10 > Constant.CHANCE_ARC)
			return true;
		else
			return false;
	}

	
	

	
}
