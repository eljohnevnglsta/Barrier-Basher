package application;

import javafx.scene.image.Image;

public class Points extends Sprite {
	int value;
	private final static double POINTS_SPEED = GameTimer.BG_SPEED*2;
	private final static int IMAGE_SIZE = 50;
	private final static Image POINTS_IMAGE_5 = new Image("file:src//images/heart.png", Points.IMAGE_SIZE, Points.IMAGE_SIZE, false, false);
	private final static Image POINTS_IMAGE_10 = new Image("file:src//images/elixir.png", Points.IMAGE_SIZE, Points.IMAGE_SIZE, false, false);
	private final static Image POINTS_IMAGE_15 = new Image("file:src//images/diamond.png", Points.IMAGE_SIZE, Points.IMAGE_SIZE, false, false);
	
	
	public Points(int type, int xPos, int yPos) {
		super(xPos, yPos);
		switch (type) {
		case 0: this.loadImage(Points.POINTS_IMAGE_5); this.value = 5; break;
		case 1: this.loadImage(Points.POINTS_IMAGE_10); this.value = 10; break;
		default: this.loadImage(Points.POINTS_IMAGE_15); this.value = 15;
		}
	}
	
	void move(){
		this.y += Points.POINTS_SPEED;
		if(this.y >= GameStage.WINDOW_HEIGHT){
			this.vanish();
		}
	}
	
	void checkCollision(Character character) {
		if (this.collidesWith(character)) {
			System.out.println(character.getName() + " has collected an elixir!");
			this.vanish();
			character.gainStrength(Points.this.value);
		}
	}
}
