/**
 * Couche symbolisant le menu, on l habille grace a MenuHud
 */
package projet_fin;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainScreenGameState extends BasicGameState {

	public static final int ID = 1; // identifiant de la couche
	private Image background; // image de fond
	private MenuHud hud; // hud qui articule notre menu
	private GameContainer container;
	
	
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
	  this.background = new Image("background/forest.png"); // on definit l image de fond
	  this.hud = new MenuHud(); // on creer une HUD pour le menu
	  this.hud.init(container, game); // on initialise cet HUD
	  this.container = container;
	  
	}
	
	// On affiche l'image de fond et l'HUD
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		background.draw(0, 0, container.getWidth(), container.getHeight());
		hud.render(container, g);

	}
	
	
	// on quitte si on appuie sur echap
	public void keyReleased(int key, char c) {
		if (Input.KEY_ESCAPE == key) {
			this.container.exit();
		}
	}
	
	// on ne fait rien ici 
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException { }
	
	
	
	//L'identifiant permet d'identifier les différentes couches
	public int getID() {
	  return ID;
	}

}