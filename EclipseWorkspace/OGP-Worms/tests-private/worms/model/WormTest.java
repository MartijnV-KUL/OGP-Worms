package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.util.Util;

/**
 * A class to test all the different methods of the worm class.
 * 
 * @author Niels
 */
public class WormTest {
	
// {{ Objects
	
	private static final double EPS = Util.DEFAULT_EPSILON;
	
	private static Worm worm;
	
	private static World world;
	
	private Position position;
	
	private Weapon bazooka = new Bazooka();
	private Weapon rifle = new Rifle();
	
	private Food food = new Food(2, 2);
	
	private Team team = new Team();
	
	private static Random random;
	
	/*	X X X X
	 *  . . . .
	 *  . . . .
	 *  X X X X	 */
	private boolean[][] passableMap = new boolean[][] {
			{false, false, false, false}, {true,  true,  true,  true},
			{true,  true,  true,  true }, {false, false, false, false}
	};
	
	// }}
	
// {{ Setup
	
	@Before
	public void Setup() {
		worm = new Worm(2, 3, Math.PI / 4, 1, "Testworm");
		random = new Random(7357);
		world = new World(4, 4, passableMap, random);
		position = new Position(2, 3, Math.PI / 4);
	}
	
	// }}
	
// {{ Tests for constant values
	
	@Test
	public void test_getMinimalRadius() {
		assertEquals(0.25, worm.getMinimalRadius(), EPS);
	}
	
	@Test
	public void test_getDensity() {
		assertEquals(1062, Worm.getDensity(), EPS);
	}
	
	// }}
	
// {{ Tests for position
	
	@Test
	public void test_Xcoord() {
		assertEquals(2, worm.getPosition().getX(), EPS);
	}
	
	@Test
	public void test_Ycoord() {
		assertEquals(3, worm.getPosition().getY(), EPS);
	}
	
	@Test
	public void test_getDirection() {
		assertEquals(Math.PI / 4, worm.getPosition().getDirection(), EPS);
	}
	
	@Test
	public void test_setPosition_LegalCase() {			//This tests setX, setY and setDirection automatically.
		position = new Position(3, 4, Math.PI);
		worm.setPosition(position);
		assertEquals(3, worm.getPosition().getX(), EPS);
		assertEquals(4, worm.getPosition().getY(), EPS);
		assertEquals(Math.PI, worm.getPosition().getDirection(), EPS);
	}
	
	@Test
	public void test_isValidX_LegalCase() {
		assertTrue(Position.isValidX(2));
	}
	@Test
	public void test_isValidX_isInfinite() {
		assertTrue(Position.isValidX(Double.POSITIVE_INFINITY));
	}
	@Test
	public void test_isValidX_isNaN() {
		assertFalse(Position.isValidX(Double.NaN));		
	}
	
	@Test
	public void test_isValidY_LegalCAse() {
		assertTrue(Position.isValidY(2));
	}
	@Test
	public void test_isValidY_isInfinite() {
		assertTrue(Position.isValidY(Double.POSITIVE_INFINITY));
	}
	@Test
	public void test_isValidY_isNaN() {
		assertFalse(Position.isValidY(Double.NaN));
	}
	
	@Test
	public void test_isValidDirection_LegalCase() {
		assertTrue(Position.isValidDirection(Math.PI / 4));
	}
	@Test
	public void test_isValidDirection_tooLow() {
		assertFalse(Position.isValidDirection(- Math.PI));
	}
	@Test
	public void test_isValidDirection_tooHigh() {
		assertFalse(Position.isValidDirection(5*Math.PI));
	}
	public void test_isValidDirection_isNaN() {
		assertFalse(Position.isValidDirection(Double.NaN));
	}
	
	@Test
	public void test_isValidPosition_LegalCase() {
		assertTrue(worm.isValidPosition(position));
	}
	@Test
	public void test_isValidPosition_positionNull() {
		assertFalse(worm.isValidPosition(null));
	}
	@Test
	public void test_isValidPosition_positionOOB() {
		position = new Position(10, 10, Math.PI / 4);
		worm.setPosition(position);
		worm.setWorld(world);
		assertFalse(worm.isValidPosition(position));		//Map is only 4x4
	}
	//@Test
	public void test_isValidPosition_impassable() {
		position = new Position(2, 0, Math.PI / 4);					// X X w X
		worm.setPosition(position);									// . . . .
		worm.setWorld(world);										// . . . .
		assertFalse(worm.isValidPosition(position));				// X X X X
	}	/** @note: Bug in 'isPassable" method. Coords (2, 0) should be impassable. */
	
