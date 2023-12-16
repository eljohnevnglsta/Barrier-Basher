package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;

public class GameTimer extends AnimationTimer{
	
	private GraphicsContext gc;
	private GameStage stage; 
	private Scene theScene;
	private Background bg;
	
	private double backgroundY;
	
	private Character myCharacter;
	
	private ArrayList<Walls> walls;
	private ArrayList<Integer> wallValuesBasis;
	private ArrayList<Points> points;
	private ArrayList<Bombs> bombs;
	
	private long startSpawnWalls;
	private long startSpawnItems;
	private long Time;
	
	private int secondCounter;
	private int gameCounter;
	private String gameCounterText;
	
	private boolean disableUP = false;
	public boolean isGameOver = false; 
	
	public final static int CHARACTER_SPEED = 4;
	public final static int WALLS_INITIAL_YPOS = -100;
	public final static int WIDTH_PER_WALLS = 125;
	public final static double WALL_SPAWN_DELAY = 5;
	public final static double ITEM_SPAWN_DELAY = 1;
	public final static int BG_SPEED = 3; 
	public final static int POINTS_INITIAL_YPOS = 0;
	public final static int BOMBS_INITIAL_YPOS = 10;
	
	
	public final Font CUSTOM_FONT = Font.loadFont(getClass().getResourceAsStream("/fonts/SuperMario256.ttf"), 40);
	
	GameTimer(GameStage stage, GraphicsContext gc, Scene theScene){
		this.stage = stage; 
		this.gc = gc;
		this.theScene = theScene;
		this.myCharacter = new Character("Eljohn",190,600, theScene);
		this.startSpawnWalls = System.nanoTime();
		this.startSpawnItems = System.nanoTime();
		
		this.bg = new Background(0, 0);
		this.walls = new ArrayList<Walls>();
		this.wallValuesBasis = new ArrayList<Integer>();
		this.points = new ArrayList<Points>();
		this.bombs = new ArrayList<Bombs>();
		
		this.gameCounter = 60;
		this.gameCounterText = "1:00";
		
		//call method to handle mouse click event
		this.handleKeyPressEvent();
		this.setBG();
		
	}

	@Override
	public void handle(long currentNanoTime) {
		seconds(currentNanoTime);
		
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		if (this.isGameOver) this.stage.setupGameOver();
		this.redrawBackgroundImage();
		this.autoSpawnWalls(currentNanoTime);
		this.autoSpawnItems(currentNanoTime);
		this.moveSprites();	
		this.renderSprites();
		this.handleCollisions();
		this.myCharacter.moveStrength();
		this.myCharacter.checkWindowBoundaries(this);
		
		this.drawTime();
		
        if(this.gameCounter == 0) { // checks if game counter runs out
        	this.stop();
        	stage.setupGameOver();
        }
	}
	
	private void handleCollisions() {
	    for (Walls wall : walls) {
	        if (myCharacter.collidesWith(wall)) {
	        	if (wall.value > 0) {
	        		if (this.myCharacter.strength < 0) this.isGameOver = true; 
		        	this.disableUP = true; 
		        	this.myCharacter.setDY(GameTimer.BG_SPEED);
	        		this.myCharacter.strength--; 
		        	wall.value--;
	        	} else {
	        		System.out.println("STRENGTH: " + this.myCharacter.strength);
	        		wall.visible = false;
	        		wall.valueText.setVisible(false);
	        		this.disableUP = false; 
	        		this.myCharacter.setDY(0);
	        		break;
	        	}
	        } 
	    }
	}
	
//	FROM KUYA BERN
	// Method that updates the game timer text and second counter (for checking stats)
	private void seconds(long currentTime){
		// Condition that checks if the time converted from nanoseconds to seconds elapses 1.00s
		// To check the instance of time in seconds, currentTime is divided by 1,000,000,000 (1 billion), 1 nanosecond = 1^e-9 second
		if (((currentTime-this.Time) / 1000000000.0) >= 1){ 
			this.secondCounter++;
			this.gameCounter = 60 - this.secondCounter; // 60 - running time
			if (gameCounter <= 9) { // updates game counter text (single digit / double digit count)
				this.gameCounterText = "0:0" + gameCounter; // single digit
			} else {
				this.gameCounterText = "0:" + gameCounter; // double digit
			}
			this.Time = System.nanoTime(); // updates time or resets back to 0
		}
	}
	
	// Draw game stats including time, score, player's strength, and witch health
	private void drawTime(){
		this.gc.setFont(this.CUSTOM_FONT);
		if (myCharacter.isAlive()) {
			this.gc.fillText("Time: " + gameCounterText, 20, 40);
		} else {
			this.gc.fillText("Time: " + gameCounterText, 20, 40);
		}
	}
	
	private void moveSprites() {
		this.moveWalls();
		this.bg.moveBG();
		this.myCharacter.move();
		this.movePoints();
		this.moveBombs();
	}
	
	private void renderSprites() {
		this.bg.render(this.gc);
		this.renderPoints();
		this.renderBombs();
		this.renderWalls();
		this.myCharacter.render(this.gc);
		this.renderWallValueDisplay();
	}
	
	private void renderWallValueDisplay() {
		for (Walls i : this.walls) {
			i.moveWallValueDisplay();
		}
	}

	private void setBG() {
		this.bg.setDY(GameTimer.BG_SPEED);
	}
	
    void redrawBackgroundImage() {
		// clear the canvas
        this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);

        // redraw background image (moving effect)
        this.backgroundY += GameTimer.BG_SPEED;

