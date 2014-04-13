package worms.model;

import be.kuleuven.cs.som.annotate.*;

public class Food {
	
	public Food(double x, double y) {
		setX(x);
		setY(y);
	}

// {{ x 

	private double x;
	
	/**
	 * Basic inspector for the x coordinate. 
	 * @return this.x
	 */
	@Basic
	public double getX() {
		return this.x;
	}
	
	/**
	 * Checker for the x-coordinate.
	 * @param x x-coordinate
	 * @return !isNaN(x)
	 */
	public boolean isValidX(double x) {
		return (!Double.isNaN(x));
	}
	
	/**
	 * Setter for the x-coordinate
	 * @param x x-coordinate
	 * @throws ModelException
	 * 		| !isValid(x)
	 */
	public void setX(double x) throws ModelException {
		if (!isValidX(x))
			throw new ModelException("Invalid x-coordinate.");
		this.x = x;
	}
	// }}
	
// {{ y

	private double y;
	
	/**
	 * Basic inspector for the y coordinate. 
	 * @return this.y
	 */
	@Basic
	public double getY() {
		return this.y;
	}
	
	/**
	 * Checker for the y-coordinate.
	 * @param y y-coordinate
	 * @return !isNaN(y)
	 */
	public boolean isValidY(double y) {
		return (!Double.isNaN(y));
	}
	
	/**
	 * Setter for the y-coordinate
	 * @param y y-coordinate
	 * @throws ModelException
	 * 		| !isValid(y)
	 */
	public void setY(double y) throws ModelException {
		if (!isValidY(y))
			throw new ModelException("Invalid y-coordinate");
		this.y = y;
	}
	// }}

// {{ Radius

	private final double radius = 0.20;
	
	/**
	 * Basic inspector for the radius.
	 * @return this.radius
	 */
	@Basic @Immutable
	public double getRadius() {
		return radius;
	}
	// }}
	
// {{ Terminated

	private boolean terminated;
	
	/**
	 * Basic inspector for the terminated attribute.
	 * @return this.terminated
	 */
	@Basic
	public boolean isTerminated() {
		return terminated;
	}
	
	/**
	 * Method to terminate the object.
	 */
	public void terminate() {
		try {
			// if world is still "null" (because a food object can have 0 or 1 worlds, this throws a nullpointerexception
			world.removeFood(this);
		} catch (NullPointerException e) {
			// do nothing
		}
		terminated = true;
	}
	// }}

// {{ World Association

	private World world;
	
	@Basic
	public World getWorld() {
		return world;
	}
	
	/**
	 * Method to set the world.
	 * @param newWorld
	 * @throws ModelException
	 */
	public void setWorld(World newWorld) throws ModelException {
		if (!canHaveAsWorld(newWorld))
			throw new ModelException("Invalid world specified");
		if (hasAWorld())
			throw new ModelException("Already has a team.");
		world = newWorld;
	}
	
	/**
	 * Method to check whether the object can have a certain world as world.
	 * @param world
	 * @return
	 */
	public boolean canHaveAsWorld(World world) {
		if (world==null)
			return false;
		return true;
	}
	
	public boolean hasAsWorld(World world) {
		return (this.world == world);
	}
	
	public boolean hasAWorld() {
		return (!(world==null));
	}
	
	public void removeWorld() {
		world = null;
	}
	
	// }}
	
	
}
