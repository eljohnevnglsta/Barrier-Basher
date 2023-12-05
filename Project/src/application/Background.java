package application;

import javafx.scene.image.Image;

public class Background extends Sprite {
	public final static Image BG_IMAGE = new Image("file:src//images/bg.png",480,800,false,false);
	private boolean stopMoving = false;
	private int initialY;
	
	public Background(int xPos, int yPos) {
		super(xPos, yPos);
		this.initialY = yPos; 
		this.loadImage(Background.BG_IMAGE);
	}

	public void moveBG() {
    	this.x += this.dx;
    	this.y += this.dy;
    	
        if (this.y >= GameStage.WINDOW_HEIGHT) {
            // Reset the background position to its initial position
            this.y = initialY;
        }
	}
	
	public void stop(){
    	this.stopMoving = true;
    }

	public boolean isStopMoving() {
		return stopMoving;
	}
}
