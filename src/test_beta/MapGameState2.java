package test_beta;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MapGameState2 extends BasicGameState implements KeyListener, MouseListener{
	
	private boolean placement_fini = false;
	private boolean prem = true;
	public static final int ID = 2;
	private GameContainer container;
	
	public int num_carte = Constant.Carte1;
	private Map map = new Map();
	
	private List<Unite> l1 = new ArrayList<>();
	public int nb_chevalier1 = 0;
	public int nb_archer1 = 0;
	public int nb_piquier1 = 0;

	private List<Unite> l2 = new ArrayList<>();
	public int nb_chevalier2 = 0;
	public int nb_archer2 = 0;
	public int nb_piquier2 = 0;
	
	
	private Unite test =null;
	private int check = 0;
	
	public int nb_bonus;
	private List<Bonus> bonus = new ArrayList<>();
	
	private Camera camera = new Camera();
	
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		Music background = new Music("sound/the-last-encounter-short-loop.ogg");
		background.loop();
		//this.map.init(num_carte);
		
		
		this.camera.setInput(container.getInput());
		container.getInput().addKeyListener(camera);
		
	}
	
	
	public void play(Graphics g, List<Unite> l1, List<Unite> l2) {
			
		
		Iterator<Unite> iterator = l1.iterator();
		while(iterator.hasNext()) {
			Unite u = iterator.next();
			TreeMap<Double, Entite> tmap = new TreeMap<>();
			
			for(Unite p : l2)
				tmap.put(u.getDistanceTo(p), p);
			
			for(Bonus b : bonus) {
				tmap.put(u.getDistanceTo(b), b);
			}
			
			for(Entite p : tmap.values()) {
				u.setCible(p);
				break;
			}
			
			if (u.getVie() > 0 ) {
				if(placement_fini) {
					u.play();
				}
				u.render(g);
			}else {
			    iterator.remove();
			}


		}
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		if(prem) {	
			initialisation();
			prem = false;
		}
		
		this.camera.place(container, g);
		this.map.renderBackground();
		for(Bonus b : bonus) {
			b.render(g);
		}
		
		
		if(l1.size() == 0) {
			g.drawString("Equipe 2 a gagné !!", camera.getX(), camera.getY());
			for(Unite u : l2) {
				u.placement_fini = false;
				placement_fini = false;
			}
		}else if(l2.size() == 0) {
			g.drawString("Equipe 1 a gagné !!", camera.getX(), camera.getY());
			for(Unite u : l1) {
				u.placement_fini = false;
				placement_fini = false;
			}
		}
		
			play(g, l1, l2);
		
			play(g, l2, l1);


		
		this.map.renderForeground();
	}

	
	public void initialisation() throws SlickException{
		
		this.map.init(num_carte);
		
		int i;
		
		for(i = 0; i<nb_chevalier1; i++) {
			l1.add(new Chevalier(1, map));
			l1.get(i).init(400 + i*40, 400, 0, 0);
		}
		nb_archer1 += i;
		for(; i<nb_archer1; i++) {
			l1.add(new Archer(1, map));
			l1.get(i).init(400 + i*40, 500, 0, 0);
		}
		nb_piquier1 += i;
		for(; i<nb_piquier1; i++) {
			l1.add(new Piquier(1, map));
			l1.get(i).init(400 + i*40, 450, 0, 0);
		}
		
		
		for(i = 0; i<nb_chevalier2; i++) {
			l2.add(new Chevalier(2, map));
			l2.get(i).init(200 + i*40, 200, 0, 0);
		}
		nb_archer2 += i;
		for(; i<nb_archer2; i++) {
			l2.add(new Archer(2, map));
			l2.get(i).init(200 + i*40, 100, 0, 0);
		}
		nb_piquier2 += i;
		for(; i<nb_piquier2; i++) {
			l2.add(new Piquier(2, map));
			l2.get(i).init(200 + i*40, 150, 0, 0);
		}
		
		
		
		for(i = 0; i< nb_bonus; i++) {
			bonus.add(new Bonus(0, 0));
		}
		for(Bonus b : bonus) {
			b.init();
		}
		
		
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		boolean isMouseClicked = container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);
		
		if(isMouseClicked && !prem) {
			
			float xMouse = container.getInput().getMouseX() + camera.getX() - Constant.XOrigine;
			float yMouse = container.getInput().getMouseY() + camera.getY() - Constant.YOrigine;
			if(check == 0) {
				for(Unite u : l1) {
					if(Math.abs(u.getX() - xMouse) < 10 && Math.abs(u.getY() - yMouse) < 10) {
						check = 1;
						test = u;
						System.out.println("unite selectionne");
						break;	
					}
				}
				
				if(check == 0) {
					for(Unite u : l2) {
						if(Math.abs(u.getX() - xMouse) < 10 && Math.abs(u.getY() - yMouse) < 10) {
							check = 1;
							test = u;
							System.out.println("unite selectionne");
							break;	
						}
					}
				}
			
				
			}else if(check == 1) {
				boolean deja_present = false;
				if(!map.isCollision(xMouse, yMouse)) {
					for(Unite u : l1) {
						if(Math.abs(u.getX() - xMouse) < 10 && Math.abs(u.getY() - yMouse) < 10 ) {
							System.out.println("Unite deja presente");
							deja_present = true;
							break;
						}
					}
					if(!deja_present) {
						for(Unite u : l2) {
							if(Math.abs(u.getX() - xMouse) < 10 && Math.abs(u.getY() - yMouse) < 10) {
								System.out.println("Unite deja presente");
								deja_present = true;
								break;
							}
						}
					}
					
					if(!deja_present) {
						test.setX(xMouse);
						test.setY(yMouse);
						check = 0;
						System.out.println("unite placee");
					}
				}else {
					System.out.println("emplacement impossible");
				}
				
				
			}
			
			
		}
		
		if(placement_fini) {
			for(Unite u : l1) {
				u.update(delta);
			}
		
			for(Unite u : l2) {
				u.update(delta);
			}
		}
		
	
	}

	@Override
	public void keyReleased(int key, char c) {
		if (Input.KEY_ESCAPE == key) {
			this.container.exit();
		}
		
	}
	
	
	public void keyPressed(int key, char c) {
		
		if(key == Input.KEY_ENTER) {
			System.out.println("c'est parti");
			placement_fini = true;
			for(Unite u : l1) {
				u.placement_fini = true;
			}
			for(Unite u : l2) {
				u.placement_fini = true;
			}
		}
			
	}
	

	
	@Override
	public int getID() {
		return ID;
	}

}
