package application;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Avoid extends Sprite{
	int value;
	private final static double AVOID_SPEED = GameTimer.BG_SPEED*3;
	private final static int IMAGE_SIZE = 50;
	private final static Image AVOID_IMAGE_10 = new Image("file:src//images/spider.png", Avoid.IMAGE_SIZE, Avoid.IMAGE_SIZE, false, false);
	private final static Image AVOID_IMAGE_15 = new Image("file:src//images/snake.png", Avoid.IMAGE_SIZE, Avoid.IMAGE_SIZE, false, false);
	private MediaPlayer mediaPlayer;
	private Media media = new Media(getClass().getResource("/music/powerdown.mp3").toExternalForm());
	
	public Avoid(int type, int xPos, int yPos) {
		super(xPos, yPos);
		switch (type) {
		case 0: this.loadImage(Avoid.AVOID_IMAGE_10); this.value = 10; break;
		default: this.loadImage(Avoid.AVOID_IMAGE_15); this.value = 15;
		}
	}
	
	void move(){
		this.y += Avoid.AVOID_SPEED;
		if(this.y >= GameStage.WINDOW_HEIGHT){
			this.vanish();
		}
	}
	
	void checkCollision(Character character) {
		if (this.collidesWith(character)) {
			System.out.println("Oh naur! " + character.getName() + " is hurt!");
			this.vanish();
			character.decStrength(Avoid.this.value);
			this.music();
			character.chooseImg(0);
		}
	}

	
	void music() {
	    this.mediaPlayer = new MediaPlayer(this.media);
	    this.mediaPlayer.setVolume(0.5);
        this.mediaPlayer.play();	
	}
}
