package worms.model;

public class Bazooka extends Weapon {
	
	public Bazooka() {
		projectileMass   = 0.300;
		baseForce        = 2.5;
		hitPointsDamage  = 80;
		actionPointsCost = 50;
	}

	@Override
	public double getForce(int propulsionYield) {
		double p = ((double)propulsionYield)/100;
		return baseForce + p*7;
	}

}
