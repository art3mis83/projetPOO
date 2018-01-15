
package projet_fin;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;


public class MenuHud implements ComponentListener{


	// pour le centrage du text dans les boutons
	private static final int Y_PADDING = 5; 
	private static final int X_PADDING = 13;
	

	private setBouton chevalier = new setBouton(); // jeu de bouton pour les chevaliers
	private setBouton archer = new setBouton(); // jeu de bouton pour les archers
	private setBouton piquier = new setBouton(); // jeu de bouton pour les piquiers
	
	private MouseOverArea launch; // bouton valider

	// jeu de bouton pour les bonus
	private MouseOverArea add_bonus;
	private MouseOverArea delete_bonus;
	private int nb_bonus;
	
	// vairable pour le changement de map
	private MouseOverArea change_map;
	private int num_map = 0;
	private String nom[] = {"La petite bataille dans la prairie", "Carte Inconnue", "Un coup de chaud ..."};
	
	private StateBasedGame game; // couche du jeu
	private int largeur; // largeur de la fenetre
	private int hauteur; // hauteur de la fenetre
	

	public MenuHud() {	} // on ne fait rien 
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		Image boutonValider = new Image("hud/button.png"); // on charge l image du bouton valider
		Image boutonPlus = new Image("hud/boutonPlus.png"); // on charge l image du bouton Plus
		Image boutonMoins = new Image("hud/boutonMoins.png"); // on charge l image du bouton Moins
		
		// on recupere les dimensions de la fenetre
		largeur = container.getWidth(); 
		hauteur = container.getHeight();
		 // on definit des points clefs dans notre fenetre
		final int X_boutonMoinsE1 = largeur/2 - 190;
		final int X_boutonMoinsE2 = largeur/2 + 120;
		final int X_boutonPlusE1 = X_boutonMoinsE1 + boutonMoins.getWidth() + X_PADDING;
		final int X_boutonPlusE2 = X_boutonMoinsE2 + boutonMoins.getWidth() + X_PADDING;
		final int Y_boutonChe = hauteur/2 - Y_PADDING;
		final int Y_boutonArc = Y_boutonChe + 75;
		final int Y_boutonPiq = Y_boutonChe - 75;
		
		// on sauvegarde la couche
		this.game = game;
		
		// bouton pour changer de map
		change_map = new MouseOverArea(container, boutonValider, largeur/2 - boutonValider.getWidth()/2, 100, this);
		
		// on definit les zones cliquables relatifs aux piquiers
		piquier.setDelete_unite1(new MouseOverArea(container, boutonMoins, X_boutonMoinsE1, Y_boutonPiq, this));
		piquier.setAdd_unite1(new MouseOverArea(container, boutonPlus, X_boutonPlusE1, Y_boutonPiq, this));
		
		piquier.setDelete_unite2(new MouseOverArea(container, boutonMoins, X_boutonMoinsE2, Y_boutonPiq, this));
		piquier.setAdd_unite2(new MouseOverArea(container, boutonPlus, X_boutonPlusE2, Y_boutonPiq, this));
		
		// on definit les zones cliquables relatifs aux chevaliers
		chevalier.setDelete_unite1(new MouseOverArea(container, boutonMoins, X_boutonMoinsE1, Y_boutonChe, this));
		chevalier.setAdd_unite1(new MouseOverArea(container, boutonPlus, X_boutonPlusE1, Y_boutonChe, this));
		
		chevalier.setDelete_unite2(new MouseOverArea(container, boutonMoins, X_boutonMoinsE2, Y_boutonChe, this));
		chevalier.setAdd_unite2(new MouseOverArea(container, boutonPlus, X_boutonPlusE2, Y_boutonChe, this));
	  
		// on definit les zones cliquables relatifs aux archers
		archer.setDelete_unite1(new MouseOverArea(container, boutonMoins, X_boutonMoinsE1, Y_boutonArc, this));
		archer.setAdd_unite1(new MouseOverArea(container, boutonPlus, X_boutonPlusE1, Y_boutonArc, this));
		
