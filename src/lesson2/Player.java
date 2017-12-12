/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 * GCS d- s+:+ a C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
package lesson2;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 * 
 * Représente le joueur
 * 
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class Player extends Unite{

	private float x, y;
	private float cibleX, cibleY;
	private Player cible;
	private int direction = 2;
	private boolean onStair = false;
	private boolean moving = false;
	private Animation[] animations = new Animation[8];

	private Map map;

	public Player(Map map, int equipe, int vie) {
		super(equipe, vie);
		this.map = map;
		
	}

	public void init(int x, int y, float f, float g) throws SlickException {
		this.x = x;
		this.y = y;
		this.cibleX = f;
		this.cibleY = g;
		
		SpriteSheet spriteSheet = new SpriteSheet("sprites/character.png", 64, 64);
		this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
		this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
		this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
		this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
		this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
		this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
		this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
		this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);
	}

	private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
		Animation animation = new Animation();
		for (int x = startX; x < endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), 100);
		}
		return animation;
	}

	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0, .5f));
		g.fillOval((int) x - 16, (int) y - 8, 32, 16);
		g.drawAnimation(animations[direction + (moving ? 4 : 0)], (int) x - 32, (int) y - 60);
	}

	public void update(int delta) {
		if (this.moving) {
			float futurX = getFuturX(delta);
			float futurY = getFuturY(delta);
			boolean collision = this.map.isCollision(futurX, futurY);
			if (collision) {
				this.moving = false;
			} else {
				this.x = futurX;
				this.y = futurY;
			}
		}
	}

	private float getFuturX(int delta) {
		float futurX = x;
		switch (direction) {
		case 1:
			futurX = this.x - getVitesse() * delta;
			break;
		case 3:
			futurX = this.x + getVitesse() * delta;
			break;
		}
		return futurX;
	}

	private float getFuturY(int delta) {
		float futurY = this.y;
		switch (this.direction) {
		case 0:
			futurY = this.y - getVitesse() * delta;
			break;
		case 2:
			futurY = this.y + getVitesse() * delta;
			break;
		case 1:
			if (this.onStair) {
				futurY = this.y + getVitesse() * delta;
			}
			break;
		case 3:
			if (this.onStair) {
				futurY = this.y - getVitesse() * delta;
			}
			break;
		}
		return futurY;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public boolean isOnStair() {
		return onStair;
	}

	public void setOnStair(boolean onStair) {
		this.onStair = onStair;
	}

	public void play() {
		float deltaX = Math.abs(x - cibleX);
		float deltaY = Math.abs(y - cibleY);
		moving = true;
		if(deltaX >= getDistance_attaque()/2) {
			if(x > cibleX) {
				direction = 1;
			}else {
				direction = 3;
			}
		}else if(deltaY >= getDistance_attaque()/2) {
			if(y > cibleY) {
				direction = 0;
			}else {
				direction = 2;
			}
		}else if(deltaX < getDistance_attaque()/2 && deltaY < getDistance_attaque()/2) {
			moving = false;
			attaque();
		}
		
	}

	public void attaque() {
		if(cible.getVie() > getDegat()) {
			cible.setVie(cible.getVie() - getDegat());
		}else {
			cible.setVie(0);
		}
	}
	
	
	public void setCible(Player c) {
		this.cible = c;
		this.cibleX = c.getX();
		this.cibleY = c.getY();
		
	}

	public Double getDistanceTo(Player p) {
		final double dx = getX() - p.getX();
        final double dy = getY() - p.getY();
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}
}
