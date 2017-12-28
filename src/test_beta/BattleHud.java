/**
 * 
 */
package test_beta;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;


public class BattleHud implements ComponentListener{


	// pour le centrage du text dans les boutons
	private static final int Y_PADDING = 5; 
	private static final int X_PADDING = 13;
	

	private MouseOverArea add_chevalier1;
	private MouseOverArea delete_chevalier1;
	private int nb_chevalier1 = 0;
	private MouseOverArea add_archer1;
	private MouseOverArea delete_archer1;
	private int nb_archer1 = 0;
	private MouseOverArea add_piquier1;
	private MouseOverArea delete_piquier1;
	private int nb_piquier1 = 0;
	private MouseOverArea add_chevalier2;
	private MouseOverArea delete_chevalier2;
	private int nb_chevalier2 = 0;
	private MouseOverArea add_archer2;
	private MouseOverArea delete_archer2;
	private int nb_archer2 = 0;
	private MouseOverArea add_piquier2;
	private MouseOverArea delete_piquier2;
	private int nb_piquier2 = 0;
	private MouseOverArea launch;

	private MouseOverArea add_bonus;
	private MouseOverArea delete_bonus;
	private int nb_bonus;
	
	private MouseOverArea change_map;
	private int num_map = 0;
	private String nom[] = {"La petite bataille dans la prairie", "carte 2", "Un coup de chaud ..."};
	
	private StateBasedGame game;
	private int largeur;
	private int hauteur;
	

	public BattleHud() {

		}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		
		Image boutonValider = new Image("hud/button.png");
		Image boutonPlus = new Image("hud/boutonPlus.png");
		Image boutonMoins = new Image("hud/boutonMoins.png");
		
		largeur = container.getWidth();
		hauteur = container.getHeight();
		final int X_boutonMoinsE1 = largeur/2 - 190;
		final int X_boutonMoinsE2 = largeur/2 + 120;
		final int X_boutonPlusE1 = X_boutonMoinsE1 + boutonMoins.getWidth() + X_PADDING;
		final int X_boutonPlusE2 = X_boutonMoinsE2 + boutonMoins.getWidth() + X_PADDING;
		
		final int Y_boutonChe = hauteur/2 - Y_PADDING;
		
		final int Y_boutonArc = Y_boutonChe + 75;
		
		final int Y_boutonPiq = Y_boutonChe - 75;
		
		change_map = new MouseOverArea(container, boutonValider, largeur/2 - boutonValider.getWidth()/2, 100, this);
		
		delete_piquier1 = new MouseOverArea(container, boutonMoins, X_boutonMoinsE1, Y_boutonPiq, this);
		add_piquier1 = new MouseOverArea(container, boutonPlus, X_boutonPlusE1, Y_boutonPiq, this);
		
		delete_piquier2 = new MouseOverArea(container, boutonMoins, X_boutonMoinsE2, Y_boutonPiq, this);
		add_piquier2 = new MouseOverArea(container, boutonPlus, X_boutonPlusE2, Y_boutonPiq, this);
		
		delete_chevalier1 = new MouseOverArea(container, boutonMoins, X_boutonMoinsE1, Y_boutonChe, this);
		add_chevalier1 = new MouseOverArea(container, boutonPlus, X_boutonPlusE1, Y_boutonChe, this);
		
		delete_chevalier2 = new MouseOverArea(container, boutonMoins, X_boutonMoinsE2, Y_boutonChe, this);
		add_chevalier2 = new MouseOverArea(container, boutonPlus, X_boutonPlusE2, Y_boutonChe, this);
	  
		delete_archer1 = new MouseOverArea(container, boutonMoins, X_boutonMoinsE1, Y_boutonArc, this);
		add_archer1 = new MouseOverArea(container, boutonPlus, X_boutonPlusE1, Y_boutonArc, this);
		
		delete_archer2 = new MouseOverArea(container, boutonMoins, X_boutonMoinsE2, Y_boutonArc, this);
		add_archer2 = new MouseOverArea(container, boutonPlus, X_boutonPlusE2, Y_boutonArc, this);
	  
		delete_bonus = new MouseOverArea(container, boutonMoins,largeur/2 -boutonMoins.getWidth(), hauteur - 150, this);
		add_bonus = new MouseOverArea(container, boutonPlus,delete_bonus.getX() + 50, hauteur - 150, this);
		
