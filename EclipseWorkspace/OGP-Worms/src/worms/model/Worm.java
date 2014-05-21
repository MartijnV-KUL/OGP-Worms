package worms.model;

import java.util.ArrayList;

import worms.gui.GUIConstants;
import worms.model.ModelException;
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
 * @invar	Every worm must can have a valid team or null as team.
 * 			| getTeam()==null || canHaveAsTeam(getTeam())
 * @invar	Every worm must can have a valid world or null as world.
 * 			| getWorld()==null || canHaveAsWorld(getWorld())
 * 
 * @author Martijn Vermaut, Niels Claes
 */
public class Worm extends BallisticBody {
	
	/**
	 * Constructor for the Worm class. Receives an x coordinate in meters, a y
	 * coordinate in meters, a direction in radians, a radius in meters and a name.
	 * 
	 * @param 	x
	 * 			The x coordinate expressed in meters.
	 * @param 	y
	 * 			The y coordinate expressed in meters.
	 * @param 	direction
	 * 			The direction expressed in radians.
	 * @param 	radius
	 * 			The radius expressed in meters.
	 * @param 	name
	 * 			The name of the worm.
	 * 
	 * @pre		The given radius is valid.
	 * 			| isValidRadius(radius)
	 * @effect 	The X and Y coordinates and the direction of the worm are set to the given values in the class BallisticBody.
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
	 * @post	The action points are set to their maximum value.
	 * 			| new.getActionPoints() == new.getMaxActionPoints()
	 * @post	The hit points are set to their maximum value.
	 * 			| new.getHitPoints() == new.getMaxHitPoints()
	 * @note	The setters in the corresponding classes will throw an exception if the given value is not valid.
	 */
	public Worm(double x, double y, double direction, double radius, String name) {
		setPosition(x, y, direction);
		setRadius(radius);
		setName(name);
		addNewWeapon(new Rifle());
		addNewWeapon(new Bazooka());
		equipNextWeapon();
		setHitPoints(getMaxHitPoints());
		setActionPoints(getMaxActionPoints());
	}
	
	/**
	 * Constructor for the Worm class. Receives an x coordinate in meters, a y
	 * coordinate in meters, a direction in radians and a name.
	 * 
	 * @param 	x
	 * 			The x coordinate expressed in meters.
	 * @param 	y
	 * 			The y coordinate expressed in meters.
	 * @param 	direction
	 * 			The direction expressed in radians.
	 * @param 	name
	 * 			The name of the worm.
	 * 
	 * @effect 	The X and Y coordinates and the direction of the worm are set to the given values in the class BallisticBody.
	 * 			| setPosition(x, y, direction)
	 * @effect 	The name of the worm is equal to the given string if it is valid.
	 * 			| setName(name)
	 * @effect	A new weapon is added, as an object from the class Rifle.
	 * 			| addNewWeapon(new Rifle())
	 * @effect	A new weapon is added, as an object from the class Bazooka.
	 * 			| addNewWeapon(new Bazooka))
	 * @effect	The first possible weapon is equipped.
	 * 			| equipNextWeapon()
	 * @post	The action points are set to their maximum value.
	 * 			| new.getActionPoints() == new.getMaxActionPoints()
	 * @post	The hit points are set to their maximum value.
	 * 			| new.getHitPoints() == new.getMaxHitPoints()
	 * @note	The setters in the corresponding classes will throw an exception if the given value is not valid.
	 */
	public Worm(double x, double y, double direction, String name) {
		setPosition(x, y, direction);
		setRadius(2*getMinimalRadius());
		setName(name);
		addNewWeapon(new Rifle());
		addNewWeapon(new Bazooka());
		equipNextWeapon();
		setHitPoints(getMaxHitPoints());
		setActionPoints(getMaxActionPoints());
	}

