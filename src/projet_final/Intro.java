/**
 * Premiere couche du jeu, sert juste de page d'introduction
 */
package projet_fin;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Intro extends BasicGameState {

	
	public static final int ID = 0; // on definit un ID pour identifier la couche
	private Image background; // image de fond
	private StateBasedGame game; // couche du jeu
	private GameContainer container; 

	
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
	  this.background = new Image("background/fight-simulator.png"); // on definit l'image de fond
	  this.game = game; // et la couche
	  
	}
	
	/**
	 * On affiche le fond et l'instruction de debut de jeu
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		background.draw(0, 0, container.getWidth(), container.getHeight());
		g.drawString("Appuyer sur une touche", 400, 550);

	}
	
	
	// Si on appui sur la touche echap on quitte, sinon on passe a la couche 1, soit le menu
	public void keyReleased(int key, char c) {
		if (Input.KEY_ESCAPE == key) {
			this.container.exit();
		}else {
			game.enterState(MainScreenGameState.ID);
		}	
	}
	
	// On ne fait rien ici 
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException { }
	
	
	
	//L'identifiant permet d'identifier les différentes couches
	public int getID() {
	  return ID;
	}

}