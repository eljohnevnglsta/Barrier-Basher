package application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
	private Button backFromGame; 
	
	private Image died = new Image("file:src/images/dead.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	private Image stuck = new Image("file:src/images/stuck.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	private Image mainMenu = new Image("file:src/images/main.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	private Image aboutDev = new Image("file:src/images/about.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	private Image howToPlay = new Image("file:src/images/how.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	private Image finish = new Image("file:src/images/win.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	
	private Media mediaMain = new Media(getClass().getResource("/music/bgm.mp3").toExternalForm());
	private Media mediaGame = new Media(getClass().getResource("/music/gamemusic.mp3").toExternalForm());
	private Media mediaOver = new Media(getClass().getResource("/music/gameover.mp3").toExternalForm());
	private Media mediaButton = new Media(getClass().getResource("/music/button.mp3").toExternalForm());
	private Media mediaFinish = new Media(getClass().getResource("/music/finish.mp3").toExternalForm());
	private Media mediaAbout = new Media(getClass().getResource("/music/yuyuyu.mp3").toExternalForm());
	
	private MediaPlayer mediaPlayerMain;
	private MediaPlayer mediaPlayerGame;
	private MediaPlayer mediaPlayerOver;
	private MediaPlayer mediaPlayerButton;
	private MediaPlayer mediaPlayerFinish;
	private MediaPlayer mediaPlayerAbout;
	
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
		this.backFromGame = new Button(); 
		this.back = new Button();
		
		this.musicMain();
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
		setupButton(this.back, 97, 29, 11, 10);
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
		
		//set stage elements here	     
		this.root.getChildren().add(canvas);
		this.stage.setTitle("Barrier Basher");	
		this.stage.setScene(this.scene);
		
		this.setupMain();
		this.mainMenu();
	}
	
	public void setupGameOver(int type) {
		this.musicOver();
		this.mediaPlayerGame.stop();
		this.gametimer.stop();
		this.clearScene();
		
		switch (type) {
			case 0: this.switchScene(this.died); break; // bombed
			default: this.switchScene(this.stuck); // got stuck
		}
		
		this.root.getChildren().add(this.backFromGame);
		this.setupButton(this.backFromGame, 300, 100, 90, 515);
		this.root.getChildren().remove(this.back);
	}
	
	public void setupFinish() {
		this.musicFinish();
		this.mediaPlayerGame.stop();
		this.gametimer.stop();
		this.clearScene();
		this.switchScene(this.finish);
		this.root.getChildren().add(this.backFromGame);
		this.setupButton(this.backFromGame, 300, 100, 90, 515);
		this.root.getChildren().remove(this.back);
	}
	
	public void mainMenu() {
		this.stage.show();
		this.play.setOnMouseClicked(event -> {
			this.clickButton();
			this.clearScene();
			this.gametimer = new GameTimer(this,this.gc,this.scene);
			this.musicGame();
			this.mediaPlayerMain.stop();
			this.gametimer.start();
		});
		this.about.setOnMouseClicked(event -> {
			this.clickButton();
			this.musicAbout();
			this.mediaPlayerMain.stop();
			this.switchScene(this.aboutDev);
		});
		this.how.setOnMouseClicked(event -> {
			this.clickButton();
			this.switchScene(this.howToPlay);
		});
		this.back.setOnMouseClicked(event -> {
			this.clickButton();
			this.clearScene();
			this.setupMain();
			this.mediaPlayerMain.play();
			this.mediaPlayerAbout.stop();
		});
		this.backFromGame.setOnMouseClicked(event -> {
			this.clickButton();
			this.clearScene();
			this.setupMain();
			this.mediaPlayerMain.play();
		});
	}
	
	void musicMain() {
	    this.mediaPlayerMain = new MediaPlayer(this.mediaMain);
	    this.mediaPlayerMain.setVolume(0.70);
        this.mediaPlayerMain.setCycleCount(MediaPlayer.INDEFINITE); // Play the music indefinitely
        this.mediaPlayerMain.play();
	}
	
	void musicGame() {
	    this.mediaPlayerGame = new MediaPlayer(this.mediaGame);
	    this.mediaPlayerGame.setVolume(0.65);
        this.mediaPlayerGame.setCycleCount(MediaPlayer.INDEFINITE); // Play the music indefinitely
        this.mediaPlayerGame.play();
	}
	
	void musicOver() {
	    this.mediaPlayerOver = new MediaPlayer(this.mediaOver);
	    this.mediaPlayerOver.setVolume(0.50);
        this.mediaPlayerOver.play();	
	}
	
	void musicFinish() {
	    this.mediaPlayerFinish = new MediaPlayer(this.mediaFinish);
	    this.mediaPlayerFinish.setVolume(0.50);
        this.mediaPlayerFinish.play();	
	}
	
	void musicAbout() {
	    this.mediaPlayerAbout = new MediaPlayer(this.mediaAbout);
	    this.mediaPlayerAbout.setVolume(0.65);
        this.mediaPlayerAbout.setCycleCount(MediaPlayer.INDEFINITE); // Play the music indefinitely
        this.mediaPlayerAbout.play();
	}
	
	void clickButton() {
	    this.mediaPlayerButton = new MediaPlayer(this.mediaButton);
	    this.mediaPlayerButton.setVolume(0.20);
        this.mediaPlayerButton.play();
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
