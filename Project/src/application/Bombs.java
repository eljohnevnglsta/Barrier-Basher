package application;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Bombs extends Sprite{
	int value;
	private final static double BOMBS_SPEED = GameTimer.BG_SPEED*3;
	private final static int IMAGE_SIZE = 50;
	private final static Image BOMBS_IMAGE_10 = new Image("file:src//images/poison.png", Bombs.IMAGE_SIZE, Bombs.IMAGE_SIZE, false, false);
	private final static Image BOMBS_IMAGE_15 = new Image("file:src//images/bomb.png", Bombs.IMAGE_SIZE, Bombs.IMAGE_SIZE, false, false);
	private MediaPlayer mediaPlayer;
	private Media media = new Media(getClass().getResource("/music/powerdown.mp3").toExternalForm());
	
	public Bombs(int type, int xPos, int yPos) {
		super(xPos, yPos);
		switch (type) {
		case 0: this.loadImage(Bombs.BOMBS_IMAGE_10); this.value = 10; break;
		default: this.loadImage(Bombs.BOMBS_IMAGE_15); this.value = 15;
		}
	}
	
	void move(){
		this.y += Bombs.BOMBS_SPEED;
		if(this.y >= GameStage.WINDOW_HEIGHT){
			this.vanish();
		}
	}
	
	void checkCollision(Character character) {
		if (this.collidesWith(character)) {
			System.out.println("Oh naur! " + character.getName() + " is hurt!");
			this.vanish();
			character.decStrength(Bombs.this.value);
			this.music();
		}
	}
	
	void music() {
	    this.mediaPlayer = new MediaPlayer(this.media);
        this.mediaPlayer.play();	
	}
}
