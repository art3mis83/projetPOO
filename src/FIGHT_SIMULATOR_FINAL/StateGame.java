/**
 * C'est un peu notre classe main
 * A l'interieur on va creer l'application, et initialiser chaque couche
 */

package projet_fin;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StateGame extends StateBasedGame {

	
	public static void main(String[] args) throws SlickException {
		// on cree l application
		new AppGameContainer(new StateGame(), 1000, 700, false).start();
	  }
	
	public StateGame() {
		super("FIGHT SIMULATOR");
		
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		
		addState(new Intro()); // on ajoute la couche d'introduction
		addState(new MainScreenGameState()); // on ajoute la couche du menu
	    addState(new MapGameState2()); // on ajoute la couche de la simulation
		
	}

	
}
