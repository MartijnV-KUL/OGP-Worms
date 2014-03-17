package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

//TODO class documentation
//TODO class invariants
//TODO are checkers public or private? The professor made them public, but why would they ever be accessed from outside the class?
public class Worm {
	
	/**
	 * NAAMPROBLEEM: Klasse PlayGameScreenPainter, regel 113,
	 * methode:    String name = getFacade().getName(sprite.getWorm());
	 * getName(sprite.getWorm()) geeft NULL terug als naam.
	 */

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
	public Worm() {}
	
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
	 * 			| new.getX() == x
	 * @post 	...
	 * 			| new.getY() == y
	 * @post 	...
	 * 			| if isValidDirection(direction) then new.getDirection() == direction
	 * @post 	...
	 * 			| if isValidRadius(radius) then new.getRadius() == radius
	 * @post 	...
	 * 			| if isValidName(name) then new.getName() == name
	 * @post	...
	 * 			| if isValidActionPoints(getMaxActionPoints()) then new.getActionPoints() == maxActionPoints;
	 */
	public Worm(double x, double y, double direction, double radius, String name) {

		// TODO Document preconditions that have to be asserted and exceptions
		// that can be thrown in the setters.

		setPosition(x, y);
		//System.out.println(x);
		//System.out.println(y);
		setDirection(direction);
		//System.out.println(direction);
		setRadius(radius);
		//System.out.println(radius);
		setName(name);
		//System.out.println(name);
		setActionPoints(getMaxActionPoints());
		
		//Alles wordt correct meegegeven naar de worm-klasse.
	}

	/**
	 * Basic inspector that returns the x coordinate of the worm.
	 * 
	 * @return The x coordinate of the worm.
	 */
	@Basic
	public double getX() {
		//System.out.println(this.x);
		return this.x;
		//Geeft x-waarde terug tot aan worm 10, daarna oneindige loop.
	}

	/**
	 * Basic inspector that returns the y coordinate of the worm.
	 * 
	 * @return The y coordinate of the worm.
	 */
	@Basic
	public double getY() {
		//System.out.println(this.y);
		return this.y;
		//Geeft y-waarde terug tot aan worm 10, daarna oneindige loop.
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
	 * @throws	IllegalArgumentException
	 *          Throws an IllegalArgumentException when the x coordinate is invalid.
	 *          | if ( ! isValidX( x ) )
	 *          |		then throw new IllegalArgumentException
	 * @throws 	IllegalArgumentException
	 *          Throws an IllegalArgumentException when the y coordinate is invalid.
	 *          | if ( ! isValidY( y ) )
	 *          |		then throw new IllegalArgumentException
	 * @throws 	IllegalArgumentException
	 *          Throws an IllegalArgumentException when the new coordinates
	 *          would result in the worm being outside of the boundaries of
	 *          the playing field.
	 *          | if ( ! isWithinBoundaries( x, y ) )
	 *          |		then throw new IllegalArgumentException
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
			//System.out.println(x);
			this.y = y;
			//System.out.println(y);
			//WERKT WEL VOOR ELKE WORM
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
	 *          The specified y coordinate expressed in meters.
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
		//System.out.println(direction);
		return this.direction;
		// Geeft de direction terug van elke worm, maar is een oneindige loop na worm 10 en geeft vervolgens 0 terug.
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
		//System.out.println(direction);
		//Deze methode werkt en definieert direction.
	}

