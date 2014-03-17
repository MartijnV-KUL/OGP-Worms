package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class with the implementation of different methods to 
 * execute the commands for each worm.
 * 
 * @invar	The position of each worm is a valid position.
 * 			| isValidX(getX())
 * 			| isValidY(getY())
 * @invar	The direction each worm is facing is a valid direction.
 * 			| isValidDirection(getDirection())
 * @invar	The radius of each worm is a valid radius.
 * 			| isValidRadius(getRadius())
 * @invar	The name of each worm is a valid name.
 * 			| isValidName(getName())
 * @invar	The amount of actionpoints each worm has is valid.
 * 			| isValidActionPoints(getActionPoints())
 * 
 * @author Niels Claes
 * 
 */
public class Worm {

	private double x;
	private double y;
	private double direction;
	private double radius;
	private String name;

	private final double minimalRadius = 0.25;
	private static final double density = 1062;
	private static final double gravitationalAcceleration = 9.80665;

	private int actionPoints;
	private int maxActionPoints;

	
	/**
	 * Constructor for the Worm class. Receives an x coördinate in meters, a y
	 * coördinate in meters, a direction in radians, a radius in meters and a name.
	 * 
	 * @param 	x
	 * 			The x coördinate expressed in meters.
	 * @param 	y
	 * 			The y coördinate expressed in meters.
	 * @param 	direction
	 * 			The direction expressed in radians.
	 * @param 	radius
	 * 			The radius expressed in meters.
	 * @param 	name
	 * 			The name of the worm.
	 * 
	 * @pre		The given X and Y coördinates must be a valid position.
	 * 			| isValidX(x)
	 * 			| isValidY(y)
	 * @pre		The given direction is valid.
	 * 			| isValidDirection(direction)
	 * @pre		The given radius is valid.
	 * 			| isValidRadius(radius)
	 * @pre		The given name is valid.
	 * 			| isValidName(name)
	 * @effect 	The X and Y coördinates of the worm are set to the given values.
	 * 			| new.getX() == x && new.getY() == y
	 * @effect 	The direction of the worm is set to the given value.
	 * 			| new.getDirection() == direction
	 * @effect 	The radius of the worm is equal to the given value.
	 * 			| new.getRadius() == radius
	 * @effect 	The name of the worm is equal to the given string if it is valid.
	 * 			| new.getName() == name
	 * @effect	The amount of action points is set to the maximum number of actionpoints.
	 * 			This is done in the method setRadius, by the method resetMaxActionPoints.
	 * 			| new.getActionPoints() == maxActionPoints;
	 * @note	The setters will throw an exception if the given value is not valid.
	 */
	public Worm(double x, double y, double direction, double radius, String name) {
		setPosition(x, y);
		setDirection(direction);
		setRadius(radius);
		setName(name);
	}

	/**
	 * Basic inspector that returns the x coördinate of the worm.
	 * 
	 * @return The x coördinate of the worm.
	 */
	@Basic
	public double getX() {
		return this.x;
	}

