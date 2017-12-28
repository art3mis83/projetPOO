/**
 * 
 */
package test_beta;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Intro extends BasicGameState {

	public static final int ID = 0;
	private Image background;
	private StateBasedGame game;
	private GameContainer container;

	
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
	  this.background = new Image("background/fight-simulator.png");
	  this.game = game;
	  
	}
	
	/**
	 * Contenons nous d'afficher l'image de fond. 
	 * Le text est placé approximativement au centre.
	 */
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		background.draw(0, 0, container.getWidth(), container.getHeight());
		g.drawString("Appuyer sur une touche", 400, 550);
		

	}
	
	/**
	* Passer à l’écran de jeu à l'appui de n'importe quel touche.
	*/
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	}
	
	@Override
	public void keyReleased(int key, char c) {
		if (Input.KEY_ESCAPE == key) {
			this.container.exit();
		}else {
			game.enterState(MainScreenGameState.ID);
		}
		
	}
	
	/**
	 * L'identifiant permet d'identifier les différentes boucles.
	 * Pour passer de l'une à l'autre.
	 */
	public int getID() {
	  return ID;
	}

}