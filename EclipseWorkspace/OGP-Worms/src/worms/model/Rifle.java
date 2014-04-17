package worms.model;

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
	 * No formal return because this method is the implementation of an abstract method in the class "Weapon".
	 */
	public double getForce(int propulsionYield) {
		return 1.5;
	}

}
