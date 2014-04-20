package worms.model;

import org.junit.Before;
import org.junit.Test;

public class ProjectileTest {
	
// {{ Objects
	
	private Projectile projectile;
	
	// }}
	
// {{ Setup
	
	@Before
	public void Setup() {
		projectile = new Projectile();
	}
	
	// }}
	
// {{ Tests for propulsionyield
	
	@Test
	public void test_setPropulsionYield_LegalCase() {
		projectile.setPropulsionYield(50);
	}
	@Test(expected = ModelException.class)
	public void test_setPropulsionYield_tooLow() {
		projectile.setPropulsionYield(-10);
	}
	@Test(expected = ModelException.class)
	public void test_setPropulsionYield_tooHigh() {
		projectile.setPropulsionYield(150);
	}
	
	// }}
	
	/**
	 * @note	The associations doesn't have to be tested, they are the same in the world and worm classes where they are already tested.
	 */
}
