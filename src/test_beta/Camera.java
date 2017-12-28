/**
 * 
 */
package test_beta;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 * 
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y-
 */
public class Camera implements KeyListener {

	private float xCamera, yCamera;

	public Camera() {
		xCamera = 500;
		yCamera = 350;
	}

		public void place(GameContainer container, Graphics g) {
			g.translate(container.getWidth() / 2 - (int) this.xCamera, container.getHeight() / 2
					- (int) this.yCamera);
		}

		public float getX() {
			return xCamera;
		}
		
		public float getY() {
			return yCamera;
		}
		
		public void update(GameContainer container) {
			
		}		

		
		public void setInput(Input input) {

		}
		
		
		public boolean isAcceptingInput() {
			return true;
		}

		
		public void inputEnded() {

		}

		
		public void inputStarted() {

		}
		
		
		public void keyPressed(int key, char c) {
			switch (key) {
			case Input.KEY_UP:
				if(yCamera > 350) {
					this.yCamera = this.yCamera- 20;
				}
				break;
			case Input.KEY_LEFT:
				if(xCamera > 500) {
					this.xCamera = this.xCamera- 20;
				}
				break;
			case Input.KEY_DOWN:
				this.yCamera = this.yCamera + 20;
				break;
			case Input.KEY_RIGHT:
				this.xCamera = this.xCamera + 20;
				break;
			 }
		}

		
		public void keyReleased(int key, char c) {
			//this.player.setMoving(false);
		}
}
