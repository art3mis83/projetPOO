package lesson2;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StateGame extends StateBasedGame {

	
	public static void main(String[] args) throws SlickException {
	    new AppGameContainer(new StateGame(), 800, 600, false).start();
	  }
	
	public StateGame() {
		super("lesson 15");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new MainScreenGameState());
	    addState(new MapGameState2());
		
	}

	
}
