package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

//TODO class documentation
//TODO class invariants
//TODO are checkers public or private? The professor made them public, but why would they ever be accessed from outside the class?
/**
 * 
 * @author 
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
	 * Basic constructor for the Worm class.
	 */
	//TODO Is this needed anywhere? Don't you always need to specify the properties?
	//public Worm() {}
	
	/**
	 * Constructor for the Worm class. Receives an x coordinate in meters, a y
	 * coordinate in meters, a direction in radians, a radius in meters and a name.
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
	 * 			| if isValidX(x) && isValidY(y) && isWithinBoundaries(x,y)
	 * 			| 	then new.getX() == x && new.getY() == y
	 * @post 	...
	 * 			| if isValidDirection(direction)
	 * 			| 	then new.getDirection() == direction
	 * @post 	...
	 * 			| if isValidRadius(radius)
	 * 			| 	then new.getRadius() == radius
	 * @post 	...
	 * 			| if isValidName(name)
	 * 			| 	then new.getName() == name
	 * @post	...
	 * 			| if isValidActionPoints(getMaxActionPoints())
	 * 			| 	then new.getActionPoints() == maxActionPoints;
	 */
	public Worm(double x, double y, double direction, double radius, String name) {

		// TODO Document preconditions that have to be asserted and exceptions
		// that can be thrown in the setters.

		setPosition(x, y);
		setDirection(direction);
		setRadius(radius);
		setName(name);
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
		// TODO Maybe handle overflow?
		/** @note 	To avoid overflow we can use Double.isInfinity(mass)
		 * 			Is it custom to handle exceptions in getters? */
		return (4 * getDensity() * Math.PI * Math.pow(getRadius(), 3) / 3);			//Om de een of andere reden werkt het niet als 4/3 samen staat? Update: Misschien omdat 4 & 3 gezien worden alsintegers en de breuk afgerond wordt? Is een interessante vraag voor de assistent.
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
	 * coordinates. This method throws an IllegalArgumentException when the x or
	 * y coordinate are invalid numbers or when the new coordinates would result
	 * in the worm being outside of the boundaries of the playing field.
	 * 
	 * @param x
	 * 			The requested x coordinate of the worm expressed in meters.
	 * @param y
	 *          The requested y coordinate of the worm expressed in meters.
	 * @throws	ModelException
	 *          Throws a ModelException when the x coordinate is invalid.
	 *          | if ( ! isValidX( x ) )
	 *          |		then throw new ModelException
	 * @throws 	ModelException
	 *          Throws a ModelException when the y coordinate is invalid.
	 *          | if ( ! isValidY( y ) )
	 *          |		then throw new ModelException
	 * @throws 	ModelException
	 *          Throws a ModelException when the new coordinates
	 *          would result in the worm being outside of the boundaries of
	 *          the playing field.
	 *          | if ( ! isWithinBoundaries( x, y ) )
	 *          |		then throw new ModelException
	 */
	public void setPosition(double x, double y) throws ModelException {
		if (!isValidX(x))
			throw new ModelException("Invalid x coordinate.");
		else if (!isValidY(y))
			throw new ModelException("Invalid y coordinate.");
		else if (!isWithinBoundaries(x, y))
			throw new ModelException("Would result in out-of-bounds.");
		else {
			this.x = x;
			this.y = y;
		}
	}

	/**
	 * Method that sets the direction of the worm to the specified direction if
	 * the direction is valid.
	 * 
	 * @param direction
	 *            The specified direction expressed in radians.
	 * @pre The specified direction has to be valid.
	 * 		| isValidDirection(direction)
	 */
	public void setDirection(double direction) {
		assert isValidDirection(direction);
		this.direction = direction;
	}

	/**
	 * Method that sets the radius of the worm to the specified radius if the
	 * radius is valid.
	 * @param 	radius
	 *          The specified radius in meters.
	 * @throws 	ModelException
	 *         	Throws a ModelException if the radius is not valid.
	 *          | if ( ! isValidRadius(radius) )
	 *          | 		then throw new ModelException
	 */
	public void setRadius(double radius) throws ModelException {
		if (!isValidRadius(radius))
			throw new ModelException("Invalid radius.");
		else {
			this.radius = radius;
			// maxActionPoints depends on the radius, so it has to be reset when the radius is changed.
			resetMaxActionPoints();
		}
	}

	/**
	 * 
	 */
	//TODO beter uitwerken volgens de regels van de kunst.
	private void resetMaxActionPoints() {
		double apFrac = 1;
		// To avoid DivideByZeroException when the worm is first created.
		if ( ! (getMaxActionPoints() == 0) )
			apFrac = ( (double) getActionPoints() ) / ( (double) getMaxActionPoints() );
		maxActionPoints = (int) Math.round(getMass());
		//TODO floor ceil round? Which one?
		setActionPoints( (int) Math.floor( getMaxActionPoints() * apFrac ) );
	}

	/**
	 * Method that sets the name of the worm to the specified name if the name
	 * is valid.
	 * 
	 * @param name
	 * 			The specified name of the worm.
	 * @throws ModelException
	 * 			Throw a ModelException if the specified name is not
	 * 			valid.
	 * 			| if ( ! isValidName(name) )
	 *          | 	then throw new ModelException
	 */
	public void setName(String name) throws ModelException {
		if (!isValidName(name))
			throw new ModelException("Invalid name specified.");
		else
			this.name = name;
	}

	/**
	 * Method that sets the amount of actionpoints of the worm.
	 * 
	 * @param actionPoints
	 * @throws ModelException
	 * 			Throws a ModelException if the speceified actionPoints are not
	 * 			valid.
	 * 			| if ( ! setActionPoints(actionPoints) )
	 *          | 	then throw new ModelException
	 */
	public void setActionPoints(int actionPoints) throws ModelException {
		if ( ! isValidActionPoints(actionPoints) )
			throw new ModelException("Invalid action points specified.");
		else
			this.actionPoints = actionPoints;
	}

	/**
	 * Checks the validity of the specified x coordinate.
	 * 
	 * @param x
	 * 			The specified x coordinate expressed in meters.
	 * @return 	False if x is NaN. True otherwise.
	 * 			| result = ( ! Double.isNaN(x) )
	 */
	public boolean isValidX(double x) {
//		if (Double.isNaN(x))
//			return false;
//		else
//			return true;
		return (!Double.isNaN(x));
	}

	/**
	 * Checks the validity of the specified y coordinate.
	 * 
	 * @param y
	 *          The specified y coordinate expressed in meters.
	 * @return 	False if y is NaN. True otherwise.
	 * 			| result = ( ! Double.isNaN(y) )
	 */
	public boolean isValidY(double y) {
//		if (Double.isNaN(y))
//			return false;
//		else
//			return true;
		return (!Double.isNaN(y));
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
	 * Checks the validity of the specified direction.
	 * 
	 * @param	direction
	 *      	The direction of the worm, given as an angle in radians.
	 * @return 	Return true if the direction lies between 0 and 2*pi, including
	 *         	0 and excluding 2*pi. Otherwise return false. 
	 *         	| return (direction >= 0 && direction < 2*Math.PI)
	 */
	public boolean isValidDirection(double direction) {
		if (direction < 0 || direction >= 2 * Math.PI)
			return false;
		if (Double.isNaN(direction))
			return false;
		else
			return true;
	}

	/**
	 * Checks the validity of the specified radius.
	 * 
	 * @param radius
	 *            The specified radius expressed in meters.
	 * @return The radius must be at least getMinimalRadius().
	 * 			| Return ( radius >= getMinimalRadius() )
	 */
	public boolean isValidRadius(double radius) {
		if (radius < getMinimalRadius())
			return false;
		if (Double.isNaN(radius))
			return false;
		else
			return true;
	}

	/**
	 * Checks the validity of the specified name as a name of a worm.
	 * 
	 * @param 	name
	 *          The name that has to be checked.
	 * @return 	The new name contains no numbers or special characters, starts
	 *         	with an upper case and contains at least two characters.
	 *         	| if ( (!name.matches("[a-zA-Z\"\' ]*") && 
	 *         	|      (!character.isUpperCase(name.charAt(0)) &&
	 *         	|      (name.length() < 2) )
	 *         	|     then return true
	 *         	| else
	 *         	|     return false
	 */
	public boolean isValidName(String name) {
		if (!name.matches("[a-zA-Z\"\' ]*"))
			return false;
		if (!Character.isUpperCase(name.charAt(0)))
			return false;
		if (name.length() < 2)
			return false;
		else
			return true;
	}

	/**
	 * Checks the validity of the specified actionpoints.
	 * 
	 * @param 	actionPoints
	 *        	The number of actionpoints of the worm.
	 * @return 	The number of actionpoints must be valid.
	 */
	public boolean isValidActionPoints(int actionPoints) {
		return ( actionPoints >= 0 && actionPoints <= getMaxActionPoints() );
	}

	/**
	 * Moves the selected worm to the given coordinates.
	 * 
	 * @param steps
	 * 		The amount of steps the worm should move.
	 * @throws ModelException
	 */
	public void activeMove(int steps) throws ModelException {
		//TODO Deze in een aparte checker steken ipv in deze methode?
		if ( steps < 0 )
			throw new ModelException("The amount of steps must be larger than zero.");
		if ( ! canMove(steps) )
			throw new ModelException("Not enough action points.");
		//TODO for loop correct?
		// the assignment says movement occurs in steps. I think this means
		// that each step should be handled separately. And the action points
		// cost should be calculated for each step individually. At a later
		// point in time it may be necessary to do additional calculations
		// after each step (wall collisions and things like that).
		for(int i=1; i<=steps; i++) {
			activeMoveSingleStep();
		}

	}
	

	/**
	 * Moves the worm a single step.
	 * 			
	 */
	public void activeMoveSingleStep() {
		double deltaX = getRadius() * Math.cos(getDirection());
		double deltaY = getRadius() * Math.sin(getDirection());
			
		setPosition( getX()+deltaX, getY()+deltaY );
		//@note Must be worked out defensively, but "setActionPoints" already throws an error for an invalid value.
		setActionPoints( getActionPoints() - getActionPointCostMove(1) );
	}
	
	/**
	 * 
	 * @param nbSteps
	 * @return
	 */
	private int getActionPointCostMove(int nbSteps) {
		// Dit werkt, maar de AP kost is wel heel klein. Zeker in vergelijking met te draaien. Maar het klopt volgens de opgave.
		return nbSteps * (int) ( Math.abs(Math.cos(getDirection())) + Math.abs(4*Math.sin(getDirection())) );
	}

	/**
	 * Checks if a worm can perform the move.
	 * @param	nbSteps
	 * 			The amount of steps the worm will move.
	 * @return	Whether the worm can move or not.
	 */
	public boolean canMove(int nbSteps) {
		//TODO welke kiezen? De worm beweegt één stap met een keer. In de eerste lijn sluit de AP-kostberekening hier beter bij aan. Maar als de code op de andere plaats verandert, dan moet de code hier ook veranderen. Alternatief: "canMoveSingleStep" en dan de worm stap per stap bewegen totdat de AP op zijn. Maar de facade verwacht een "canMove(nbsteps)". Dus moet die toch geïmplementeerd worden.
		if ( ! isValidActionPoints( getActionPoints() - (nbSteps*getActionPointCostMove(1)) ) )
//		if ( ! isValidActionPoints( getActionPoints() - getActionPointCostMove(nbSteps) ) )
			return false;
		else
			return true;
	}
	
	/**
	 * Adds the specified additional direction to the direction of the worm.
	 * 
	 * @pre	The added direction should be not-nan.
	 * 		| ! Double.isNaN(additionalDirection)
//	 * @pre	The added direction should be more than -pi and less than or equal
//	 * 		to pi. Otherwise, too many action points are consumed. 
//	 * 		| ( (additionalDirection > -Math.PI) && 
//				(additionalDirection <= Math.PI) )
	 * @pre	There should be enough action points left to complete the turn.
	 * 		| isValidActionPoints( getActionPoints() - Math.ceil( 60*(Math.abs(additionalDirection)/(2*Math.PI)) ) )
	 * @param additionalDirection
	 *            The additional direction of the worm.
	 */
	public void turn(double additionalDirection) {
		assert ( canTurn(additionalDirection) );
//TODO add this asserion in? if so, add it to canTurn
	//		assert ( (additionalDirection > -Math.PI) && 
	//				 (additionalDirection <= Math.PI) );
		
		setActionPoints(getActionPoints() - getActionPointCostTurn(additionalDirection));
		
		setDirection( (getDirection() + additionalDirection) % (2*Math.PI) );
		
	}

	/**
	 * 
	 * @param additionalDirection
	 * @return
	 */
	private int getActionPointCostTurn(double additionalDirection) {
		return (int) Math.ceil( 60*(Math.abs(additionalDirection)/(2*Math.PI)) );
	}
	


	/**
	 * Checks if the worm can turn or not.
	 * @param	direction
	 * 			The direction of the worm.
	 * @pre		The direction is a valid number.
	 * 			| ! Double.isNaN(direction)
	 * @pre		The additional direction is a valid number.
	 * 			| ! Double.isNaN(additionalDirection)
	 * @pre		There are sufficient action points to perform the turn.
	 * 			| ...
	 * @return	Returns whether the worm can turn or not.
	 */
	public boolean canTurn(double additionalDirection) {
		if ( Double.isNaN(additionalDirection) )
			return false;
		if ( ! (isValidActionPoints(getActionPoints() - getActionPointCostTurn(additionalDirection)) ) )
			return false;
		else
			return true;
	}

	/**
	 * Makes the selected worm jump.
	 * 
	 * @post
	 */
	public void jump() {
		double[] newPos = jumpStep(jumpTime());
		setPosition( newPos[0], newPos[1] );
		setActionPoints(0);		// Jumping consumes all remaining action points.
	}
	
	/**
	 * Calculates and returns the x and y position of the worm during the jump at a specified time.
	 * @param time
	 * 			The time at which the jump should be evaluated.
	 * @return The x and y positions of the worm during the jump at a specified time.
	 * @throws ModelException
	 * 			...
	 * 			| time < 0
	 * @throws ModelException
	 * 			...
	 * 			| time > jumpTime()
	 */
	public double[] jumpStep(double time) throws ModelException {
		if ( time < 0 )
			throw new ModelException("Time cannot be negative.");
		else if ( time > jumpTime() )
			throw new ModelException("Cannot evaluate the trajectory after the end of the jump.");
		else {
			//Only jump if the worm is facing upwards.
			if ( canJump() ) {
				double v0 = getJumpInitVel();
				double deltaX = v0*Math.cos(getDirection()) * time;
				double deltaY = v0*Math.sin(getDirection()) * time - 
						        0.5*getGravitationalAcceleration()*Math.pow(time, 2);
				double[] output = { getX()+deltaX, getY()+deltaY};
				return output;
			}
			else {
				double[] output = {0,0};
				return output;
			}
		}
	}

	/**
	 * Calculates and returns the initial velocity of a jump of the worm.
	 * 
	 * @return The initial velocity of a jump of the worm.
	 */
	public double getJumpInitVel() {		
		/* Method to avoid code duplication.
		 * In this method, there should be no difference whether the worm can or cannot jump.
		 */
		double force = 5*getActionPoints() + getMass()*getGravitationalAcceleration();
		return ( (force/getMass()) * 0.5 );
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
			//TODO Should we use this extra formula or throw an exception? Would this be considered total programming instead of defensive programming or is this just common sense? We could ask that during one of the session with an assistant.
			return (2*(v0*Math.sin(getDirection()))) / getGravitationalAcceleration();
		}
		else {
			double d = (Math.pow(v0, 2)*Math.sin(2*getDirection())) / getGravitationalAcceleration();
			return ( d / (v0*Math.cos(getDirection())) );
		}
	}
	
	/**
	 * Verifies whether a worm can jump in its current state. As of now, the 
	 * worm only has to be facing upwards.
	 * 
	 * @return Return whether the worm can jump in its current state or not.
	 * 			| return ( (0 <= getDirection()) && (getDirection()<=Math.PI) )
	 */
	public boolean canJump() {			// Add this method to avoid code duplication and for expandability.
		if (0 <= getDirection() && getDirection() <= Math.PI && getActionPoints() != 0)
			return true;	// Don't jump if there are no AP left.
		else
			return false;
		//return ( (0 <= getDirection()) && (getDirection() <= Math.PI) );
	}

}