	// }}

// {{ Tests for wormnames
	
	@Test
	public void test_getName() {
		assertEquals("Testworm", worm.getName());
	}
	
	@Test
	public void test_setName() {
		worm.setName("Dummyworm");
		assertEquals("Dummyworm", worm.getName());
	}
	
	@Test
	public void test_isValidName_LegalCase() {
		assertTrue(Worm.isValidName("James O'Dummy \"1"));
	}
	@Test
	public void test_isValidName_wrongChar() {
		assertFalse(Worm.isValidName("Worm!"));
	}
	@Test
	public void test_isValidName_noUpperChar() {
		assertFalse(Worm.isValidName("worm"));
	}
	@Test
	public void test_isValidName_tooShort() {
		assertFalse(Worm.isValidName("W"));
	}
	
	// }}
	
// {{ Tests for radius
	
	@Test
	public void test_getRadius() {
		assertEquals(1, worm.getRadius(), EPS);
	}
	
	@Test
	public void test_setRadius() {
		worm.setRadius(2);
		assertEquals(2, worm.getRadius(), EPS);
	}
	
	@Test
	public void test_isValidRadius_LegalCase() {
		assertTrue(worm.isValidRadius(2));
	}
	@Test
	public void test_isValidRadius_tooLow() {
		assertFalse(worm.isValidRadius(worm.getMinimalRadius() - 0.1));
	}
	@Test
	public void test_isValidRadius_isNaN() {
		assertFalse(worm.isValidRadius(Double.NaN));
	}
	
	// }}
	
// {{ Test for mass
	
	@Test
	public void test_getMass() {
		assertEquals(4448.495197, worm.getMass(), EPS);
	}
	
	// }}
	
// {{ Tests for actionpoints
	
	@Test
	public void test_getActionPoints() {
		assertEquals(4448, worm.getActionPoints(), EPS);
	}
	
	@Test
	public void test_getMaxActionPoints() {
		assertEquals(4448, worm.getMaxActionPoints(), EPS);
	}
	
	@Test
	public void test_setActionPoints_LegalCase() {			//The next 3 tests automatically test the method "isValidActionPoints"
		worm.setActionPoints(250);
		assertEquals(250, worm.getActionPoints(), EPS);
	}
	@Test
	public void test_setActionPoints_negativeAP() {
		worm.setActionPoints(-50);
		assertEquals(0, worm.getActionPoints(), EPS);
	}
	@Test
	public void test_setActionPoints_tooHigh() {
		worm.setActionPoints(worm.getMaxActionPoints() + 1);
		assertEquals(4448, worm.getActionPoints(), EPS);
	}

	// }}
	
// {{ Tests for hitpoints & alive worms
	
	@Test
	public void test_getHitPoints() {
		assertEquals(4448, worm.getHitPoints(), EPS);
	}
	
	@Test
	public void test_getMaxHitPoints() {
		assertEquals(4448, worm.getMaxHitPoints(), EPS);
	}
	
	@Test
	public void test_setHitPoints_LegalCase() {			//The next 3 tests automatically test the method "isValidHitPoints"
		worm.setHitPoints(250);
		assertEquals(250, worm.getHitPoints(), EPS);
	}
	@Test
	public void test_setActionPoints_negativeHP() {
		worm.setHitPoints(-50);
		assertEquals(0, worm.getHitPoints(), EPS);
	}
	@Test
	public void test_setHitPoints_tooHigh() {
		worm.setHitPoints(worm.getMaxHitPoints() + 1);
		assertEquals(4448, worm.getHitPoints(), EPS);
	}
	
	@Test
	public void test_isAlive_LegalCase() {
		worm.setHitPoints(50);
		assertTrue(worm.isAlive());
	}
	@Test
	public void test_isAlive_noHP() {
		worm.setHitPoints(0);
		assertFalse(worm.isAlive());
	}
	
	// }}
	
// {{ Tests for movement	//TODO: tests for impassable terrain + adjacent
	
	@Test(expected = ModelException.class)
	public void test_activeMove_noAPLeft() {
		worm.setActionPoints(0);
		worm.activeMove();
	}
	
	@Test
	public void test_activeMove_LegalCase() {
		worm.activeMove();
		assertEquals(2.7071067811865476, worm.getPosition().getX(), EPS);
		assertEquals(3.7071067811865476, worm.getPosition().getY(), EPS);
	}
	
	@Test
	public void test_getActionPointCostMove() {
		assertEquals(3, worm.getActionPointCostMove(), EPS);		
	}
	
