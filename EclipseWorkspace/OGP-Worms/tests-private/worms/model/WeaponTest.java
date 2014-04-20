package worms.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import worms.util.Util;

public class WeaponTest {
	
// {{ Objects
	
	private static final double EPS = Util.DEFAULT_EPSILON;
	
	private Weapon weapon;
	private Weapon rifle = new Rifle();
	private Weapon bazooka = new Bazooka();
	
	// }}
	
// {{ Setup
	
	@Before
	public void Setup() {
		weapon = new Rifle();			//This can be either bazooka or rifle, they have the same specifications for testing.
	}
	
	// }}
	
// {{ Tests for projectile mass
	
	@Test
	public void test_setProjectileMass_legalCase() {
		weapon.setProjectileMass(150);
	}
	@Test(expected = ModelException.class)
	public void test_setProjectileMass_massIsNaN() {
		weapon.setProjectileMass(Double.NaN);
	}
	@Test(expected = ModelException.class)
	public void test_setProjectileMass_massNegative() {
		weapon.setProjectileMass(-10);
	}
	@Test(expected = ModelException.class)
	public void test_setProjectileMass_massInfinite() {
		weapon.setProjectileMass(Double.POSITIVE_INFINITY);
	}
	
	// }}
	
// {{ Tests for name of weapons
	
	@Test
	public void test_setName_LegalCase() {
		weapon.setName("Weapon 1");
	}
	@Test(expected = ModelException.class)
	public void test_setName_noUpperCase() {
		weapon.setName("weapon 1");
	}
	@Test(expected = ModelException.class)
	public void test_setName_badChar() {
		weapon.setName("Weapon$$");
	}
	@Test(expected = ModelException.class)
	public void test_setName_tooShort() {
		weapon.setName("W");
	}
	
	// }}
	
// {{ Tests for force of weapons
	
	@Test
	public void test_getForceRifle() {
		assertEquals(1.5, rifle.getForce(10), EPS);
	}
	@Test
	public void test_getForceBazooka() {
		assertEquals(6, bazooka.getForce(50), EPS);
	}
	
	// }}
	
// {{ Tests for hitpoints damage
	
	@Test
	public void test_setHitPointsDamage_LegalCase() {
		weapon.setHitPointsDamage(50);
	}
	@Test(expected = ModelException.class)
	public void test_setHitPointsDamage_tooLow() {
		weapon.setHitPointsDamage(-10);
	}
	
	// }}

// {{ Tests for actionpoints cost
	
	@Test
	public void test_setActionPointsCost_LegalCase() {
		weapon.setActionPointsCost(50);
	}
	@Test(expected = ModelException.class)
	public void test_setActionPointsCost_tooLow() {
		weapon.setActionPointsCost(-10);
	}
	
	// }}
	
// {{ Tests for ammunition
	
	@Test
	public void test_setAmmunition_LegalCase() {
		weapon.setAmmunition(50);
	}
	@Test(expected = ModelException.class)
	public void test_setAmmunition_tooLow() {
		weapon.setAmmunition(-10);
	}
	
	// }}
	
/**
 * @note	The associations doesn't have to tested in this class. They are the same as in the World and Worm classes,
 * 			where they are already extensively tested.
 */
}
