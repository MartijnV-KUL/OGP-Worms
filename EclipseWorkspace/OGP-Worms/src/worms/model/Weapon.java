package worms.model;

import be.kuleuven.cs.som.annotate.Basic;

public abstract class Weapon {

// {{ Projectile Mass
	
	protected double projectileMass;
	
	@Basic
	public double getProjectileMass() {
		return projectileMass;
	}
	
	// }}
	
// {{ Force

	protected double baseForce;
	
	@Basic
	public double getBaseForce() {
		return baseForce;
	}
	
	public abstract double getForce( int propulsionYield );
	
	// }}
	
// {{ Hit Points Damage

	protected double hitPointsDamage;
	
	public double getHitPointsDamage() {
		return hitPointsDamage;
	}
	
	// }}
	
// {{ Action Points Cost

	protected double actionPointsCost;
	
	public double getActionPointsCost() {
		return actionPointsCost;
	}
	
	// }}
	
// {{ Ammunition

	private int ammunition;
	
	public int getAmmunition() {
		return ammunition;
	}
	
	
	// }}

// {{ Terminated
	
	private boolean terminated;
	
	public boolean isTerminated() {
		return terminated;
	}
	
	public void terminate() {
		try {
			worm.removeWeapon(this);
		} catch (NullPointerException e) {
			// do nothing
		}
		try {
			removeProjectile();
		} catch (NullPointerException e) {
			// do nothing
		}
		terminated = true;
	}
	
	// }}
	
// {{ Worm Association
	
	private Worm worm;
	
	public Worm getWorm() {
		return worm;
	}
	
	public void setWorm(Worm worm) throws ModelException {
		if (!canHaveAsWorm(worm))
			throw new ModelException("Invalid world specified");
		if (hasAWorm())
			throw new ModelException("Already has a world.");
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
	
	public boolean hasAsWorld(Worm worm) {
		return (this.worm==worm);
	}
	
	public void removeWorm() {
		worm = null;
	}
	
	// }}
	
// {{ Projectile Association
	
	private Projectile projectile;
	
	public Projectile getProjectile() {
		return projectile;
	}
	
	public void setProjectile(Projectile projectile) throws ModelException {
		if (!canHaveAsProjectile(projectile))
			throw new ModelException("Invalid world specified");
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
	
	
	
	public void shoot( int propulsionYield ) {
		if (worm.getWorld().hasAProjectile())
			throw new ModelException("There is already a projectile in the world.");
		
		Projectile newProjectile = new Projectile();
		setProjectile(newProjectile);
		worm.getWorld().setProjectile(newProjectile);
	}
	
	
	
	

}
