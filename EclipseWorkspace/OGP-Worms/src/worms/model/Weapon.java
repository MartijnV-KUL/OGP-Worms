package worms.model;

import be.kuleuven.cs.som.annotate.Basic;

public abstract class Weapon {

// {{ Projectile Mass
	
	protected double projectileMass;
	
	@Basic
	/**
	 * Returns the mass of projectiles.
	 * 
	 * @return	The mass of projectiles.
	 */
	public double getProjectileMass() {
		return projectileMass;
	}
	
	/**
	 * Method to set the mass of projectiles (if the given mass is valid).
	 * 
	 * @param 	projectileMass
	 * 			The new mass of projectiles.
	 * @post	| new.getProjectileMass() == projectileMass
	 * @throws 	ModelException
	 * 			| ( !isValidProjectileMass(projectileMass))
	 */
	public void setProjectileMass(double projectileMass) throws ModelException {
		if (!isValidProjectileMass(projectileMass))
			throw new ModelException("Invalid projectile mass.");
		this.projectileMass = projectileMass;	
	}
	
	/**
	 * Checks if the given mass is valid.
	 * 
	 * @param 	projectileMass
	 * 			The given mass of projectiles.
	 * @return	| if (Double.isNaN(projectileMass))
	 * 			|	return false
	 * 			| if (projectileMass <= 0)
	 * 			|	return false
	 * 			| if (projectileMass == Double.POSITIVE_INFINITY)
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public boolean isValidProjectileMass(double projectileMass) {
		if (Double.isNaN(projectileMass))
			return false;
		if (projectileMass<=0)
			return false;
		if (projectileMass==Double.POSITIVE_INFINITY)
			return false;
		return true;
	}
	
	// }}
	
// {{ Name
	
	private String name;
	
	/**
	 * Returns the name of the weapon.
	 * 
	 * @return	The name of the weapon.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the weapon to the given name (if it is valid).
	 * 
	 * @param 	name
	 * 			The new name.
	 * 
	 * @post	| new.getName() == name
	 * @throws 	ModelException
	 * 			| if (!isValidName(name))
	 */
	protected void setName(String name) throws ModelException {
		if (!isValidName(name))
			throw new ModelException("Invalid name.");
		this.name = name;
	}
	
	/**
	 * Checks if the given name is valid or not.
	 * 
	 * @param 	name
	 * 			The given name.
	 * 
	 * @return	| if (!name.matches("[a-zA-Z0-9- ]*"))
	 *			|	return false
	 *			| if (!Character.isUpperCase(name.charAt(0)))
	 *			|	return false
	 *			| if (name.length() < 2)
	 *  		|	return false
	 *			| else
	 *			| 	return true
	 */
	public boolean isValidName(String name) {
		if (!name.matches("[a-zA-Z0-9- ]*"))
			return false;
		if (!Character.isUpperCase(name.charAt(0)))
			return false;
		if (name.length() < 2)
			return false;
		return true;
	}
	
// }}
	
// {{ Force

	/**
	 * Abstract method to return the force.
	 * 
	 * @param 	propulsionYield
	 * 			The given yield of the weapon.
	 * @return	The force of the weapon.
	 * 			No formal documentation because of abstract method.
	 */
	public abstract double getForce( int propulsionYield );
	
	// }}
	
// {{ Hit Points Damage

	protected int hitPointsDamage;
	
	/**
	 * Returns the amount of damage.
	 * 
	 * @return	The amount of damage of the weapon.
	 */
	public int getHitPointsDamage() {
		return hitPointsDamage;
	}
	
	/**
	 * Method to set the amount of damage a weapon does.
	 * 
	 * @param 	hitPointsDamage
	 * 			The amount of damage
	 * 
	 * @post	| new.getHitPointsDamage() == hitPointsDamage
	 * @throws 	ModelException
	 * 			| if (!isValidHitPointsDamage(hitPointsDamage))
	 */
	public void setHitPointsDamage(int hitPointsDamage) throws ModelException {
		if (!isValidHitPointsDamage(hitPointsDamage))
			throw new ModelException("Invalid hit points damage");
		this.hitPointsDamage = hitPointsDamage;
	}
	
