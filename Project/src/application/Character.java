package application;

import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Character extends Sprite{
	private String name;
	private Scene scene; 
	public int strength;
	Text strengthText;
	private boolean alive;
	public final static Image CHAR_IMAGE = new Image("file:src//images/ej.png", 51, 90,false,false);
	public final static Image HURT_IMAGE = new Image("file:src//images/hurt.png", 51, 90,false,false);
	public final static Image CHAR_RIGHT = new Image("file:src//images/ej_right.png", 51, 90,false,false);
	
	public Character(String name, int x, int y, Scene scene){
		super(x,y);
		this.scene = scene;
		this.name = name;
		this.strength = 100;
		this.alive = true;
		this.strengthText = new Text();
		this.chooseImg(2);
		
		this.setUpStrengthDisplay();
	}
	
	public void chooseImg(int type) {
		switch(type) {
		case 0: 
			this.loadImage(Character.HURT_IMAGE); 
			PauseTransition p1 = new PauseTransition(Duration.seconds(1));
			p1.setOnFinished(e -> this.loadImage(Character.CHAR_IMAGE));
			p1.play();
			break;
		case 1:
			this.loadImage(Character.CHAR_RIGHT);
			break;
		default: this.loadImage(Character.CHAR_IMAGE);
		}
		
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

	private void setUpStrengthDisplay() {
    	Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/SuperMario256.ttf"), 40);
    	this.strengthText.setFont(customFont);
    	this.strengthText.setFill(Color.rgb(238, 66, 102));
    	this.strengthText.setStroke(Color.BLACK);
    	this.strengthText.setStrokeWidth(2);
        this.strengthText.setX(this.x - 3); // Adjust the X position
        this.strengthText.setY(this.y + 120); // Adjust the Y position
        Group root = (Group) this.scene.getRoot(); // Assuming root is a Group, change the type if needed
        root.getChildren().add(strengthText);
	}
	
	void moveStrength() {
        this.strengthText.setText(Integer.toString(this.strength));
        this.strengthText.setX(this.x - 3);
        this.strengthText.setY(this.y + 120);
	}
	
    void checkWindowBoundaries(GameTimer gt) {
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
        
        if (this.y >= (GameStage.WINDOW_HEIGHT + (this.height/4))) {
            gt.stage.setupGameOver(1);
            
       }
    }
    
    void gainStrength(int increase){
    	this.strength+=increase;
    	System.out.println("STRENGTH: "+ this.strength);
    }
    
    void decStrength(int increase){
    	this.strength-=increase;
    	System.out.println("STRENGTH: "+ this.strength);
    }

}
