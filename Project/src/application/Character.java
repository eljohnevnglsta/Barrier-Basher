package application;

import java.util.Random;
import javafx.scene.image.Image;

public class Character extends Sprite{
	private String name;
	private int strength;
	private boolean alive;
	
	public final static Image CHAR_IMAGE = new Image("file:src//images/ej.png",Character.CHAR_SIZE,Character.CHAR_SIZE,false,false);
	private final static int CHAR_SIZE = 100;

	public Character(String name, int x, int y){
		super(x,y);
		this.name = name;
		this.strength = 1;
		this.alive = true;
		
		this.loadImage(Character.CHAR_IMAGE);
	}
	//getters

	public boolean isAlive(){
		if(this.alive) return true;
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

}
