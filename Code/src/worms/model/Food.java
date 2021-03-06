package worms.model;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;

@SuppressWarnings("unused")
public class Food {
	
	private double x;
	private double y;
	private final double radius = 0.20;
	
	private ArrayList<Food> foodCollection;
	
	private World world;
	
	public Food(World world, double x, double y) {
		setX(x);
		setY(y);
		foodCollection.add(this);
		world.setFood(this);
	}

	public double getX() {
		return this.x;
	}
	
	public boolean isValidX(double x) {
		return (!Double.isNaN(x));
	}
	
	public void setX(double x) throws ModelException {
		if (!isValidX(x))
			throw new ModelException("Invalid x-coordinate.");
		this.x = x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public boolean isValidY(double y) {
		return (!Double.isNaN(y));
	}
	
	public void setY(double y) throws ModelException {
		if (!isValidY(y))
			throw new ModelException("Invalid y-coordinate");
		this.y = y;
	}
	
	@Immutable
	public double getRadius() {
		return radius;
	}
	
	public Food getFoodAt(int index) {
		return foodCollection.get(index);
	}
	
	public ArrayList<Food> getAllFood() {
		return foodCollection;
	}


	
	

}
