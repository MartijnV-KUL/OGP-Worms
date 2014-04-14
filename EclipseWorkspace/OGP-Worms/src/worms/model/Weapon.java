package worms.model;

import be.kuleuven.cs.som.annotate.Basic;

public abstract class Weapon {

// {{ Projectile Mass
	
	protected double projectileMass;
	
	@Basic
	public double getProjectileMass() {
		return projectileMass;
	}
	
	public void setProjectileMass(double projectileMass)throws ModelException {
		if (!isValidProjectileMass(projectileMass))
			throw new ModelException("Invalid projectile mass.");
		this.projectileMass = projectileMass;	
	}
	
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
	
	public String getName() {
		return name;
	}
	
	protected void setName(String name) throws ModelException {
		if (!isValidName(name))
			throw new ModelException("Invalid name.");
		this.name = name;
	}
	
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

	public abstract double getForce( int propulsionYield );
	
	// }}
	
// {{ Hit Points Damage

	protected int hitPointsDamage;
	
	public int getHitPointsDamage() {
		return hitPointsDamage;
	}
	
	public void setHitPointsDamage(int hitPointsDamage) throws ModelException {
		if (!isValidHitPointsDamage(hitPointsDamage))
			throw new ModelException("Invalid hit points damage");
		this.hitPointsDamage = hitPointsDamage;
	}
	
	public boolean isValidHitPointsDamage(int hitPointsDamage) {
		if (hitPointsDamage<0)
			return false;
		return true;
	}
	
	// }}
	
// {{ Action Points Cost

	protected int actionPointsCost;
	
	public int getActionPointsCost() {
		return actionPointsCost;
	}
	
	public void setActionPointsCost(int actionPointsCost) throws ModelException {
		if (!isValidActionPointsCost(actionPointsCost))
			throw new ModelException("Invalid action points cost.");
		this.actionPointsCost = actionPointsCost;
	}
	
	public boolean isValidActionPointsCost(int actionPointsCost) {
		if (actionPointsCost<0)
			return false;
		return true;
	}
	
	// }}
	
// {{ Ammunition

	private int ammunition = 0;
	
	public int getAmmunition() {
		return ammunition;
	}
	
	public void setAmmunition(int ammunition) throws ModelException {
		if (!isValidAmmunition(ammunition))
			throw new ModelException("Invalid ammunition.");
		this.ammunition = ammunition;
	}
	
	public boolean isValidAmmunition(int ammunition) {
		if (ammunition<0)
			return false;
		return true;
	}
	
	// }}

// {{ Terminated
	
	private boolean terminated;
	
	@Basic
	public boolean isTerminated() {
		return terminated;
	}
	
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
	public Worm getWorm() {
		return worm;
	}
	
	public void setWorm(Worm worm) throws ModelException {
		if (!canHaveAsWorm(worm))
			throw new ModelException("Invalid worm specified.");
		if (hasAWorm())
			throw new ModelException("Already has a worm.");
		this.worm = worm;
	}
	
	public boolean canHaveAsWorm(Worm worm) {
		if (worm==null)
			return false;
		if (worm.isTerminated())
			return false;
		return true;
	}
	
	public boolean hasAWorm() {
		return(!(worm==null));
	}
	
	public boolean hasAsWorm(Worm worm) {
		return (this.worm==worm);
	}
	
	public void removeWorm() {
		worm = null;
	}
	
	// }}
	
// {{ Projectile Association
	
	private Projectile projectile;
	
	@Basic
	public Projectile getProjectile() {
		return projectile;
	}
	
	public void setProjectile(Projectile projectile) throws ModelException {
		if (!canHaveAsProjectile(projectile))
			throw new ModelException("Invalid projectile specified.");
		if (hasAProjectile())
			throw new ModelException("Already has a world.");
		projectile.setWeapon(this);
		this.projectile = projectile;
	}
	
	public boolean canHaveAsProjectile(Projectile projectile) {
		if (projectile==null)
			return false;
		if (projectile.isTerminated())
			return false;
		return true;
	}
	
	public boolean hasAProjectile() {
		return(!(projectile==null));
	}
	
	public boolean hasAsProjectile(Projectile projectile) {
		return (this.projectile==projectile);
	}
	
	public void removeProjectile() {
		if (hasAProjectile()) {
			projectile.removeWeapon();
			projectile = null;
		}
	}
	
	// }}
	
	
	
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
