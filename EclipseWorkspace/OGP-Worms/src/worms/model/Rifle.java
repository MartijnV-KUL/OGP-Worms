package worms.model;

/**
 * This class is an extension of the weapon class.
 * All aspects related to a rifle-type weapon are defined here.
 * 
 * @author Martijn Vermaut, Niels Claes
 *
 */
public class Rifle extends Weapon {
	
	/**
	 * Constructor for the Rifle class.
	 * 
	 * @effect	| setProjectileMass(0.010)
	 * @effect	| setHitPointsDamage(20)
	 * @effect	| setActionPointsCost(10)
	 * @effect	| setName("Rifle")
	 */
	public Rifle() {
		setProjectileMass(0.010);
		setHitPointsDamage(20);
		setActionPointsCost(10);
		setName("Rifle");
	}

	@Override
	/**
	 * Method to return the force a rifle projectile is fired with.
	 * 
	 * @param	propulsionYield
	 * 			The propulsionYield.
	 * 
	 * @return	Returns the force a rifle projectile is fired with.
	 * 			| return 1.5
	 */
	public double getForce(int propulsionYield) {
		return 1.5;
	}

}
