package test_beta;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StateGame extends StateBasedGame {

	
	public static void main(String[] args) throws SlickException {
		
		new AppGameContainer(new StateGame(), 1000, 700, false).start();
	  }
	
	public StateGame() {
		super("Simulateur de bataille");
		
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		
		addState(new Intro());
		addState(new MainScreenGameState());
	    addState(new MapGameState2());
		
	}

	
}
