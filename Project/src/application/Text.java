package application;

import javafx.scene.control.TextField;

public class Text{
	private final static double TEXT_SPEED = GameTimer.BG_SPEED;
	
	public Text (int value) {
		TextField t = new TextField();
		t.setText(Integer.toString(value));
	}
}