		archer.setDelete_unite2(new MouseOverArea(container, boutonMoins, X_boutonMoinsE2, Y_boutonArc, this));
		archer.setAdd_unite2(new MouseOverArea(container, boutonPlus, X_boutonPlusE2, Y_boutonArc, this));
	  
		
		// on definit les zones cliquables relatifs aux bonus
		delete_bonus = new MouseOverArea(container, boutonMoins,largeur/2 -boutonMoins.getWidth(), hauteur - 150, this);
		add_bonus = new MouseOverArea(container, boutonPlus,delete_bonus.getX() + 50, hauteur - 150, this);
		
		// on definit la zones cliquable relatif au bouton de valider
		launch = new MouseOverArea(container, boutonValider, largeur/2 -boutonValider.getWidth()/2 + 15, hauteur - 100, this);

	}

	// on affiche les elements de l HUD
	public void render(GameContainer container, Graphics g) { 
	  g.setColor(Color.black);
	  
	  afficher_bouton(container, g); // on affiche les boutons
	  switch(num_map) { // on affiche le titre de la carte choisie
		  case 0 :
			  g.drawString(nom[num_map], change_map.getX() - 70, change_map.getY() + 30);  
			  break;
		  case 1 :
			  g.drawString(nom[num_map], change_map.getX() + 10, change_map.getY() + 30);  
			  break;
		  case 2 :
			  g.drawString(nom[num_map], change_map.getX() - 10, change_map.getY() + 30);  
			  break;
	  }
	  
	  // le label et le nombre de cette unite par equipe
	  g.drawString("Piquier", largeur/2-25, hauteur/2 - 75);
	  g.drawString(""+ piquier.getNb_unite1(), largeur/2 - 75, hauteur/2 - 75);
	  g.drawString(""+ piquier.getNb_unite2(), largeur/2 + 75, hauteur/2 - 75);
	  
	  // le label et le nombre de cette unite par equipe
	  g.drawString("Chevalier", largeur/2-35, hauteur/2);
	  g.drawString(""+ chevalier.getNb_unite1(), largeur/2 - 75, hauteur/2);
	  g.drawString(""+ chevalier.getNb_unite2(), largeur/2 + 75, hauteur/2);
	  
	  // le label et le nombre de cette unite par equipe
	  g.drawString("Archer", largeur/2-22, hauteur/2 + 75);
	  g.drawString(""+ archer.getNb_unite1(), largeur/2 - 75, hauteur/2 + 75);
	  g.drawString(""+ archer.getNb_unite2(), largeur/2 + 75, hauteur/2 + 75);
	  
	  // label d equipe
	  g.drawString("Equipe 1", piquier.getDelete_unite1().getX() + X_PADDING, piquier.getDelete_unite1().getY() - 50);
	  g.drawString("Equipe 2", piquier.getDelete_unite2().getX() + X_PADDING, piquier.getDelete_unite1().getY() - 50);
	 
	}
	
	// on affiche tous les boutons
	public void afficher_bouton(GameContainer container, Graphics g) {
		
		change_map.render(container, g);
		g.drawString("Changer Carte", change_map.getX() + X_PADDING , change_map.getY() + Y_PADDING);
		
		
		piquier.getAdd_unite1().render(container, g);
		piquier.getAdd_unite2().render(container, g);
		piquier.getDelete_unite1().render(container, g);
		piquier.getDelete_unite2().render(container, g);
		
		chevalier.getDelete_unite1().render(container, g);
		chevalier.getDelete_unite2().render(container, g);
		chevalier.getAdd_unite1().render(container, g);
		chevalier.getAdd_unite2().render(container, g);
		  
		archer.getAdd_unite1().render(container, g);
		archer.getAdd_unite2().render(container, g);
		archer.getDelete_unite1().render(container, g);
		archer.getDelete_unite2().render(container, g);
		
		delete_bonus.render(container, g);
		add_bonus.render(container, g);
		g.drawString("Bonus : " + nb_bonus, delete_bonus.getX(), add_bonus.getY() - add_bonus.getHeight());
		
		launch.render(container, g);
		g.drawString("Valider", launch.getX() + launch.getWidth()/2 - 30, launch.getY() + Y_PADDING);
	}
	
	// on regarde avec quelle zone clicable l utilisateur interagit
	public void componentActivated(AbstractComponent source) {

		// on met a jour les compteurs en fonction du bouton clique
		
		if (source == chevalier.getAdd_unite1()) {
			chevalier.setNb_unite1(chevalier.getNb_unite1() + 1);
		} else if (source == chevalier.getAdd_unite2()) {
			chevalier.setNb_unite2(chevalier.getNb_unite2() + 1);
		}else if(source == archer.getAdd_unite1()) {
			archer.setNb_unite1(archer.getNb_unite1() + 1);
		}else if(source == piquier.getAdd_unite1()) {
			piquier.setNb_unite1(piquier.getNb_unite1() + 1);
		}else if(source == piquier.getAdd_unite2()) {
			piquier.setNb_unite2(piquier.getNb_unite2() + 1);
		}else if(source == archer.getAdd_unite2()) {
			archer.setNb_unite2(archer.getNb_unite2() + 1);
		}else if(source == add_bonus) {
			nb_bonus++;
		}else if(source == chevalier.getDelete_unite1()) {
			if(chevalier.getNb_unite1() > 0) {
				chevalier.setNb_unite1(chevalier.getNb_unite1() - 1);
			}
		}else if(source == chevalier.getDelete_unite2()) {
			if(chevalier.getNb_unite2() > 0) {
				chevalier.setNb_unite2(chevalier.getNb_unite2() - 1);
			}
		}else if(source == archer.getDelete_unite1()) {
			if(archer.getNb_unite1() > 0) {
				archer.setNb_unite1(archer.getNb_unite1() - 1);
			}
		}else if(source == archer.getDelete_unite2()) {
			if(archer.getNb_unite2() > 0) {
				archer.setNb_unite2(archer.getNb_unite2() - 1);
			}
		}else if(source == piquier.getDelete_unite1()) {
			if(piquier.getNb_unite1() > 0) {
				piquier.setNb_unite1(piquier.getNb_unite1() - 1);
			}
		}else if(source == piquier.getDelete_unite2()) {
			if(piquier.getNb_unite2() > 0) {
				piquier.setNb_unite2(piquier.getNb_unite2() - 1);
			}
		}else if(source == delete_bonus) {
			if(nb_bonus > 0) {
				nb_bonus--;
			}
		}else if(source == change_map) {
			num_map = (num_map+ 1)%3;
			
		} else if (source == launch) { // si c est le bouton valider, on envoie la valeur des compteurs dans les equipes
			// puis on change de couche
			  
			  ((MapGameState2) game.getState(MapGameState2.ID)).e1.setNb_chevalier(chevalier.getNb_unite1());
			  ((MapGameState2) game.getState(MapGameState2.ID)).e2.setNb_chevalier(chevalier.getNb_unite2());
			  ((MapGameState2) game.getState(MapGameState2.ID)).e1.setNb_archer(archer.getNb_unite1());
			  ((MapGameState2) game.getState(MapGameState2.ID)).e2.setNb_archer(archer.getNb_unite2());
			  ((MapGameState2) game.getState(MapGameState2.ID)).e1.setNb_piquier(piquier.getNb_unite1());
			  ((MapGameState2) game.getState(MapGameState2.ID)).e2.setNb_piquier(piquier.getNb_unite2());
			  ((MapGameState2) game.getState(MapGameState2.ID)).bonus.setNb_bonus(nb_bonus);
			  ((MapGameState2) game.getState(MapGameState2.ID)).map.setNum_carte(num_map);
			  ((MapGameState2) game.getState(MapGameState2.ID)).fin = false;
				game.enterState(MapGameState2.ID);
			
		  }
		}
	
}
