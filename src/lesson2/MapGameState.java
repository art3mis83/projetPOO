package lesson2;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y-
 */
public class MapGameState extends BasicGameState {
	
	public static final int ID = 2;
	private GameContainer container;
	private Map map = new Map();
	private Player player1 = new Player(map);
	private Player player2 = new Player(map);
	private TriggerController triggers1 = new TriggerController(map, player1);
	private TriggerController triggers2 = new TriggerController(map, player2);
	private Camera camera = new Camera(player1);
	//private StateBasedGame game;
	private PlayerController controller1 = new PlayerController(player1);
	//private PlayerController controller2 = new PlayerController(player2);
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		Music background = new Music("sound/lost-in-the-meadows.ogg");
		background.loop();
		this.map.init();
		this.player1.init(300, 300, 100, 500);
		this.player2.init(320, 200, player1.getX(), player1.getY());
		//this.controller1.setInput(container.getInput());
		//container.getInput().addKeyListener(controller);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		this.camera.place(container, g);
		this.map.renderBackground();
		this.player1.play();
		this.player2.setCible(player1.getX(), player1.getY());
		this.player2.play();
		this.player1.render(g);
		this.player2.render(g);
		this.map.renderForeground();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		//this.controller.update();
		this.triggers1.update();
		this.player1.update(delta);
		this.player2.update(delta);
		this.camera.update(container);
	}

	@Override
	public void keyReleased(int key, char c) {
		if (Input.KEY_ESCAPE == key) {
			this.container.exit();
		}
		if(Input.KEY_E == key) {
		//	game.enterState(MainScreenGameState.ID);
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
