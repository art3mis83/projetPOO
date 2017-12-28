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

public class MainScreenGameState extends BasicGameState {

	public static final int ID = 1;
	private Image background;
	private BattleHud hud;
	private GameContainer container;
	
	
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
	  this.background = new Image("background/forest.png");
	  this.hud = new BattleHud();
	  this.hud.init(container, game);
	  this.container = container;
	  
	}
	
	/**
	 * Contenons nous d'afficher l'image de fond. 
	 * Le text est placé approximativement au centre.
	 */
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		background.draw(0, 0, container.getWidth(), container.getHeight());
		hud.render(container, g);

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