/**
 *  Unite representant un piquier
 */
package projet_fin;

import java.util.List;
import java.util.TreeMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Piquier extends Unite {
	
	
	public Piquier(int e, Map m) {
		super(e, m, Constant.PIQUIER);
		setVie(Constant.VIE_MAX_PIQ);
		setDistance_attaque(50);
		setDegat(Constant.ATQ_PIQ);
		setVitesse(Constant.VITESSE_DEF);
		setVitesseAttaque((long) 700);
	}

	/*
	 * Fonction herite de la classe Unite, on charge les sprites associes a l'archer
	 */
	public void config_sprite() throws SlickException {
		
		SpriteSheet spriteSheetPiquier_Deplacement = new SpriteSheet("sprites/piquier_deplacement.png", 64, 64);
		// sprite pour les deplacements
		this.animations[0] = loadAnimation(spriteSheetPiquier_Deplacement, 0, 1, 0);
		this.animations[1] = loadAnimation(spriteSheetPiquier_Deplacement, 0, 1, 1);
		this.animations[2] = loadAnimation(spriteSheetPiquier_Deplacement, 0, 1, 2);
		this.animations[3] = loadAnimation(spriteSheetPiquier_Deplacement, 0, 1, 3);
		this.animations[4] = loadAnimation(spriteSheetPiquier_Deplacement, 1, 9, 0);
		this.animations[5] = loadAnimation(spriteSheetPiquier_Deplacement, 1, 9, 1);
		this.animations[6] = loadAnimation(spriteSheetPiquier_Deplacement, 1, 9, 2);
		this.animations[7] = loadAnimation(spriteSheetPiquier_Deplacement, 1, 9, 3);
		
		
		SpriteSheet spriteSheetPiquier_Attaque = new SpriteSheet("sprites/piquier_attaque.png", 64, 64);
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

	/*
	 * on affiche un rectangle vert correspondant à la vie de l'unite (herite de Unite)
	 */
	public void afficher_barre_vie(Graphics g) {
		g.setColor(new Color(0, 255, 0)); 
		g.fillRect((float)this.getX() -5, (float)this.getY() - 60, (float)((20 * vie)/Constant.VIE_MAX_PIQ), (float)5);
		
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
