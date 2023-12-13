package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class GameTimer extends AnimationTimer{
	
	private GraphicsContext gc;
	private GameStage stage; 
	private Scene theScene;
	private Background bg;
	private double backgroundY;
	private Character myCharacter;
	private ArrayList<Walls> walls;
	private long startSpawn;
	private boolean disableUP = false;
	public boolean isGameOver = false; 
	
	public final static int CHARACTER_SPEED = 4;
	public final static int WALLS_INITIAL_YPOS = -100;
	public final static int WIDTH_PER_WALLS = 125;
	public final static double SPAWN_DELAY = 5;
	public final static int BG_SPEED = 3; 
	
	GameTimer(GameStage stage, GraphicsContext gc, Scene theScene){
		this.stage = stage; 
		this.gc = gc;
		this.theScene = theScene;
		this.myCharacter = new Character("Eljohn",190,600);
		this.startSpawn = System.nanoTime();
		
		this.bg = new Background(0, 0);
		this.walls = new ArrayList<Walls>();
		
		//call method to handle mouse click event
		this.handleKeyPressEvent();
		this.setBG();
	}

	@Override
	public void handle(long currentNanoTime) {
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.redrawBackgroundImage();
		this.autoSpawn(currentNanoTime);
		this.moveSprites();	
		this.renderSprites();
		this.handleCollisions();
		if (this.isGameOver) this.stage.setupGameOver();
	}
	
	private void handleCollisions() {
	    for (Walls wall : walls) {
	        if (myCharacter.collidesWith(wall)) {
	        	if (this.myCharacter.strength <= 0) {this.isGameOver = true; break;}
	        	System.out.println("WALL VALUE: " + wall.value);
	        	if (wall.value > 0) {
		        	this.disableUP = true; 
		        	this.myCharacter.setDY(GameTimer.BG_SPEED);
	        		this.myCharacter.strength--; 
		        	wall.value--;
	        	} else {
	        		System.out.println("STRENGTH: " + this.myCharacter.strength);
	        		wall.visible = false;
	        		this.disableUP = false; 
	        		this.myCharacter.setDY(0);
	        		break;
	        	}
	        	
	        } 
	    }
	}
	
	private void moveSprites() {
		this.moveWalls();
		this.bg.moveBG();
		this.myCharacter.move();
	}
	
	private void renderSprites() {
		this.bg.render(this.gc);
		this.renderWalls();
		this.myCharacter.render(this.gc);
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
        	
        }
	}
	
	private void wallsSpawn() {
	    int yPos = GameTimer.WALLS_INITIAL_YPOS;
	    
	    
	    // Spawn walls at the center
//	    this.walls.add(new Walls(xPos, yPos));

	    // You can adjust the pattern based on your requirements
	    // For example, spawn additional walls at specific intervals
	    // Adjust the pattern based on your desired rhythmic behavior

	    // Example: Spawn a wall every 5 seconds
	    if ((System.nanoTime() - this.startSpawn) / 1000000000.0 > 5.0) {
		    this.walls.add(new Walls(0, -5, yPos, this.myCharacter.strength));
		    this.walls.add(new Walls(1, 116, yPos, this.myCharacter.strength));
		    this.walls.add(new Walls(2, 238, yPos, this.myCharacter.strength));
		    this.walls.add(new Walls(3, 360, yPos, this.myCharacter.strength));
		    this.startSpawn = System.nanoTime();
	    }
	}


	private void autoSpawn(long currentNanoTime) {
    	double spawnElapsedTime = (currentNanoTime - this.startSpawn) / 1000000000.0;
        // spawn walls
        if(spawnElapsedTime > GameTimer.SPAWN_DELAY) {
        	this.wallsSpawn();
        	this.startSpawn = System.nanoTime();
        }
	}
	
	
}
