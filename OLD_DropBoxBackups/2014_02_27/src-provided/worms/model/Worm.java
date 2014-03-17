package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

//TODO class documentation
//TODO class invariants
//TODO are checkers public or private? The professor made them public, but why would they ever be accessed from outside the class?
public class Worm {

	private double x;
	private double y;
	private double direction;
	private double radius;
	private String name;

	private final double minimalRadius;
	private static final double density = 1062;
	private static final double gravitationalAcceleration = 9.80665;

	private int actionPoints;

	/**
	 * Constructor for the Worm class. Receives an x coordinate in meters, a y
	 * coordinate in meters, a direction in radians, a radius in meters and a
	 * name.
	 * 
	 * @param x
	 * 			The x coordinate expressed in meters.
	 * @param y
	 * 			The y coordinate expressed in meters.
	 * @param direction
	 * 			The direction expressed in radians.
	 * @param radius
	 * 			The radius expressed in meters.
	 * @param name
	 * 			The name of the worm.
	 * 
	 * @post 	...
	 * 			| new.getX() == x
	 * @post 	...
	 * 			| new.getY() == y
	 * @post 	...
	 * 			| if isValidDirection(direction) then new.getDirection() == direction
	 * @post 	...
	 * 			| if isValidRadius(radius) then new.getRadius() == radius
	 * @post 	...
	 * 			| if isValidName(name) then new.getName() == name
	 */
	public Worm(double x, double y, double direction, double radius, String name) {

		// TODO Document preconditions that have to be asserted and exceptions
		// that can be thrown in the setters.

		setPosition(x, y);

		setDirection(direction);
		setRadius(radius);
		setName(name);

		minimalRadius = 0.25;
		actionPoints = 0;
	}

	/**
	 * Basic inspector that returns the x coordinate of the worm.
	 * 
	 * @return The x coordinate of the worm.
	 */
	@Basic
	public double getX() {
		return this.x;
	}

	/**
	 * Basic inspector that returns the y coordinate of the worm.
	 * 
	 * @return The y coordinate of the worm.
	 */
	@Basic
	public double getY() {
		return this.y;
	}

	/**
	 * Method that sets the position of the worm to the requested x and y
	 * coordinates. This method throws an IllegalArgumentException when the x or
	 * y coordinate are invalid numbers or when the new coordinates would result
	 * in the worm being outside of the boundaries of the playing field.
	 * 
	 * @param x
	 *            The requested x coordinate of the worm expressed in meters.
	 * @param y
	 *            The requested y coordinate of the worm expressed in meters.
	 * @throws IllegalArgumentException
	 *             Throws an IllegalArgumentException when the x coordinate is
	 *             invalid.
	 *             | if ( ! isValidX( x ) )
	 *             | 	then throw new IllegalArgumentException
	 * @throws IllegalArgumentException
	 *             Throws an IllegalArgumentException when the y coordinate is
	 *             invalid.
	 *             | if ( ! isValidY( y ) )
	 *             | 	then throw new IllegalArgumentException
	 * @throws IllegalArgumentException
	 *             Throws an IllegalArgumentException when the new coordinates
	 *             would result in the worm being outside of the boundaries of
	 *             the playing field.
	 *             | if ( ! isWithinBoundaries( x, y ) )
	 *             | 	then throw new IllegalArgumentException
	 */
	public void setPosition(double x, double y) throws IllegalArgumentException {
		if (!isValidX(x))
			throw new IllegalArgumentException("Invalid x coordinate.");
		else if (!isValidY(y))
			throw new IllegalArgumentException("Invalid y coordinate.");
		else if (!isWithinBoundaries(x, y))
			throw new IllegalArgumentException("Would result in out-of-bounds.");
		else {
			this.x = x;
			this.y = y;
		}
	}

	/**
	 * Checks the validity of the specified x coordinate.
	 * 
	 * @param x
	 * 			The specified x coordinate expressed in meters.
	 * @return 	False if x is NaN. True otherwise.
	 * 			| result == ( ! Double.isNaN(x) )
	 */
	private static boolean isValidX(double x) {
		if (Double.isNaN(x))
			return false;
		else
			return true;
	}

