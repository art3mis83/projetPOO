/**
 *  Unite representant un archer
 */
package projet_fin;

import java.util.List;
import java.util.TreeMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Archer extends Unite {
	
	
	public Archer(int e, Map m) {
		super(e, m, Constant.ARCHER);
		setVie(Constant.VIE_MAX_ARC);
		setDistance_attaque(150);
		setDegat(Constant.ATQ_ARC);
		setVitesse(Constant.VITESSE_DEF);
		setVitesseAttaque((long) 500);
	}


	/*
	 * Fonction herite de la classe Unite, on charge les sprites associes a l'archer
	 */
	public void config_sprite() throws SlickException {
		
		SpriteSheet spriteSheetArcher_Deplacement = new SpriteSheet("sprites/character.png", 64, 64);
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


	/*
	 * on affiche un rectangle vert correspondant à la vie de l'unite (herite de Unite)
	 */
	public void afficher_barre_vie(Graphics g) {
		g.setColor(new Color(0, 255, 0)); 
		g.fillRect((float)this.getX() -5, (float)this.getY() - 60, (float)((20 * vie)/Constant.VIE_MAX_ARC), (float)5);
	}


	/*
	 * on dresse une treeMap en fonction des distances de chaque entite de la carte et de cet archer
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

	
	

	
}
