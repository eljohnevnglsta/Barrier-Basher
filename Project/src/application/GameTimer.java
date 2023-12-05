package application;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameTimer extends AnimationTimer{
	
	private GraphicsContext gc;
	private Scene theScene;
	private Background bg;
	private double backgroundY;
	private Character myCharacter;
	private final int speed = 1; 
	
	GameTimer(GraphicsContext gc, Scene theScene){
		this.gc = gc;
		this.theScene = theScene;
		this.myCharacter = new Character("Eljohn",190,600);
		
		this.bg = new Background(0, 0);
		//call method to handle mouse click event
		this.handleKeyPressEvent();
		this.setBG();
	}

	@Override
	public void handle(long currentNanoTime) {
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.redrawBackgroundImage();
		this.bg.moveBG();
		this.myCharacter.move();
		this.bg.render(this.gc);
		this.myCharacter.render(this.gc);
		
	}
	
	private void setBG() {
		this.bg.setDY(this.speed);
	}
	
    void redrawBackgroundImage() {
		// clear the canvas
        this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);

        // redraw background image (moving effect)
        this.backgroundY += this.speed;

        this.gc.drawImage(bg.BG_IMAGE, 0, this.backgroundY-this.bg.BG_IMAGE.getHeight() );
        this.gc.drawImage(bg.BG_IMAGE, 0, this.backgroundY );
        
        if (this.backgroundY >= GameStage.WINDOW_HEIGHT) {
        	this.backgroundY = GameStage.WINDOW_HEIGHT-this.bg.BG_IMAGE.getHeight();
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
		if(ke==KeyCode.UP) this.myCharacter.setDY(-3);                 

		if(ke==KeyCode.LEFT) this.myCharacter.setDX(-3);

		if(ke==KeyCode.DOWN) this.myCharacter.setDY(3);
		
		if(ke==KeyCode.RIGHT) this.myCharacter.setDX(3);
		
		System.out.println(ke+" key pressed.");
   	}
	
	//method that will stop the ship's movement; set the ship's DX and DY to 0
	private void stopMoving(KeyCode ke){
		this.myCharacter.setDX(0);
		this.myCharacter.setDY(0);
	}

	
}
