/**
 * Premiere couche du jeu, sert juste de page d'introduction
 * C'est une image de fond sur lequel on écrit une instruction
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
	private GameContainer container; // fenetre dans laquelle on se trouve
	
	private long cligno; // temps pour l'affiche du message en clignotement 
	private String message = "Appuyer sur une touche"; // message comportant l'indication

	
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
	  this.background = new Image("background/fight-simulator.png"); // on definit l'image de fond
	  this.game = game; // et la couche
	  this.cligno = System.currentTimeMillis(); // on recupere le temps actuel pour faire un minuteur 
	}
	
	/**
	 * On affiche le fond et l'instruction de debut de jeu
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		background.draw(0, 0, container.getWidth(), container.getHeight()); // on affiche l'image de fond
		
		if(System.currentTimeMillis() - cligno > 500) { // si notre minuteur tourne depuis plus de 0.5 s
			cligno = System.currentTimeMillis(); // on le reinitialise
			if(message.equals("")) { // si on avait rien d'ecrit, on affiche l'indication
				message = "Appuyer sur une touche";
			}else {
				message = ""; // sinon on affiche un message vide 
			}
		}
		g.drawString(message, 400, 500); // on affiche le message
			
		

	}
	
	
	/**
	 * Si on appui sur la touche echap on quitte, sinon on passe a la couche 1, soit le menu
	 */
	public void keyReleased(int key, char c) {
		if (Input.KEY_ESCAPE == key) {
			this.container.exit();
		}else {
			game.enterState(MainScreenGameState.ID);
		}	
	}
	
	
	/**
	 * L'identifiant permet d'identifier les différentes couches
	 */
	public int getID() {
	  return ID;
	}
	
	
	/**
	 *  On ne fait rien ici 
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException { }
	
	
	
	

}