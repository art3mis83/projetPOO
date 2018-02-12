/**
 * Cette classe est la représentation d'un chevalier
 *  Elle est fille de Unite
 */
package projet_fin;

import java.util.List;
import java.util.TreeMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Chevalier extends Unite {
	
	/**
	 * Constructeur d'un chevalier, on lui donne toutes ses caractéristiques
	 * @param e représente l'équipe
	 * @param m définit la map sur laquelle le personnage évolue
	 */
	public Chevalier(int equipe, Map m) {
		super(equipe, m, Constant.CHEVALIER);
		setVie(Constant.VIE_MAX_CHE);
		setDistance_attaque(Constant.DIST_CHE);
		setDegat(Constant.ATQ_CHE);
		setVitesse(Constant.VITESSE_DEF);
		setVitesseAttaque(Constant.VIT_ATQ_CHE);
	}

	
	/**
	 * Fonction herite de la classe Unite, on charge les sprites associes au chevalier
	 */
	public void config_sprite() throws SlickException {
		
		SpriteSheet spriteSheetChevalier_Deplacement = new SpriteSheet("sprites/chevalier_deplacement.png", 64, 64);
		// sprites pour les deplacements
		this.animations[0] = loadAnimation(spriteSheetChevalier_Deplacement, 0, 1, 0);
		this.animations[1] = loadAnimation(spriteSheetChevalier_Deplacement, 0, 1, 1);
		this.animations[2] = loadAnimation(spriteSheetChevalier_Deplacement, 0, 1, 2);
		this.animations[3] = loadAnimation(spriteSheetChevalier_Deplacement, 0, 1, 3);
		this.animations[4] = loadAnimation(spriteSheetChevalier_Deplacement, 1, 9, 0);
		this.animations[5] = loadAnimation(spriteSheetChevalier_Deplacement, 1, 9, 1);
		this.animations[6] = loadAnimation(spriteSheetChevalier_Deplacement, 1, 9, 2);
		this.animations[7] = loadAnimation(spriteSheetChevalier_Deplacement, 1, 9, 3);
		
		
		SpriteSheet spriteSheetA = new SpriteSheet("sprites/chevalier_attaque.png", 64, 64);
		// sprites pour les attaques
		this.attaque[0] = loadAnimation(spriteSheetA, 0, 1, 0);
		this.attaque[1] = loadAnimation(spriteSheetA, 0, 1, 1);
		this.attaque[2] = loadAnimation(spriteSheetA, 0, 1, 2);
		this.attaque[3] = loadAnimation(spriteSheetA, 0, 1, 3);
		this.attaque[4] = loadAnimation(spriteSheetA, 1, 5, 0);
		this.attaque[5] = loadAnimation(spriteSheetA, 1, 5, 1);
		this.attaque[6] = loadAnimation(spriteSheetA, 1, 5, 2);
		this.attaque[7] = loadAnimation(spriteSheetA, 1, 5, 3);
		
	}

	/**
	 * on affiche un rectangle vert correspondant à la vie de l'unite (herite de Unite)
	 * la taille par défaut est 20px, et elle diminue en fonction des degat subis
	 */
	public void afficher_barre_vie(Graphics g) {
		g.setColor(new Color(0, 255, 0)); 
		g.fillRect((float)this.getX() -5, (float)this.getY() - 60, (float)((20 * vie)/Constant.VIE_MAX_CHE), (float)5);
		
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
	 * Constant.CHANCE_CHE vaut 4 : il a donc 60% de chance de toucher
	 */
	public boolean toucher() {
		if(Math.random()*10 > Constant.CHANCE_CHE)
			return true;
		else
			return false;
	}
	
	

	
}