	/**
	 * Checks the validity of the specified direction.
	 * @param	direction
	 *      	The direction of the worm, given as an angle in radians.
	 * @return 	Return true if the direction lies between 0 and 2*pi, including
	 *         	0 and excluding 2*pi. Otherwise return false. 
	 *         	| return (direction >= 0 && direction < 2*Math.PI)
	 */
	private static boolean isValidDirection(double direction) {
		if (direction < 0 || direction >= 2 * Math.PI) {
			return false;
		}
		if (Double.isNaN(direction)) {
			return false;
		}
		else {
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
		//System.out.println(radius);
		return this.radius;
		//Oneindige loop na worm 10.
	}

	/**
	 * Method that sets the radius of the worm to the specified radius if the
	 * radius is valid.
	 * @param 	radius
	 *          The specified radius in meters.
	 * @throws 	IllegalArgumentException
	 *         	Throws an IllegalArgumentException if the radius is not valid.
	 *          | if ( ! isValidRadius(radius) )
	 *          | 		then throw new IllegalArgumentException
	 */
	public void setRadius(double radius) throws IllegalArgumentException {
		if (!isValidRadius(radius)) {
			throw new IllegalArgumentException("Invalid radius.");
		} else {
			this.radius = radius;
			//System.out.println(radius);
			//DEZE SETTER WERKT VOOR ELKE WORM
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
			return false;
		}
		if (Double.isNaN(radius)) {					/** @note	I don't think that the first "if" excludes NaN. */
			return false;							/**			We can use this just to be sure. */
		}
		else {
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
		//System.out.println(minimalRadius);
		return minimalRadius;
		//DIT WERKT VOOR ELKE WORM
	}

	/**
	 * Basic inspector that returns the density of worms.
	 * 
	 * @return The density of worms.
	 */
	@Basic
	@Immutable
	public static double getDensity() {
		//System.out.println(density);
		return density;
		//IS ONEINDIGE LOOP, MAAR GEEFT WEL TELKENS DENSITY TERUG.
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
		return (getDensity() * (4 / 3) * Math.PI * Math.pow(getRadius(), 3));
	}
	

	/**
	 * Basic inspector that returns the name of the worm.
	 * 
	 * @return The name of the worm.
	 */
	@Basic
	public String getName() {
		//System.out.println(name);
		return this.name;
		//ALLE NAMEN ZIJN "NULL"
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
			//System.out.println(name);
			//DIT WERKT VOOR ELKE WORM
		}
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
	 * Basic inspector that returns the action points of the worm.
	 * 
	 * @return The action points of the worm.
	 */
	@Basic
	public int getActionPoints() {
		//System.out.println(actionPoints);
		return actionPoints;
		//GEEFT TELKENS 0 TERUG
	}
	
	/**
	 * Basic inspector that returns the maximum amount of action points of the worm.
	 * @return	The maximum amount of action points of the worm.
	 */
	@Basic
	public int getMaxActionPoints()	{
		maxActionPoints = (int) Math.round(getMass());
		//System.out.println(maxActionPoints);
		return maxActionPoints;
		//DIT WERKT TOT EERSTE 10, DAARNA ONEINDIGE LOOP VAN 0
	}

	/**
	 * Method that sets the amount of actionpoints of the worm.
	 * @param actionPoints
	 * @throws IllegalArgumentException
	 */
	public void setActionPoints(int actionPoints) throws IllegalArgumentException {
			if ( ! isValidActionPoints(actionPoints) )
				throw new IllegalArgumentException("Invalid action points specified.");
			else {
				this.actionPoints = actionPoints;
				//System.out.println(actionPoints);
				//WERKT VOOR ELKE WORM
			}
	}


	
	/**
	 * Checks the validity of the specified actionpoints.
	 * 
	 * @param 	actionPoints
	 *        	The number of actionpoints of the worm.
	 * @return 	The number of actionpoints must be valid.
	 */
	private boolean isValidActionPoints(int actionPoints) {
		return ( actionPoints >= 0 && actionPoints <= getMaxActionPoints() );
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
	 * @throws 	IllegalArgumentException
	 * 			
	 */
	public void activeMoveSingleStep() throws IllegalArgumentException {
		double deltaX = getRadius() * Math.cos(getDirection());
		double deltaY = getRadius() * Math.sin(getDirection());
			
		setPosition( getX()+deltaX, getY()+deltaY );
		//@note Must be worked out defensively, but "setActionPoints" already throws an error for an invalid value.
	}
	
	
	/**
	 * Checks if a worm can perform the move.
	 * @param	nbSteps
	 * 			The amount of steps the worm will move.
	 * @return	Whether the worm can move or not.
	 */
	public boolean canMove(int nbSteps) {
		int costAP = (int) ( Math.abs(Math.cos(getDirection())) + Math.abs(4*Math.sin(getDirection())) );
		costAP = costAP * nbSteps;
		if (costAP > getActionPoints()) {
			return false;
		}
		else {
			int newAP = getActionPoints() - costAP;
			setActionPoints(newAP);
			return true;
		}
	}
	
	/**
	 * Checks if the worm can turn or not.
	 * @param	direction
	 * 			The direction of the worm.
	 * @pre		The direction is a valid number.
	 * 			| ! Double.isNaN(direction)
	 * @pre		There are sufficient action points to perform the turn.
	 * 			| ...
	 * @return	Returns whether the worm can turn or not.
	 */
	public boolean canTurn(double direction) {
		assert (!Double.isNaN(direction));
		int costAP = (int) Math.ceil( 60*(Math.abs(direction)/(2*Math.PI)) );
		
		if (costAP > getActionPoints()) {
			return false;
		}
		else {
			int newAP = getActionPoints() - costAP;
			//TODO Write this assert in a precondition.
			assert isValidActionPoints(newAP);
			setActionPoints(newAP);
			return true;
		}
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
	 * @param additionalDirection
	 *            The additional direction of the worm.
	 */
	public void turn(double additionalDirection) {
		assert ( ! Double.isNaN(additionalDirection) );

		assert ( (additionalDirection > -Math.PI) && 
				 (additionalDirection <= Math.PI) );
		setDirection( (getDirection() + additionalDirection) % (2*Math.PI) );
		
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
		// There are no exceptions here, they are all handled in the setPosition(...) method.
		double[] delta = jumpStep(jumpTime());
		setPosition( getX()+delta[1], getY()+delta[2] );
		
		setActionPoints(0);		// Jumping consumes all remaining action points.
	}
	
	/**
	 * Verifies whether a worm can jump in its current state. As of now, the 
	 * worm only has to be facing upwards.
	 * 
	 * @return Return whether the worm can jump in its current state or not.
	 * 			| return ( (0 <= getDirection()) && (getDirection()<=Math.PI) )
	 */
	public boolean canJump() {			// Add this method to avoid code duplication and for expandability.
		return ( (0 <= getDirection()) && (getDirection()<=Math.PI) );
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
