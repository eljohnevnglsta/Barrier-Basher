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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameTimer extends AnimationTimer{
	
	private GraphicsContext gc;
	GameStage stage; 
	private Scene theScene;
	private Background bg;
	
	private double backgroundY;
	
	private Character myCharacter;
	
	private ArrayList<Walls> walls;
	private ArrayList<Integer> wallValuesBasis;
	private ArrayList<Collect> collect;
	private ArrayList<Avoid> avoid;
	
	private long startSpawnWalls;
	private long startSpawnItems;
	private long Time;
	
	private int secondCounter;
	private int gameCounter;
	private String gameCounterText;
	
	private boolean disableKey = false;
	public boolean isGameOver = false; 
	
	public static int CHARACTER_SPEED = 2;
	public final static int WALLS_INITIAL_YPOS = -100;
	public final static int WIDTH_PER_WALLS = 125;
	public final static double WALL_SPAWN_DELAY = 5;
	public static double ITEM_SPAWN_DELAY = 1.25;
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
		this.collect = new ArrayList<Collect>();
		this.avoid = new ArrayList<Avoid>();
		
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
        	if (this.myCharacter.isAlive()) {
        		this.stop();
        		this.stage.setupFinish();
        	}else {
            	this.stop();
            	this.stage.setupGameOver(0);
        	}
        }
        
        if (this.gameCounter == 45) {
        	GameTimer.ITEM_SPAWN_DELAY = 1.15;
        	GameTimer.CHARACTER_SPEED = 3;
        }
        if (this.gameCounter == 30) {
        	GameTimer.ITEM_SPAWN_DELAY = .85;
        	GameTimer.CHARACTER_SPEED = 4;
        }
        if (this.gameCounter == 10) {
        	GameTimer.ITEM_SPAWN_DELAY = .65;
        	GameTimer.CHARACTER_SPEED = 5;
        }
	}
	
//	FROM KUYA BERN
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

	private void drawTime(){
		this.gc.setFont(this.CUSTOM_FONT);
		if (myCharacter.isAlive()) {
			this.gc.fillText("Time: " + gameCounterText, 20, 40);
			this.gc.setFill(Color.rgb(238, 66, 102));
		}
	}
	
	
	private void handleCollisions() {
	    for (Walls wall : walls) {
	        if (myCharacter.collidesWith(wall)) {
	        	if (wall.value > 0) {
	        		if (this.myCharacter.strength < 0) {
	        			this.isGameOver = true; 
	        			getStage().setupGameOver(1);
	        		} 
		        	this.disableKey = true; 
		        	this.myCharacter.setDY(GameTimer.BG_SPEED);
	        		this.myCharacter.strength--; 
		        	wall.value--;
	        	} else {
	        		System.out.println("STRENGTH: " + this.myCharacter.strength);
	        		wall.visible = false;
	        		wall.valueText.setVisible(false);
	        		this.disableKey = false; 
	        		this.myCharacter.setDY(0);
	        		break;
	        	}
	        }
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
	
	private void moveSprites() {
		this.moveWalls();
		this.bg.moveBG();
		this.myCharacter.move();
		this.moveCollect();
		this.moveAvoid();
	}
	
	private void renderSprites() {
		this.bg.render(this.gc);
		this.renderCollect();
		this.renderAvoid();
		this.renderWalls();
		this.myCharacter.render(this.gc);
		this.renderWallValueDisplay();
	}
	
	private void moveCharacter(KeyCode ke) {
		if(this.disableKey) return;
			
		if(ke==KeyCode.UP) this.myCharacter.setDY(-1 * GameTimer.CHARACTER_SPEED);              

		if(ke==KeyCode.LEFT) {
			this.myCharacter.setDX(-1 * GameTimer.CHARACTER_SPEED);
			this.myCharacter.chooseImg(2);
		}

		if(ke==KeyCode.DOWN) this.myCharacter.setDY(GameTimer.CHARACTER_SPEED);
		
		if(ke==KeyCode.RIGHT) {
			this.myCharacter.setDX(GameTimer.CHARACTER_SPEED);
			this.myCharacter.chooseImg(1);
			
		}
		
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
	
	private void moveCollect() {
		for(int i = 0; i < this.collect.size(); i++){
			Collect c = this.collect.get(i);
			if (c.isVisible()){
				c.move();
				c.checkCollision(this.myCharacter);
			}
			else this.collect.remove(i);
		}
	}
	
	private void moveAvoid() {
		for(int i = 0; i < this.avoid.size(); i++){
			Avoid b = this.avoid.get(i);
			if (b.isVisible()){
				b.move();
				b.checkCollision(this.myCharacter);
				if (this.myCharacter.strength < 0) {
					this.isGameOver = true; 
					getStage().setupGameOver(0);
				}
			}
			else this.avoid.remove(i);
		}
	}
	
	
	private void renderWalls() {
        for (Walls wall : this.walls) {
        	wall.render(this.gc);
//        	System.out.println("Wall coordinates: x=" + wall.getX() + ", y=" + wall.getY());
        }
	}
	
	private void renderWallValueDisplay() {
		for (Walls i : this.walls) {
			i.moveWallValueDisplay();
		}
	}
	
	private void renderCollect() {
		for (Collect collect : this.collect) {
			collect.render(this.gc);
		}
	}
	
	private void renderAvoid() {
		for (Avoid avoid : this.avoid) {
			avoid.render(this.gc);
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
	
	private void collectSpawn() {
		int yPos = GameTimer.POINTS_INITIAL_YPOS, xPos, type;

		Random r = new Random();
		
		type = r.nextInt(3);
		xPos = r.nextInt(400);
		
		this.collect.add(new Collect(type, xPos, yPos));
	}
	
	private void avoidSpawn() {
		int yPos = GameTimer.BOMBS_INITIAL_YPOS, xPos, type;
		
		Random r = new Random();
		
		type = r.nextInt(2);
		xPos = r.nextInt(400);
		
		this.avoid.add(new Avoid(type, xPos, yPos));
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
        if (spawnElapsedTime > GameTimer.ITEM_SPAWN_DELAY) {
            this.collectSpawn();
            this.avoidSpawn();
            this.startSpawnItems = System.nanoTime();
        }
	}

	public GameStage getStage() {
		return stage;
	}
	
	
}
