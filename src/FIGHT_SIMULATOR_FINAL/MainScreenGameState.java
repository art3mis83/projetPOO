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
	private GameContainer container; // fenetre dans laquelle on est
	
	
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
	  this.background = new Image("background/fond_menu.jpg"); // on definit l image de fond
	  this.hud = new MenuHud(); // on creer une HUD pour le menu
	  this.hud.init(container, game); // on initialise cet HUD
	  this.container = container; 
	  
	}
	
	/**
	 * On affiche notre menu : d'abord notre image de fond puis par dessus l'HUD
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		background.draw(0, 0, container.getWidth(), container.getHeight());
		hud.render(container, g);

	}
	
	
	/**
	 * Si l'utilisateur appuie sur echap, on quitte l'application
	 */
	public void keyReleased(int key, char c) {
		if (Input.KEY_ESCAPE == key) {
			this.container.exit();
		}
	}
	
	
	/**
	 * L'identifiant permet d'identifier les différentes couches
	 */
		public int getID() {
		  return ID;
		}
		
	/**
	 * on ne fait rien ici 
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException { }
	

}