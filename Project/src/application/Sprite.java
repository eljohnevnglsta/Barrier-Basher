package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite {
	protected Image img;
	protected int x, y, dx, dy;
	protected boolean visible;
	protected double width;
	protected double height;
}
