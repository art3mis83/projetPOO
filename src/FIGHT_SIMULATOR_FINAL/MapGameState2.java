/*
 * C'est le corps du jeu, il gere toute la partie simulation
 */
package projet_fin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	
	public static final int ID = 2; // numero d identification de la couche
	
	public boolean fin = false; // boolean representant la fin de partie
	private boolean placement_fini = false, initialisationEnCours = true; // variable d initialisation
	
	private GameContainer container; // fenetre de jeu
	
	public Map map = new Map(); // carte en question
	
	public Equipe e1 = new Equipe(); // toutes les infos sur l equipe 1
	public Equipe e2 = new Equipe(); // toutes les infos sur l equipe 2
	public Bonus bonus = new Bonus(); // liste des bonus
	
	private Unite cible = null; // unie ciblé par la souris pour le placement
	private boolean check = false;
	
	private Camera camera = new Camera(); // camera qui permet de se deplacer sur la carte
	public StateBasedGame game; // couche sur laquelle on est
	private FinHud finHud; // HUD qui vient se placer par dessus la fenetre de jeu a la fin de la partie
	private DebutHud debutHud; // HUD qui vient se placer par dessus la fenetre de jeu au debut de la partie
	private Music music; // musique qui change en fonction de la phase de jeu
	
	private Mouse mouse; // sert de selection de masse pour les unités
	
	/**
	 * initialisation de notre couche de jeu
	 * On crée les HUD, on définit la musique, on place la camera, ...
	 */
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container; // on sauvegarde notre fenetre
		this.game = game; // on sauvegarde notre couche
		music = new Music("sound/lost-in-the-meadows.ogg"); // on definit la musique de fond
		music.loop(); // la musique est jouée en boucle
		this.debutHud = new DebutHud(camera); // on creer notre hud de debut de partie
		this.finHud = new FinHud(camera); // on creer notre hud de fin de partie
		this.camera.setInput(container.getInput()); // on initialise la camera sur notre fenetre
		container.getInput().addKeyListener(camera); // la camera ecoute le clavier
		this.mouse = new Mouse(); // on creer notre selecteur d'unité

	}
	
	
	
	
	/**
	 * permet l affichage de tous les éléments de la simulation
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		this.camera.place(container, g); // on place la camera par rapport a notre fenetre
		this.map.renderBackground(); // on affiche l arriere plan 
		
		bonus.render(g); // on affiche les bonus
		
		// si une des equipes est vide on declare la fin de partie
		if(!fin && (e1.getL().size() == 0 || e2.getL().size() == 0)) {
			traitement_fin(g);
		}
		
		
		// on affiche les unites de l equipe 1
		for(Unite u : e1.getL()) {
			u.render(g);
		}
		// on affiche les unites de l equipe 2
		for(Unite u : e2.getL()) {
			u.render(g);
		}
		
		
		if(mouse.getRec() != null) { // si on a la selecteur d'activé, on dessine le rectangle symbolisant l'air de selection
			g.draw( mouse.getRec());
		}
		
		this.map.renderForeground(); // on affiche le premier plan
		
		if(!placement_fini) { // si c'est le debut de partie, on affiche l'HUD de debut de partie 	
			debutHud.setErreur(!placement_valide());
			debutHud.render(container,g);
			if(check) {
				g.drawString("UNITE SELECTIONNE", camera.getX() + 170, camera.getY() - 50);
			}
			
		}
		
		if(fin) { // si c est la fin de partie, on affiche l HUD de fin de partie
			finHud.render(container, g);
		}
		
	}
	
	/**
	 * on fait la mise a jour physique (coordone, ...) de toutes nos entites
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		if(initialisationEnCours) {	// on fait l initialisation des equipes la premiere fois
			initialisation();
			initialisationEnCours = false;
		}
		
		if(!fin && !initialisationEnCours && !placement_fini) { // on realise le placement des unites comme le desire l utilisateur
			placement_debut();
		}
		
		if(!fin && placement_fini) { // si le placement des unite est fini on peut jouer
			play(e1.getL(), e2.getL(), delta); // l equipe 1 joue en fonction des positions de l'equipe 2
			
			play(e2.getL(), e1.getL(), delta); // l equipe 2 joue en fonction des positions de l'equipe 1
			
			/*for(Unite u : e1.getL()) { // on fait la mise a jour physique des unites de l equipe 1
				u.update(delta);
			}
		
			for(Unite u : e2.getL()) { // on fait la mise a jour physique des unites de l equipe 2
				u.update(delta);
			}*/
		}	
		bonus.update();
		this.camera.update();
	}

	/**
	 * gere les unites d une equipe en fonction de l equipe adverse ainsi que les déplacements
	 * @param l_allie liste des unité allié (soit l'equipe qui joue)
	 * @param l_enemi liste des unité ennemi (soit l'equipe adverse)
	 * @param delta temps depuis la derniere mise a jour des coordonnes
	 */
	public void play(List<Unite> l_allie, List<Unite> l_enemi, int delta) {
			
			Iterator<Unite> iterator = l_allie.iterator(); // on place un iterateur afin de pouvoir interagir sur l equipe directement
			while(iterator.hasNext()) { // on parcourt toute l equipe
				Unite u = iterator.next();
				
				if (u.getVie() > 0 ) { // si l unite est encore en vie
					u.attribuer_cible(l_enemi, bonus.getList()); // on attribue une cible a l unite
					u.play(l_allie); // elle cherche un chemin jusqu'a sa cible ou attaque
					u.update(delta); // mise a jour des coordoones
	
				}else {
				    iterator.remove(); // sinon on l exclue 
				}
			}	
		
	}
	
	/**
	 * initialisation de tous les éléments du jeu :
	 *  - equipe 
	 *  - carte
	 *  - bonus
	 *  - selecteur d unite
	 *  - camera
	 */
	public void initialisation() throws SlickException{
		
		this.map.init(); // on charge la carte adequat
		
		this.camera.init(map); // on place la camera sur la carte
		
		this.e1.init(map, Constant.EQUIPE1); // on ajoute les membres de l'équipe 1
		
		this.e2.init(map, Constant.EQUIPE2); // on ajoute les membres de l'équipe 1
		
		bonus.init(map); // on ajoute les bonus
		
		mouse.init(); // on ajoute le selecteur
		
		
	}
	
	/**
	 * Cette fonction est appellée lorsqu'une équipe a gagné
	 * On déclenche la musique de fin de partie et on écrit le numéro de l'équipe vainqueur
	 * @param g : graphics sur lequel on va écrire
	 */
	public void traitement_fin(Graphics g) throws SlickException {
		fin = true; // on materialise la fin
		music = new Music("sound/lively-meadow-victory-fanfare.ogg"); // on defini la musique de fin
		music.play(); // on lance la musique
		
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
	
	/**
	 * Fonction qui permet de gérer le placement des équipes : le premier clic avec la souris vérifie qu'on a bien cliqué 
	 * sur une unité, si tel est le cas, on "enregistre" la dite unite
	 * Le second clic vérifie que l'emplacement est valide, si tel est le cas, on change les coordonnées de l'unité selectionnée
	 * Sinon on ne fait rien et on garde l'unité selectionnée en memoire
	 */
	public void placement_debut() {
		
		boolean isMouseClicked = container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON); // detection du clic gauche
		
		if(isMouseClicked) {
			
			// on recupere les coordones du clic
			float xMouse = container.getInput().getMouseX() + camera.getX() - Constant.XOrigine; 
			float yMouse = container.getInput().getMouseY() + camera.getY() - Constant.YOrigine;
			
			if(!check) { // si aucune unite n est deja selectionnee
				for(Unite u : e1.getL()) { // on parcourt l equipe 1 pour voir si une unite se trouve dans la zone du clic
					if(Math.abs(u.getX() - xMouse) < 10 && Math.abs(u.getY() - yMouse) < 10) {
						check = true; // on passe a vrai, une unite est selectionnee
						cible = u; // on enregistre l unite
						break;	// si on a trouvé une unité, on ne continue pas de chercher dans l'équipe
					}
				}
				
				if(!check) { // meme chose sur l equipe 2, si on a trouve aucune unite dans l equipe 1
					for(Unite u : e2.getL()) {
						if(Math.abs(u.getX() - xMouse) < 10 && Math.abs(u.getY() - yMouse) < 10) {
							check = true;
							cible = u;
							break;	
						}
					}
				}
			
				// on arrive ici si on a fait un clic gauche et qu'on a deja selectionne une unite
			}else if(check) { 
				boolean deja_present = false; // boolean signalant une unite 
				if(!map.isCollision(xMouse, yMouse)) { // si la case que l utilisateur a choisi est valide
					// on verifie qu aucune unite ne s y trouve deja
					for(Unite u : e1.getL()) {
						if(Math.abs(u.getX() - xMouse) < 10 && Math.abs(u.getY() - yMouse) < 10 ) { 
							// on a detecté une unite, on l'affiche, on le materialise avec le booleen et on sort
							System.out.println("Unite deja presente");
							deja_present = true; 
							break;
						}
					}
					// on reitere l'operation avec l'équipe 2
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
				}else { // sinon on signale que l'emplacement n'est pas possible
					System.out.println("emplacement impossible");
				}	
			}	
		}
	}
	

	/**
	 * Fonction qui se lance lorsqu'une touche est relachée
	 * Ici, seule la touche echap nous interesse
	 */
	public void keyReleased(int key, char c) {
		if (Input.KEY_ESCAPE == key) { // si on appuie sur echap, on ferme
			this.container.exit();
		}
		
	}
	
	/**
	 * verifie que la position de toutes les unités est valide
	 * @return false si au moins une unité est mal placée, true sinon
	 */
	public boolean placement_valide() {
		for(Unite u : e1.getL()) {// on parcourt l'équipe 1
			if(map.isCollision(u.getX(), u.getY())) { 
				return false;
			}	
		}
		for(Unite u : e2.getL()) { // puis l'équipe 2
			if(map.isCollision(u.getX(), u.getY())) {
				return false;
			}			
		}
		return true; // si on a detecte aucune collision, alors on renvoie true
	}
	
	/**
	 * On gere les interuptions clavier
	 * 
	 */
	public void keyPressed(int key, char c) {
		
		if(key == Input.KEY_ENTER) { // on lance la partie en appuyant sur entre si toutes les unites sont bien placées
			if(placement_valide()) {
				try {
					music = new Music("sound/the-last-encounter-short-loop.ogg"); // on definit la musique de combat
				} catch (SlickException e) { 
					e.printStackTrace();
				} // on lance la musique
				music.loop();

				// on signale la fin de la phase de placement  
				placement_fini = true; 
				for(Unite u : e1.getL()) {
					u.placement_fini = true;
				}
				for(Unite u : e2.getL()) {
					u.placement_fini = true;
				}
			}
			
		} else if(key == Input.KEY_R && fin) { // avec R, on reinitialise au tout debut de la partie (placement des unites)
			placement_fini = false; // on doit donc placer les unites
			initialisationEnCours = true; // on est en phase d'initialisation
			cible = null; // aucune unité n'est selectionnee
			// les équipes sont vidées
			e1.setL(new ArrayList<Unite>()); 
			e2.setL(new ArrayList<Unite>());
			// ce n'est plus la fin de partie
			fin = false;
			
		}else if(key == Input.KEY_M && fin) { // avec M, on reinitialise et on revient au menu (changement de couche)
			placement_fini = false; // on doit donc placer les unites
			initialisationEnCours = true; // on est en phase d'initialisation
			cible = null; // aucune unité n'est selectionnee
			// les équipes sont vidées
			e1.setL(new ArrayList<Unite>()); 
			e2.setL(new ArrayList<Unite>());
			game.enterState(MainScreenGameState.ID); // on retourne au menu
			
		}
			
	}
	
	
	/**
	 * Fonction qui se déclenche lorsqu'on relache le clic de la souris
	 */
	public void mouseReleased(int button, int x, int y) {
	
		if(!fin && !initialisationEnCours && !placement_fini 
			) { // si on est bien dans la partie de placement du début
		
			// on enregistre les coordonées de la souris lors du relachement
			x = (int) (x + (camera.getX() - Constant.XOrigine)); 
			y = (int) (y + (camera.getY() - Constant.YOrigine));
			// on regarde si des unités se trouvent dans le rectangle que l'on a forme
			mouse.mouseReleased(e1, e2);
		}
		
	}
	
	/**
	 * Fonction qui se lance lorsque la souris est déplacée avec un clic enfoncé
	 */
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if(!fin && !initialisationEnCours && !placement_fini) { // si on est bien dans la partie de placement du début
			
			// on enregistre les coordonées de la souris lors de son déplacement
			newx = (int) (newx + (camera.getX() - Constant.XOrigine));
			newy = (int) (newy + (camera.getY() - Constant.YOrigine));
			// on dessine le rectangle
			mouse.mouseDragged(newx, newy);
		}
	}
	
	/**
	 * Fonction qui se lance lorsqu'une pression sur la souris est detecté
	 */
	public void mousePressed(int button, int x, int y) {
		if(!fin && !initialisationEnCours && !placement_fini) { // si on est bien dans la partie de placement du début
		
			// on enregistre les coordonées de la souris 
			x = (int) (x + (camera.getX() - Constant.XOrigine));
			y = (int) (y + (camera.getY() - Constant.YOrigine));
			// on envoie les coordonnées au selecteur
			mouse.mousePressed(x, y);
		}
		
	}

	
	/**
	 * Fonction qui se lance lorsqu'un clic sur la souris est detecté
	 * On va replacer les unités
	 */
	public void mouseClicked(int button, int x, int y, int clickCount) {
		if(!placement_fini) {// si on est bien dans la partie de placement du début
			
			// on enregistre les coordonées de la souris 
			x = (int) (x + (camera.getX() - Constant.XOrigine));
			y = (int) (y + (camera.getY() - Constant.YOrigine));
			// on envoie les coordonnées au selecteur
			mouse.mouseClicked(x, y);
			
		}
		
	}

	
	/**
	 * On retourne le numéro d'identification de la couche
	 * Utilisé lors de la navigation entre les couches
	 */
	public int getID() {
		return ID;
	}



	/*** GETTER retournant les équipes ou les bonus **/
	public Equipe getE1() {
		return e1;
	}

	public Equipe getE2() {
		return e2;
	}

	
	public Bonus getBonus() {
		return bonus;
	}

	
	/*** SETTER définissant les équipes ou les bonus **/
	public void setE1(Equipe e1) {
		this.e1 = e1;
	}


	public void setE2(Equipe e2) {
		this.e2 = e2;
	}


	public void setBonus(Bonus bonus) {
		this.bonus = bonus;
	}
	
	


}
