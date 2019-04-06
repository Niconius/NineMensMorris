package sample;

import javafx.scene.image.Image;

public class Piece {

	private Image img;
	private int id;
	private String color;
	private int position;
	private boolean isInMill;
	
	public Piece(String color, int id, Image img) {
		this.isInMill=false;
		this.position=0;
		this.color=color;
		this.id=id;
		this.img=img;
	}
	
	public int getId() {
		return id;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setPosition(int newPosition) {
		this.position=newPosition;
	}
	
	public int getPosition() {
		return position;
	}
	
	public boolean getIsInMill() {
		return isInMill;
	}
	
	public void setIsInMill(boolean newState) {
		isInMill=newState;
	}
}
