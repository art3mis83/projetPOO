/*
 * C'est le corps du jeu, il gere toute la partie simulation
 */
package projet_fin;


import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MapGameState2 extends BasicGameState implements KeyListener, MouseListener, MouseMotionListener{
	
	public static final int ID = 2; // numero d identification de la couche
	
	public boolean fin = false; // boolean representant la fin de partie
	private boolean placement_fini = false, initialisationEnCours = true; // variable d initialisation
	
	private GameContainer container; // fenetre de jeu
	
	public Map map = new Map(); // carte en question
	
	public Equipe e1 = new Equipe(); // toutes les infos sur l equipe 1
	public Equipe e2 = new Equipe(); // toutes les infos sur l equipe 2

	private Unite cible = null; // unie ciblé par la souris pour le placement
	private boolean check = false;
	
	public Bonus bonus = new Bonus(); // liste des bonus
	
	private Camera camera = new Camera(); // camera qui permet de se deplacer sur la carte
	StateBasedGame game; // couche sur laquelle on est
	private FinHud finHud; // HUD qui vient se placer par dessus la fenetre de jeu a la fin de la partie
	private DebutHud debutHud;
	private Music music;
	
	
	// initialisation de notre couche de jeu
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container; // on sauvegarde notre fenetre
		this.game = game; // on sauvegarde notre couche
		music = new Music("sound/the-last-encounter-short-loop.ogg"); // on definit la musique de fond
		music.loop(); // la musique doit tourner en boucle
		this.debutHud = new DebutHud(camera); // on creer notre hud de fin de partie
		this.finHud = new FinHud(camera); // on creer notre hud de fin de partie
		this.camera.setInput(container.getInput()); // on initialise la camera
		container.getInput().addKeyListener(camera); // la camera ecoute le clavier
	
	}
	
	
	// permet l affichage
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		this.camera.place(container, g); // on place la camera par rapport a notre fenetre
		this.map.renderBackground(); // on affiche l arriere plan 
		
		bonus.render(g);
		
		// si une des equipes est vide on declare la fin de partie
		if(!fin && (e1.getL().size() == 0 || e2.getL().size() == 0)) {
			traitement_fin(g);
		}
		
		if(!placement_fini) {
			if(cible != null) {
				cible.render(g);
			}	
		}
		// on affiche les unites de l equipe 1
		for(Unite u : e1.getL()) {
			u.render(g);
		}
		// on affiche les unites de l equipe 1
		for(Unite u : e2.getL()) {
			u.render(g);
		}
		
		// on affiche les barres de vie de l equipe 1
		for(Unite u : e1.getL()) {
			u.afficher_barre_vie(g);
		}
		// on affiche les barre de vie de l equipe 2
		for(Unite u : e2.getL()) {
			u.afficher_barre_vie(g);
		}
		
		this.map.renderForeground(); // on affiche le premier plan
		
		if(!placement_fini && !fin) { // HUD de debut de partie 	
			debutHud.setErreur(!placement_valide());
			debutHud.render(container,g);
		}
		
		if(fin) { // si c est la fin de partie, on affiche l HUD
			finHud.render(container, g);
		}
		
	}
	
	// on fait la mise a jour physique (coordone, ...) de toutes nos entites
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		if(initialisationEnCours) {	// on fait l initialisation des equipes
			initialisation();
			initialisationEnCours = false;
		}
		
		if(!fin && !initialisationEnCours) { // on realise le placement des unites comme le desire l utilisateur
			placement_debut();
		}
		
		if(!fin && placement_fini) { // si le placement des unite est fini on peut jouer
			play(e1.getL(), e2.getL()); // l equipe 1 joue en fonction des positions de l'equipe 2
			
			play(e2.getL(), e1.getL()); // l equipe 2 joue en fonction des positions de l'equipe 1
			
			for(Unite u : e1.getL()) { // on fait la mise a jour physique des unites de l equipe 1
				u.update(delta);
			}
		
			for(Unite u : e2.getL()) { // on fait la mise a jour physique des unites de l equipe 2
				u.update(delta);
			}
		}	
		
		this.camera.update();
	}

	// gere la vie des unites d une equipe en fonction de l equipe adverse
	public void play(List<Unite> l_allie, List<Unite> l_enemi) {
			
			Iterator<Unite> iterator = l_allie.iterator(); // on place un iterateur afin de pouvoir interagir sur l equipe directement
			while(iterator.hasNext()) { // on parcourt toute l equipe
				Unite u = iterator.next();
				
				if (u.getVie() > 0 ) { // si l unite est encore en vie
					u.attribuer_cible(l_enemi, bonus.getList()); // on attribue une cible a l unite
					u.play(l_allie); 
	
				}else {
				    iterator.remove(); // sinon on l exclue 
				}
			}	
		
	}
	
	// initialisation des equipe et de la carte
	public void initialisation() throws SlickException{
		
		this.map.init(); // on charge la carte adequat
		
		this.camera.init(map);
		
		this.e1.init(map, Constant.EQUIPE1);
		
		this.e2.init(map, Constant.EQUIPE2);
		
		bonus.init(map);
		
	}
	
	
	public void traitement_fin(Graphics g) {
		fin = true; // on materialise la fin
		
		if(e1.getL().size() == 0) { // si l equipe 1 est vide
			this.finHud.setGagnant(2); // on declare l equipe 2 gagnante
			for(Unite u : e2.getL()) {  // on fige les unites restante
				u.placement_fini = false;
			}
		}else if(e2.getL().size() == 0) { // reciproquement si c est l equipe 2 qui est vide
			this.finHud.setGagnant(1);
			for(Unite u : e1.getL()) {
				u.placement_fini = false;
			}
		}
		
		
	}
	
	public void placement_debut() {
		
		boolean isMouseClicked = container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON); // detection du clic gauche
		
		if(isMouseClicked) {
			
			// on recupere les coordones du clic
			float xMouse = container.getInput().getMouseX() + camera.getX() - Constant.XOrigine; 
			float yMouse = container.getInput().getMouseY() + camera.getY() - Constant.YOrigine;
			
			if(!check) { // si aucune unite n est selectionne
				for(Unite u : e1.getL()) { // on parcourt l equipe 1 pour voir si une unite se trouve dans la zone du clic
					if(Math.abs(u.getX() - xMouse) < 10 && Math.abs(u.getY() - yMouse) < 10) {
						check = true; // on passe a vrai, une unite est selectionnee
						cible = u; // on enregistre l unite
						System.out.println("unite selectionne");
						break;	
					}
				}
				
				if(!check) { // meme chose sur l equipe 2, si on a trouve aucune unite dans l equipe 1
					for(Unite u : e2.getL()) {
						if(Math.abs(u.getX() - xMouse) < 10 && Math.abs(u.getY() - yMouse) < 10) {
							check = true;
							cible = u;
							System.out.println("unite selectionne");
							break;	
						}
					}
				}
			
				
			}else if(check) { // si on a trouve une unite
				boolean deja_present = false; // boolean signalant une unite 
				if(!map.isCollision(xMouse, yMouse)) { // si la case que l utilisateur a choisi est valide
					// on verifie qu une unite ne s y trouve pas deja
					for(Unite u : e1.getL()) {
						if(Math.abs(u.getX() - xMouse) < 10 && Math.abs(u.getY() - yMouse) < 10 ) { 
							System.out.println("Unite deja presente");
							deja_present = true;
							break;
						}
					}
					if(!deja_present) {
						for(Unite u : e2.getL()) {
							if(Math.abs(u.getX() - xMouse) < 10 && Math.abs(u.getY() - yMouse) < 10) {
								System.out.println("Unite deja presente");
								deja_present = true;
								break;
							}
						}
					}
					
					if(!deja_present) { // si la case est entierement valide, on place l unite en question
						cible.setxPos(xMouse);
						cible.setyPos(yMouse);
						check = false;
						System.out.println("unite placee");
					}
				}else {
					System.out.println("emplacement impossible");
				}	
			}	
		}
	}
	

	@Override
	public void keyReleased(int key, char c) {
		if (Input.KEY_ESCAPE == key) { // si on appuie sur echap, on ferme
			this.container.exit();
		}
		
	}
	
	// si on detecte une case invalide pour une des unites on renvoie faux
	// utilise principalement quand on a beaucoup d unite en debut de partie (donc que le placement par dafaut pose probleme)
	public boolean placement_valide() {
		for(Unite u : e1.getL()) {
			if(map.isCollision(u.getX(), u.getY())) { 
				return false;
			}	
		}
		for(Unite u : e2.getL()) {
			if(map.isCollision(u.getX(), u.getY())) {
				return false;
			}			
		}
		return true;	
	}
	
	// on gere les interuptions clavier
	public void keyPressed(int key, char c) {
		
		if(key == Input.KEY_ENTER) { // on lance la partie en appuyant sur entre
			if(placement_valide()) {
				System.out.println("c'est parti");
				// le placement est fini 
				placement_fini = true; 
				for(Unite u : e1.getL()) {
					u.placement_fini = true;
				}
				for(Unite u : e2.getL()) {
					u.placement_fini = true;
				}
			}
			
		} else if(key == Input.KEY_R) { // avec R, on reinitialise au tout debut de la partie (placement des unites)
			placement_fini = false;
			initialisationEnCours = true;
			cible = null;
			e1.setL(new ArrayList<Unite>());
			e2.setL(new ArrayList<Unite>());
			fin = false;
			
		}else if(key == Input.KEY_M) { // avec M, on reinitialise et on revient au menu (changement de couche)
			placement_fini = false;
			initialisationEnCours = true;
			cible = null;
			e1.setL(new ArrayList<Unite>());
			e2.setL(new ArrayList<Unite>());
			game.enterState(MainScreenGameState.ID);
			
		}
			
	}
	

	
	@Override
	public int getID() {
		return ID;
	}


	@Override
	public void mouseDragged(MouseEvent arg0) {}


	@Override
	public void mouseMoved(MouseEvent e) {
		if(!placement_fini && cible != null && check) {
			System.out.println("coucou");
			cible.setxPos(e.getX());
			cible.setyPos(e.getY());
		}
		
	}

}
