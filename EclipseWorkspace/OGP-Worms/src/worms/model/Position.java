package worms.model;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * Class that contains and handles everything that has to do with the position of a worm.
 *
 */
public class Position {
	
	/**
	 * Constructor for the Position class.
	 * 
	 * @param 	x
	 * 			The x-coordinate.
	 * @param 	y
	 * 			The y-coordinate.
	 * @param 	direction
	 * 			The direction.
	 * 
	 * @effect	| setX(x)
	 * @effect	| setY(y)
	 * @effect	| setDirection(direction)
	 */
	public Position(double x, double y, double direction) {
		setX(x);
		setY(y);
		setDirection(direction);
	}
	
// {{ x
	
	private double x;

	@Basic
	/**
	 * Returns the x-coordinate.
	 * 
	 * @return	The x-coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Method to set the x-coordinate.
	 * 
	 * @param 	x
	 *          The requested x coordinate of the worm expressed in meters.
	 *          
	 * @post	The x-coordinate of the worm is changed to the given value.
	 * 			| new.getX() == x
	 * @throws 	ModelException
	 *          Throws a ModelException if the x coordinate is invalid.
	 *          | if ( ! isValidX( x ) )
	 *          |		throw new ModelException
	 */
	public void setX(double x) {
		if (!isValidX(x))
			throw new ModelException("Invalid x coordinate.");
		this.x = x;
	}
	
	/**
	 * Checks the validity of the specified x coordinate.
	 * 
	 * @param 	x
	 * 			The specified x coordinate expressed in meters.
	 * 
	 * @return 	False if x is NaN. True otherwise.
	 * 			| result = ( ! Double.isNaN(x) )
	 */
	public static boolean isValidX(double x) {
		return (!Double.isNaN(x));
	}
	
	// }}

// {{ y
	
	private double y;

	@Basic
	/**
	 * Returns the y-coordinate
	 * 
	 * @return	The y-coordinate
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Method to set the y-coordinate.
	 * 
	 * @param 	y
	 *          The requested y coordinate of the worm expressed in meters.
	 *          
	 * @post	The y-coordinate of the worm is changed to the given value.
	 * 			| new.getY() == y
	 * @throws 	ModelException
	 *          Throws a ModelException if the y coordinate is invalid.
	 *          | if ( ! isValidY( y ) )
	 *          |		throw new ModelException
	 */
	public void setY(double y) {
		if (!isValidX(y))
			throw new ModelException("Invalid y coordinate.");
		this.y = y;
	}
	
	/**
	 * Checks the validity of the specified y coordinate.
	 * 
	 * @param 	y
	 * 			The specified y coordinate expressed in meters.
	 * 
	 * @return 	False if y is NaN. True otherwise.
	 * 			| result = ( ! Double.isNaN(y) )
	 */
	public static boolean isValidY(double y) {
		return (!Double.isNaN(y));
	}
	
	// }}

// {{ direction

	private double direction;
	
	@Basic
	/**
	 * Returns the direction.
	 * 
	 * @return	The direction.
	 */
	public double getDirection() {
		return direction;
	}
	
	/**
	 * Method that sets the direction of the worm to the specified direction if
	 * the direction is valid.
	 * 
	 * @param	direction
	 * 			The specified direction expressed in radians.
	 * 
	 * @pre 	The specified direction has to be valid.
	 * 			| isValidDirection(direction)
	 * @post	The direction of the worm is changed in the given direction.
	 * 			| new.getDirection() == direction
	 */
	public void setDirection(double direction) {
		assert isValidDirection(direction);
		this.direction = direction;
	}
	
	/**
	 * Checks the validity of the specified direction.
	 * 
	 * @param	direction
	 *      	The direction of the worm, given as an angle in radians.
	 *      
	 * @return 	Return true if the direction lies between 0 and 2*pi, including
	 *         	0 and excluding 2*pi. Otherwise return false. 
	 *         	| return (direction >= 0 && direction < 2*Math.PI)
	 */
	public static boolean isValidDirection(double direction) {
		if (direction < 0 || direction >= 2 * Math.PI)
			return false;
		if (Double.isNaN(direction))
			return false;
		return true;
	}
	
	// }}
	
// {{ Trajectory
	
	/**
	 * 
	 * Calculates and returns the x and y position of a trajectory at a specified time.
	 * 
	 * @param 	force
	 * 			The force of the weapon that is fired.
	 * @param 	duration
	 * 			The duration of the exerted force.
	 * @param 	mass
	 * 			The mass of the projectile.
	 * @param 	g
	 * 			The gravitational accelleration.
	 * @param 	time
	 * 			The time at which the trajectory should be evaluated.
	 * 
	 * @return 	The x and y positions of the trajectory at a specified time.
	 * 
	 * @throws	ModelException
	 * 			Throws a ModelException if the time given is less than zero.
	 * 			| time < 0
	 */
	public double[] ballisticTrajectory(double force, double duration, double mass, double time) {
		if ( time < 0 )
			throw new ModelException("Time cannot be negative.");
		
		double g = World.getGravitationalAcceleration();
		double v0 = ( force / mass ) * duration;
		double deltaX = v0*Math.cos(getDirection()) * time;
		double deltaY = v0*Math.sin(getDirection()) * time - 
				        0.5*g*Math.pow(time, 2);
		double[] output = {getX()+deltaX, getY()+deltaY};
		return output;
		
	}
	
	/**
	 * Method to calculate the time it takes to perform the ballistic trajectory.
	 * 
	 * @param 	world
	 * 			The world the trajectory is calculated in.
	 * @param 	force
	 * 			The force of the weapon fired.
	 * @param 	duration
	 * 			The duration of the exerted force.
	 * @param 	mass
	 * 			The mass of the projectile.
	 * @param 	timeStep
	 * 			The timestep with which the trajectory is calculated.
	 * 
	 * @return	The time it takes to perform the ballistic trajectory.
	 */
	public double ballisticTrajectoryTime(World world, double force, double duration, double mass, double timeStep) {
		double time = 0;
		Position newPosition = new Position(getX(),getY(),getDirection());
		while ( world.isWithinBoundaries(newPosition.getX(), newPosition.getY()) && 
				world.isPassable(        newPosition.getX(), newPosition.getY()) ) {
			
			time = time + timeStep;
			double[] delta = ballisticTrajectory(force, duration, mass, time);
			
			newPosition.setX(getX()+delta[0]);
			newPosition.setY(getY()+delta[1]);
			
		}
		return (time-timeStep);
	}
	
	
	// }}
	
	
	
	
	
	
	
	
	
	
	

}
