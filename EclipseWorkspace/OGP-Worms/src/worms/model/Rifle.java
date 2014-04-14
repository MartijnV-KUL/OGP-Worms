package worms.model;

public class Rifle extends Weapon {
	
	
	public Rifle() {
		setProjectileMass(0.010);
		setHitPointsDamage(20);
		setActionPointsCost(10);
		setName("Rifle");
	}

	@Override
	public double getForce(int propulsionYield) {
		return 1.5;
	}

}
