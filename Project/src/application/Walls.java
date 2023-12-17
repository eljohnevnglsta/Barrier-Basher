package application;

import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Walls extends Sprite{
	int value;
	Text valueText;
	Scene scene;
	private final static double WALLS_SPEED = GameTimer.BG_SPEED;
	public final static int IMAGE_SIZE = 120;
	private final static Image WALLS_IMAGE_RED = new Image("file:src//images/wall1.png", Walls.IMAGE_SIZE, Walls.IMAGE_SIZE, false, false);
	private final static Image WALLS_IMAGE_VIOLET = new Image("file:src//images/wall2.png", Walls.IMAGE_SIZE, Walls.IMAGE_SIZE, false, false);
	private final static Image WALLS_IMAGE_YELLOW = new Image("file:src//images/wall3.png", Walls.IMAGE_SIZE, Walls.IMAGE_SIZE, false, false);
	private final static Image WALLS_IMAGE_BLUE = new Image("file:src//images/wall4.png", Walls.IMAGE_SIZE, Walls.IMAGE_SIZE, false, false);
	
	public Walls(int type, int xPos, int yPos, int currentStrength, Scene scene) {
		super(xPos, yPos);
		this.value = Walls.getRandom(currentStrength);
		this.valueText = new Text();
		this.scene = scene;
		this.setUpWallValueDisplay();
		switch (type) {
		case 0: this.loadImage(Walls.WALLS_IMAGE_RED); break;
		case 1: this.loadImage(Walls.WALLS_IMAGE_VIOLET); break;
		case 2: this.loadImage(Walls.WALLS_IMAGE_YELLOW); break;
		default: this.loadImage(Walls.WALLS_IMAGE_BLUE);
		}
	}
	
	void move() {
		this.y += Walls.WALLS_SPEED;
		if (this.y >= GameStage.WINDOW_HEIGHT){	// if this monster passes through the bottom of the scene, set visible to false
			this.vanish();
			this.valueText.setVisible(false);
		}
	}
	
	private void setUpWallValueDisplay() {
		Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/SuperMario256.ttf"), 40);
    	this.valueText.setFont(customFont);
    	this.valueText.setFill(Color.rgb(238, 66, 102));
    	this.valueText.setStroke(Color.BLACK);
    	this.valueText.setStrokeWidth(2);
    	this.valueText.setX(this.x);
    	this.valueText.setX(this.y);
        Group root = (Group) this.scene.getRoot(); // Assuming root is a Group, change the type if needed
    	root.getChildren().add(this.valueText);
	}
	
	public void moveWallValueDisplay() {
        this.valueText.setText(Integer.toString(this.value));
        this.valueText.setX(this.x);
        this.valueText.setY(this.y);
	}
	
	public static int getRandom(int strength) {
		  Random random = new Random();
		  int min = Math.max(1, strength/2);
		  int max = strength + 5;
		  int temp = random.nextInt(max - min + 1) + min;
		  return (temp);
	}	
}
