package sample;

import java.io.Serializable;

public class Piece implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1355966955550090780L;
	private int id;
	private String color;
	private int position;
	private boolean isInMill;
	
	public Piece(String color, int id) {
		this.isInMill=false;
		this.position=0;
		this.color=color;
		this.id=id;
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