        this.gc.drawImage(Background.BG_IMAGE, 0, this.backgroundY-Background.BG_IMAGE.getHeight() );
        this.gc.drawImage(Background.BG_IMAGE, 0, this.backgroundY );
        
        if (this.backgroundY >= GameStage.WINDOW_HEIGHT) {
        	this.backgroundY = GameStage.WINDOW_HEIGHT-Background.BG_IMAGE.getHeight();
        }
    }
	
	private void handleKeyPressEvent() {
		theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveCharacter(code);
			}
			
		});
		
		theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            public void handle(KeyEvent e){
		            	KeyCode code = e.getCode();
		                stopMoving(code);
		            }
		        });
    }
	
	private void moveCharacter(KeyCode ke) {
		if(!this.disableUP) {
			if(ke==KeyCode.UP) this.myCharacter.setDY(-1 * GameTimer.CHARACTER_SPEED); 
		}                

		if(ke==KeyCode.LEFT) this.myCharacter.setDX(-1 * GameTimer.CHARACTER_SPEED);

		if(ke==KeyCode.DOWN) this.myCharacter.setDY(GameTimer.CHARACTER_SPEED);
		
		if(ke==KeyCode.RIGHT) this.myCharacter.setDX(GameTimer.CHARACTER_SPEED);
		
   	}
	
	//method that will stop the ship's movement; set the ship's DX and DY to 0
	private void stopMoving(KeyCode ke){
		this.myCharacter.setDX(0);
		this.myCharacter.setDY(0);
	}

	private void moveWalls() {
		for(int i = 0; i < this.walls.size(); i++){
			Walls w = this.walls.get(i);
			if (w.isVisible()){
				w.move();
			}
			else this.walls.remove(i);
		}
	}
	
	private void renderWalls() {
        for (Walls wall : this.walls) {
        	wall.render(this.gc);
//        	System.out.println("Wall coordinates: x=" + wall.getX() + ", y=" + wall.getY());
        }
	}
	
	private void wallsSpawn() {
	    int yPos = GameTimer.WALLS_INITIAL_YPOS;

	    this.wallValuesBasis.clear();
	    
	    this.wallValuesBasis.add(this.myCharacter.strength);
	    this.wallValuesBasis.add(this.myCharacter.strength);
	    this.wallValuesBasis.add(this.myCharacter.strength);
	    this.wallValuesBasis.add(this.myCharacter.strength/2);
	    
	    Collections.shuffle(wallValuesBasis);
	    
	    if ((System.nanoTime() - this.startSpawnWalls) / 1000000000.0 > 5.0) {
		    this.walls.add(new Walls(0, -5, yPos, this.wallValuesBasis.get(0), this.theScene));
		    this.walls.add(new Walls(1, 116, yPos, this.wallValuesBasis.get(1), this.theScene));
		    this.walls.add(new Walls(2, 238, yPos, this.wallValuesBasis.get(2), this.theScene));
		    this.walls.add(new Walls(3, 360, yPos, this.wallValuesBasis.get(3), this.theScene));
		    this.startSpawnWalls = System.nanoTime();
	    }
	}

	
	private void movePoints() {
		for(int i = 0; i < this.points.size(); i++){
			Points p = this.points.get(i);
			if (p.isVisible()){
				p.move();
				p.checkCollision(this.myCharacter);
			}
			else this.points.remove(i);
		}
	}
	
	private void moveBombs() {
		for(int i = 0; i < this.bombs.size(); i++){
			Bombs b = this.bombs.get(i);
			if (b.isVisible()){
				b.move();
				b.checkCollision(this.myCharacter);
				if (this.myCharacter.strength < 0) {
					this.isGameOver = true; 
				}
			}
			else this.bombs.remove(i);
		}
	}
	
	private void pointsSpawn() {
		int yPos = GameTimer.POINTS_INITIAL_YPOS, xPos, type;

		Random r = new Random();
		
		type = r.nextInt(3);
		xPos = r.nextInt(400);
		
		this.points.add(new Points(type, xPos, yPos));
	}
	
	private void bombsSpawn() {
		int yPos = GameTimer.BOMBS_INITIAL_YPOS, xPos, type;
		
		Random r = new Random();
		
		type = r.nextInt(3);
		xPos = r.nextInt(400);
		
		this.bombs.add(new Bombs(type, xPos, yPos));
	}
	
	private void renderPoints() {
		for (Points point : this.points) {
			point.render(this.gc);
		}
	}
	
	private void renderBombs() {
		for (Bombs bomb : this.bombs) {
			bomb.render(this.gc);
		}
	}
	
	private void autoSpawnWalls(long currentNanoTime) {
    	double spawnElapsedTime = (currentNanoTime - this.startSpawnWalls) / 1000000000.0;
        // spawn walls
        if(spawnElapsedTime > GameTimer.WALL_SPAWN_DELAY) {
        	this.wallsSpawn();
        	this.startSpawnWalls = System.nanoTime();
        }
	}
	
	private void autoSpawnItems(long currentNanoTime) {
	    double spawnElapsedTime = (currentNanoTime - this.startSpawnItems) / 1000000000.0;
	    int numberOfWalls = this.walls.size(); // Get the current number of walls

	    // Check if a certain number of walls have been spawned before spawning items
	    if (numberOfWalls > 0 || numberOfWalls % 5 == 0) {
	        if (spawnElapsedTime > GameTimer.ITEM_SPAWN_DELAY) {
	            this.pointsSpawn();
	            this.bombsSpawn();
	            this.startSpawnItems = System.nanoTime();
	        }
	    }
	}
	
	
}
