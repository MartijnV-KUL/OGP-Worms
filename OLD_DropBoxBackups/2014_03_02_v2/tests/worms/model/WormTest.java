package worms.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import worms.util.Util;


public class WormTest {
	
	/**
	 * @note	Is it necessary to test getters?
	 */
	
	private static final double EPS = Util.DEFAULT_EPSILON;
	
	private static Worm worm;
	
	@Before
	public void setup() {
		worm = new Worm(2, 3, Math.PI / 4, 1, "Test");
	}
	
	
	
	@Test
	public void test_getX() {
		assertEquals(2, worm.getX(), EPS);
	}
	
	
	
	@Test
	public void test_getY() {
		assertEquals(3, worm.getY(), EPS);
	}
	
	
	
	@Test
	public void test_setPosition() {
		worm.setPosition(7, 3);
		assertEquals(7, worm.getX(), EPS);
		assertEquals(3, worm.getY(), EPS);
	}
	
	 
	/**
	 * Changed the "isValidX" and "isValidY" methodes to public and removed static for testing. Or is testing not necessary / make test static?
	 */
	@Test
	public void test_isValidX_LegalCase() {
		assertTrue(worm.isValidX(5));
	}
	@Test
	public void test_isValidX_isNaN() {
		assertFalse(worm.isValidX(Double.POSITIVE_INFINITY));			//=> Waarom deze enkel juistrekenen bij assertTrue?
	}
	
	
	
	@Test
	public void test_isValidY_LegalCase() {
		assertTrue(worm.isValidY(4));
	}
	@Test
	public void test_isValidY_isNaN() {
		assertFalse(worm.isValidY(Double.POSITIVE_INFINITY));			//=> Waarom deze enkel juistrekenen bij assertTrue?
	} 
	
	
	
	@Test
	public void test_getDirection() {
		assertEquals(Math.PI / 4, worm.getDirection(), EPS);
	}
	
	
	
	@Test
	public void test_setDirection() {
		worm.setDirection(Math.PI / 4);
		assertEquals(Math.PI / 4, worm.getDirection(), EPS);
	}
	
	
	
	/**
	 * Changed the "isValidDirection" method to public and removed static. Or is testing not necessary / make test static?
	 */
	@Test
	public void test_isValidDirection_LegalCase() {
		assertTrue(worm.isValidDirection(Math.PI / 4));
	}
	@Test
	public void test_isValidDirection_directionTooLow() {
		assertFalse(worm.isValidDirection(-1));
	}
	@Test
	public void test_isValidDirection_directionTooHigh() {
		assertFalse(worm.isValidDirection(4 * Math.PI));
	}
	@Test
	public void test_isValidDirection_directionIsNaN() {
		assertFalse(worm.isValidDirection(Double.POSITIVE_INFINITY));
	}
	
	
	
	@Test
	public void test_getRadius() {
		assertEquals(1, worm.getRadius(), EPS);
	}
	
	
	
	@Test
	public void test_setRadius_LegalCase() {
		worm.setRadius(3);
		assertEquals(3, worm.getRadius(), EPS);
	}
	/**@Test(expected = ModelException.class)
	public void test_setRadius_notEnoughAP() {
		worm.setActionPoints(0);
		worm.setRadius(2);
	}*/
	
	
	
	@Test
	public void test_isValidRadius_LegalCase() {
		assertTrue(worm.isValidRadius(2));
	}
	@Test
	public void test_isValidRadius_radiusTooLow() {
		assertFalse(worm.isValidRadius(0));
	}
	@Test
	public void test_isValidRadius_radiusNaN() {
		assertFalse(worm.isValidRadius(Double.POSITIVE_INFINITY));			//=> Waarom deze enkel juistrekenen bij assertTrue?
	}
	
	
	
	@Test
	public void test_getMinimalRadius() {
		assertEquals(0.25, worm.getMinimalRadius(), EPS);
	}
	
	
	
	@Test
	public void test_getDensity() {
		assertEquals(1062, worm.getDensity(), EPS);
	}
	
	
	
	@Test
	public void test_getMass() {
		assertEquals(4448.495197, worm.getMass(), EPS);
	}
	
	
	@Test
	public void test_getName() {
		assertEquals("Test", worm.getName());
	}
	
	
	
	@Test
	public void test_setName() {
		worm.setName("TestName");
		assertEquals("TestName", worm.getName());
	}
	
	
	
	/**
	 * Changed the "isValidName" method to public and removed static. Or is testing not necessary / make test static?
	 */
	@Test
	public void test_isValidName_LegalCase() {
		assertTrue(worm.isValidName("Test \'and \"test"));
	}
	@Test
	public void test_isValidName_nameNoUpperCase() {
		assertFalse(worm.isValidName("test"));
	}
	@Test
	public void test_isValidName_nameContainsNumbers() {
		assertFalse(worm.isValidName("Test1"));
	}
	public void test_isValidName_nameTooShort() {
		assertFalse(worm.isValidName("T"));
	}
	
	
	
	@Test
	public void test_getActionPoints() {
		assertEquals(4448, worm.getActionPoints(), EPS);
	}
	
	
	@Test
	public void test_getMaxActionPoints() {
		assertEquals(4448, worm.getMaxActionPoints());
	}
	
	
	
