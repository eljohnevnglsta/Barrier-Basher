package application;


import java.util.ArrayList;
import java.util.Random;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class Walls extends Sprite{
	int value;
//	private Text text;
	private final static double WALLS_SPEED = GameTimer.BG_SPEED;
	private final static int IMAGE_SIZE = 125;
	private final static Image WALLS_IMAGE_RED = new Image("file:src//images/1.png", Walls.IMAGE_SIZE, Walls.IMAGE_SIZE, false, false);
	private final static Image WALLS_IMAGE_VIOLET = new Image("file:src//images/2.png", Walls.IMAGE_SIZE, Walls.IMAGE_SIZE, false, false);
	private final static Image WALLS_IMAGE_YELLOW = new Image("file:src//images/3.png", Walls.IMAGE_SIZE, Walls.IMAGE_SIZE, false, false);
	private final static Image WALLS_IMAGE_BLUE = new Image("file:src//images/4.png", Walls.IMAGE_SIZE, Walls.IMAGE_SIZE, false, false);
	
	
	public Walls(int type, int xPos, int yPos, int charStrength) {
		super(xPos, yPos);
		this.value = Walls.getRandom(charStrength);
		switch (type) {
		case 0: this.loadImage(Walls.WALLS_IMAGE_RED); break;
		case 1: this.loadImage(Walls.WALLS_IMAGE_VIOLET); break;
		case 2: this.loadImage(Walls.WALLS_IMAGE_YELLOW); break;
		default: this.loadImage(Walls.WALLS_IMAGE_BLUE);
		}
	}
	
	void move() {
		this.y += Walls.WALLS_SPEED;
		if (this.y >= GameStage.WINDOW_HEIGHT){
			this.vanish();
		}
	}
	
	public static int getRandom(int strength) {
		  Random random = new Random();
		  int min = Math.max(1, strength/2);
		  int max = strength + 5;
		  int temp = random.nextInt(max - min + 1) + min;
		  return (temp);
	}	
}
