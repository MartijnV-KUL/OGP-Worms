package worms.model;

import java.util.ArrayList;

public class Projectile {
	
	private double x;
	private double y;
	private double radius;
	private int propulsionYield;
	
	private final double projectileDensity = 7800;
	private final double gravitationalAcceleration = 9.80665;
	
	private final double massRifleProjectile = 0.010;
	private final double rifleProjectileForce = 1.5;
	
	private final double massBazookaProjectile = 0.300;
	
	private ArrayList<Projectile> projectileCollection;
	
	private Worm worm;
	
	public void setProjectilePosition() {
		if (!isValidX(worm.getX() + worm.getRadius() * Math.cos(worm.getDirection())))
			throw new ModelException("The x-coordinate is invalid.");
		if (!isValidY(worm.getX() + worm.getRadius() * Math.sin(worm.getDirection())))
			throw new ModelException("The y-coordinate is invalid.");
		this.x = worm.getX() + worm.getRadius() * Math.cos(worm.getDirection());
		this.y = worm.getY() + worm.getRadius() * Math.sin(worm.getDirection());
	}
	
	public double getX() {
		return this.x;
	}
	
	public boolean isValidX(double x) {
		return (!Double.isNaN(x));
	}
	
	public double getY() {
		return this.y;
	}
	
	public boolean isValidY(double y) {
		return (!Double.isNaN(y));
	}
	
	public double getDirection() {
		return worm.getDirection();
	}
	
	public double getBazookaForce() {
		// TODO Assignment says nothing about how the force is scaled? Only information is that it lays between 2.5 and 9.5?
		return -1;
	}
	
	//TODO
	public boolean wormIsHit() {
		return false;
	}
	
	//TODO
	public void shootRifle() {
		
	}
	
	//TODO
	public void shootBazooka(int yield) {
		
	}
	
	
	

	public double[] jumpStep(double t) {
		return worm.jumpStep(t);
	}
	
	//TODO
	public double jumpTime(double timeStep) {
		return -1;
	}
	
	//TODO
	public void Shoot(int propulsionYield) {
		
	}
	
	public void setPropulsionYield(int propulsionYield) {
		if (!isValidYield(propulsionYield))
			throw new ModelException("Invalid propulsion yield specified.");
		this.propulsionYield = propulsionYield;
	}
	
	public int getPropulsionYield() {
		return this.propulsionYield;
	}
	
	public boolean isValidYield(int propulsionYield) {
		if (propulsionYield < 0 || propulsionYield > 100)
			return false;
		return true;
	}
	
	
	

// {{ Terminated
	
	private boolean terminated;
	
	public boolean isTerminated() {
		return terminated;
	}
	
	public void terminate() {
		try {
			world.removeProjectile();
		} catch (NullPointerException e) {
			// do nothing
		}
		terminated = true;
	}
	
	// }}
	
// {{ World Association
	
	private World world;
	
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) throws ModelException {
		if (!canHaveAsWorld(world))
			throw new ModelException("Invalid world specified");
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
	
// {{ Weapon Association
	
	private Weapon weapon;
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public void setWeapon(Weapon weapon) throws ModelException {
		if (!canHaveAsWeapon(weapon))
			throw new ModelException("Invalid world specified");
		if (hasAWeapon())
			throw new ModelException("Already has a world.");
		this.weapon = weapon;
	}
	
	public boolean canHaveAsWeapon(Weapon weapon) {
		if (weapon==null)
			return false;
		if (weapon.isTerminated())
			return false;
		return true;
	}
	
	public boolean hasAWeapon() {
		return(!(weapon==null));
	}
	
	public boolean hasAsWeapon(Weapon weapon) {
		return (this.weapon==weapon);
	}
	
	public void removeWeapon() {
		weapon = null;
	}
	
	
	// }}
}