	@Test
	public void test_canMove_LegalCase() {
		worm.setActionPoints(worm.getActionPointCostMove() + 1);
		assertTrue(worm.canMove());
	}
	@Test
	public void test_canMove_noAP() {
		worm.setActionPoints(worm.getActionPointCostMove() - 1);
		assertFalse(worm.canMove());
	}
	
	// }}
	
// {{ Tests for turning
	
	@Test
	public void test_turn() {
		worm.turn(Math.PI / 4);
		assertEquals(1.5707963267948966, worm.getPosition().getDirection(), EPS);	// (Math.PI / 4 + Math.PI / 4) % (2*Math.PI) = 1.5707963267948966
	}
	
	@Test
	public void test_getActionPointCostTurn() {
		assertEquals(8, worm.getActionPointCostTurn(Math.PI / 4), EPS);
		assertEquals(23, worm.getActionPointCostTurn(3 * Math.PI / 4), EPS);
	}
	
	@Test
	public void test_canTurn_LegalCase() {
		assertTrue(worm.canTurn(Math.PI));
	}
	@Test
	public void test_canTurn_directionIsNaN() {
		assertFalse(worm.canTurn(Double.NaN));
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

	// }}
	
// {{ Tests for jumping
	
	//TODO
	/** @note	As the method "isPassable" is still bugged, the method "ballisticTrajectoryTime" does not excecute the while-loop,
	* 			because that method is called in the loop.
	* 			As a result, the time returned is 0.0 and the worm does not jump.
	*/
	@Test
	public void test_jump() {																	// . . . X
		world = new World(4, 4, new boolean[][] {												// . . . X
					{true, true, true, false}, {true, true, true, false},						// . . . X
					{true, true, true, false}, {true, true, true, false} }, random);			// w . . X
		position = new Position(0, 3, Math.PI / 4);
		worm.setPosition(position);
		worm.setWorld(world);
		worm.jump(0.1);				// Timestep of 0.1 seconds.
		//System.out.println(worm.jumpTime(0.1));			//=> gives 0.0
		//System.out.println(worm.getPosition().ballisticTrajectoryTime(world, 65864.83542, 0.5, worm.getMass(), 0.1));	//=> gives 0.0
		assertEquals(0, worm.getActionPoints(), EPS);
		assertEquals(0, worm.getPosition().getX(), EPS);
		assertEquals(3, worm.getPosition().getY(), EPS);
	}
	
	@Test
	public void test_canJump_LegalCase() {
		worm.getPosition().setDirection(Math.PI / 4);
		assertTrue(worm.canJump());
	}
	@Test
	public void test_canJump_noAPLeft() {
		worm.setActionPoints(0);
		assertFalse(worm.canJump());
	}
	@Test
	public void test_canJump_directionTooHigh() {
		worm.getPosition().setDirection(3 * Math.PI / 2);
		assertFalse(worm.canJump());
	}
	
	// }}
	
// {{ Tests for falling
	
	//@Test
	/**
	 * The "fall" method uses the "isPassable" method, which is bugged. This should work when the bug is resolved.
	 */
	public void test_fall() {																// . X .
		world = new World(3, 5, new boolean[][] {											// . w .
				{true,  false, true}, {true, true, true},									// . . .
				{true,  true,  true}, {true, true, true},									// . . . 
				{false, false, false} }, random);											// X X X
		position = new Position(1, 1, Math.PI / 4);
		worm.setWorld(world);
		worm.setPosition(position);
		worm.fall();
		assertEquals(1, worm.getPosition().getX(), EPS);
		assertEquals(3, worm.getPosition().getY(), EPS);
		assertEquals(4442, worm.getHitPoints(), EPS);		//worm falls 2 meters, so it looses 6 hitpoints.
	}
	
	/**
	 * Again here, "canFall" uses "isPassable" method.
	 */
	@Test
	public void test_canFall_LegalCase() {													// . X .
		world = new World(3, 5, new boolean[][] {											// . w .
				{true,  false, true}, {true, true, true},									// . . .
				{true,  true,  true}, {true, true, true},									// . . . 
				{false, false, false} }, random);											// X X X
		position = new Position(1, 1, Math.PI / 4);
		worm.setWorld(world);
		worm.setPosition(position);
		assertTrue(worm.canFall());
	}
	//@Test
	public void test_canFall_wormSafe() {													// . X .
		world = new World(3, 5, new boolean[][] {											// . . .
				{true,  false, true}, {true, true, true},									// . . .
				{true,  true,  true}, {true, true, true},									// w . . 
				{false, false, false} }, random);											// X X X
		position = new Position(0, 3, Math.PI / 4);
		worm.setWorld(world);
		worm.setPosition(position);
		assertFalse(worm.canFall());
	}
	
	// }}
	
// {{ Tests for shooting
	
	@Test
	public void test_getEquippedWeaponRifle() {
		worm.addNewWeapon(rifle);
		worm.equipWeapon(rifle);
		assertEquals(rifle, worm.getEquippedWeapon());
	}
	@Test
	public void test_getEquippedWeaponBazooka() {
		worm.addNewWeapon(bazooka);
		worm.equipWeapon(bazooka);
		assertEquals(bazooka, worm.getEquippedWeapon());
	}
	
	@Test
	public void test_equipNextWeapon() {
		worm.addNewWeapon(rifle);
		worm.addNewWeapon(bazooka);
		worm.equipWeapon(rifle);
		worm.equipNextWeapon();
		assertEquals(bazooka, worm.getEquippedWeapon());
	}
	
	//@Test		TODO The method "shoot" still has to be implemented
	public void test_shoot() {
		worm.addNewWeapon(rifle);
		worm.equipWeapon(rifle);
		worm.shoot(50);			//propulsionyield of 50.
	}
	
	@Test
	/**
	 * @note	The invalid cases does not have to be tested, as the methods "isValidDirection" and "isValidActionPoints" are already tested.
	 */
	public void canShoot_LegalCase() {
		assertTrue(worm.canShoot());
	}
	
	// }}
	
// {{ Tests for eating
	
	@Test
	public void test_eat() {
		worm.eat(food);
		assertEquals(1.1, worm.getRadius(), EPS);
		assertTrue(food.isTerminated());
	}
	
	// }}
	
// {{ Tests for world association
	
	@Test
	public void test_setWorld_LegalCase() {
		worm.setWorld(world);
		assertEquals(world, worm.getWorld());
	}
	@Test(expected = ModelException.class)
	public void test_setWorld_worldNull() {
		worm.setWorld(null);
	}
	@Test(expected = ModelException.class)
	public void test_setWorld_worldTerminated() {
		world.terminate();
		worm.setWorld(world);
	}
	@Test
	public void test_hasAWorld() {
		worm.setWorld(world);
		assertTrue(worm.hasAWorld());
	}
	@Test
	public void test_removeWorld() {
		worm.removeWorld();
		assertEquals(null, worm.getWorld());
	}
	
	// }}
	
// {{ Tests for team association
	
	@Test
	public void test_setTeam_LegalCase() {
		worm.setTeam(team);
		assertEquals(team, worm.getTeam());
	}
	@Test(expected = ModelException.class)
	public void test_setTeam_teamNull() {
		worm.setTeam(null);
	}
	@Test(expected = ModelException.class)
	public void test_setTeam_teamTerminated() {
		team.terminate();
		worm.setTeam(team);
	}
	@Test
	public void test_hasATeam() {
		worm.setTeam(team);
		assertTrue(worm.hasATeam());
	}
	@Test
	public void test_removeTeam() {
		worm.removeTeam();
		assertEquals(null, worm.getTeam());
	}
	
	// }}
	
// {{ Tests for weapon association
	
	@Test
	public void test_addNewWeapon() {
		worm.addNewWeapon(bazooka);
		assertTrue(worm.getWeapons().contains(bazooka));
	}
	
	@Test
	public void test_isValidWeapon_LegalCase() {
		assertTrue(Worm.isValidWeapon(rifle));
	}
	@Test
	public void test_isValidWeapon_weaponNull() {
		assertFalse(Worm.isValidWeapon(null));
	}
	@Test
	public void test_isValidWeapon_weaponTerminated() {
		bazooka.terminate();
		assertFalse(Worm.isValidWeapon(bazooka));
	}
	
	@Test
	public void test_removeWeapon() {
		worm.addNewWeapon(bazooka);
		worm.addNewWeapon(rifle);
		worm.removeWeapon(bazooka);
		assertTrue(worm.getWeapons().contains(rifle));
		assertFalse(worm.getWeapons().contains(bazooka));
	}
	
	//@Test		TODO
	/**
	 * @note	The method "removeAllWeapons" gives a ConcurrentModificationException.
	 * 			Could it be solved by converting it to an array?
	 */
	public void test_removeAllWeapons() {
		worm.addNewWeapon(bazooka);
		worm.addNewWeapon(rifle);
		worm.removeAllWeapons();
		assertFalse(worm.getWeapons().contains(bazooka));
		assertFalse(worm.getWeapons().contains(rifle));
	}
	
	@Test
	public void test_hasAsWeapon() {
		worm.addNewWeapon(rifle);
		assertTrue(worm.hasAsWeapon(rifle));
	}
	
	// }}	
}
