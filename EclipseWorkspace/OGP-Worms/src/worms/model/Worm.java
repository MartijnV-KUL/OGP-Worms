package worms.model;

import java.util.ArrayList;

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
	 * @pre		The given X and Y co�rdinates must be a valid position.
	 * 			| isValidX(x)
	 * 			| isValidY(y)
	 * @pre		The given direction is valid.
	 * 			| isValidDirection(direction)
	 * @pre		The given radius is valid.
	 * 			| isValidRadius(radius)
	 * @pre		The given name is valid.
	 * 			| isValidName(name)
	 * @effect 	The X and Y co�rdinates and the direction of the worm are set to the given values in the class Position.
	 * 			| setPosition(new Position(x, y, direction))
	 * @effect 	The radius of the worm is equal to the given value.
	 * 			| setRadius(radius)
	 * @effect 	The name of the worm is equal to the given string if it is valid.
	 * 			| setName(name)
	 * @effect	A new weapon is added, as an object from the class Rifle.
	 * 			| addNewWeapon(new Rifle())
	 * @effect	A new weapon is added, as an object from the class Bazooka.
	 * 			| addNewWeapon(new Bazooka))
	 * @effect	The amount of action points is set to the maximum number of actionpoints.
	 * 			This is done in the method setRadius, by the method resetMaxActionPoints.
	 * 			| new.getActionPoints() == maxActionPoints;
	 * @note	The setters will throw an exception if the given value is not valid.
	 */
	public Worm(double x, double y, double direction, double radius, String name) {
		setPosition(new Position(x, y, direction));
		setRadius(radius);
		setName(name);
		addNewWeapon(new Rifle());
		addNewWeapon(new Bazooka());
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

	private Position position;
	
	/**
	 * Basic inspector that returns the position object of the worm. 
	 * 
	 * @return The position object of the worm.
	 */
	@Basic
	public Position getPosition() {
		return position;
	}
	
	/**
	 * Method that sets the position of the worm.
	 * 
	 * @param	position
	 * 			The position of the worm.
	 */
	public void setPosition(Position position) {
		if (!isValidPosition(position))
			throw new ModelException("Invalid position specified");
		this.position = position;
		if (canFall())
			fall();
		tryToEatAll();
		//TODO other checks?
	}
	
	/**
	 * Checks the validity of the specified position as a position of a worm.
	 * 
	 * @param	position
	 * 			The position that has to be checked.
	 * @return	The new position is not "null", is within the world boundaries and is passable.
	 * 			| if (hasAWorld()) {
	 * 			|	if ( !getWorld().isWithinBoundaries( position.getX(), position.getY() ) )
	 * 			|		return false
	 * 			|	if ( !getWorld().isPassable( position.getX(), position.getY() ) )
	 * 			|		return false
	 * 			| }
	 * 			| else 
	 * 			|	return true
	 * @note	The line "if (hasAWorld())" checks if the current world is not equal to "null".
	 */
	public boolean isValidPosition(Position position) {
		if (position==null)
			return false;
		if (hasAWorld()) {
			if ( !getWorld().isWithinBoundaries( position.getX(), position.getY() ) )
				return false;
			if ( !getWorld().isPassable( position.getX(), position.getY() ) )
				return false;
		}
		return true;
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
	public static boolean isValidName(String name) {
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
	public boolean isValidRadius(double radius) {
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
	public double getMass() {
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
	public void setActionPoints(int actionPoints) {
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
	public boolean isValidActionPoints(int actionPoints) {
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
	 * 			| if (hitPoints < 0)
	 * 			|		new.getHitPoints() == 0
	 * 			| if (hitPoints > getMaxHitPoints()
	 * 			|		new.getHitPoints() == getMaxHitPoints()
	 * 			| else
	 * 			|		new.getHitPoints() == hitPoints
	 */
	public void setHitPoints(int hitPoints) {
		if (! isValidHitPoints(hitPoints)) {
			if (hitPoints < 0)
				this.hitPoints = 0;
			if (hitPoints > getMaxActionPoints())
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
	public boolean isValidHitPoints(int hitPoints) {
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
	 * Moves the selected worm.
	 * 
	 * @effect	The worm moves through the method activeMoveSingleStep if it can move.
	 * 			| activeMoveSingleStep()
	 * @throws 	ModelException
	 * 			Throws a ModelException if the worm can not move.
	 * 			| if (!canMove())
	 * 			|		throw new ModelException
	 */
	public void activeMove() throws ModelException {
		if ( ! canMove() )
			throw new ModelException("Not enough action points.");
		activeMoveSingleStep();
	}
	

	//TODO implement divergence
	/**
	 * Moves the worm a single step.
	 * 
	 * @effect	The X-coordinate of the worm is set to the new value.
	 * 			| getPosition().setX(getPosition().getX() + deltaX)
	 * @effect	The Y-coordinate of the worm is set to the new value.
	 * 			| getPosition().setY(getPosition().getY() + deltaY)
	 * @effect	The amount of actionpoints diminishes when moving the worm.
	 * 			| setActionPoints( getActionPoints() - getActionPointsCostMove() )
	 * @note	This method should be implemented in a defensive manner,
	 * 			but the methods "setActionPoints", "setX" and "setY" already throw an error for an invalid value.
	 * 			No need to use double code for this exceptionhandling.
	 */
	public void activeMoveSingleStep() {
		double deltaX = getRadius() * Math.cos(getPosition().getDirection());
		double deltaY = getRadius() * Math.sin(getPosition().getDirection());

		getPosition().setX(getPosition().getX()+deltaX);
		getPosition().setY(getPosition().getY()+deltaY);
		setActionPoints( getActionPoints() - getActionPointCostMove() );
	}
	
	/**
	 * Calculates the total amount of actionpoints it takes to perform a move.
	 * 
	 * @return	The total cost of actionpoints to perform the move.
	 * 			| return (int) ( Math.abs(Math.cos(getDirection())) + Math.abs(4*Math.sin(getDirection())) )
	 * @note	The cost of actionpoints is rather low, especially in comparison with the cost for turning.
	 */
	public int getActionPointCostMove() {
		return (int) ( Math.abs(Math.cos(getPosition().getDirection())) + Math.abs(4*Math.sin(getPosition().getDirection())) );
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
	//TODO implement impassable terrain
	public boolean canMove() {
		if ( ! isValidActionPoints( getActionPoints() - getActionPointCostMove() ) )
			return false;
		return true;
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
		getPosition().setDirection( (((getPosition().getDirection() + additionalDirection) % (2*Math.PI)) + 2*Math.PI) % (2*Math.PI) );
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
	public int getActionPointCostTurn(double additionalDirection) {
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

	/**
	 * Makes the selected worm jump.
	 * 
	 * @param	timeStep
	 * 			The timestep with which the jump trajectory is calculated.
	 * 
	 * @effect	The amount of actionpoints is set to zero after performing the jump.
	 * 			| setActionPoints(0)
	 * @effect	The position of the worm is set to it's new co�rdinates, with newPos[0] the new x-co�rdinate.
	 * 			newPos[1] is the new y-co�rdinate. These points are calculated in jumpStep and jumpTime.
	 * 			| getPosition().setX( newPos[0] )
	 * 			| getPosition().setY( newPox[1] )
	 * @note	If the player tries to jump when it's not possible he will get "punished" for trying.
	 * 			The amount of actionpoints will always be set to 0, whether the worm actually jumps or not.
	 * 			This is a choice of implementation.
	 */
	public void jump(double timeStep) {
		double[] newPos = jumpStep(jumpTime(timeStep));
		getPosition().setX( newPos[0] );
		getPosition().setY( newPos[1] );
		
		setActionPoints(0);
	}
	
	/**
	 * Method to calculate the duration of a jump.
	 * 
	 * @param	timeStep
	 * 			The timestep with which the jump trajectory is calculated.
	 * 
	 * @return	The time needed to perform the jump.
	 * 			| return getPosition().ballisticTrajectoryTime(getWorld(), force, 0.5, getMass, timeStep)
	 * @note	The method "ballisticTrajectoryTime(...)" is used to calculate the ballistic trajectory of the worm while jumping.
	 */
	public double jumpTime(double timeStep) {
		double force = ((double)5*getActionPoints()) + (getMass()*World.getGravitationalAcceleration());
		return getPosition().ballisticTrajectoryTime(getWorld(), force, 0.5, getMass(), timeStep);
	}
	
	/**
	 * Calculates and returns the x and y position of the worm during the jump at a specified time.
	 * 
	 * @param 	time
	 * 			The time at which the jump should be evaluated.
	 * 
	 * @return 	The x and y positions of the worm during the jump at a specified time, returned in an array of doubles.
	 * @throws	ModelException
	 * 			Throws a ModelException if the time given is less than zero.
	 * 			| time < 0
	 * @note	If the code "if (canJump())" is not present a trajectory will still be displayed by the GUI, even if the worm can't jump.
	 * 			This line is added to prevent the GUI from getting and displaying the trajectory if the worm can not jump.
	 */
	public double[] jumpStep(double time) throws ModelException {
		if (time < 0)
			throw new ModelException("The given time is less than zero.");
		if (canJump()) {
			double force = ((double)5*getActionPoints()) + (getMass()*World.getGravitationalAcceleration());
			return getPosition().ballisticTrajectory(force, 0.5, getMass(), time);
		}
		double [] output = {getPosition().getX(), getPosition().getY()};
		return output;
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
		if (getActionPoints() == 0) {
			return false;
		}
		if (getPosition().getDirection() < 0) {
			return false;
		}
		if (getPosition().getDirection() > Math.PI) {
			return false;
		}
		return true;
	}
	
	// }}	

// {{ Fall

	/**
	 * Method to make a worm fall down to impassable terrain or out of the game world.
	 */
	public void fall() {
		if (canFall()) {
			for (int i = (int) getPosition().getY(); i <= getWorld().getHeight(); i++) {
				if (i == getWorld().getHeight()) {
					getWorld().removeWorm(this);
					break;
				}
				if (!getWorld().isPassable(getPosition().getX(), i, getRadius())) {
					getPosition().setX(getPosition().getX());
					getPosition().setY(i);
					double metersFallen = i - getPosition().getY();
					int hitPointsLost = (int) Math.floor(metersFallen * 3);
					setHitPoints(getHitPoints() - hitPointsLost);
					if (!isAlive()) {
						getWorld().removeWorm(this);
					}
					break;				
				}
			}
		}
	}
	
	/**
	 * Checks if a worm can fall or not.
	 * 
	 * @return	True if a worm is not adjacent to any terrain.
	 * 			| if (world.isAdjacent(getPosition().getX(), getPosition().getY()))
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public boolean canFall() {
		if (world.isAdjacent(getPosition().getX(), getPosition().getY()))
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
		int index = getWeapons().indexOf(getEquippedWeapon());
		if (index==(getWeapons().size()-1))
			index = 0;
		else
			index += 1;
		equipWeapon(getWeapons().get(index));
	}
	
	/**
	 * Method to let a worm shoot with an equipped weapon, if it is able to shoot.
	 * 
	 * @param 	propulsionYield
	 * 			The yield the weapon is fired with.
	 * @throws	ModelException
	 * 			Throws a modelexception if the worm can not shoot.
	 * 			if (!canShoot())
	 */
	public void shoot(int propulsionYield) throws ModelException {
		if (!canShoot())
			throw new ModelException("This worm can not shoot.");
		getEquippedWeapon().shoot(propulsionYield);
	}
	
	/**
	 * Checks whether a worm can shoot or not.
	 * 
	 * @return	The worm has to have a sufficient amount of actionpoints left, and has to be
	 * 			in a valid position.
	 * 			| if (!isValidActionPoints(getActionPoints()))
	 * 			|	return false
	 * 			| if (!isValidPosition(getPosition()))
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public boolean canShoot() {
		if (!isValidActionPoints(getActionPoints()))
			return false;
		if (!isValidPosition(getPosition()))
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
	public void tryToEatAll() {
		for ( Food food : getWorld().getFood() ) {
			if (World.isOverlapping( getPosition().getX(), getPosition().getY(),      getRadius(), 
					                          food.getX(),          food.getY(), food.getRadius() )) {
				eat(food);
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
	public void eat(Food food) {
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
	 * @return	True if the world is not null and if it is not terminated.
	 * 			| if (world == null)
	 * 			|	return false
	 * 			| if (world.isTerminated())
	 * 			|	return false
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
	 * @return	Whether or not the world is null.
	 * 			| return (!(world == null))
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
	 * @return	Whether of not the given world is equal to the current world
	 * 			| return (this.world == world)
	 */
	public boolean hasAsWorld(World world) {
		return (this.world==world);
	}
	
	/**
	 * Removes the current world.
	 * 
	 * @post	The current world is removed.
	 * 			| new.getWorld() == null
	 */
	public void removeWorld() {
		world = null;
	}
	
	
	
	// }}
	
// {{ Team Association
	
	private Team team;
	
	@Basic
	/**
	 * Returns the current team.
	 * 
	 * @return	The current team
	 */
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
	public void setTeam(Team team) throws ModelException {
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
	public boolean canHaveAsTeam(Team team) {
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
	public boolean hasATeam() {
		return(!(team==null));
	}
	
	/**
	 * Checks if the current team is equal to the given team.
	 * 
	 * @param 	team
	 * 			The given team.
	 * 
	 * @return	Whether or not the given team is equal to the current team.
	 * 			| return (this.team == team)
	 */
	public boolean hasAsTeam(Team team) {
		return (this.team==team);
	}
	
	/**
	 * Removes the current team.
	 * 
	 * @post	The current team is removed.
	 * 			| new.getTeam() == null
	 */
	public void removeTeam() {
		team = null;
	}
	
	
	// }}
	
// {{ Weapon Association

	private final ArrayList<Weapon> weaponCollection = new ArrayList<Weapon>();
	
	@Basic
	/**
	 * Returns the collection of weapons.
	 * 
	 * @return	The collection of weapons.
	 */
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
	public static boolean isValidWeapon(Weapon weapon) {
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
	public void removeAllWeapons() {
		for ( Weapon weapon : weaponCollection ) {
			removeWeapon(weapon);
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
	public boolean hasAsWeapon(Weapon weapon) {
		return weaponCollection.contains(weapon);
	}
	
	// }}
	
// {{ Terminated
	
	private boolean terminated;
	
	@Basic
	/**
	 * Returns the boolean-type terminated.
	 * 
	 * @return	The boolean-type terminated.
	 */
	public boolean isTerminated() {
		return terminated;
	}
	
	/**
	 * Method to terminate the worm and all corresponding objects from the world.
	 */
	public void terminate() {
		if (hasAWorld())
			world.removeWorm(this);
		if (hasATeam())
			team.removeWorm(this);
		removeAllWeapons();
		terminated = true;
	}
	
	// }}
	
	
	
}


