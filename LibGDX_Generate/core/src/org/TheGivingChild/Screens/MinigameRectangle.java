package org.TheGivingChild.Screens;

import com.badlogic.gdx.math.Rectangle;

public class MinigameRectangle extends Rectangle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean occupied;
	private ChildSprite occupant;	
	
	public MinigameRectangle(float x, float y, float width, float height) {
		super(x,y,width,height);
		this.x = x;
		this.y = y;
	}
	
	public boolean isOccupied()
	{
		return occupied;
	}
	
	public ChildSprite getOccupant()
	{
		return occupant;
	}
	
	public void setOccupied(ChildSprite s)
	{
		System.out.println("Occupied");
		occupant = s;
		occupied = true;
	}
	
	public void empty()
	{
		occupied = false;
		occupant = null;
	}
	
}
