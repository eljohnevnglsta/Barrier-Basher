package application;

import java.util.Random;

import javafx.scene.image.Image;

public class Walls extends Sprite{
	int value;
	private final static double WALLS_SPEED = GameTimer.BG_SPEED;
	private final static Image WALLS_IMAGE_RED = new Image("file:src//images/1.png", 125, 125, false, false);
	private final static Image WALLS_IMAGE_VIOLET = new Image("file:src//images/2.png", 125, 125, false, false);
	private final static Image WALLS_IMAGE_YELLOW = new Image("file:src//images/3.png", 125, 125, false, false);
	private final static Image WALLS_IMAGE_BLUE = new Image("file:src//images/4.png", 125, 125, false, false);
	private boolean isPositive;
	
	public Walls(int type, int xPos, int yPos) {
		super(xPos, yPos);
//		 Random random = new Random();
//	        isPositive = random.nextBoolean();
//	        value = random.nextInt(10) + 1; // Random value between 1 and 10
//
//	        if (!isPositive) {
//	            value = -value; // Make it negative if isPositive is false
//	        }
		this.value = 10;
		switch (type) {
		case 0: this.loadImage(Walls.WALLS_IMAGE_RED); break;
		case 1: this.loadImage(Walls.WALLS_IMAGE_VIOLET); break;
		case 2: this.loadImage(Walls.WALLS_IMAGE_YELLOW); break;
		default: this.loadImage(Walls.WALLS_IMAGE_BLUE);
		}
	}
	
	void move() {
		this.y += Walls.WALLS_SPEED;
		if(this.y >= GameStage.WINDOW_HEIGHT){	// if this monster passes through the bottom of the scene, set visible to false
			this.vanish();
		}
	}
	
//	private void loadWall() {
//		this.loadImage(Walls.WALLS_IMAGE_RED);
//		this.loadImage(Walls.WALLS_IMAGE_VIOLET);
//		this.loadImage(Walls.WALLS_IMAGE_YELLOW);
//		this.loadImage(Walls.WALLS_IMAGE_BLUE);
//	}
	
	
}