	@Test
	public void test_setActionPoints() {
		worm.setActionPoints(250);
		assertEquals(250, worm.getActionPoints());
	}
	
	
	
	/**
	 * Changed the "isValidActionPoints" method to public and removed static. Or is testing not necessary / make test static?
	 */
	@Test
	public void test_isValidActionPoints_LegalCase() {
		assertTrue(worm.isValidActionPoints(25));
	}
	@Test
	public void test_isValidActionPoints_actionPointsTooLow() {
		assertFalse(worm.isValidActionPoints(-50));
	}
	@Test
	public void test_isValidActionPoints_actionPointsTooHigh() {
		assertFalse(worm.isValidActionPoints(worm.getMaxActionPoints() + 1));
	}
	
	
	
	@Test(expected = ModelException.class)
	public void test_activeMove() {
		worm.activeMove(-1);
	}
	
	
	
	@Test
	public void test_activeMoveSingleStep() {
		worm.activeMoveSingleStep();
		assertEquals(2.7071067811865476, worm.getX(), EPS);
		assertEquals(3.7071067811865476, worm.getY(), EPS);
	}
	
	
	
	@Test
	public void test_canMove_LegalCase() {
		assertTrue(worm.canMove(2));
	}
	@Test
	public void test_canMove_costTooHigh() {
		assertFalse(worm.canMove(worm.getActionPoints() + 1));
	}
	
	
	
	@Test
	public void test_canTurn_LegalCase() {
		assertTrue(worm.canTurn(Math.PI));
	}
	@Test
	public void test_canTurn_directionIsNaN() {
		assertFalse(worm.canTurn(Double.POSITIVE_INFINITY));
	}
	@Test
	public void test_canTurn_costTooHigh() {
		assertFalse(worm.canTurn(worm.getActionPoints() + 1));
	}
	@Test
	public void test_canTurn_noAPLeft() {
		worm.setActionPoints(0);
		assertFalse(worm.canTurn(Math.PI));
	}
	
	
	
	@Test
	public void test_turn() {
		worm.turn(Math.PI / 4);
		assertEquals(1.5707963267948966, worm.getDirection(), EPS);	// (Math.PI / 4 + Math.PI / 4) % (2*Math.PI) = 1.5707963267948966
	}
	
	
	
	@Test
	public void test_getGravitationalAcceleration() {
		assertEquals(9.80665, worm.getGravitationalAcceleration(), EPS);
	}
	
	
	//TODO
	@Test
	public void test_jump() {
		worm.jump();
		assertEquals(0, worm.getActionPoints(), EPS);
		assertEquals(7.588564956, worm.getX(), EPS);
		assertEquals(3 - 5.062*Math.pow(10, -10), worm.getY(), EPS);
	}
	
	
	
	@Test
	public void test_canJump_LegalCase() {
		worm.setDirection(Math.PI / 2);
		assertTrue(worm.canJump());
	}
	/**
	 * @note	De methode "setDirection" verhindert deze testen.
	@Test
	public void test_canJump_directionTooLow() {
		worm.setDirection(- Math.PI / 2);
		assertFalse(worm.canJump());
	}
	@Test
	public void test_canJump_directionTooHigh() {
		worm.setDirection(3*Math.PI);
		assertFalse(worm.canJump());
	}*/
	@Test
	public void test_canJump_notEnoughAP() {
		worm.setActionPoints(0);
		assertFalse(worm.canJump());
	}
	
	
	
	@Test
	public void test_getJumpInitVel() {
		assertEquals(7.403046705, worm.getJumpInitVel(), EPS);
	}
	
	
	//TODO
	@Test
	public void test_jumpTime_cosineDirectionEqualsZero_Case1() {
		worm.setDirection(0);
		assertEquals(0, worm.jumpTime(), EPS);
	}
	@Test
	public void test_jumpTime_cosineDirectionEqualsZero_Case2() {
		worm.setDirection(Math.PI / 2);
		assertEquals(1.50980135, worm.jumpTime(), EPS);
	}
	@Test
	public void test_jumpTime_LegalCase() {
		assertEquals(1.067590773, worm.jumpTime(), EPS);
	}
	
	
	
	//TODO
	@Test
	public void test_jumpStep_LegalCase() {
		worm.jumpStep(worm.jumpTime());
		assertEquals(5.588564956, worm.jumpStep(worm.jumpTime())[0], EPS);
		assertEquals(-5.062*Math.pow(10, -10), worm.jumpStep(worm.jumpTime())[1], EPS);
	}
	@Test
	public void test_jumpStep_wormCanNotJump() {
		worm.setDirection((5/4)*Math.PI);
		assertEquals(0, worm.jumpStep(worm.jumpTime())[0], EPS);
		assertEquals(0, worm.jumpStep(worm.jumpTime())[1], EPS);
	}
	@Test(expected = ModelException.class)
	public void test_jumpStep_timeNegative() {
		worm.jumpStep(-1);
	}
	@Test(expected = ModelException.class)
	public void test_jumpStep_timeTooHigh() {
		worm.jumpStep(worm.jumpTime() + 1);
	}
}


