package worms.model;

import be.kuleuven.cs.som.annotate.Basic;

public class Projectile {
	
	
	//TODO
	public boolean wormIsHit() {
		return false;
	}
	
	//TODO
	public void shoot() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
// {{ Constructors
	
	/**
	 * Constructor for the Projectile class.
	 */
	public Projectile() {
		this(0,0,0,0);
	}
	
	/**
	 * Constructor for the Projectile class.
	 * 
	 * @param 	x
	 * 			The x-coordinate of the projectile.
	 * @param 	y
	 * 			The y-coordinate of the projectile.
	 * @param 	direction
	 * 			The direction of the projectile.
	 * @param 	propulsionYield
	 * 			The yield the projectile is fired with.
	 * 
	 * @effect	| setPosition(new position(x, y, direction))
	 * @effect	| setPropulsionYield(propulsionYield)
	 */
	public Projectile(double x, double y, double direction, int propulsionYield) {
		setPosition(new Position(x, y, direction));
		setPropulsionYield(propulsionYield);
	}
	
	// }}
	
	/**
	 * Method to jump.
	 * 
	 * @param 	timeStep
	 * 			The timestep with which the jump is evaluated.
	 */
	public void jump(double timeStep) {
		double[] newPos = jumpStep(jumpTime(timeStep));
		getPosition().setX( newPos[0] );
		getPosition().setY( newPos[1] );
	}
	
	/**
	 * Method to calculate the time it takes to perform the jump.
	 * 
	 * @param 	timeStep
	 * 			The timestep with which the time is calculated.
	 * 
	 * @return	The time it takes to perform the jump.
	 */
	public double jumpTime(double timeStep) {
		double force = getWeapon().getForce(getPropulsionYield());
		return getPosition().ballisticTrajectoryTime(getWorld(), force, 0.5, getWeapon().getProjectileMass(), timeStep);
	}
	
	/**
	 * Calculates and returns the x and y position of the projectile during the jump at a specified time.
	 * 
	 * @param 	time
	 * 			The time at which the jump should be evaluated.
	 * 
	 * @return 	The x and y positions of the projectile during the jump at a specified time, returned in an array of doubles.
	 */
	public double[] jumpStep(double time) {
		double force = getWeapon().getForce(getPropulsionYield());
		return getPosition().ballisticTrajectory(force, 0.5, getWeapon().getProjectileMass(), time);
	}
	
	
// {{ Position
	
	private Position position;
	
	/**
	 * Basic inspector that returns the position object of the projectile. 
	 * 
	 * @return 	The position object of the projectile.
	 */
	@Basic
	public Position getPosition() {
		return position;
	}
	
	/**
	 * Method to set the position of a projectile (if it is valid).
	 * 
	 * @param 	position
	 * 			The position of a projectile
	 * 
	 * @post	| new.getPosition() == position
	 * @throws	ModelException
	 * 			| (!isValidPosition(position))
	 */
	public void setPosition(Position position) throws ModelException{
		if (!isValidPosition(position))
			throw new ModelException("Invalid position specified");
		this.position = position;
	}
	
	/**
	 * Method to check whether a position is valid or not.
	 * 
	 * @param 	position
	 * 			The position to check.7
	 * 
	 * @return	| return (position != null)
	 */
	public boolean isValidPosition(Position position) {
		if (position==null)
			return false;
		return true;
	}
	
	
	// }}
	
// {{ Propulsion Yield
	
	private int propulsionYield;
	
	@Basic
	/**
	 * Basic inspector to get the propulsionyield.
	 * 
	 * @return	The propulsionyield.
	 */
	public int getPropulsionYield() {
		return propulsionYield;
	}
	
