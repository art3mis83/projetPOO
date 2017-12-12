package lesson2;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

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
public class MapGameState2 extends BasicGameState {
	
	private int nb = 3;
	public static final int ID = 2;
	private GameContainer container;
	private Map map = new Map();
	
	private List<Player> l1 = new ArrayList<>(3);
	private List<Player> l2 = new ArrayList<>(3);
	
	//private TriggerController triggers1 = new TriggerController(map, player1);
	//private TriggerController triggers2 = new TriggerController(map, player2);
	private Camera camera;
	//private StateBasedGame game;
	//private PlayerController controller1;
	//private PlayerController controller2 = new PlayerController(player2);
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		int i = 0;
		this.container = container;
		Music background = new Music("sound/lost-in-the-meadows.ogg");
		background.loop();
		this.map.init();
		Player cam = new Player(map,3,300);
		cam.init(300, 300, 0,0);
		for(i = 0; i<3; i++) {
			l1.add(new Player(map, 1, 100));
			l1.get(i).init(300 + i*40, 400 + i*30, 0, 0);
		}
		
		for(i = 0; i<3; i++) {
			l2.add(new Player(map, 2, 50));
			l2.get(i).init(200 + i*40, 200 + i*50, 0, 0);
		}
		
		camera = new Camera();
		this.camera.setInput(container.getInput());
		container.getInput().addKeyListener(camera);
		
	}
	
	
	public void play(Graphics g, List<Player> l_a, List<Player> l_e) {
		
		Iterator<Player> iterator = l_a.iterator();
		while(iterator.hasNext()) {
			Player u = iterator.next();
			TreeMap<Double, Player> tmap = new TreeMap<>();
			
			for(Player p : l_e)
				tmap.put(u.getDistanceTo(p), p);
			
			for(Player p : tmap.values()) {
				u.setCible(p);
				break;
			}
			
			if (u.getVie() > 0 ) {
			   	u.play();
				u.render(g);
			}else {
			    iterator.remove();
			}


		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		this.camera.place(container, g);
		this.map.renderBackground();
		
		play(g, l1, l2);
		
		play(g, l2, l1);
		
		
		
		this.map.renderForeground();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		//this.controller1.update();
		//this.triggers1.update();
		
		for(Player u : l1) {
			u.update(delta);
		}
		
		for(Player u : l2) {
			u.update(delta);
		}
		
		//this.camera.update(container);
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