	/**
	 * The worm has died.
	 * 
	 * @effect	The hitpoints are set to 0.
	 * 			| setHitPoints(0)
	 * @effect	The actionpoints are set to 0.
	 * 			| setActionPoints(0)
	 * @effect	The next worm will be selected.
	 * 			| getWorld().nextTurn()
	 * @effect	The worm that has died will be terminated.
	 * 			| terminate()
	 */
	public void die() {
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
	 * @return 	The minimal radius of worms.
	 * 			| return this.minimalradius
	 */
	@Basic
	@Immutable
	public double getMinimalRadius() {
		return minimalRadius;
	}
	
	/**
	 * Basic inspector that returns the density of worms.
	 * 
	 * @return 	The density of worms.
	 * 			| return this.density
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
	 * @param	x
	 * 			The x-coordinate of the worm.
	 * @param	y
	 * 			The y-coordinate of the worm.
	 * @param	direction
	 * 			The direction of the worm.
	 * 
	 * @effect	The position is set to the given values. This is done in the BallisticBody class, through
	 * 			the super method.
	 * 			| super.setPosition(x, y, direction)
	 * @effect	The worm will try to eat all the food that it is overlapping with.
	 * 			| tryToEatAll()
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
	 * @return 	The name of the worm.
	 * 			| return this.name
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
	 * @return 	The radius of the worm.
	 * 			| return this.radius
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
	 * 			| return ( radius >= getMinimalRadius() )
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
	 * @return 	The action points of the worm.
	 * 			| return this.actionPoints
	 */
	@Basic
	public int getActionPoints() {
		return actionPoints;
	}

	/**
	 * Basic inspector that returns the maximum amount of action points of the worm.
	 * 
	 * @return	The maximum amount of action points of the worm.
	 * 			| return this.maxActionPoints
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
	protected void setActionPoints(int actionPoints) {
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
	 * @post	If the mass of the worm is greater than the maximum value of an integer,
	 * 			the maximum amount of actionpoints is set to the maximum integer value.
	 * 			| if (getMass() > Integer.MAX_VALUE)
	 * 			|	new.getMaxActionPoints() == Integer.MAX_VALUE
	 * @post	The maximum amount of actionpoints is equal to the mass of the worm,
	 * 			rounded to the nearest integer. The mass has to be smaller than the maximum value
	 * 			of an integer.
	 * 			| new.getMaxActionPoints() == (int) Math.round(getMass())
	 * @effect	The actionPoints are unchanged.
	 * 			| setActionPoints(getActionPoints())
	 */
	private void updateMaxActionPoints() {
		if ( getMass() > Integer.MAX_VALUE)
			maxActionPoints = Integer.MAX_VALUE;
		else
			maxActionPoints = (int) Math.round(getMass());
		setActionPoints(getActionPoints());
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
	 * 			| return this.hitPoints
	 */
	@Basic
	public int getHitPoints() {
		return hitPoints;
	}

	/**
	 * Basic inspector that returns the maximum amount of hitpoints of the worm.
	 * 
	 * @return	The maximum amount of hitpoints of the worm.
	 * 			| return this.maxHitPoints
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
	 * @post	The amount of hitpoints is set to the given value if that is valid.
	 * 			| if (isValidHitPoints(hitPoints)) {
	 * 			|	if (hitPoints < 0)
	 * 			|		new.getHitPoints() == 0
	 * 			|	if (hitPoints > getMaxHitPoints())
	 * 			|		new.getHitPoints() == this.getMaxHitPoints()
	 * 			| }
	 * 			| else
	 * 			|	new.getHitPoints() == hitPoints
	 * @effect	The worm will die if the amount of hitpoints is negative.
	 * 			| if (hitPoints < 0)
	 * 			|	die()
	 */
	protected void setHitPoints(int hitPoints) {
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
	 * @post	If the mass of the worm is greater than the maximum value of an integer,
	 * 			the maximum amount of hitpoints is set to the maximum integer value.
	 * 			| if (getMass() > Integer.MAX_VALUE)
	 * 			|	new.getMaxHitonPoints() == Integer.MAX_VALUE
	 * @post	The maximum amount of hitpoints is equal to the mass of the worm,
	 * 			rounded to the nearest integer. The mass has to be smaller than the maximum value
	 * 			of an integer.
	 * 			| new.getMaxHitPoints() == (int) Math.round(getMass())
	 * @effect	The hitPoints are unchanged.
	 * 			| setHitPoints(getHitPoints())
	 */
	private void updateMaxHitPoints() {
		if (getMass() > Integer.MAX_VALUE)
			maxHitPoints = Integer.MAX_VALUE;
		else
			maxHitPoints = (int) Math.round(getMass());
		setHitPoints ( getHitPoints() );
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
	 * @effect	The worm will die when if moves outside of the playfield boundaries.
	 * 			| if (!getWorld().isWithinBoundaries( getX() + getMoveDistance()[0],
	 * 			|									  getY() + getMoveDistance()[1], getDirection()))
	 * 			|	die()
	 * @effect	The position is set to the calculated value if the worm is
	 * 			within the field boundaries.
	 * 			| setPosition( getX() + getMoveDistance()[0], 
	 * 			|			   getY() + getMoveDistance()[1], getDirection())
	 * @effect	The amount of actionpoints diminishes when moving the worm, if the worm is
	 * 			within the field boundaries.
	 * 			| setActionPoints( getActionPoints() - getActionPointsCostMove(delta) )
	 * @throws	ModelException
	 * 			Throws a modelexception when the worm can not move.
	 * 			if (!canMove())
	 */
	public void move() throws ModelException {
		if ( ! canMove() )
			throw new ModelException("Can't move.");
		
		double[] delta = getMoveDistance();

		if (!getWorld().isWithinBoundaries(getX()+delta[0],getY()+delta[1]))
			die();
		else {
			setActionPoints( getActionPoints() - getActionPointCostMove(delta) );
			setPosition(getX()+delta[0],getY()+delta[1],getDirection());
		}
	}
	
	/**
	 * Method to calculate the distance covered in one step, taking slope into count.
	 * 
	 * @return	The distance a worm can move in one step.
	 * 			If there is no slope, the distance will be equal to the worm's radius.
	 * 			If there is a slope present, the method will calculate the highest possible distance a worm can cover.
	 * 			The result (output) is returned as an array of doubles. output[0] equals the new x-coordinate,
	 * 			output[1] equals the new y-coordinate.
	 * 			| for the set {dx1,dy1} it holds that
	 * 			|		sqrt(dx1^2 + dy1^2) <= getRadius() &&
	 * 			| 		atan(dy1 / dx1) < getDirection() + 0.7875 &&
	 * 			| 		getWorld().isAdjacent(getX() + dx1, getY() + dy1) }
	 * 			| if (!isempty(XY)) {
	 *			|	then for the set {dx, dy} it holds that
	 * 			| 		sqrt(dx^2 + dy^2) - atan(dy / dx) >= sqrt(dx1^2 + dy1^2) - atan(dy1 / dx1)
	 * 			|	return { getX() + dx*cos(getDirection), getY() + dy*sin(getDirection()
	 * 			| }
	 * 			| else {
	 * 			|	for the set {dx2, dy2} it holds that
	 * 			| 		getWorld().isWithinBoundaries(getX() + dx2, getY() + dy2) &&
	 * 			|		isPassable(getX() + dx2, getY() + dy2, 0, getRadius())
	 * 			|	for the set {dx, dy} it holds that
	 * 			|		sqrt(dx^2 + dy^2) >= sqrt(dx2^2, dy2^2)
	 * 			|	return { getX() + dx*cos(getDirection), getY() + dy*sin(getDirection()
	 * 			| }
	 */
	protected double[] getMoveDistance() {
		
		double testX = getX();
		double testY = getY();
		double testDivergence = 0;
		
		double optimRadius = 0;
		double optimX = testX;
		double optimY = testY;
		double optimDivergence = testDivergence;

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
			for (int i=0; getRadius()-i*testRadiusInterval>=0.1; i++) {
				testRadius = getRadius()-i*testRadiusInterval;
				testX = getX() + testRadius*Math.cos(getDirection());
				testY = getY() + testRadius*Math.sin(getDirection());
				if ( !getWorld().isWithinBoundaries(testX, testY) || getWorld().isPassable(testX, testY, 0, getRadius()) ) {
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
	/**
	 * Method to calculate the force with which a worm jumps.
	 * 
	 * @return	The force with which a worm jumps.
	 * 			| return (5 * getActionPoints()) + (getMass() * World.getGravitationalAcceleration())
	 */
	protected double getJumpForce() {
		return ((double)5*getActionPoints()) + (getMass()*World.getGravitationalAcceleration());
	}

	/**
	 * Makes the selected worm jump.
	 * 
	 * @param	timeStep
	 * 			The timestep with which the jump trajectory is calculated.
	 * 
	 * @note	The arrays of doubles used in this documentation are defined as:
	 * 			| double[] newPos  = jumpStep(jumpTime(timeStep))
	 * 			| double[] nextPos = jumpStep(jumpTime(timeStep) + timeStep)
	 * @effect	The worm will die if it moves outside of the playfield boundaries.
	 * 			| if (!getWorld().isWithinBoundaries(nextPos[0], nextPos[1]))
	 * 			|	die();
	 * @effect	Nothing will happen if the distance a worm will cover when jumping
	 * 			is smaller than the radius of that worm
	 * 			| if (distanceCoveredByJump < getRadius())
	 * 			|	return
	 * @effect	The amount of actionpoints is set to zero after performing the jump.
	 * 			| setActionPoints(0)
	 * @effect	The position of the worm is set to it's new coordinates.
	 * 			| setPosition(newPos[0], newPos[1], getDirection())
	 * @effect	After the jump, checks if a worm can fall.
	 * 			| if (canFall())
	 * 			|	fall()
	 * @throws	ModelException
	 * 			Throws a ModelException when the worm can not jump.
	 * 			| if (!canJump())
	 * @note	A worm will not receive damage when jumping. The assignment only specifies that a worm has to take damage
	 * 			when falling, not when jumping.
	 */
	@Override
	public void jump(double timeStep) throws ModelException {
		if (!canJump())
			throw new ModelException("Can't jump");

		
		double[] newPos = jumpStep(jumpTime(timeStep));
		double[] nextPos = jumpStep(jumpTime(timeStep) + timeStep);
		
		if (!getWorld().isWithinBoundaries(nextPos[0], nextPos[1]))
			die();
		else if ( Math.sqrt(Math.pow(newPos[0] - getX(), 2)+Math.pow(newPos[1] - getY(), 2)) < getRadius())
			return;
		else { //the worm has actually jumped
			setActionPoints(0);
			setPosition(newPos[0], newPos[1], getDirection());
			if (canFall())
				fall();
		}
	}
	
	/**
	 * Makes the selected worm jump with the standard jumpstep, which is 1e-4.
	 * 
	 * @throws 	ModelException
	 * 			Throws a ModelException if the worm can not jump. This is done in the super method.
	 * 			| if (!canJump())
	 */
	public void jump() throws ModelException {
		jump(GUIConstants.JUMP_TIME_STEP);
	}
	
	/**
	 * Verifies whether a worm can jump in its current state. As of now, the 
	 * worm only has to be facing upwards.
	 * 
	 * @return 	True if the worm has actionpoints left, if it has a world and if the position is passable.
	 * 			| if (getActionPoints() == 0
	 * 			| 	return false
	 * 			| if (!hasAWorld())
	 * 			|	return false
	 * 			| if (!getWorld().isPassable(getX(), getY(), 0, getRadius()))
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public boolean canJump() {
		if (getActionPoints() == 0)
			return false;
		if (!hasAWorld())
			return false;
		if (!getWorld().isPassable(getX(), getY(), 0, getRadius()))
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
	 * 			| if (!getWorld().isPassable(getX(), y, getRadius(), 1.1 * getRadius()))
	 * 			|	setPosition(getX(), y, getDirection)
	 * @effect	The worm will loose hitpoints after falling and will die if they are less than zero.
	 * 			| setHitPoints(newHitPoints)
	 */
	public void fall() {
		if (canFall()) {
			double resolution = 0.1*getRadius();
			double startPosition = getY();
			int distanceFallen = 0;

			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				double y = getY() - i * resolution;
				if (!getWorld().isWithinBoundaries(getX(),y)) {
					// Fell out of world.
					setPosition(getX(),y,getDirection());
					die();
					break;
				}
				if ( !getWorld().isPassable( getX(), y, getRadius(), 1.1 * getRadius() ) ) {
					// Can't fall anymore --> Worm has hit the ground.
					setPosition(getX(), y, getDirection());
					distanceFallen = (int) (startPosition - y);
					
					int newHitPoints = (int) (getHitPoints() - Math.floor(distanceFallen * 3));
					setHitPoints(newHitPoints);
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
	 * 			| else
	 * 			|	return true
	 */
	public boolean canFall() {
		if (!hasAWorld())
			return false;
		if ( getWorld().isAdjacent( getX(), getY(), getRadius() ) )
			return false;
		return true;
	}
	
	// }}
	
// {{ Shoot
	
	private Weapon equippedWeapon;
	
	/**
	 * Method to return the currently equipped weapon.
	 * 
	 * @return	The currently equipped weapon.
	 * 			| return equippedWeapon
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
	 * 			| new.getEquippedWeapon() == weapon
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
	 * 			| int index = getWeapons.indexOf(getEquippedWeapon())
	 * 			| equipWeapon(getWeapons().get(index))
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
	 * @effect	The amount of actionpoints diminishes when firing a weapon.
	 * 			Every weapon costs a specific amount of actionpoints when firing.
	 * 			| setActionPoints(getActionPoints() - getEquippedWeapon().getActionPointsCost())
	 * @effect	The equipped weapon is fired with the given propulsionyield.
	 * 			| getEquippedWeapon().shoot(propulsionYield)
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
	 * @return	The worm has to have a sufficient amount of actionpoints left, has to have
	 * 			a world and the location must be passable.
	 * 			| if (!isValidActionPoints(getActionPoints() - getEquippedWeapon().getActionPointsCost()))
	 * 			|	return false
	 * 			| if (!hasAWorld())
	 * 			|	return false
	 * 			| if (!getWorld().isPassable(getX(), getY(), 0, getRadius()))
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public boolean canShoot() {
		if (!isValidActionPoints(getActionPoints()-getEquippedWeapon().getActionPointsCost()))
			return false;
		if (!hasAWorld())
			return false;
		if (!getWorld().isPassable(getX(), getY(), 0, getRadius()))
			return false;
		return true;
	}
	
	// }}
	
// {{ Eating
	
	/**
	 * Method to let a worm try to eat all the food.
	 * 
	 * @note	A worm is able to eat a foodobject if the two objects (worm and food) are overlapping.
	 * 
	 * @effect	The worm will eat all the food it is overlapping with.
	 * 			| forall food {
	 * 			|	if (World.isOverlapping(getX(), getY(), getRadius(), food.getX(), food.getY(), Food.getRadius()))
	 * 			|		eat(food)
	 * 			| }
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
	 * 			| return this.world
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
	 * 			| return this.team
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
	 * 			| return this.weaponCollection
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
	 * 
	 * @effect	Removes each weapon object from the worm.
	 * 			| forall ( Weapon w : weaponCollection) )
	 * 			| 	removeWeapon(w);
	 */
	private void removeAllWeapons() {
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
	/**
	 * Basic inspector to return the program.
	 * 
	 * @return	The program.
	 * 			| return this.program
	 */
	public Program getProgram() {
		return program;
	}
	
	/**
	 * Method to set the program to the given program.
	 * 
	 * @param 	program
	 * 			The given program.
	 * 
	 * @post	The program is set to the given program.
	 * 			| new.getProgram() == program
	 * @effect	The current worm is associated with this program.
	 * 			| program.setWorm(this)
	 * @throws	ModelException
	 * 			Throws a ModelException when the program is invalid or if the
	 * 			worm already has a program.
	 * 			| if (!canHaveAsProgram(program))
	 * 			| if (hasAProgram)
	 */
	void setProgram(Program program) {
		if (!canHaveAsProgram(program))
			throw new ModelException("Invalid program specified.");
		if (hasAProgram())
			throw new ModelException("Already has a program");
		program.setWorm(this);
		this.program = program;
	}
	
	/**
	 * Method to check if the program is valid.
	 * 
	 * @param	program
	 * 			The program that has to be checked.
	 * @return	True if the program is not null and if it is not terminated.
	 * 			| if (program == null)
	 * 			|	return false
	 * 			| if (program.isTerminated())
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	private static boolean canHaveAsProgram(Program program) {
		if (program == null)
			return false;
		if (program.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Method to check if the worm has a program.
	 * 
	 * @return	True if the program is not null.
	 * 			| return (!(program == null))
	 */
	protected boolean hasAProgram() {
		return (!(program == null));
	}
	
	/**
	 * Method to remove the program and worm association.
	 * 
	 * @effect	If the worm has a program it will be removed.
	 * 			| if (hasAProgram())
	 * 			|	program.removeWorm()
	 * @post	The program will be set to null.
	 * 			| new.getProgram() == null
	 */
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
	 * 
	 * @effect	The worm will be removed from the world if it has a world.
	 * 			| if (hasAWorld())
	 * 			|	world.removeWorm(this)
	 * @effect	The worm will be removed from the team if it has a team.
	 * 			| if (hasATeam())
	 * 			|	team.removeWorm(this)
	 * @effect	The program will be removed from the worm if it has a program.
	 * 			| if (hasAProgram())
	 * 			|	program.removeWorm();
	 * @effect	All the weapons the worm has will be removed.
	 * 			| removeAllWeapons();
	 * @post	The boolean value terminated is set to true.
	 * 			| new.isTerminated() == true;
	 */
	public void terminate() {
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


