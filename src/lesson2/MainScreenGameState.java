/**
 * 
 */
package lesson2;

import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainScreenGameState extends BasicGameState {

	public static final int ID = 1;
	private Image background;
	private StateBasedGame game;
	
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
	  this.game = game;
	  this.background = new Image("background/forest.png");
	}
	
	/**
	 * Contenons nous d'afficher l'image de fond. 
	 * Le text est placé approximativement au centre.
	 */
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		Scanner sc = new Scanner(System.in);
		
		background.draw(0, 0, container.getWidth(), container.getHeight());
		g.drawString("Veuillez saisir la taille de l'équipe 1 :", 300, 300);
		//int str = sc.nextInt();
	  
		
	  /*
	  g.clear();
	  background.draw(0, 0, container.getWidth(), container.getHeight());
	  g.drawString("Bonjour", 200, 200);*/
	}
	
	/**
	* Passer à l’écran de jeu à l'appui de n'importe quel touche.
	*/
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	}
	
	@Override
	public void keyReleased(int key, char c) {
	  game.enterState(MapGameState.ID);
	}
	
	/**
	 * L'identifiant permet d'identifier les différentes boucles.
	 * Pour passer de l'une à l'autre.
	 */
	public int getID() {
	  return ID;
	}

}