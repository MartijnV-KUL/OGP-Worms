package worms.model;

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
	
	
	
	

	

}
