package worms.model;

import java.util.ArrayList;

import worms.gui.GUIConstants;
import worms.util.Util;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class with the implementation of different methods to 
 * execute the commands for each worm.
 * 
 * @invar	The radius of each worm is a valid radius.
 * 			| isValidRadius(getRadius())
 * @invar	The name of each worm is a valid name.
 * 			| isValidName(getName())
 * @invar	The amount of actionpoints each worm has is valid.
 * 			| isValidActionPoints(getActionPoints())
 * @invar	The amount of hitPoints each worm has is valid.
 * 			| isValidHitPoints(getHitPoints())
 * @invar	Every weapon a worm has must be a valid weapon.
 * 			| isValidWeapon(getWeapon())
 * 
 * @invar	Every worm must can have a valid team as team.
 * 			| canHaveAsTeam(getTeam())
 * @invar	Every worm must can have a valid world as world.
 * 			| canHaveAsWorld(getWorld())
 * 
 * @author Martijn Vermaut, Niels Claes
 */
public class Worm extends BallisticBody {
	
	
//	TODO Questions:
//	-	Direction boundaries?
//	-	Method isAdjacent => computations fast enough?
//	-	Method getMoveDistance => computations fast enough?
//	- 	StatementForEach => when interrupted, continue from last worm or again from the beginning?
//	- 	Documentation of Statement classes?
//	- 	Do we have to write our own worm program? If so, what's the complexity?
//	- 	In StatementWhile (for example): Warning "Unchecked cast from Type<capture#2-of ?> to Type<Boolean>"
//		=> What's Type<capture#2-of ?> and how to fix it?
//	-	TypeErrorOccured => correct implementation?
//	-	What to do with the columns of the program file? Loops take lines into account, not columns.
//	-	Write testcases for worm programs or assume they are correct?
//	-	UML diagram?
//	-	Worms able to jump down?


	/**
	 * Constructor for the Worm class. Receives an x co�rdinate in meters, a y
	 * co�rdinate in meters, a direction in radians, a radius in meters and a name.
	 * 
	 * @param 	x
	 * 			The x co�rdinate expressed in meters.
	 * @param 	y
	 * 			The y co�rdinate expressed in meters.
	 * @param 	direction
	 * 			The direction expressed in radians.
	 * @param 	radius
	 * 			The radius expressed in meters.
	 * @param 	name
	 * 			The name of the worm.
	 * 
	 * @pre		The given radius is valid.
	 * 			| isValidRadius(radius)
	 * @effect 	The X and Y co�rdinates and the direction of the worm are set to the given values in the class BallisticBody.
	 * 			| setPosition(x, y, direction)
	 * @effect 	The radius of the worm is equal to the given value.
	 * 			| setRadius(radius)
	 * @effect 	The name of the worm is equal to the given string if it is valid.
	 * 			| setName(name)
	 * @effect	A new weapon is added, as an object from the class Rifle.
	 * 			| addNewWeapon(new Rifle())
	 * @effect	A new weapon is added, as an object from the class Bazooka.
	 * 			| addNewWeapon(new Bazooka))
	 * @effect	The first possible weapon is equipped.
	 * 			| equipNextWeapon()
	 * @note	The setters in the corresponding classes will throw an exception if the given value is not valid.
	 */
	public Worm(double x, double y, double direction, double radius, String name) {
		setPosition(x, y, direction);
		setRadius(radius);
		setName(name);
		addNewWeapon(new Rifle());
		addNewWeapon(new Bazooka());
		equipNextWeapon();
	}
	
	/**
	 * Constructor for the Worm class. Receives an x co�rdinate in meters, a y
	 * co�rdinate in meters, a direction in radians and a name.
	 * 
	 * @param 	x
	 * 			The x co�rdinate expressed in meters.
	 * @param 	y
	 * 			The y co�rdinate expressed in meters.
	 * @param 	direction
	 * 			The direction expressed in radians.
	 * @param 	name
	 * 			The name of the worm.
	 * 
	 * @pre		The given radius is valid.
	 * 			| isValidRadius(radius)
	 * @effect 	The X and Y co�rdinates and the direction of the worm are set to the given values in the class BallisticBody.
	 * 			| setPosition(x, y, direction)
	 * @effect 	The name of the worm is equal to the given string if it is valid.
	 * 			| setName(name)
	 * @effect	A new weapon is added, as an object from the class Rifle.
	 * 			| addNewWeapon(new Rifle())
	 * @effect	A new weapon is added, as an object from the class Bazooka.
	 * 			| addNewWeapon(new Bazooka))
	 * @effect	The first possible weapon is equipped.
	 * 			| equipNextWeapon()
	 * @note	The setters in the corresponding classes will throw an exception if the given value is not valid.
	 */
	public Worm(double x, double y, double direction, String name) {
		setPosition(x, y, direction);
		setRadius(getMinimalRadius());
		setName(name);
		addNewWeapon(new Rifle());
		addNewWeapon(new Bazooka());
		equipNextWeapon();
	}

