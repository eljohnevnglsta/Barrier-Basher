package application;

import java.util.Random;
import javafx.scene.image.Image;

public class Character extends Sprite{
	private String name;
	public int strength;
	private boolean alive;
	public final static Image CHAR_IMAGE = new Image("file:src//images/ej.png",51,90,false,false);

	public Character(String name, int x, int y){
		super(x,y);
		this.name = name;
		this.strength = 20;
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

}
