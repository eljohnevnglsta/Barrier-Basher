package application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameStage {
	public static final int WINDOW_HEIGHT = 800;
	public static final int WINDOW_WIDTH = 480;
	private Scene scene;
	private Stage stage;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;
	private Button play;
	private Button about;
	private Button how;
	private Button back;
	private Image mainMenu = new Image("file:src/images/menu.png",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	private Image aboutDev = new Image("file:src/images/about.png",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	private Image howToPlay = new Image("file:src/images/how.png",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	private Image gameProper = new Image("file:src/images/game.png",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	
	public GameStage () {
		this.root = new Group();
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);	
		this.gc = canvas.getGraphicsContext2D();
		
		//Scenes
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.CADETBLUE);
		
		//buttons
		this.play = new Button();
		this.about = new Button();
		this.how = new Button();
		this.back = new Button("Go Back");

	}
	
	private void setupButton(Button button, int sX, int sY, int pX, int pY) {
		button.setPrefSize(sX, sY);
		button.setLayoutX(pX);
		button.setLayoutY(pY);
		button.setOpacity(0); 
	}
	
	private void setupMain() {
		this.root.getChildren().add(new ImageView(mainMenu)); 
		this.root.getChildren().add(this.play);
		this.root.getChildren().add(this.about);
		this.root.getChildren().add(this.how);
		
		setupButton(this.play, 300, 100, 90, 515);
		setupButton(this.about, 201, 70, 27, 631);
		setupButton(this.how, 201, 70, 245, 631);
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
		
		//set stage elements here	     
		this.root.getChildren().add(canvas);
		this.stage.setTitle("Barrier Basher");	
		this.stage.setScene(this.scene);
		
		setupMain();
		
		//invoke the start method of the animation timer
		//this.gametimer.start();
	}
	
	public void mainMenu() {
		this.stage.show();
		this.play.setOnMouseClicked(event -> {
			switchScene(gameProper);
		});
		this.about.setOnMouseClicked(event -> {
			switchScene(aboutDev);
		});
		this.how.setOnMouseClicked(event -> {
			switchScene(howToPlay);
		});
		this.back.setOnMouseClicked(event -> {
			clearScene();
			setupMain();
		});
	}
	
	private void clearScene() {
	    // Clear the root group
	    this.root.getChildren().clear();
	    
	    // Add the canvas back to the root
	    this.root.getChildren().add(canvas);
	}
	
	private void switchScene(Image image) {
		gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.root.getChildren().add(new ImageView(image));
		this.root.getChildren().add(this.back);
	}
}