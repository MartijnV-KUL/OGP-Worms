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
	 * @effect 	The X and Y co�rdinates of the worm are set to the given values.
	 * 			| setPosition(x, y)
	 * @effect 	The direction of the worm is set to the given value.
	 * 			| setDirection(direction)
	 * @effect 	The radius of the worm is equal to the given value.
	 * 			| setRadius(radius)
	 * @effect 	The name of the worm is equal to the given string if it is valid.
	 * 			| setName(name)
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
	 * @return The position object of the worm.
	 */
	@Basic
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		if (!isValidPosition(position))
			throw new ModelException("Invalid position specified");
		this.position = position;
		if (canFall())
			fall();
		tryToEatAll();
		//TODO other checks?
	}
	
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
	 * @return	The hitpoints of the worm.
	 */
	@Basic
	public int getHitPoints() {
		return hitPoints;
	}

	/**
	 * Basic inspector that returns the maximum amount of hitpoints of the worm.
	 * @return
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
	
	//TODO
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
	 * @return	True if the number of hitpoints lays between 0 and the maximum amount.
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
	 * Moves the selected worm to the given coordinates.
	 * 
	 * @effect	The worm moves through the method activeMoveSingleStep if it can move.
	 * 			| activeMove()
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
	

	/**
	 * Moves the worm a single step.
	 * 
	 * @effect	The position of the worm is set to it's new co�rdinates.
	 * 			| setPosition( getRadius() * Math.cos(getDirection()), getRadius() * Math.sin(getDirection()) )
	 * @effect	The amount of actionpoints diminishes when moving the worm.
	 * 			| setActionPoints( getActionPoints() - getActionPointsCostMove() )
	 * @note	This method should be implemented in a defensive manner,
	 * 			but the methods "setActionPoints" and "setposition" already throw an error for an invalid value.
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
	 * @effect	The amount of actionpoints is set to zero after performing the jump.
	 * 			| setActionPoints(0)
	 * @effect	The position of the worm is set to it's new co�rdinates, with newPos[0] the new x-co�rdinate.
	 * 			newPos[1] is the new y-co�rdinate. These points are calculated in jumpStep and jumpTime.
	 * 			| setPosition( newPos[0], newPos[1] )
	 * @note	The code "if (canJump())" prevents unnecessary calculations if the worm can't jump.
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
	
	public double jumpTime(double timeStep) {
		double force = ((double)5*getActionPoints()) + (getMass()*World.getGravitationalAcceleration());
		return getPosition().ballisticTrajectoryTime(getWorld(), force, 0.5, getMass(), timeStep);
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
	 * 			| return ( (0 >= getDirection()) && (getDirection() <= Math.PI) && getActionPoints() > 0 )
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
	
	/* TODO Note: Does the pixels count up or down when you move from top to bottom?
	 * Right now it's implemented that pixels count up when you move from top to bottom. (So 0 on top and max on bottom).
	 * If it is reversed the if-loops have to be modified for i--.
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
	
	public boolean canFall() {
		if (world.isAdjacent(getPosition().getX(), getPosition().getY()))
			return false;
		return true;		
	}
	
	// }}
	
// {{ Shoot
	
	private Weapon equippedWeapon;
	
	public Weapon getEquippedWeapon() {
		return equippedWeapon;
	}
	
	public void equipWeapon(Weapon weapon) throws ModelException {
		if (!hasAsWeapon(weapon))
			throw new ModelException("Worm does not have this weapon.");
		equippedWeapon = weapon;
	}
	
	public void equipNextWeapon() {
		int index = getWeapons().indexOf(getEquippedWeapon());
		if (index==(getWeapons().size()-1))
			index = 0;
		else
			index += 1;
		equipWeapon(getWeapons().get(index));
	}
	
	public void shoot(int propulsionYield) throws ModelException {
		if (!canShoot())
			throw new ModelException("This worm can not shoot.");
		getEquippedWeapon().shoot(propulsionYield);
	}
	
	public boolean canShoot() {
		if (!isValidActionPoints(getActionPoints()))
			return false;
		if (!isValidPosition(getPosition()))
			return false;
		return true;
	}
	
	// }}
	
// {{ Eating
	
	public void tryToEatAll() {
		for ( Food food : getWorld().getFood() ) {
			if (World.isOverlapping( getPosition().getX(), getPosition().getY(),      getRadius(), 
					                          food.getX(),          food.getY(), food.getRadius() )) {
				eat(food);
			}	
		}
	}
	
	public void eat(Food food) {
		setRadius(1.1*getRadius());
		food.terminate();
	}
	
	// }}
	
// {{ World Association
	
	private World world;
	
	@Basic
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) throws ModelException {
		if (!canHaveAsWorld(world))
			throw new ModelException("Invalid world specified.");
		if (hasAWorld())
			throw new ModelException("Already has a world.");
		this.world = world;
	}
	
	public boolean canHaveAsWorld(World world) {
		if (world==null)
			return false;
		if (world.isTerminated())
			return false;
		return true;
	}
	
	public boolean hasAWorld() {
		return(!(world==null));
	}
	
	public boolean hasAsWorld(World world) {
		return (this.world==world);
	}
	
	public void removeWorld() {
		world = null;
	}
	
	
	
	// }}
	
// {{ Team Association
	
	private Team team;
	
	@Basic
	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team team) throws ModelException {
		if (!canHaveAsTeam(team))
			throw new ModelException("Invalid team specified.");
		if (hasATeam())
			throw new ModelException("Already has a team.");
		this.team = team;
	}
	
	public boolean canHaveAsTeam(Team team) {
		if (team==null)
			return false;
		if (team.isTerminated())
			return false;
		return true;
	}
	
	public boolean hasATeam() {
		return(!(team==null));
	}
	
	public boolean hasAsTeam(Team team) {
		return (this.team==team);
	}
	
	public void removeTeam() {
		team = null;
	}
	
	
	// }}
	
// {{ Weapon Association

	private final ArrayList<Weapon> weaponCollection = new ArrayList<Weapon>();
	
	@Basic
	public ArrayList<Weapon> getWeapons() {
		return weaponCollection;
	}
	
	public void addNewWeapon(Weapon weapon) throws ModelException {
		if (!isValidWeapon(weapon))
			throw new ModelException("Invalid worm specified.");
		if (hasAsWeapon(weapon))
			throw new ModelException("Worm already has weapon.");
		weapon.setWorm(this);
		weaponCollection.add(weapon);
	}
	
	public static boolean isValidWeapon(Weapon weapon) {
		if (weapon==null)
			return false;
		if (weapon.isTerminated())
			return false;
		return true;
	}
	
	public void removeWeapon(Weapon weapon) {
		if (!hasAsWeapon(weapon)) {
			throw new ModelException("Weapon not found.");
		}
		weapon.removeWorm();
		weaponCollection.remove(weapon);
	}
	
	public void removeAllWeapons() {
		for ( Weapon weapon : weaponCollection ) {
			removeWeapon(weapon);
		}
	}
	
	public boolean hasAsWeapon(Weapon weapon) {
		return weaponCollection.contains(weapon);
	}
	
	// }}
	
// {{ Terminated
	
	private boolean terminated;
	
	@Basic
	public boolean isTerminated() {
		return terminated;
	}
	
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