	/**
	 * Method to set the propulsionyield to the given value if the given value is valid.
	 * 
	 * @param 	propulsionYield
	 * 			The new propulsionyield.
	 * 
	 * @post	| new.getPropulsionYield() == propulsionYield()
	 * @throws 	ModelException
	 * 			if (!isValidPropulsionYield(propulsionYield))
	 */
	public void setPropulsionYield(int propulsionYield) throws ModelException {
		if (!isValidPropulsionYield(propulsionYield))
			throw new ModelException("Invalid propulsion yield.");
		this.propulsionYield = propulsionYield;
	}
	
	/**
	 * Method to check if the given propulsionyield is valid.
	 * 
	 * @param 	propulsionYield
	 * 			The propulsionyield that has to be checked.
	 * 
	 * @return	| if (propulsionyield < 0)
	 * 			|	return false
	 * 			| if (propulsionyield > 100)
	 *			|	return false
	 *			| else
	 *			| 	return true
	 */
	public boolean isValidPropulsionYield(int propulsionYield) {
		if (propulsionYield<0)
			return false;
		if (propulsionYield>100)
			return false;
		return true;
	}
	
	// }}
	
// {{ Radius

	/**
	 * Method to get the radius of a projectile.
	 * 
	 * @return	The radius of a projectile.
	 * 
	 * @throws 	ModelException
	 * 			if (!hasAWeapon())
	 */
	public double getRadius() throws ModelException {
		if (!hasAWeapon())
			throw new ModelException("This projectile does not have a weapon assigned to it.");
		// m = rho*V
		// V = (4/3)*pi*r^3
		// r = ((m/rho)*(3/4)/pi)^(1/3)
		double m = weapon.getProjectileMass();
		double rho = 7800;
		double pi = Math.PI;
		return Math.pow((m/rho)*(3/4)*(1/pi), 1/3);
	}
	
// }}
	
// {{ Terminated
	
	private boolean terminated;
	
	@Basic
	/**
	 * Returns the boolean-type terminated.
	 * 
	 * @return	this.terminated
	 */
	public boolean isTerminated() {
		return terminated;
	}
	
	/**
	 * Method to terminate a projectile.
	 */
	public void terminate() {
		if (hasAWorld())
			world.removeProjectile();
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
	 * 			|	return true
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
	
// {{ Weapon Association
	
	private Weapon weapon;
	
	@Basic
	/**
	 * Returns the weapon associated with the projectile.
	 * 
	 * @return	this.weapon
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * Method to set the weapon to the given weapon.
	 * 
	 * @param 	weapon
	 * 			The new weapon.
	 * 
	 * @post	| new.getWeapon() == weapon
	 * @throws 	ModelException
	 * 			| if (!canHaveAsWeapon(weapon))
	 * 			| if (hasAWeapon())
	 */
	public void setWeapon(Weapon weapon) throws ModelException {
		if (!canHaveAsWeapon(weapon))
			throw new ModelException("Invalid weapon specified.");
		if (hasAWeapon())
			throw new ModelException("Already has a weapon.");
		this.weapon = weapon;
	}
	
	/**
	 * Method to check the validity of the given weapon.
	 * 
	 * @param 	weapon
	 * 			The weapon to check.
	 * @return	| if (weapon == null)
	 * 			|	return false
	 * 			| if (weapon.isTerminated())
	 * 			|	return false
	 * 			| else
	 * 			| return true
	 */
	public boolean canHaveAsWeapon(Weapon weapon) {
		if (weapon==null)
			return false;
		if (weapon.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Checks if the weapon is equal to null.
	 * 
	 * @return	| return (!(weapon == null))
	 */
	public boolean hasAWeapon() {
		return (!(weapon==null));
	}
	
	/**
	 * Checks if the current weapon is equal to the given weapon.
	 * 
	 * @param 	weapon
	 * 			The given weapon.
	 * 
	 * @return	| (this.weapon == weapon)
	 */
	public boolean hasAsWeapon(Weapon weapon) {
		return (this.weapon==weapon);
	}
	
	/**
	 * Method to remove a weapon.
	 */
	public void removeWeapon() {
		weapon = null;
	}
	
	
	// }}
}
