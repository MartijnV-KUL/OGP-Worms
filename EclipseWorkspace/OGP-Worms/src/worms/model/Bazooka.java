package worms.model;

public class Bazooka extends Weapon {
	
	public Bazooka() {
		setProjectileMass(0.300);
		setHitPointsDamage(80);
		setActionPointsCost(50);
		setName("Bazooka");
	}

	@Override
	public double getForce(int propulsionYield) {
		// Linear scaling
		double p = ((double)propulsionYield)/100;
		return 2.5 + 7*p;
	}

}