	/**
	 * Checks if the amount of damage is valid.
	 * 
	 * @param 	hitPointsDamage
	 * 			The amount of damage
	 * 
	 * @return	| return (hitPointsDamage > 0)
	 */
	public boolean isValidHitPointsDamage(int hitPointsDamage) {
		if (hitPointsDamage<0)
			return false;
		return true;
	}
	
	// }}
	
// {{ Action Points Cost

	protected int actionPointsCost;
	
	/**
	 * Returns the cost of actionpoints for firing the weapon.
	 * 
	 * @return	The cost of actionpoints.
	 */
	public int getActionPointsCost() {
		return actionPointsCost;
	}
	
	/**
	 * Method to set the cost of actionpoints for firing the weapon.
	 * 
	 * @param 	actionPointsCost
	 * 			The amount of actionpoints a weapon costs
	 * 
	 * @post	| new.getActionPointsCost() == actionPointsCost
	 * @throws 	ModelException
	 * 			| if (!isValidActionPointsCost(actionPointsCost))
	 */
	public void setActionPointsCost(int actionPointsCost) throws ModelException {
		if (!isValidActionPointsCost(actionPointsCost))
			throw new ModelException("Invalid action points cost.");
		this.actionPointsCost = actionPointsCost;
	}
	
	/**
	 * Checks if the cost is valid.
	 * 
	 * @param 	actionPointsCost
	 * 			The actionpoint cost for firing.
	 * 
	 * @return	| return (actionPointsCost > 0)
	 */
	public boolean isValidActionPointsCost(int actionPointsCost) {
		if (actionPointsCost<0)
			return false;
		return true;
	}
	
	// }}
	
// {{ Ammunition

	private int ammunition = 0;
	
	/**
	 * Returns the ammunition.
	 * 
	 * @return	The ammunition.
	 */
	public int getAmmunition() {
		return ammunition;
	}
	
	/**
	 * Method to set the ammunition.
	 * 
	 * @param 	ammunition
	 * 			The amount of ammunition
	 * 
	 * @post	| new.getAmmunition() == ammunition
	 * @throws 	ModelException
	 * 			| if (!isValidAmmunition(ammunition))
	 */
	public void setAmmunition(int ammunition) throws ModelException {
		if (!isValidAmmunition(ammunition))
			throw new ModelException("Invalid ammunition.");
		this.ammunition = ammunition;
	}
	