	/**
	 * The worm has died.
	 * 
	 * @effect	The hitpoints are set to 0.
	 * 			| setHitPoints(0)
	 * 			| setActionPoints(0)
	 */
	public void die() { //TODO update doc
		setHitPoints(0);
		setActionPoints(0);
		getWorld().nextTurn();
		terminate();
	}
	
// {{ Constants

	private final double minimalRadius = 0.25;
	private static final double density = 1062;
	
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
	
	// }}
	
// {{ Position
	
	/**
	 * Method that sets the position of the worm.
	 * 
	 * @param	position
	 * 			The position of the worm.
	 */
	public void setPosition(double x, double y, double direction) {
		super.setPosition(x, y, direction);
		tryToEatAll();
	}
	
	// }}
	
// {{ Name

	private String name;
	
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
	private static boolean isValidName(String name) {
		if (!name.matches("[a-zA-Z0-9\"\' ]*"))
			return false;
		if (!Character.isUpperCase(name.charAt(0)))
			return false;
		if (name.length() < 2)
			return false;
		return true;
	}
	
	// }}
	
// {{ Radius

	private double radius;
	
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
	 * @param 	radius
	 *          The specified radius in meters.
	 *          
	 * @post	The new radius of the worm is changed in the given radius.
	 * 			| new.getRadius() == radius
	 * @effect	The maximum amount of actionpoints is updated after the radius is changed,
	 * 			because the maximum amount of actionpoints depends on the radius.
	 * 			| updateMaxActionPoints()
	 * @effect	The maximum amount of hitpoints is updated after the radius is changed,
	 * 			because the maximum amount of hitpoints depends on the radius.
	 * 			| updateMaxHitPoints();
	 * @throws 	ModelException
	 *         	Throws a ModelException if the radius is not valid.
	 *          | if ( ! isValidRadius(radius) )
	 *          | 		then throw new ModelException
	 */
	public void setRadius(double radius) throws ModelException {
		if (!isValidRadius(radius))
			throw new ModelException("Invalid radius.");
		this.radius = radius;
		updateMaxActionPoints();
		updateMaxHitPoints();
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
	private boolean isValidRadius(double radius) {
		if (radius < getMinimalRadius())
			return false;
		if (Double.isNaN(radius))
			return false;
		return true;
	}
	
	// }}

// {{ Mass

	/**
	 * Inspector that calculates and returns the mass of the worm.
	 * 
	 * @return	The mass of the worm.
	 * 			| (4 * getDensity() * Math.PI * Math.pow(getRadius(), 3) / 3)
	 */
	protected double getMass() {
		return (4 * getDensity() * Math.PI * Math.pow(getRadius(), 3) / 3);
	}
	
	// }}
	
// {{ Action Points

	private int actionPoints;
	private int maxActionPoints;
	
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
	 * Method that sets the amount of actionpoints of the worm.
	 * 
	 * @param 	actionPoints
	 * 			The amount of actionpoints of the worm.
	 * 
	 * @post	The amount of actionpoints is set to the given value.
	 * 			| if (actionPoints < 0)
	 * 			|		new.getActionPoints() == 0
	 * 			| if (actionPoints > getMaxActionPoints())
	 * 			|		new.getActionPoints() == getMaxActionPoints
	 * 			| else
	 * 			| 		new.getActionPoints() == actionPoints
	 */
	protected void setActionPoints(int actionPoints) {		//All aspects related to actionpoints must be worked out in a total manner.
		if ( ! isValidActionPoints(actionPoints) ) {
			if (actionPoints < 0)
				this.actionPoints = 0;
			if (actionPoints > getMaxActionPoints())
				this.actionPoints = getMaxActionPoints();
		}
		else
			this.actionPoints = actionPoints;
	}
	
	/**
	 * Resets the actionpoints of a worm to the maximum number of actionpoints.
	 * 
	 * @effect	The amount of actionpoints is set to the maximum value.
	 * 			| setActionPoints(getMaxActionPoints())
	 */
	public void resetActionPoints() {
		setActionPoints(getMaxActionPoints());
	}

	/**
	 * Method that updates the maximum number of actionpoints.
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
	 * @note	The second if-loop is implemented to prevent loss of numbers when typecasting to an integer.
	 * 			If the value of getMass() is larger than the maximum value of an integer, all the numbers greater than that maximum will be lost.
	 * 			As everything related to actionpoints has to be implemented in a total manner is the usage of exceptions out of the question.
	 * 			The problem is solved by setting the maximum amount of actionpoints to the highest possible integer value in case the mass is
	 * 			greater than Integer.MAX_VALUE.
	 */
	private void updateMaxActionPoints() {
		double apFrac = 1;
		if ( ! (getMaxActionPoints() == 0) )
			apFrac = ( (double) getActionPoints() ) / ( (double) getMaxActionPoints() );
		if ( getMass() > Integer.MAX_VALUE)
			maxActionPoints = Integer.MAX_VALUE;
		else
			maxActionPoints = (int) Math.round(getMass());
		setActionPoints( (int) Math.floor( getMaxActionPoints() * apFrac ) );
	}

	/**
	 * Checks the validity of the specified actionpoints.
	 * 
	 * @param 	actionPoints
	 *        	The number of actionpoints of the worm.
	 *        
	 * @return 	True if the number of actionpoints lays between 0 and the maximum amount.
	 * 			| return ( actionPoints >= 0 && actionPoints <= getMaxActionPoints() )
	 */
	private boolean isValidActionPoints(int actionPoints) {
		return ( actionPoints >= 0 && actionPoints <= getMaxActionPoints() );
	}
	
	// }}
	
// {{ Hit Points

	private int hitPoints;
	private int maxHitPoints;
	
	/**
	 * Basic inspector that returns the hitpoints of the worm.
	 * 
	 * @return	The hitpoints of the worm.
	 */
	@Basic
	public int getHitPoints() {
		return hitPoints;
	}

	/**
	 * Basic inspector that returns the maximum amount of hitpoints of the worm.
	 * 
	 * @return	The maximum amount of hitpoints of the worm.
	 */
	@Basic
	public int getMaxHitPoints() {
		return maxHitPoints;
	}
	
	/**
	 * Method that sets the amount of hitpoints of the worm.
	 * 
	 * @param	hitPoints
	 * 			The amount of hitpoints of the worm.
	 * 
	 * @post	The amount of hitpoints is set to the given value.
	 * 			| if (isValid
	 * @throws
	 */
	protected void setHitPoints(int hitPoints) { //All aspects related to hitpoints must be worked out in a total manner.
		//TODO update doc with "die()"
		if ( ! isValidHitPoints(hitPoints) ) {
			if (hitPoints < 0) {
				this.hitPoints = 0;
				die();
			}
			if (hitPoints > getMaxHitPoints())
				this.hitPoints = getMaxHitPoints();
		}
		else
			this.hitPoints = hitPoints;
	}
	
	/**
	 * Method that updates the maximum number of hitpoints.
	 * 
	 * @post	The maximum amount of hitpoints is equal to the mass of the worm,
	 * 			rounded to the nearest integer.
	 * 			| new.getMaxHitPoints() == (int) Math.round(getMass())
	 * @effect	The number of hitpoints is set to the maximum amount of hitpoints times the fraction.
	 * 			The result is rounded down to an integer.
	 * 			| setHitPoints( (int) Math.floor(getMaxHitPoints() * hpFrac) )
	 * @note	hpFrac is equal to 1 when the worm is first created, the if-loop prevents a DivideByZeroException
	 * 			at the initialisation of the game.
	 * 			During the rest of the game, hpfrac is equal to the fraction of hitpoints the worm currently has left.
	 * 			This is necessary to maintain control over how the hitpoints are displayed when changing the worm's radius.
	 * 			Without this method the bar of hitpoints would exceed the white bar of hitpoints when the radius is lowered.
	 * @note	The second if-loop is implemented to prevent loss of numbers when typecasting to an integer.
	 * 			If the value of getMass() is larger than the maximum value of an integer, all the numbers greater than that maximum will be lost.
	 * 			As everything related to hitpoints has to be implemented in a total manner is the usage of exceptions out of the question.
	 * 			The problem is solved by setting the maximum amount of hitpoints to the highest possible integer value in case the mass is
	 * 			greater than Integer.MAX_VALUE.
	 */
	private void updateMaxHitPoints() {
		double hpFrac = 1;
		if (! (getMaxHitPoints() == 0) )
			hpFrac = ( (double) getHitPoints() ) / ( (double) getMaxHitPoints() );
		if (getMass() > Integer.MAX_VALUE)
			maxHitPoints = Integer.MAX_VALUE;
		else
			maxHitPoints = (int) Math.round(getMass());
		setHitPoints ( (int) Math.floor( getMaxHitPoints() * hpFrac ) );
	}
	
	/**
	 * Checks the validity of the specified number of hitpoints.
	 * 
	 * @param 	hitPoints
	 * 			The number of hitpoints of the worm.
	 * 
	 * @return	True if the number of hitpoints lays between 0 and the maximum amount.
	 * 			| return (hitPoints >= 0 && hitPoints <= getMaxHitPoints())
	 */
	private boolean isValidHitPoints(int hitPoints) {
		return (hitPoints >= 0 && hitPoints <= getMaxHitPoints()) ;
	}

	
	/**
	 * Verifies whether a worm is alive, i.e. when it's amount of hitpoints is greater than zero.
	 * 
	 * @return	True if the amount of hitpoints is greater than zero.
	 * 			| return ( getHitPoints() > 0)
	 */
	public boolean isAlive() {
		return ( getHitPoints() > 0);
	}
	
	// }}
	
// {{ Move

	/**
	 * Moves the worm.
	 * 
	 * @effect	The position is set to the calculated value.
	 * 			| setPosition(getX() + delta [0], getY() + delta[1], getDirection())
	 * @effect	The amount of actionpoints diminishes when moving the worm.
	 * 			| double [] delta = getMoveDistance()
	 * 			| setActionPoints( getActionPoints() - getActionPointsCostMove(delta) )
	 * @throws	ModelException
	 * 			Throws a modelexception when the worm can not move.
	 * 			if (!canMove())
	 */
	public void move() throws ModelException {
		if ( ! canMove() )
			throw new ModelException("Can't move.");
		
		double[] delta = getMoveDistance();

		setActionPoints( getActionPoints() - getActionPointCostMove(delta) );
		setPosition(getX()+delta[0],getY()+delta[1],getDirection());
	}
	
	/**
	 * Method to calculate the distance covered in one step, taking slope into count.
	 * 
	 * @return	The distance a worm can move in one step.
	 * 			If there is no slope, the distance will be equal to the worm's radius.
	 * 			If there is a slope present, the method will calculate the highest possible distance a worm can cover.
	 * 			The result (output) is returned as an array of doubles. output[0] equals the new x-coordinate,
	 * 			output[1] equals the new y-coordinate.
	 * 			| return output
	 */
	protected double[] getMoveDistance() {//TODO update doc?
		
		double testX = getX();
		double testY = getY();
		double testDivergence = 0;
		
		double optimRadius = 0;
		double optimX = testX;
		double optimY = testY;
		double optimDivergence = testDivergence;

//		double testRadiusInterval = Math.min(getWorld().getResolutionX(), getWorld().getResolutionY());
		double testRadiusInterval = 0.1*getRadius();
		double testAngleInterval = 0.0175;
		double scaleDivergence = 1;
		double scaleRadius = 1;
		
		int[] posNegSign = new int[] {-1,1};
		
		double testRadius;
		double testAngle;
		
		boolean adjacentFound = false;
		for (int i=0; Util.fuzzyGreaterThanOrEqualTo(getRadius()-i*testRadiusInterval,0.1); i++) {
			for (int j=0; j<=45; j++) {
				testRadius = getRadius()-i*testRadiusInterval;
				testAngle = j*testAngleInterval;
				for ( int sign : posNegSign ){
					// Calculate the coordinates to test and the corresponding divergence.
					testX = getX() + testRadius*Math.cos(getDirection()+sign*testAngle);
					testY = getY() + testRadius*Math.sin(getDirection()+sign*testAngle);
					testDivergence = testAngle;
					// The comparison takes less time to compute, so test this first.
					if ( ( scaleDivergence * ( optimDivergence - testDivergence ) +
							scaleRadius     * ( testRadius      - optimRadius    ) ) > 0 ) { //Test if a more optimal divergence/radius combination is found.
						if ( getWorld().isAdjacent(testX,testY,getRadius()) ) { // Test if the location is adjacent to impassable terrain.
							adjacentFound = true;
							optimRadius = testRadius;
							optimX = testX;
							optimY = testY;
							optimDivergence = testDivergence;
						}
					}
				}
			}
		}
		
		if (!adjacentFound) {
			for (int i=0; getRadius()-i*testRadiusInterval>=0.1; i--) {
				testRadius = getRadius()-i*testRadiusInterval;
				testX = getX() + testRadius*Math.cos(getDirection());
				testY = getY() + testRadius*Math.sin(getDirection());
				if (getWorld().isPassable(testX, testY)) {
					optimX = testX;
					optimY = testY;
					break;
				}
			}
		}
		
		double[] output = new double[2];
		output[0] = optimX-getX();
		output[1] = optimY-getY();
		return output;
	}
	
	/**
	 * Calculates the total amount of actionpoints it takes to perform a move.
	 * 
	 * @return	The total cost of actionpoints to perform the move.
	 * 			| return (int) ( Math.abs(Math.cos(getDirection())) + Math.abs(4*Math.sin(getDirection())) )
	 * @note	The cost of actionpoints is rather low, especially in comparison with the cost for turning.
	 */
	protected int getActionPointCostMove(double[] delta) {
		double slope = Math.atan2(delta[1], delta[0]);
		return (int) Math.ceil( Math.abs(Math.cos(slope)) + Math.abs(4*Math.sin(slope)) );
	}

	/**
	 * Checks if a worm can perform the move.
	 * 
	 * @return	True if there are enough actionpoints left to perform the move.
	 * 			| if (! isValidActionPoints( getActionPoints() - getActionPointsCostMove() ) )
	 * 			|		return false
	 * 			| else
	 * 			|		return true
	 */
	public boolean canMove() {
		double[] delta = getMoveDistance();
		return ( isValidActionPoints( getActionPoints() - getActionPointCostMove(delta) ) );
	}
	
	// }}
	
// {{ Turn
	
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
	 * @return	The total amount of actionpoints it takes to perform this turn, converted to an integer.
	 * 			| return (int) Math.ceil( 60*(Math.abs(additionalDirection)/(2*Math.PI)) )
	 */
	protected static int getActionPointCostTurn(double additionalDirection) {
		return (int) Math.ceil( 60*(Math.abs(additionalDirection)/(2*Math.PI)) );
	}
	
	/**
	 * Checks if the worm can turn or not.
	 * 
	 * @param	direction
	 * 			The direction of the worm.
	 * 
	 * @return	The new direction must be valid (non-NaN) and has to me smaller than the maximum value of a double-type.
	 * 			The worm must have enough actionpoints left to perform the turn.
	 * 			| if (Double.isNaN(additionalDirection))
	 * 			|	return false
	 * 			| if (additionalDirection > Double.MAX_VALUE)
	 * 			|	return false
	 * 			| if (! (isValidActionPoints(getActionPoints() - getActionPointsCostTurn(additionalDirection)) ) )
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public boolean canTurn(double additionalDirection) {
		if ( Double.isNaN(additionalDirection) )
			return false;
		if ( additionalDirection > Double.MAX_VALUE)
			return false;
		if ( ! (isValidActionPoints(getActionPoints() - getActionPointCostTurn(additionalDirection)) ) )
			return false;
		return true;
	}
	
	// }}
	
// {{ Jump
	
	@Override
	protected double getJumpForce() {
		return ((double)5*getActionPoints()) + (getMass()*World.getGravitationalAcceleration());
	}

	/**
	 * Makes the selected worm jump.
	 * 
	 * @param	timeStep
	 * 			The timestep with which the jump trajectory is calculated.
	 * 
	 * @effect	The amount of actionpoints is set to zero after performing the jump.
	 * 			| setActionPoints(0)
	 * @effect	The position of the worm is set to it's new co�rdinates. This is calculated in BallisticBody, as the method
	 * 			calls a method from the superclass.
	 * 			| super.jump(timeStep)
	 * @effect	After the jump, checks if a worm can fall.
	 * 			| if (canFall())
	 * 			|	fall()
	 * @note	If the player tries to jump when it's not possible he will get "punished" for trying.
	 * 			The amount of actionpoints will always be set to 0, whether the worm actually jumps or not.
	 * 			This is a choice of implementation.
	 * @note	A worm will not receive damage when jumping. The assignment only specifies that a worm has to take damage
	 * 			when falling, not when jumping.
	 */
	@Override
	public void jump(double timeStep) throws ModelException {
		super.jump(timeStep);
		
		setActionPoints(0);
		
		if (canFall())
			fall();
	}
	
	// TODO new function; add documentation
	public void jump() throws ModelException {
		jump(GUIConstants.JUMP_TIME_STEP);
	}
	
	/**
	 * Verifies whether a worm can jump in its current state. As of now, the 
	 * worm only has to be facing upwards.
	 * 
	 * @return 	True if the worm has actionpoints left and the direction is valid for a jump.
	 * 			To be valid to perform a jump, the direction must lay between 0 and 2*pi.
	 * 			The worm also has to have some actionpoints left.
	 * 			| if (getActionPoints() == 0
	 * 			| 	return false
	 * 			| if (getPosition().getDirection() < 0)
	 * 			|	return false
	 * 			| if (getPosition().getDirection() > Math.PI)
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 * @note	This method can easily be adapted in the future to include other conditions.
	 */
	public boolean canJump() {
		if (getActionPoints() == 0)
			return false;
		if (getDirection() < 0)
			return false;
		if (getDirection() > Math.PI)
			return false;
		return true;
	}
	
	
	
	
	
	// }}	

// {{ Fall

	/**
	 * Method to make a worm fall down to impassable terrain or out of the game world.
	 * 
	 * @effect	The worm will die if it falls out of the world.
	 * 			| if (!getWorld().isWithinBoundaries(getX(), y)) {
	 * 			|	setPosition(getX(), y, getDirection())
	 * 			|	die()
	 * 			| }
	 * @effect	The worm will hit the ground if it reaches impassable terrain when falling.
	 * 			| if (!getWorld().isPassable(getX(), y, getRadius()))
	 * 			|	setPosition(getX(), y, getDirection)
	 * @effect	The worm will loose hitpoints after falling and will die if they are less than zero.
	 * 			| if (getHitPoints > 0)
	 * 			|	setHitPoints(newHitPoints)
	 * 			| else
	 * 			|	die()
	 */
	public void fall() {//TODO update documentation
		if (canFall()) {
//			double resolution = 0.1*getWorld().getResolutionY();
			double resolution = 0.1*getRadius();
			double y = getY();
			for (int i=0; i<Integer.MAX_VALUE; i++) {
				y = getY()-i*resolution;
				if (!getWorld().isWithinBoundaries(getX(),y)) {
					// Fell out of world.
					setPosition(getX(),y,getDirection());
					die();
					break;
				}
//				if ( getWorld().isAdjacent( getX(), y, getRadius() ) ) {
				if ( !getWorld().isPassable( getX(), y, getRadius(), 1.1*getRadius() ) ) {
					// Can't fall anymore --> Worm has hit the ground.
					setPosition(getX(), y, getDirection());
					int newHitPoints = getHitPoints() - (int) Math.floor((getY()-y) * 3);
					if (newHitPoints>0)
						setHitPoints(newHitPoints);
					else
						die();
					break;
				}
			}
		}
	}
	
	/**
	 * Checks if a worm can fall or not.
	 * 
	 * @return	True if a worm is not adjacent to any terrain, false if the worm has no world.
	 * 			| if (!hasAWorld())
	 * 			|	return false
	 * 			| if (getWorld().isAdjacent( getX(), getY(), getRadius() ))
	 * 			|	return false
	 * 			| if (!getWorld().isPassable( getX(), getY(), getRadius() ))
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public boolean canFall() {//TODO updated function --> update documentation
		if (!hasAWorld())
			return false;
		if ( getWorld().isAdjacent( getX(), getY(), getRadius() ) )
			return false;
		return true;
		//return ( !getWorld().isOnSolidGround( getX(), getY(), getRadius() ) ); // better implementation for future iterations.	
	}
	
	// }}
	
// {{ Shoot
	
	private Weapon equippedWeapon;
	
	/**
	 * Method to return the currently equipped weapon.
	 * 
	 * @return	The currently equipped weapon.
	 */
	public Weapon getEquippedWeapon() {
		return equippedWeapon;
	}
	
	/**
	 * Method to equip a weapon.
	 * 
	 * @param 	weapon
	 * 			The weapon that has to be equipped.
	 * @throws 	ModelException
	 * 			Throws a modelexception if the worm doesn't have the specified weapon.
	 * 			| if (!hasAsWeapon(weapon))
	 * @post	The equipped weapon is set to the given weapon.
	 * 			| new.getEquippedWeapon() == this.weapon
	 */
	public void equipWeapon(Weapon weapon) throws ModelException {
		if (!hasAsWeapon(weapon))
			throw new ModelException("Worm does not have this weapon.");
		equippedWeapon = weapon;
	}
	
	/**
	 * Method to equip the next available weapon.
	 * 
	 * @effect	The next available weapon is equipped.
	 * 			| equipWeapon(getWeapon().get(index))
	 * @note	The index is equal to the corresponding place in the array the weapon has.
	 */
	public void equipNextWeapon() {
		int index = 0;
		if (getEquippedWeapon()!=null) {
			index = getWeapons().indexOf(getEquippedWeapon());
			if (index==(getWeapons().size()-1))
				index = 0;
			else
				index += 1;
		}
		equipWeapon(getWeapons().get(index));
	}
	
	/**
	 * Method to let a worm shoot with an equipped weapon, if it is able to shoot.
	 * 
	 * @param 	propulsionYield
	 * 			The yield the weapon is fired with.
	 * 
	 * @throws	ModelException
	 * 			Throws a modelexception if the worm can not shoot.
	 * 			if (!canShoot())
	 */
	public void shoot(int propulsionYield) throws ModelException {
		if (!canShoot())
			throw new ModelException("This worm can not shoot.");
		setActionPoints(getActionPoints()-getEquippedWeapon().getActionPointsCost());
		getEquippedWeapon().shoot(propulsionYield);
	}
	
	/**
	 * Checks whether a worm can shoot or not.
	 * 
	 * @return	The worm has to have a sufficient amount of actionpoints left.
	 * 			| if (!isValidActionPoints(getActionPoints() - getEquippedWeapon().getActionPointsCost()))
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public boolean canShoot() {
		if (!isValidActionPoints(getActionPoints()-getEquippedWeapon().getActionPointsCost()))
			return false;
		return true;
	}
	
	// }}
	
// {{ Eating
	
	/**
	 * Method to let a worm try to eat all the food.
	 * 
	 * @note	A worm is able to eat a foodobject if the two objects (worm and food) are overlapping.
	 */
	private void tryToEatAll() {
		if (hasAWorld()) {
			ArrayList<Food> oldFood = new ArrayList<Food>(getWorld().getFood());
			for ( Food food : oldFood ) {
				if (World.isOverlapping( 	  getX(), 				getY(),      getRadius(), 
										 food.getX(),          food.getY(), Food.getRadius() )) {
					eat(food);
				}	
			}
		}
	}
	
	/**
	 * Method to let a worm eat the food it is overlapping.
	 * 
	 * @param	food
	 * 			The food the worm can eat.
	 * 
	 * @effect	The worm grows after eating food (so it's radius increases).
	 * 			| setRadius(1.1 * getRadius())
	 * @effect	The foodobject is terminated from the game world.
	 * 			| food.terminate()
	 */
	protected void eat(Food food) {
		setRadius(1.1*getRadius());
		food.terminate();
	}
	
	// }}
	
// {{ World Association
	
	private World world;
	
	@Basic
	/**
	 * Returns the current world.
	 * 
	 * @return	The current world.
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * Method that sets the world to the given world, if it is valid.
	 * 
	 * @param	world
	 * 			The new world.
	 * 
	 * @post	The current world is set to the given world.
	 * 			new.getWorld() == world
	 * @throws 	ModelException
	 * 			Throws a modelexception if the given world is not valid.
	 * 			| if (!canHaveAsWorld(world))
	 * 			|	throw new ModelException
	 * 			| if (hasAWorld())
	 * 			|	throw new ModelException
	 */
	@Raw
	void setWorld(World world) throws ModelException {
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
	 * @return	True if the world is not null and if it is not terminated.
	 * 			| if (world == null)
	 * 			|	return false
	 * 			| if (world.isTerminated())
	 * 			|	return false
	 */
	private static boolean canHaveAsWorld(World world) {
		if (world==null)
			return false;
		if (world.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Checks if the world is not null.
	 * 
	 * @return	Whether or not the world is null.
	 * 			| return (!(world == null))
	 */
	private boolean hasAWorld() {
		return(!(world==null));
	}
	
	/**
	 * Removes the current world.
	 * 
	 * @post	The current world is removed.
	 * 			| new.getWorld() == null
	 */
	@Raw
	void removeWorld() {
		world = null;
	}
	
	
	
	// }}
	
// {{ Team Association
	
	private Team team;
	
	/**
	 * Returns the current team.
	 * 
	 * @return	The current team
	 */
	@Basic
	public Team getTeam() {
		return team;
	}
	
	/**
	 * Method to set the team to the given team if it is valid.
	 * 
	 * @param 	team
	 * 			The given team.
	 * 
	 * @post	The current team is set to the given team.
	 * 			| new.getTeam() == team
	 * @throws 	ModelException
	 * 			Throws a modelexception if the team is invalid or if the worm already has a team.
	 * 			| if (!canHaveAsTeam(team))
	 * 			|	throw new ModelException
	 * 			| if (hasATeam())
	 * 			|	throw new ModelException
	 */
	@Raw
	void setTeam(Team team) throws ModelException {
		if (!canHaveAsTeam(team))
			throw new ModelException("Invalid team specified.");
		if (hasATeam())
			throw new ModelException("Already has a team.");
		this.team = team;
	}
	
	/**
	 * Checks if the given team is valid.
	 * 
	 * @param 	team
	 * 			The given team.
	 * @return	True if the given team is not null and if it is not terminated.
	 * 			| if (team == null)
	 * 			|	return false
	 * 			| if (team.isTerminated())
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	private static boolean canHaveAsTeam(Team team) {
		if (team==null)
			return false;
		if (team.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Checks if a team is not null.
	 * 
	 * @return	Whether or not the team is null.
	 * 			| return (!(team == null))
	 */
	private boolean hasATeam() {
		return(!(team==null));
	}
	
	/**
	 * Removes the current team.
	 * 
	 * @post	The current team is removed.
	 * 			| new.getTeam() == null
	 */
	@Raw
	void removeTeam() {
		team = null;
	}
	
	
	// }}
	
// {{ Weapon Association

	private final ArrayList<Weapon> weaponCollection = new ArrayList<Weapon>();
	
	/**
	 * Returns the collection of weapons.
	 * 
	 * @return	The collection of weapons.
	 */
	@Basic
	public ArrayList<Weapon> getWeapons() {
		return weaponCollection;
	}
	
	/**
	 * Method to add a new weapon to the existing collection of weapons if the new weapon is valid.
	 * 
	 * @param 	weapon
	 * 			The weapon that has to be added.
	 * 
	 * @effect	The weapon is added to that worm.
	 * 			| weapon.setWorm(this)
	 * @effect	The weapon is added to the collection of weapons.
	 * 			| weaponCollection.add(weapon)
	 * @throws 	ModelException
	 * 			Throws a modelexception if the new weapon is invalid or if the worm already has a weapon.
	 * 			| if (!isValidWeapon(weapon)
	 * 			|	throw new ModelException
	 * 			| if (hasAsWeapon(weapon))
	 * 			|	throw new ModelException
	 */
	public void addNewWeapon(Weapon weapon) throws ModelException {
		if (!isValidWeapon(weapon))
			throw new ModelException("Invalid weapon specified.");
		if (hasAsWeapon(weapon))
			throw new ModelException("Worm already has weapon.");
		weapon.setWorm(this);
		weaponCollection.add(weapon);
	}
	
	/**
	 * Checks if the given weapon is valid.
	 * 
	 * @param 	weapon
	 * 			The given weapon.
	 * @return	True if the given weapon is not null and is not terminated.
	 * 			| if (weapon == null)
	 * 			|	return false
	 * 			| if (weapon.isTerminated())
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	private static boolean isValidWeapon(Weapon weapon) {
		if (weapon==null)
			return false;
		if (weapon.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Removes the weapon from the collection of weapons (if the collection has that weapon).
	 * 
	 * @param 	weapon
	 * 			The weapon that has to be removed.
	 * @effect	The weapon is removed from the worm that has it.
	 * 			| weapon.removeWorm()
	 * @effect	The weapon is removed from the collection of weapons.
	 * 			| weaponCollection.remove(weapon)
	 * @throws 	ModelException
	 * 			Throws a modelexception if the weaponcollection does not contain the given weapon.
	 * 			if (!hasAsWeapon(weapon))
	 */
	public void removeWeapon(Weapon weapon) throws ModelException {
		if (!hasAsWeapon(weapon)) {
			throw new ModelException("Weapon not found.");
		}
		weapon.removeWorm();
		weaponCollection.remove(weapon);
	}
	
	/**
	 * Method to remove all weapons from the collection.
	 */
	private void removeAllWeapons() { //TODO update all other removeAll methods to this. The other way of doing does not work 
		while (!weaponCollection.isEmpty()) {
			removeWeapon(weaponCollection.get(0));
		}
	}
	
	/**
	 * Method to check if the collection of weapons contains the given weapon.
	 * 
	 * @param 	weapon
	 * 			The given weapon.
	 * 
	 * @return	Whether or not the collection of weapons contains the given weapon.
	 * 			| return weaponCollection.contains(weapon)
	 */
	protected boolean hasAsWeapon(Weapon weapon) {
		return weaponCollection.contains(weapon);
	}
	
	// }}
	
// {{ Program Association
	
	private Program program;
	
	@Basic
	public Program getProgram() {
		return program;
	}
	
	void setProgram(Program program) {
		if (!canHaveAsProgram(program))
			throw new ModelException("Invalid program specified.");
		if (hasAProgram())
			throw new ModelException("Already has a program");
		program.setWorm(this);
		this.program = program;
	}
	
	private static boolean canHaveAsProgram(Program program) {
		if (program == null)
			return false;
		if (program.isTerminated())
			return false;
		return true;
	}
	
	protected boolean hasAProgram() {
		return (!(program == null));
	}
	
	void removeProgram() {
		if (hasAProgram()) {
			program.removeWorm();
			program = null;
		}
	}
	
// }}
	
// {{ Terminated
	
	private boolean terminated;
	
	/**
	 * Returns the boolean-type terminated.
	 * 
	 * @return	The boolean-type terminated.
	 */
	@Basic
	public boolean isTerminated() {
		return terminated;
	}
	
	/**
	 * Method to terminate the worm and all corresponding objects from the world.
	 */
	public void terminate() {//TODO update doc
		if (hasAWorld())
			world.removeWorm(this);
		if (hasATeam())
			team.removeWorm(this);
		if (hasAProgram())
			program.removeWorm();
		removeAllWeapons();
		terminated = true;
	}
	
	// }}
	
	/**
	 * Returns the name of the team the worm is in.
	 * 
	 * @return	The name of the team the worm is in.
	 * 			| if (getTeam() == null)
	 * 			|	return ""
	 * 			| else
	 * 			|	return getTeam().getName()
	 */
	public String getTeamName() {
		if (getTeam()==null)
			return "";
		return getTeam().getName();
	}
	
	
}


