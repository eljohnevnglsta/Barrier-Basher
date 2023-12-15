package application;

import java.util.Random;
import javafx.scene.image.Image;

public class Character extends Sprite{
	private String name;
	public int strength;
	private boolean alive;
	private final static int length = 90;
	private final static int width = 51;
	
	public final static Image CHAR_IMAGE = new Image("file:src//images/ej.png",Character.width,Character.length,false,false);

	public Character(String name, int x, int y){
		super(x,y);
		this.name = name;
		this.strength = 100;
		this.alive = true;
		
		this.loadImage(Character.CHAR_IMAGE);
	}

	public boolean isAlive(){
		if (this.alive) return true;
		return false;
	} 
	
	public String getName(){
		return this.name;
	}

	public void die(){
    	this.alive = false;
    }

	public void move() {
    	this.x += this.dx;
    	this.y += this.dy;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

    void checkWindowBoundaries() {
        // Check if the character is going out of the left boundary
        if (this.x < 0) {
            this.x = 0; // Set the character's position to the left boundary
        }

        // Check if the character is going out of the right boundary
        if (this.x > (GameStage.WINDOW_WIDTH - this.width)) {
            this.x = (int) (GameStage.WINDOW_WIDTH - this.width); // Set the character's position to the right boundary
        }

        // Check if the character is going out of the top boundary
        if (this.y < 0) {
            this.y = 0; // Set the character's position to the top boundary
        }

        // Check if the character is going out of the bottom boundary
        if (this.y > (GameStage.WINDOW_HEIGHT - this.height)) {
            this.y = (int) (GameStage.WINDOW_HEIGHT - this.height); // Set the character's position to the bottom boundary
        }
    }
}
