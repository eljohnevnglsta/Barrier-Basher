package application;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Collect extends Sprite {
	int value;
	private final static double POINTS_SPEED = GameTimer.BG_SPEED*2;
	private final static int IMAGE_SIZE = 50;
	private final static Image POINTS_IMAGE_5 = new Image("file:src//images/heart.png", Collect.IMAGE_SIZE, Collect.IMAGE_SIZE, false, false);
	private final static Image POINTS_IMAGE_10 = new Image("file:src//images/apple.png", Collect.IMAGE_SIZE, Collect.IMAGE_SIZE, false, false);
	private final static Image POINTS_IMAGE_15 = new Image("file:src//images/diamond.png", Collect.IMAGE_SIZE, Collect.IMAGE_SIZE, false, false);
	private MediaPlayer mediaPlayer;
	private Media media = new Media(getClass().getResource("/music/powerup.mp3").toExternalForm());
	
	public Collect(int type, int xPos, int yPos) {
		super(xPos, yPos);
		switch (type) {
		case 0: this.loadImage(Collect.POINTS_IMAGE_5); this.value = 5; break;
		case 1: this.loadImage(Collect.POINTS_IMAGE_10); this.value = 10; break;
		default: this.loadImage(Collect.POINTS_IMAGE_15); this.value = 15;
		}
	}
	
	void move(){
		this.y += Collect.POINTS_SPEED;
		if(this.y >= GameStage.WINDOW_HEIGHT){
			this.vanish();
		}
	}
	
	void checkCollision(Character character) {
		if (this.collidesWith(character)) {
			System.out.println(character.getName() + " increased his strength!");
			this.vanish();
			character.gainStrength(Collect.this.value);
			this.music();
		}
	}
	
	void music() {
	    this.mediaPlayer = new MediaPlayer(this.media);
	    this.mediaPlayer.setVolume(0.5);
        this.mediaPlayer.play();	
	}
}