		launch = new MouseOverArea(container, boutonValider, largeur/2 -boutonValider.getWidth()/2, hauteur - 100, this);
		this.game = game;

	}

	public void render(GameContainer container, Graphics g) { 
	  g.setColor(Color.black);
	  
	  afficher_bouton(container, g);
	  
	  g.drawString(nom[num_map], change_map.getX() + 10, change_map.getY() + 30);
	  g.drawString("Piquier", largeur/2-25, hauteur/2 - 75);
	  g.drawString(""+this.nb_piquier1, largeur/2 - 75, hauteur/2 - 75);
	  g.drawString(""+this.nb_piquier2, largeur/2 + 75, hauteur/2 - 75);
	  
	  g.drawString("Chevalier", largeur/2-35, hauteur/2);
	  g.drawString(""+this.nb_chevalier1, largeur/2 - 75, hauteur/2);
	  g.drawString(""+this.nb_chevalier2, largeur/2 + 75, hauteur/2);
	  
	  
	  g.drawString("Archer", largeur/2-22, hauteur/2 + 75);
	  g.drawString(""+this.nb_archer1, largeur/2 - 75, hauteur/2 + 75);
	  g.drawString(""+this.nb_archer2, largeur/2 + 75, hauteur/2 + 75);
	  
	  g.drawString("Equipe 1", delete_piquier1.getX() + X_PADDING, add_piquier1.getY() - 50);
	  g.drawString("Equipe 2", delete_piquier2.getX() + X_PADDING, add_piquier2.getY() - 50);
	 
	  
	  
	}
	
	
	public void afficher_bouton(GameContainer container, Graphics g) {
		
		change_map.render(container, g);
		g.drawString("Changer Carte", change_map.getX() + X_PADDING, change_map.getY() + Y_PADDING);
		
		add_piquier1.render(container, g);
		add_piquier2.render(container, g);
		delete_piquier1.render(container, g);
		delete_piquier2.render(container, g);
		
		add_chevalier1.render(container, g);
		add_chevalier2.render(container, g);
		delete_chevalier1.render(container, g);
		delete_chevalier2.render(container, g);
		  
		add_archer1.render(container, g);
		add_archer2.render(container, g);
		delete_archer1.render(container, g);
		delete_archer2.render(container, g);
		
		delete_bonus.render(container, g);
		add_bonus.render(container, g);
		g.drawString("bonus : " + nb_bonus, delete_bonus.getX(), add_bonus.getY() - add_bonus.getHeight());
		
		launch.render(container, g);
		g.drawString("Valider", launch.getX() + launch.getWidth()/2 - 30, launch.getY() + Y_PADDING);
	}
	
	
	public void componentActivated(AbstractComponent source) {

		if (source == add_chevalier1) {
			nb_chevalier1++;
		} else if (source == add_chevalier2) {
			nb_chevalier2++;
		}else if(source == add_archer1) {
			nb_archer1++;
		}else if(source == add_piquier1) {
			nb_piquier1++;
		}else if(source == add_piquier2) {
			nb_piquier2++;
		}else if(source == add_archer2) {
			nb_archer2++;
		}else if(source == delete_chevalier1) {
			if(nb_chevalier1 > 0) {
				nb_chevalier1--;
			}
		}else if(source == delete_chevalier2) {
			if(nb_chevalier2 > 0) {
				nb_chevalier2--;
			}
		}else if(source == delete_archer1) {
			if(nb_archer1 > 0) {
				nb_archer1--;
			}
		}else if(source == delete_archer2) {
			if(nb_archer2 > 0) {
				nb_archer2--;
			}
		}else if(source == delete_piquier1) {
			if(nb_piquier1 > 0) {
				nb_piquier1--;
			}
		}else if(source == delete_piquier2) {
			if(nb_piquier2 > 0) {
				nb_piquier2--;
			}
		}else if(source == add_bonus) {
			nb_bonus++;
		}else if(source == delete_bonus) {
			if(nb_bonus > 0) {
				nb_bonus--;
			}
		}else if(source == change_map) {
			
			num_map = (num_map+ 1)%3;
			System.out.println("click" + num_map);
		} else if (source == launch) {
			  
			  ((MapGameState2) game.getState(MapGameState2.ID)).nb_chevalier1 = nb_chevalier1;
			  ((MapGameState2) game.getState(MapGameState2.ID)).nb_chevalier2 = nb_chevalier2;
			  ((MapGameState2) game.getState(MapGameState2.ID)).nb_archer1 = nb_archer1;
			  ((MapGameState2) game.getState(MapGameState2.ID)).nb_archer2 = nb_archer2;
			  ((MapGameState2) game.getState(MapGameState2.ID)).nb_piquier1 = nb_piquier1;
			  ((MapGameState2) game.getState(MapGameState2.ID)).nb_piquier2 = nb_piquier2;
			  ((MapGameState2) game.getState(MapGameState2.ID)).nb_bonus = nb_bonus;
			  ((MapGameState2) game.getState(MapGameState2.ID)).num_carte = num_map;
				game.enterState(MapGameState2.ID);
			
		  }
		}
	
}