	/**
	 * Checks if the given ammunition is valid.
	 * 
	 * @param 	ammunition
	 * 			The given ammunition.
	 * @return	| return (ammunition > 0)
	 */
	public boolean isValidAmmunition(int ammunition) {
		if (ammunition<0)
			return false;
		return true;
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
	 * Method to terminate the weapon and all corresponding objects from the world.
	 */
	public void terminate() {
		if (hasAWorm())
			worm.removeWeapon(this);
		if (hasAProjectile())
			removeProjectile();
		terminated = true;
	}
	
	// }}
	
// {{ Worm Association
	
	private Worm worm;
	
	@Basic
	/**
	 * Returns the worm the weapon belongs to.
	 * 
	 * @return	The worm the weapon belongs to.
	 */
	public Worm getWorm() {
		return worm;
	}
	
	/**
	 * Method to set a new worm to a weapon.
	 * 
	 * @param 	worm
	 * 			The new worm.
	 * 
	 * @post	| new.getWorm() == worm
	 * @throws 	ModelException
	 * 			| if (!canHaveAsWorm(worm)
	 * 			|	throw new ModelException
	 * 			| if (hasAWorm())
	 * 			|	throw new ModelException
	 */
	public void setWorm(Worm worm) throws ModelException {
		if (!canHaveAsWorm(worm))
			throw new ModelException("Invalid worm specified.");
		if (hasAWorm())
			throw new ModelException("Already has a worm.");
		this.worm = worm;
	}
	
	/**
	 * Checks if the given worm is valid.
	 * 
	 * @param 	worm
	 * 			The given worm.
	 * 
	 * @return	| if (worm == null)
	 * 			| 	return false
	 * 			| if (worm.isTerminated())
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public boolean canHaveAsWorm(Worm worm) {
		if (worm==null)
			return false;
		if (worm.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Checks if a worm is not null.
	 * 
	 * @return	(!(worm == null))
	 */
	public boolean hasAWorm() {
		return(!(worm==null));
	}
	
	/**
	 * Checks if the given worm is equal to the current worm.
	 * 
	 * @param 	worm
	 * 			The given worm.
	 * 
	 * @return	| return (this.worm == worm)
	 */
	public boolean hasAsWorm(Worm worm) {
		return (this.worm==worm);
	}
	
	/**
	 * Method to remove a worm.
	 * 
	 * @post	| new.getWorm() == null
	 */
	public void removeWorm() {
		worm = null;
	}
	
	// }}
	
// {{ Projectile Association
	
	private Projectile projectile;
	
	@Basic
	/**
	 * Basic inspector to return the current projectile.
	 * 
	 * @return	The current projectile.
	 */
	public Projectile getProjectile() {
		return projectile;
	}
	
	/**
	 * Method to set the projectile to the given projectile if it is valid.
	 * 
	 * @param 	projectile
	 * 			The given projectile.
	 * 
	 * @post	| new.getProjectile() == projectile
	 * @effect	| projectile.setWeapon(this)
	 * @throws 	ModelException
	 * 			| if (!canHaveAsProjectile(projectile))
	 * 			| if (hasAProjectile())
	 */
	public void setProjectile(Projectile projectile) throws ModelException {
		if (!canHaveAsProjectile(projectile))
			throw new ModelException("Invalid projectile specified.");
		if (hasAProjectile())
			throw new ModelException("Already has a world.");
		projectile.setWeapon(this);
		this.projectile = projectile;
	}
	
	/**
	 * Checks if the given projectile is valid.
	 * 
	 * @param 	projectile
	 * 			The given projectile.
	 * 
	 * @return	| if (projectile == null)
	 * 			|	return false
	 * 			| if (projectile.isTerminated())
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public boolean canHaveAsProjectile(Projectile projectile) {
		if (projectile==null)
			return false;
		if (projectile.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Checks if a projectile is not null.
	 * 
	 * @return	| return (!(projectile == null))
	 */
	public boolean hasAProjectile() {
		return(!(projectile==null));
	}
	
	/**
	 * Checks if the current projectile is equal to the given projectile.
	 * 
	 * @param 	projectile
	 * 			The given projectile.
	 * 
	 * @return	| return (this.projectile == projectile)
	 */
	public boolean hasAsProjectile(Projectile projectile) {
		return (this.projectile==projectile);
	}
	
	/**
	 * Removes the current projectile and the weapon associated with it.
	 * 
	 * @post	| new.getProjectile() == null
	 */
	public void removeProjectile() {
		if (hasAProjectile()) {
			projectile.removeWeapon();
			projectile = null;
		}
	}
	
	// }}
	
	
	/**
	 * Method to let a worm shoot with an equipped weapon, if it has a projectile.
	 * 
	 * @param 	propulsionYield
	 * 			The yield the weapon is fired with.
	 * 
	 * @effect	| setProjectile(newProjectile)
	 * @note	The object "newProjectile" is created when the worm doesn't have a projectile yet.
	 * @effect	| getWorm().getWorld().setProjectile(newprojectile)
	 * @throws	ModelException
	 * 			| if (getWorm().getWorld().hasAProjectile())
	 */
	public void shoot( int propulsionYield ) throws ModelException {
		if (getWorm().getWorld().hasAProjectile())
			throw new ModelException("There is already a projectile in the world.");

		double projectileX = getWorm().getPosition().getX() + getWorm().getRadius() * Math.cos(getWorm().getPosition().getDirection());
		double projectileY = getWorm().getPosition().getX() + getWorm().getRadius() * Math.sin(getWorm().getPosition().getDirection());
		double projectileDirection = getWorm().getPosition().getDirection();
		Projectile newProjectile = new Projectile(projectileX, projectileY, projectileDirection, propulsionYield);
		
		setProjectile(newProjectile);
		getWorm().getWorld().setProjectile(newProjectile);
		
		newProjectile.shoot();
		
	}
	
	
	
	

}
