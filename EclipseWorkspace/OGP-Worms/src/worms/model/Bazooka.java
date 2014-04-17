package worms.model;

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
	 * No formal return because this method is the implementation of an abstract method in the class "Weapon".
	 */
	public double getForce(int propulsionYield) {
		// Linear scaling
		double p = ((double)propulsionYield)/100;
		return 2.5 + 7*p;
	}

}
