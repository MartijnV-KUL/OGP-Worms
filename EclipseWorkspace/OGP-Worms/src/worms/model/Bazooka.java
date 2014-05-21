package worms.model;

/**
 * This class is an extension of the Weapon class.
 * All aspects related to a bazooka-type weapon are defined here.
 * 
 * @author Martijn Vermaut, Niels Claes
 *
 */
public class Bazooka extends Weapon {
	
	/**
	 * Constructor for the Bazooka class.
	 * 
	 * @effect	| setProjectileMass(0.300)
	 * @effect	| setHitPointsDamage(80)
	 * @effect	| setActionPointsCost(50)
	 * @effect	| setName("Bazooka")
	 */
	public Bazooka() {
		setProjectileMass(0.300);
		setHitPointsDamage(80);
		setActionPointsCost(50);
		setName("Bazooka");
	}

	@Override
	/**
	 * Method to return the force a bazooka projectile is fired with.
	 *
	 * @param	propulsionYield
	 * 			The propulsionYield.
	 * 
	 * @return	The force a bazooka projectile is fired with.
	 * 			| return 2.5 + 7 * (propulsionYield / 100)
	 */
	public double getForce(int propulsionYield) {
		// Linear scaling
		double p = ((double)propulsionYield)/100;
		return 2.5 + 7*p;
	}

}