	/**
	 * Basic inspector that returns the y coördinate of the worm.
	 * 
	 * @return The y coördinate of the worm.
	 */
	@Basic
	public double getY() {
		return this.y;
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
	 * Basic inspector that returns the radius of the worm.
	 * 
	 * @return The radius of the worm.
	 */
	@Basic
	public double getRadius() {
		return this.radius;
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
	 * Inspector that calculates and returns the mass of the worm.
	 * 
	 * @return The mass of the worm.
	 */
	public double getMass() {
		return (4 * getDensity() * Math.PI * Math.pow(getRadius(), 3) / 3);
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
	 * Basic inspector that returns the action points of the worm.
	 * 
	 * @return The action points of the worm.
	 */
	@Basic
	public int getActionPoints() {
		return actionPoints;
	}

	/**
	 * Basic inspector that returns the maximum amount of action points of the worm.
	 * 
	 * @return	The maximum amount of action points of the worm.
	 */
	@Basic
	public int getMaxActionPoints()	{
		return maxActionPoints;
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
	 * Basic inspector that returns the gravitational acceleration.
	 * 
	 * @return Returns the gravitational acceleration.
	 */
	@Basic @Immutable
	public static double getGravitationalAcceleration() {
		return gravitationalAcceleration;
	}

	/**
	 * Method that sets the position of the worm to the requested x and y
	 * coordinates. This method throws a ModelException when the x or
	 * y coördinate are invalid numbers or when the new coördinates would result
	 * in the worm being outside of the boundaries of the playing field.
	 * 
	 * @param 	x
	 * 			The requested x coördinate of the worm expressed in meters.
	 * @param 	y
	 *          The requested y coördinate of the worm expressed in meters.
	 *          
	 * @post	The x-coördinate of the worm is changed to the given value.
	 * 			| new.getX() == x
	 * @post	The y-coördinate of the worm is changed to the given value.
	 * 			| new.getY() == y
	 * @throws	ModelException
	 *          Throws a ModelException if the x coördinate is invalid.
	 *          | if ( ! isValidX( x ) )
	 *          |		then throw new ModelException
	 * @throws 	ModelException
	 *          Throws a ModelException if the y coördinate is invalid.
	 *          | if ( ! isValidY( y ) )
	 *          |		then throw new ModelException
	 */
	public void setPosition(double x, double y) throws ModelException {
		if (!isValidX(x))
			throw new ModelException("Invalid x coordinate.");
		if (!isValidY(y))
			throw new ModelException("Invalid y coordinate.");
		this.x = x;
		this.y = y;
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
	 * Method that sets the radius of the worm to the specified radius if the
	 * radius is valid.
	 * 
	 * @param 	radius
	 *          The specified radius in meters.
	 *          
	 * @post	The new radius of the worm is changed in the given radius.
	 * 			| new.getRadius() == radius
	 * @effect	The maximum amount of actionpoints is reset after the radius is changed,
	 * 			because the maximum amount of actionpoints depends on the radius.
	 * 			| resetMaxActionPoints()
	 * @throws 	ModelException
	 *         	Throws a ModelException if the radius is not valid.
	 *          | if ( ! isValidRadius(radius) )
	 *          | 		then throw new ModelException
	 */
	public void setRadius(double radius) throws ModelException {
		if (!isValidRadius(radius))
			throw new ModelException("Invalid radius.");
		this.radius = radius;
		resetMaxActionPoints();
	}


	/**
	 * Method that resets the maximum number of actionpoints.
	 * 
	 * @post	The maximum amount of actionpoints is equal to the mass of the worm,
	 * 			rounded to the nearest integer.
	 * 			| new.getMaxActionPoints() == (int) Math.round(getMass())
	 * @effect	The number of actionpoints is set to the maximum amount of actionpoints times the fraction.
	 * 			The result is rounded down to an integer.
	 * 			| setActionPoints( (int) Math.floor(getMaxActionPoints() * apFrac) )
	 * @note	apFrac is equal to 1 when the worm is first created, the if-loop prevents a DivideByZeroException
	 * 			at the initialisation of the game.
	 * 			During the rest of the game, apfrac is equal to the fraction of actionpoints the worm currently has left.
	 * 			This is necessary to maintain control over how the actionpoints are displayed when changing the worm's radius.
	 * 			Without this method the green bar of actionpoints would exceed the white bar of actionpoints when the radius is lowered.
	 */
	private void resetMaxActionPoints() {
		double apFrac = 1;
		if ( ! (getMaxActionPoints() == 0) )
			apFrac = ( (double) getActionPoints() ) / ( (double) getMaxActionPoints() );
		maxActionPoints = (int) Math.round(getMass());
		setActionPoints( (int) Math.floor( getMaxActionPoints() * apFrac ) );
	}

	/**
	 * Method that sets the name of the worm to the specified name if the name
	 * is valid.
	 * 
	 * @param 	name
	 * 			The specified name of the worm.
	 * 
	 * @post	The old name has changed to the given name (if it is valid).
	 * 			| new.getName() == name
	 * @throws 	ModelException
	 * 			Throw a ModelException if the specified name is not
	 * 			valid.
	 * 			| if ( ! isValidName(name) )
	 *          | 	then throw new ModelException
	 */
	public void setName(String name) throws ModelException {
		if (!isValidName(name))
			throw new ModelException("Invalid name specified.");
		this.name = name;
	}

	/**
	 * Method that sets the amount of actionpoints of the worm.
	 * 
	 * @param 	actionPoints
	 * 			The amount of actionpoints of the worm.
	 * 
	 * @post	The amount of actionpoints is set to the given value (if it is valid).
	 * 			| new.getActionPoints() == actionPoints
	 * @throws	ModelException
	 * 			Throws a ModelException if the specified number of actionPoints is not valid.
	 * 			| if ( ! isValidActionPoints(actionPoints) )
	 *          | 	then throw new ModelException
	 */
	public void setActionPoints(int actionPoints) throws ModelException {
		if ( ! isValidActionPoints(actionPoints) )
			throw new ModelException("Invalid action points specified.");
		this.actionPoints = actionPoints;
	}

	/**
	 * Checks the validity of the specified x coördinate.
	 * 
	 * @param 	x
	 * 			The specified x coördinate expressed in meters.
	 * 
	 * @return 	False if x is NaN. True otherwise.
	 * 			| result = ( ! Double.isNaN(x) )
	 */
	public static boolean isValidX(double x) {
		return (!Double.isNaN(x));
	}

	/**
	 * Checks the validity of the specified y coördinate.
	 * 
	 * @param 	y
	 *          The specified y coördinate expressed in meters.
	 *          
	 * @return 	False if y is NaN. True otherwise.
	 * 			| result = ( ! Double.isNaN(y) )
	 */
	public  static boolean isValidY(double y) {
		return (!Double.isNaN(y));
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

	/**
	 * Checks the validity of the specified radius.
	 * 
	 * @param	radius
	 * 			The specified radius expressed in meters.
	 * 
	 * @return 	The radius must be at least getMinimalRadius().
	 * 			| Return ( radius >= getMinimalRadius() )
	 */
	public boolean isValidRadius(double radius) {
		if (radius < getMinimalRadius())
			return false;
		if (Double.isNaN(radius))
			return false;
		return true;
	}

	/**
	 * Checks the validity of the specified name as a name of a worm.
	 * 
	 * @param 	name
	 *          The name that has to be checked.
	 *          
	 * @return 	The new name contains no numbers or special characters, starts
	 *         	with an upper case and contains at least two characters.
	 *         	| if ( (!name.matches("[a-zA-Z\"\' ]*") && 
	 *         	|      (!character.isUpperCase(name.charAt(0)) &&
	 *         	|      (name.length() < 2) )
	 *         	|     then return true
	 *         	| else
	 *         	|     return false
	 */
	public static boolean isValidName(String name) {
		if (!name.matches("[a-zA-Z\"\' ]*"))
			return false;
		if (!Character.isUpperCase(name.charAt(0)))
			return false;
		if (name.length() < 2)
			return false;
		return true;
	}

	/**
	 * Checks the validity of the specified actionpoints.
	 * 
	 * @param 	actionPoints
	 *        	The number of actionpoints of the worm.
	 *        
	 * @return 	True if the number of actionpoints lays between 0 and the maximum amount.
	 */
	public boolean isValidActionPoints(int actionPoints) {
		return ( actionPoints >= 0 && actionPoints <= getMaxActionPoints() );
	}

	/**
	 * Moves the selected worm to the given coordinates.
	 * 
	 * @param	steps
	 * 			The amount of steps the worm should move.
	 * 
	 * @effect	The worm moves with the given amount of steps through the method activeMoveSingleStep
	 * 			if the number of steps is valid.
	 * 			| activeMoveSingleStep()
	 * @throws 	ModelException
	 * 			Throws a ModelException if the number of steps is less than or
	 * 			equal to zero, or when the worm can not move.
	 * 			| if (steps <= 0)
	 * 			|		throw new ModelException
	 * 			| if (!canMove(steps))
	 * 			|		throw new ModelException
	 * @note	The assignment says that movement has to occur in steps. It is possible to modify this method so the worm
	 * 			can move with more than one step at the same time.
	 */
	public void activeMove(int steps) throws ModelException {
		if ( steps <= 0 )
			throw new ModelException("The amount of steps must be larger than zero.");
		if ( ! canMove(steps) )
			throw new ModelException("Not enough action points.");
		for(int i=1; i<=steps; i++) {
			activeMoveSingleStep();
		}
	}
	

	/**
	 * Moves the worm a single step.
	 * 
	 * @effect	The position of the worm is set to it's new coördinates.
	 * 			| setPosition( getRadius() * Math.cos(getDirection()), getRadius() * Math.sin(getDirection()) )
	 * @effect	The amount of actionpoints diminishes when moving the worm.
	 * 			| setActionPoints( getActionPoints() - getActionPointsCostMove(1) )
	 * @note	This method should be implemented in a defensive manner,
	 * 			but the methods "setActionPoints" and "setposition" already throw an error for an invalid value.
	 * 			No need to use double code for this exceptionhandling.
	 */
	public void activeMoveSingleStep() {
		double deltaX = getRadius() * Math.cos(getDirection());
		double deltaY = getRadius() * Math.sin(getDirection());
			
		setPosition( getX()+deltaX, getY()+deltaY );
		setActionPoints( getActionPoints() - getActionPointCostMove(1) );
	}
	
	/**
	 * Calculates the total amount of actionpoints it takes to perform a move.
	 * 
	 * @param 	nbSteps
	 * 			The number of steps the worm moves.
	 * 
	 * @return	The total cost of actionpoints to perform the move.
	 * @note	The cost of actionpoints is rather low, especially in comparison with the cost for turning.
	 */
	public int getActionPointCostMove(int nbSteps) {
		return nbSteps * (int) ( Math.abs(Math.cos(getDirection())) + Math.abs(4*Math.sin(getDirection())) );
	}

	/**
	 * Checks if a worm can perform the move.
	 * 
	 * @param	nbSteps
	 * 			The amount of steps the worm will move.
	 * 
	 * @return	True if there are enough actionpoints left to perform the move.
	 * @note	There are two lines of code, one is set into comments.
	 * 			The first line of code gives a better actionpoint-cost calculation.
	 * 			The second line can be used when an implementation is required in which
	 * 			the worm can move with more than 1 step at a time.
	 */
	public boolean canMove(int nbSteps) {
		if ( ! isValidActionPoints( getActionPoints() - (nbSteps*getActionPointCostMove(1)) ) )
			return false;
		return true;
	}
	
	/**
	 * Adds the specified additional direction to the direction of the worm.
	 * 
	 * @param 	additionalDirection
	 *          The additional direction of the worm.
	 *          
	 * @pre		The added direction should be not-nan.
	 * 			| ! Double.isNaN(additionalDirection)
	 * @pre		There should be enough action points left to complete the turn.
	 * 			| isValidActionPoints( getActionPoints() - Math.ceil( 60*(Math.abs(additionalDirection)/(2*Math.PI)) ) )
	 * @effect	The number of actionpoints diminishes when turning.
	 * 			| setActionPoints(getActionPoints() - getActionPointCostTurn(additionalDirection))
	 * @effect	The direction of the worm has changed.
	 * 			| setDirection(  (((getDirection() + additionalDirection) % (2*Math.PI)) + 2*Math.PI) % (2*Math.PI)  )
	 * @note	The last line of code prefents an assertion error.
	 * 			If only (getDirection() + additionalDirection) % (2*Math.PI) is used,
	 * 			it is possible that a negative argument is passed to setDirection. This is in conflict with the assertion of setDirection.
	 * 			To prevent this, 2*pi is added to the result and modulo 2*pi is taken again.
	 * 			This way the method setDirection will always receive a positive argument.

	 */
	public void turn(double additionalDirection) {
		assert ( canTurn(additionalDirection) );		
		setActionPoints(getActionPoints() - getActionPointCostTurn(additionalDirection));		
		setDirection( (((getDirection() + additionalDirection) % (2*Math.PI)) + 2*Math.PI) % (2*Math.PI) );
	}

	/**
	 * Calculates the amount of actionpoints it takes to turn the worm a given angle.
	 * 
	 * @param 	additionalDirection
	 * 			The given angle of the worm, expressed in radians.
	 * 
	 * @return	The total amount of actionpoints it takes to perform this turn.
	 */
	public int getActionPointCostTurn(double additionalDirection) {
		return (int) Math.ceil( 60*(Math.abs(additionalDirection)/(2*Math.PI)) );
	}
	


	/**
	 * Checks if the worm can turn or not.
	 * 
	 * @param	direction
	 * 			The direction of the worm.
	 * 
	 * @return	False if the given direcion is invalid, or if there are not enough actionpoints available.
	 */
	public boolean canTurn(double additionalDirection) {
		if ( Double.isNaN(additionalDirection) )
			return false;
		if ( ! (isValidActionPoints(getActionPoints() - getActionPointCostTurn(additionalDirection)) ) )
			return false;
		return true;
	}

	/**
	 * Makes the selected worm jump.
	 * 
	 * @effect	The amount of actionpoints is set to zero after performing the jump.
	 * 			| setActionPoints(0)
	 * @effect	The position of the worm is set to it's new coördinates, with newPos[0] the new x-coördinate.
	 * 			newPos[1] is the new y-coördinate. These points are calculated in jumpStep and jumpTime.
	 * 			| setPosition( newPos[0], newPos[1] )
	 * @note	The code "if (canJump())" prevents unnecessary calculations if the worm can't jump.
	 * @note	If the player tries to jump when it's not possible he will get "punished" for trying.
	 * 			The amount of actionpoints will always be set to 0, whether the worm actually jumps or not.
	 * 			This is a choice of implementation.
	 */
	public void jump() {
		double[] newPos = jumpStep(jumpTime());
		setPosition( newPos[0], newPos[1] );
		
		setActionPoints(0);
	}
	
	/**
	 * Calculates and returns the x and y position of the worm during the jump at a specified time.
	 * @param 	time
	 * 			The time at which the jump should be evaluated.
	 * 
	 * @return 	The x and y positions of the worm during the jump at a specified time.
	 * @throws	ModelException
	 * 			Throws a ModelException if the time given is less than zero.
	 * 			| time < 0
	 * @throws 	ModelException
	 * 			Throws a ModelException if the time given is larger than the time it takes to perform the jump.
	 * 			| time > jumpTime()
	 * @note	If the code "if (canJump())" is not present a trajectory will still be displayed by the GUI, even if the worm can't jump.
	 * 			This line is added to prevent the GUI from getting and displaying the trajectory if the worm can not jump.
	 */
	public double[] jumpStep(double time) throws ModelException {
		if ( time < 0 )
			throw new ModelException("Time cannot be negative.");
		if ( time > jumpTime() )
			throw new ModelException("Cannot evaluate the trajectory after the end of the jump.");
		if (canJump()) {
			double v0 = getJumpInitVel();
			double deltaX = v0*Math.cos(getDirection()) * time;
			double deltaY = v0*Math.sin(getDirection()) * time - 
					        0.5*getGravitationalAcceleration()*Math.pow(time, 2);
			double[] output = {getX()+deltaX, getY()+deltaY};
			return output;
		}
		double [] output = {getX(), getY()};
		return output;
	}

	/**
	 * Calculates and returns the initial velocity of a jump of the worm.
	 * 
	 * @return 	The initial velocity of a jump of the worm.
	 * @note	In this method there should be no difference whether the worm can or can not jump.
	 * @note	This piece of code is added to avoid code duplication in other methods and for adaptability in the future.
	 */
	public double getJumpInitVel() {		
		double force = 5*getActionPoints() + getMass()*getGravitationalAcceleration();
		return ( (force/getMass()) * 0.5 );
	}

	
	/**
	 * Calculates and returns the time needed to perform the jump of the worm.
	 * 
	 * @return 	The time needed to perform the jump.
	 * @note	The first if-loop prevents a division by zero in the standard formula.
	 */
	public double jumpTime() {
		double v0 = getJumpInitVel();
		
		if ( Math.cos(getDirection()) == 0 ) {
			return (2*(v0*Math.sin(getDirection()))) / getGravitationalAcceleration();
		}
		
		double distance = (Math.pow(v0, 2)*Math.sin(2*getDirection())) / getGravitationalAcceleration();
		return ( distance / (v0*Math.cos(getDirection())) );
	}
	
	/**
	 * Verifies whether a worm can jump in its current state. As of now, the 
	 * worm only has to be facing upwards.
	 * 
	 * @return 	True if the worm has actionpoints left and the direction is valid for a jump.
	 * 			To be valid to perform a jump, the direction must lay between 0 and 2*pi.
	 * 			| return ( (0 >= getDirection()) && (getDirection() <= Math.PI) && getActionPoints() > 0 )
	 * @note	This method can easily be adapted in the future to include other conditions.
	 */
	public boolean canJump() {
		if (getActionPoints() == 0) {
			return false;
		}
		if (getDirection() < 0) {
			return false;
		}
		if (getDirection() > Math.PI) {
			return false;
		}
		return true;
	}

}
