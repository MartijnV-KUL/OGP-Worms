package worms.model;

public class Rifle extends Weapon {
	
	
	public Rifle() {
		projectileMass   = 0.010;
		baseForce        = 1.5;
		hitPointsDamage  = 20;
		actionPointsCost = 10;
	}

	@Override
	public double getForce(int propulsionYield) {
		return baseForce;
	}

}