	/**
	 * Checks the validity of the specified y coordinate.
	 * 
	 * @param y
	 *            The specified y coordinate expressed in meters.
	 * @return 	False if y is NaN. True otherwise.
	 * 			| result == ( ! Double.isNaN(y) )
	 */
	private static boolean isValidY(double y) {
		if (Double.isNaN(y))
			return false;
		else
			return true;
	}

	/**
	 * Checks whether the specified x and y coordinates would result in the worm
	 * still being within the boundaries of the playing field.
	 * 
	 * @param x
	 *            The specified x coordinate expressed in meters.
	 * @param y
	 *            The specified y coordinate expressed in meters.
	 * @return True
	 */
	private static boolean isWithinBoundaries(double x, double y) {
		// TODO Is this adaptability or implementing unnecessary features at
		// this moment in time?
		return true;
	}

	/**
	 * Basic inspector that returns the direction of the worm.
	 * 
	 * @return The direction of the worm.
	 */
	@Basic
	public double getDirection() {
		return this.direction;
	}

	/**
	 * Method that sets the direction of the worm to the specified direction if
	 * the direction is valid.
	 * 
	 * @param direction
	 *            The specified direction expressed in radians.
	 * @pre The specified direction has to be valid.
	 * 		| isValid(direction)
	 */
	public void setDirection(double direction) {
		assert isValidDirection(direction);
		this.direction = direction;
	}

