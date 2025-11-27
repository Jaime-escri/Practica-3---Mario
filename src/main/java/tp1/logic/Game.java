package tp1.logic;

import tp1.view.Messages;

import java.io.PrintWriter;
import java.util.ArrayList;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.OffBoardException;
import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.Land;
import tp1.logic.gameobjects.Mario;
import tp1.logic.gameobjects.Goomba;


public class Game implements GameModel, GameStatus, GameWorld{

	public static final int DIM_X = 30;
	public static final int DIM_Y = 15;

	private GameObjectContainer container;
	private int time;
	private int points;
	private int lives;
	private boolean wins;
	private boolean loses;
	private Mario mario;
	public int nLevel;
	private boolean isFinished;
	private String fileloaded;


	public Game(int nLevel) {
		this.nLevel = nLevel;
		this.time = 100;
		this.points = 0;
		this.lives = 3;
		this.wins = false;
		this.loses = false;
		this.isFinished = false;
		this.fileloaded = null;
		this.container = new GameObjectContainer();
		initLevel(nLevel);
	}

	public Game(int points, int lives, int time, GameObjectContainer cont, Mario m){
		this.points = points;
		this.lives = lives;
		this.time = time;
		this.container = cont;
		this.mario = m;
		this.wins = false;
		this.loses = false;
		this.isFinished = false;
	}
	
	
	public String positionToString(int col, int row) {
		Position pos = new Position(row, col);
		return container.positionToString(pos);
	}

	public boolean playerWins() {
		return wins;
	}

	public boolean isInsideBoard(Position p){
		return (p.getRow() >= 0 && p.getRow() < DIM_Y && p.getCol() >= 0 && p.getCol() < DIM_X);
	}


	public boolean 	playerLoses() {
		lives--;

		if(lives > 0){
			reset(nLevel); //Si ha muerto, resetamos en nivel marcado por el usuario
		}else{
			loses = true;
			setFinish();
		}
		return loses;
	}  


	public int remainingTime() {
		return time;
	}

	public void addPoints(int p){
		points = points + p;
	}

	public int points() {

		return points;
	}

	public int numLives() {
		return lives;
	}

	public GameObjectContainer getObjectContainer(){
		return this.container;
	}

	
	public void initLevel(int nLevel) { //Vemos qué nivel inicializamos
		this.time = 100;
		if(fileloaded != null){
			try{
				GameConfiguration gc = new FileGameConfiguration(fileloaded, this);
				setFileConfiguration(gc);
			}catch(GameLoadException gle){
				
			}
		}else{
			if(nLevel == 0){
				initLevel0();
			}else if(nLevel == 1){
				initLevel1();
			}else if(nLevel == -1){
				this.lives = 3;
				this.time = 100;
				this.points = 0;
				initLevelFree();
			}
		}		
	}

	public void reset(int nLevel){
		this.time = 100;
		container.cleanObjects();
		initLevel(nLevel);
	}

	public boolean isSolid(Position p){

		if(p.getRow() < 0 || p.getRow() >= DIM_Y || p.getCol() < 0 || p.getCol() >= DIM_X){
			return true;
		}

		String contenido = container.positionToString(p);
		
		return (contenido.equals(Messages.LAND)) ; 
	}

	public int getLevel(){
		return this.nLevel;
	}

	public void setLevel(int level){
		this.nLevel = level;
	}

	public void setMario(Mario mario){
		this.mario = mario;
	}

	


	private void initLevel0(){

		// 1. Mapa
		for(int col = 0; col < 15; col++) {
			container.add(new Land(new Position(13,col), this));
			container.add(new Land(new Position(14,col), this));		
		}

		container.add(new Land(new Position(Game.DIM_Y-3,9), this));
		container.add(new Land(new Position(Game.DIM_Y-3,12), this));
		for(int col = 17; col < Game.DIM_X; col++) {
			container.add(new Land(new Position(Game.DIM_Y-2, col), this));
			container.add(new Land(new Position(Game.DIM_Y-1, col), this));		
		}

		container.add(new Land(new Position(9,2), this));
		container.add(new Land(new Position(9,5), this));
		container.add(new Land(new Position(9,6), this));
		container.add(new Land(new Position(9,7), this));
		container.add(new Land(new Position(5,6), this));

		// Salto final
		int tamX = 8; //tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;

		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				container.add(new Land(new Position(posIniY- fila, posIniX+ col), this));
			}
		}

		container.add(new ExitDoor(new Position(Game.DIM_Y-3, Game.DIM_X-1), this));

		// 3. Personajes
		
		this.mario = new Mario(1, false, new Position(Game.DIM_Y-3, 0), true, this);
		container.add(this.mario);

		//container.add(new Goomba(-1, false, new Position(0, 19), this));
	}


	private void initLevel1(){
		initLevel0();
		container.add(new Goomba(-1, false, new Position(12,6), this));
		container.add(new Goomba(-1, false, new Position(12,8), this));
		container.add(new Goomba(-1, false, new Position(12,11), this));
		container.add(new Goomba(-1, false, new Position(12,14), this));
		container.add(new Goomba(-1, false, new Position(10,10), this));
		container.add(new Goomba(-1, false, new Position(4,6), this));
		container.add(new Goomba(-1, false, new Position(0,19), this));
		
	}

	private void initLevelFree(){

	}

	public void update(){
		if(numLives() == 0){
			return;
		}

		if(remainingTime() == 0){
			playerLoses();
			return;
		}	
		time--;
		container.update(); //Actualizamos tanto a mario como a los goombas y comprobamos colisiones
		if(mario != null){
			if(!mario.isAlive())playerLoses();
		}
		
		
	}

	public void addAction(Action a){
		if(a!=null) mario.addAction(a);
	}

	public void marioExited(){ //Si mario ha tocado la puerta, actualizamos puntos y hacemos que gane
		wins = true;
		setFinish();
		int value = time * 10;
		int newPoints = points() + value;

		this.points = newPoints;
		
	}

	public void doInteractionsFrom(GameItem obj){ 
		container.doInteractionsFrom(obj);
	}

	public boolean isFinished(){
		return isFinished;
	}

	public void setFinish(){
		this.isFinished = true;
	}


	public void addObject(GameObject o) throws OffBoardException{
		if(!isInsideBoard(o.getPosition())){
			throw new OffBoardException(Messages.OFF_BOARD);
		}
		container.add(o);
	}

	public void save(String fileName) throws GameModelException {
		try (PrintWriter pw = new PrintWriter(fileName)) {
			pw.println(this.time + " " + this.points + " " + this.lives);
			pw.print(container.toString());
		} catch (Exception e) {
			throw new GameModelException("Error writting file " + fileName, e);
		}
	}

	public void load(String file) throws GameLoadException{
		try {
			this.fileloaded = file;
			GameConfiguration conf = new FileGameConfiguration(file, this);
			setFileConfiguration(conf);
		} catch (Exception e) {
			throw new GameLoadException(e.getMessage(), e);
		}
		
	}

	public void setFileConfiguration(GameConfiguration gc){
		this.points = gc.points();
		this.lives = gc.numLives();
		this.time = gc.getRemainingTime();

		this.container = new GameObjectContainer();
		for(GameObject g : gc.getNPCObjects()){
			container.add(g);
		}

		this.mario = gc.getMario();
		container.add(this.mario);
	}
}