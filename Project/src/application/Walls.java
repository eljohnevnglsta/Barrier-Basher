package application;


import java.util.Random;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class Walls extends Sprite{
	int value;
//	private Text text;
	private final static double WALLS_SPEED = GameTimer.BG_SPEED;
	private final static Image WALLS_IMAGE_RED = new Image("file:src//images/1.png", 125, 125, false, false);
	private final static Image WALLS_IMAGE_VIOLET = new Image("file:src//images/2.png", 125, 125, false, false);
	private final static Image WALLS_IMAGE_YELLOW = new Image("file:src//images/3.png", 125, 125, false, false);
	private final static Image WALLS_IMAGE_BLUE = new Image("file:src//images/4.png", 125, 125, false, false);
	
	
	public Walls(int type, int xPos, int yPos, int charStrength) {
		super(xPos, yPos);
		this.value = Walls.getRandom(charStrength);
		switch (type) {
		case 0: this.loadImage(Walls.WALLS_IMAGE_RED); break;
		case 1: this.loadImage(Walls.WALLS_IMAGE_VIOLET); break;
		case 2: this.loadImage(Walls.WALLS_IMAGE_YELLOW); break;
		default: this.loadImage(Walls.WALLS_IMAGE_BLUE);
		}
		TextField t = new TextField();
		t.setText(Integer.toString(this.value));

	}
	
	void move() {
		this.y += Walls.WALLS_SPEED;
		if(this.y >= GameStage.WINDOW_HEIGHT){	// if this monster passes through the bottom of the scene, set visible to false
			this.vanish();
		}
	}

//	public int getValue() {
//		return value;
//	}
//
//	public void setValue(int value) {
//		this.value = value;
//	}
//	
//	private void loadWall() {
//		this.loadImage(Walls.WALLS_IMAGE_RED);
//		this.loadImage(Walls.WALLS_IMAGE_VIOLET);
//		this.loadImage(Walls.WALLS_IMAGE_YELLOW);
//		this.loadImage(Walls.WALLS_IMAGE_BLUE);
//	}
	
	public static int getRandom(int strength) {
		  Random random = new Random();
		  int min = Math.max(1, strength/2);
		  int max = strength + 5;
		  
		  int temp = random.nextInt(max - min + 1) + min;
		  
		  return (temp);
		}

}