	/**
	 * Checks the validity of the specified direction.
	 * 
	 * @param direction
	 *            The direction of the worm, given as an angle in radians.
	 * @return Return trues if the direction lies between 0 and 2*pi, including
	 *         0 and excluding 2*pi. Otherwise return false. 
	 *         | return (direction >= 0 && direction < 2*Math.PI)
	 */
	private static boolean isValidDirection(double direction) {
		// TODO Does this exclude NaN? If not, add an extra test for NaN.
		if (direction < 0 || direction >= 2 * Math.PI) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Basic inspector that returns the radius of the worm.
	 * 
	 * @return The radius of the worm.
	 */
	@Basic
	public double getRadius() {
		return this.radius;
	}

	/**
	 * Method that sets the radius of the worm to the specified radius if the
	 * radius is valid.
	 * 
	 * @param radius
	 *            The specified radius in meters.
	 * @throws IllegalArgumentException
	 *             Throws an IllegalArgumentException if the radius is not
	 *             valid.
	 *             | if ( ! isValidRadius(radius) )
	 *             | 	then throw new IllegalArgumentException
	 */
	public void setRadius(double radius) throws IllegalArgumentException {
		if (!isValidRadius(radius)) {
			throw new IllegalArgumentException("Invalid radius.");
		} else {
			this.radius = radius;
		}
	}

	/**
	 * Checks the validity of the specified radius.
	 * 
	 * @param radius
	 *            The specified radius expressed in meters.
	 * @return The radius must be at least getMinimalRadius().
	 * 			| Return ( radius >= getMinimalRadius() )
	 */
	private boolean isValidRadius(double radius) {
		if (radius < getMinimalRadius()) {
			// TODO Does this exclude NaN?
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Basic inspector that returns the minimal radius of worms.
	 * 
	 * @return The minimal radius of worms.
	 */
	@Basic
	@Immutable
	public double getMinimalRadius() {
		return minimalRadius;
	}

	/**
	 * Basic inspector that returns the density of worms.
	 * 
	 * @return The density of worms.
	 */
	@Basic
	@Immutable
	public static double getDensity() {
		return density;
	}

	/**
	 * Inspector that calculates and returns the mass of the worm.
	 * 
	 * @return The mass of the worm.
	 */
	public double getMass() {
		// TODO Should overflow be taken into account? How does Java handle
		// overflow in doubles?
		return (getDensity() * (4 / 3) * Math.PI * Math.pow(getRadius(), 3));
	}

	/**
	 * Basic inspector that returns the name of the worm.
	 * 
	 * @return The name of the worm.
	 */
	@Basic
	public String getName() {
		return this.name;
	}

	/**
	 * Method that sets the name of the worm to the specified name if the name
	 * is valid.
	 * 
	 * @param name
	 * 			The specified name of the worm.
	 * @throws IllegalArgumentException
	 * 			Throw an illegalArgumentException if the specified name is not
	 * 			valid.
	 * 			| if ( ! isValidName(name) )
 *              | 	then throw new IllegalArgumentException
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (!isValidName(name)) {
			throw new IllegalArgumentException("Invalid name specified.");
		} else {
			this.name = name;
		}
	}

	// TODO replace the throws by return false
	// A checker does not throw exceptions, the setter does that based on the
	// output of the checker. I think.
	/**
	 * Checks the validity of the specified name as a name of a worm.
	 * 
	 * @param name
	 *            The name that has to be checked.
	 * @return The new name contains no numbers or special characters, starts
	 *         with an upper case and contains at least two characters.
	 *         | if ( (!name.matches("[a-zA-Z\"\' ]*") && 
	 *         |      (!character.isUpperCase(name.charAt(0)) &&
	 *         |      (name.length() < 2) )
	 *         |     then return true
	 *         | else
	 *         |     return false
	 */
	private static boolean isValidName(String name) {
		if (!name.matches("[a-zA-Z\"\' ]*")) {
			return false;
		}
		if (!Character.isUpperCase(name.charAt(0))) {
			return false;
		}
		if (name.length() < 2) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Basic inspector that return the action points of the worm.
	 * 
	 * @return The action points of the worm.
	 */
	@Basic
	public int getActionPoints() {
		return actionPoints;
	}

	/**
	 * 
	 * @param actionPoints
	 * @throws IllegalArgumentException
	 */
	public void setActionPoints(int actionPoints) throws IllegalArgumentException {
		if ( ! isValidActionPoints(actionPoints) )
			throw new IllegalArgumentException("Invalid action points specified.");
		else
			this.actionPoints = actionPoints;
	}

	// TODO
	/**
	 * Checks the validity of the specified actionPoints.
	 * 
	 * @param actionPoints
	 *            The number of actionpoints of the worm.
	 * @return The number of actionpoints must be valid.
	 */
	private static boolean isValidActionPoints(int actionPoints) {
		return ( actionPoints >= 0 );
	}

	/**
	 * Moves the selected worm to the given coordinates.
	 * 
	 * @param steps
	 * 		The amount of steps the worm should move.
	 * @throws IllegalArgumentException
	 */
	public void activeMove(int steps) throws IllegalArgumentException {
		if ( steps < 0 ) {
			throw new IllegalArgumentException("The amount of steps must be larger than zero.");
		}
		//TODO for loop correct?
		// the assignment says movement occurs in steps. I think this means
		// that each step should be handled separately. And the action points
		// cost should be calculated for each step individually. At a later
		// point in time it may be necessary to do additional calculations
		// after each step (wall collisions and things like that).
		for(int i=1; i<=steps; i++){
			activeMoveSingleStep();
		}

	}
	
	/**
	 * Moves the worm a single step.
	 */
	public void activeMoveSingleStep() {
		//TODO exception when there are not enough action points to complete the move?
		double deltaX = getRadius() * Math.cos(getDirection());
		double deltaY = getRadius() * Math.sin(getDirection());
		int costAP = (int) ( Math.abs(Math.cos(getDirection())) + Math.abs(4*Math.sin(getDirection())) );
		int newAP = getActionPoints() - costAP;
		
		setPosition( getX()+deltaX, getY()+deltaY );
		//@note Must be worked out defensively, but "setActionPoints" already throws an error for an invalid value.
		setActionPoints(newAP);
		
	}
	


	/**
	 * Adds the specified additional direction to the direction of the worm.
	 * 
	 * @pre	The added direction should be not-nan.
	 * 		| ! Double.isNaN(additionalDirection)
	 * @pre	The added direction should be more than -pi and less than or equal
	 * 		to pi. Otherwise, too many action points are consumed. 
	 * 		| ( (additionalDirection > -Math.PI) && 
				(additionalDirection <= Math.PI) )
	 * @pre	There should be enough action points left to complete the turn.
	 * 		| isValidActionPoints( getActionPoints() - Math.ceil( 60*(Math.abs(additionalDirection)/(2*Math.PI)) ) )
	 * 
	 * @param additionalDirection
	 *            The additional direction of the worm.
	 */
	public void turn(double additionalDirection) {
		assert ( ! Double.isNaN(additionalDirection) );

		//TODO maybe allow all additional directions and us the "%" to limit the movement to -pi and pi?
		assert ( (additionalDirection > -Math.PI) && 
				 (additionalDirection <= Math.PI) );
		
		int costAP = (int) Math.ceil( 60*(Math.abs(additionalDirection)/(2*Math.PI)) );
		int newAP = getActionPoints() - costAP;
		
		//TODO How to write this in a precondition? Now it is quite long. And the local variables cannot be used for the precondition.
		assert isValidActionPoints(newAP);

		setDirection( (getDirection() + additionalDirection) % (2*Math.PI) );
		//This code will still be executed even if newAP < 0, because it is written nominally.
		setActionPoints(newAP);
		
	}
	
	/**
	 * Basic inspector that returns the gravitational acceleration.
	 * 
	 * @return Returns the gravitational acceleration.
	 */
	@Basic @Immutable
	public static double getGravitationalAcceleration() {
		return gravitationalAcceleration;
	}

	/**
	 * Makes the selected worm jump.
	 * 
	 * @post
	 */
	public void jump() {
		//TODO This method should be implemented defensively. But I don't see any exceptions here except for the setPosition, bu then the exceptions will be hendled in the method if I am not mistaken.
		//@note Whether or not the worm can actually jump will be evaluated in the "jumpStep" method.
		double[] delta = jumpStep(jumpTime());
		setPosition( getX()+delta[1], getY()+delta[2] );
		//@note Jumping consumes all remaining action points.
		setActionPoints(0);
	}
	
	/**
	 * Verifies whether a worm can jump in its current state. As of now, the 
	 * worm only has to be facing upwards.
	 * 
	 * @return Return whether the worm can jump in its current state or not.
	 * 			| return ( (0 <= getDirection()) && (getDirection()<=Math.PI) )
	 */
	//@note Add this method to avoid code duplication and for expandability.
	public boolean canJump() {
		return ( (0 <= getDirection()) && (getDirection()<=Math.PI) );
	}
	
	/**
	 * Calculates and returns the initial velocity of a jump of the worm.
	 * 
	 * @return The initial velocity of a jump of the worm.
	 */
	//@note Method to avoid code duplication.
	public double getJumpInitVel() {
		//@note In this method, there should be not difference whether the worm can or cannot jump.
		double force = 5*getActionPoints() + getMass()*getGravitationalAcceleration();
		return (force/getMass()) * 0.5;
	}

	
	/**
	 * Calculates and returns the time needed to perform the jump of the worm.
	 * 
	 * @return The time needed to perform the jump.
	 */
	public double jumpTime() {
		double v0 = getJumpInitVel();
		
		if ( Math.cos(getDirection()) == 0 ) {
			//The standard formula would divide by zero.
			//TODO Maybe also use this formula when cos < 0.5 for numerical stability?
			//TODO Should we use this extra formula or throw an exception? Would this be considered total programming instead of defensive programming or is this just common sense? We could ask that during one of the session with an assistant.
			return (2*(v0*Math.sin(getDirection()))) / getGravitationalAcceleration();
		}
		else {
			double d = (Math.pow(v0, 2)*Math.sin(2*getDirection())) / getGravitationalAcceleration();
			return ( d / (v0*Math.cos(getDirection())) );
		}
	}
	
	/**
	 * Calculates and returns the x and y position of the worm during the jump at a specified time.
	 * @param time
	 * 			The time at which the jump should be evaluated.
	 * @return The x and y positions of the worm during the jump at a specified time.
	 * @throws IllegalArgumentException
	 * 			...
	 * 			| time < 0
	 * @throws IllegalArgumentException
	 * 			...
	 * 			| time > jumpTime()
	 */
	public double[] jumpStep(double time) throws IllegalArgumentException {
		if ( time < 0 ) {
			throw new IllegalArgumentException("Time cannot be negative.");
		}
		else if ( time > jumpTime() ) {
			throw new IllegalArgumentException("Cannot evaluate the trajectory after the end of the jump.");
		}
		else {
			//Only jump if the worm is facing upwards.
			if ( canJump() ) {
				double v0 = getJumpInitVel();
				double deltaX = v0*Math.cos(getDirection()) * time;
				double deltaY = v0*Math.sin(getDirection()) * time - 
						        0.5*getGravitationalAcceleration()*Math.pow(time, 2);
				double[] output = {deltaX, deltaY};
				return output;
			}
			else {
				double[] output = {0,0};
				return output;
			}
		}
	}

}
