package application;

import javafx.scene.image.Image;

public class Character extends Sprite {
	private String name;
	private int strength;
	private boolean alive;
	
	public final static Image SHIP_IMAGE = new Image("file:src/images/eljohn.png",Character.SIZE,Character.SIZE,false,false);
	private final static int SIZE = 50;
}
