/**
 * 
 */
package test_beta;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Bonus extends Entite{
	
	

	//private int nb_bonnus;
	private Image gemme;
	private float x;
	private float y;
	
	private float Min = 100;
	private float Max = 500;
	
	public Bonus(double xPos, double yPos) {
		super(xPos, yPos);
		
	}
	
	public void init() throws SlickException {
		  gemme = new Image("sprites/gemme_rouge.png");
		  x = (float) (Math.random()* (Max - Min) + Min);
		  y = (float) (Math.random()* (Max - Min) + Min);
		}
	
	
	public void render(Graphics g) {
		  
		gemme.draw(x ,y);
	}

	public void change() {
		 x = (float) (Math.random()* (Max - Min) + Min);
		 y = (float) (Math.random()* (Max - Min) + Min);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void supprimer() {
		x = 1000;
		y = 1000;
		
	}

	
}
