package worms.model;

import be.kuleuven.cs.som.annotate.*;

public class Food {
	
	/*public Food() {
				
		this(randX,randY);
	}*/
	
	/**
	 * Constructor for the food class.
	 * 
	 * @param 	x
	 * 			The x-coordinate of the food.
	 * @param 	y
	 * 			The y-coordinate of the food.
	 * 
	 * @effect	| setX(x)
	 * @effect	| setY(y)
	 */
	public Food(double x, double y) {
		setX(x);
		setY(y);
	}
	


// {{ x 

	private double x;
	
	/**
	 * Basic inspector for the x coordinate. 
	 * 
	 * @return this.x
	 */
	@Basic
	public double getX() {
		return this.x;
	}
	
	/**
	 * Checker for the x-coordinate.
	 * 
	 * @param 	x 
	 * 			The x-coordinate.
	 * 
	 * @return 	!isNaN(x)
	 */
	public boolean isValidX(double x) {
		return (!Double.isNaN(x));
	}
	
	/**
	 * Setter for the x-coordinate.
	 * 
	 * @param 	x
	 * 			x-coordinate.
	 * @throws 	ModelException
	 * 			| !isValid(x)
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
	 *  
	 * @return this.y
	 */
	@Basic
	public double getY() {
		return this.y;
	}
	
	/**
	 * Checker for the y-coordinate.
	 * 
	 * @param 	y 
	 * 			y-coordinate.
	 * 
	 * @return 	!isNaN(y)
	 */
	public boolean isValidY(double y) {
		return (!Double.isNaN(y));
	}
	
	/**
	 * Setter for the y-coordinate.
	 * 
	 * @param 	y
	 * 			y-coordinate.
	 * @throws 	ModelException
	 * 			| !isValid(y)
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
	 * 
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
	 * 
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
		if (hasAWorld())
			world.removeFood(this);
		terminated = true;
	}
	// }}

// {{ World Association

	private World world;
	
	@Basic
	/**
	 * Method to return the world.
	 * 
	 * @return	this.world
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * Method to set the world.
	 * 
	 * @param 	world
	 * 			The given world.
	 * 
	 * @post	|new.getWorld() == world
	 * @throws 	ModelException
	 * 			| if (!canHaveAsWorld(world))
	 *			| if (hasAWorld())
	 */
	public void setWorld(World world) throws ModelException {
		if (!canHaveAsWorld(world))
			throw new ModelException("Invalid world specified.");
		if (hasAWorld())
			throw new ModelException("Already has a world.");
		this.world = world;
	}
	
	/**
	 * Checks if the given world is valid.
	 * 
	 * @param 	world
	 * 			The given world.
	 * 
	 * @return	| if (world == null)
	 * 			|	return false
	 * 			| if (world.isTerminated())
	 * 			|	return false
	 * 			| else
	 * 			| 	return true
	 */
	public boolean canHaveAsWorld(World world) {
		if (world==null)
			return false;
		if (world.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Checks if the world is not null.
	 * 
	 * @return	| return (!(world == null))
	 */
	public boolean hasAWorld() {
		return(!(world==null));
	}
	
	/**
	 * Checks if the given world is already set.
	 * 
	 * @param 	world
	 * 			The given world.
	 * 
	 * @return	| return (this.world == world)
	 */
	public boolean hasAsWorld(World world) {
		return (this.world==world);
	}
	
	/**
	 * Removes the current world.
	 * 
	 * @post	| new.getWorld() == null
	 */
	public void removeWorld() {
		world = null;
	}
	
	// }}
	
	
}