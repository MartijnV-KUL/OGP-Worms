package worms.model;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.Basic;

public class Projectile {
	
	
	//TODO
	public boolean wormIsHit() {
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
// {{ Constructors
	
	public Projectile() {
		this(0,0,0,0);
	}
	
	public Projectile(double x, double y, double direction, int propulsionYield) {
		setPosition(new Position(x, y, direction));
		setPropulsionYield(propulsionYield);
	}
	
	// }}
	
	public void jump(double timeStep) {
		double[] newPos = jumpStep(jumpTime(timeStep));
		getPosition().setX( newPos[0] );
		getPosition().setY( newPos[1] );
	}
	
	public double jumpTime(double timeStep) {
		double force = getWeapon().getForce(getPropulsionYield());
		return getPosition().ballisticTrajectoryTime(getWorld(), force, 0.5, getWeapon().getProjectileMass(), timeStep);
	}
	
	public double[] jumpStep(double time) {
		double force = getWeapon().getForce(getPropulsionYield());
		return getPosition().ballisticTrajectory(force, 0.5, getWeapon().getProjectileMass(), time);
	}
	
	
// {{ Position
	
	private Position position;
	
	/**
	 * Basic inspector that returns the position object of the projectile. 
	 * @return The position object of the projectile.
	 */
	@Basic
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		if (!isValidPosition(position))
			throw new ModelException("Invalid position specified");
		this.position = position;
	}
	
	public boolean isValidPosition(Position position) {
		if (position==null)
			return false;
		return true;
	}
	
	
	// }}
	
// {{ Propulsion Yield
	
	private int propulsionYield;
	
	@Basic
	public int getPropulsionYield() {
		return propulsionYield;
	}
	
	public void setPropulsionYield(int propulsionYield) throws ModelException {
		if (!isValidPropulsionYield(propulsionYield))
			throw new ModelException("Invalid propulsion yield.");
		this.propulsionYield = propulsionYield;
	}
	
	public boolean isValidPropulsionYield(int propulsionYield) {
		if (propulsionYield<0)
			return false;
		if (propulsionYield>100)
			return false;
		return true;
	}
	
	// }}
	
// {{ Radius

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
	public boolean isTerminated() {
		return terminated;
	}
	
	public void terminate() {
		if (hasAWorld())
			world.removeProjectile();
		terminated = true;
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
	
// {{ Weapon Association
	
	private Weapon weapon;
	
	@Basic
	public Weapon getWeapon() {
		return weapon;
	}
	
	public void setWeapon(Weapon weapon) throws ModelException {
		if (!canHaveAsWeapon(weapon))
			throw new ModelException("Invalid weapon specified.");
		if (hasAWeapon())
			throw new ModelException("Already has a weapon.");
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
		return (!(weapon==null));
	}
	
	public boolean hasAsWeapon(Weapon weapon) {
		return (this.weapon==weapon);
	}
	
	public void removeWeapon() {
		weapon = null;
	}
	
	
	// }}
}
