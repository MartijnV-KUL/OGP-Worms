package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.util.Util;

public class WorldTest {
	
// {{ Objects
	
	private static final double EPS = Util.DEFAULT_EPSILON;
	
	private static Worm worm;
	
	private static World world;
	
	private static Random random;
	
	private Team team = new Team("FirstTeam");
	
	private Food food = new Food(2, 2);		//create food at (2,2).
	
	private Projectile projectile = new Projectile();		//initialisation of a projectile object.
	
	private boolean[][] passableMap = new boolean[][] {	{false, false, false, false},			// X X X X
														{true,  true,  true,  true},			// . . . .
														{true,  true,  true,  true },			// . . . .
														{false, false, false, false}};			// X X X X
	
	// }}
	
// {{ Setup
	
	@Before
	public void Setup() {
		random = new Random(7357);
		worm = new Worm(1, 1, Math.PI / 4, 1, "Testworm");
		world = new World(4, 4, passableMap, random);
	}
	
	// }}
	
// {{ Tests for constants
	
	@Test
	public void test_gravAccel() {
		assertEquals(9.80665, World.getGravitationalAcceleration(), EPS);
	}
	
	// }}
	
// {{ Tests for width and height
	
	@Test
	public void test_getWidth() {
		assertEquals(4, world.getWidth(), EPS);
	}

	@Test
	public void test_getHeight() {
		assertEquals(4, world.getHeight(), EPS);
	}
	
	@Test(expected = ModelException.class)
	public void test_widthTooLow() {
		world = new World(-10, 4, passableMap, random);
	}
	@Test(expected = ModelException.class)
	public void test_widthTooHigh() {
		world = new World(Double.POSITIVE_INFINITY, 4, passableMap, random);
	}
	
	@Test(expected = ModelException.class)
	public void test_heightTooLow() {
		world = new World(4, -10, passableMap, random);
	}
	@Test(expected = ModelException.class)
	public void test_heightTooHigh() {
		world = new World(4, Double.POSITIVE_INFINITY, passableMap, random);
	}
	
	@Test(expected = ModelException.class)
	public void test_invalidRandomSeem() {
		world = new World(4, 4, passableMap, null);
	}
	
	// }}
	
// {{ Tests for passable map
	
	@Test
	public void test_getHorizontalPixels() {
		assertEquals(4, world.getHorizontalPixels(), EPS);
	}
	
	@Test
	public void test_getVerticalPixels() {
		assertEquals(4, world.getVerticalPixels(), EPS);
	}
	
	@Test
	public void test_isValidPassableMap_LegalCase() {
		world.setPassableMap(passableMap);
	}
	@Test(expected = ModelException.class)
	public void test_isValidPassableMap_null() {
		world.setPassableMap(null);
	}
	
	// }}

// {{ Tests for map & passability related methods
	
	@Test
	public void test_boundaries_LegalCase() {
		assertTrue(world.isWithinBoundaries(1, 1));
	}
	@Test
	public void test_boundaries_xTooLow() {
		assertFalse(world.isWithinBoundaries(-1, 1));
	}
	@Test
	public void test_boundaries_xTooHigh() {
		assertFalse(world.isWithinBoundaries(world.getWidth() + 1, 1));
	}
	@Test
	public void test_boundaries_yTooLow() {
		assertFalse(world.isWithinBoundaries(1, -1));
	}
	@Test
	public void test_boundaries_yTooHigh() {
		assertFalse(world.isWithinBoundaries(1, world.getHeight() + 1));
	}
	
	/* The map is equal to
	 * 	X X X X
	 * 	. . . .
	 *  . . . .
	 *  X X X X
	 */
	@Test
	public void test_isPassable_true() {
		assertTrue(world.isPassable(1, 1, 1));
	}
	@Test	//TODO Tests say still bug in isPassable method, but I can't find it in the code.
			//Playing the game doesn't reveal the bug, worms act as they should.
	public void test_isPassable_false() {
		System.out.println(passableMap[0][2]);
		assertFalse(world.isPassable(0, 2, 1));
	}
	
	@Test //TODO
	// isPassable method is bugged, so this fails.		// X X X X
	public void test_isAdjacent_true() {				// . . w .
		worm.setPosition(1, 2, Math.PI / 4);			// . . . .
		assertTrue(world.isAdjacent(1, 2, 1));			// X X X X
	}
	@Test
	public void test_isAdjacent_false() {
		world = new World(3, 5, new boolean[][] { {true,  true,  true,  true},			// . . . .
												  {true,  true,  true,  true},			// . . w .
												  {true,  true,  true,  true},			// . . . .
												  {false, false, false, false}}			// X X X X
												  , random);
		worm.setPosition(1, 3, Math.PI / 4);
		assertFalse(world.isAdjacent(1, 3, 1));
	}
	
	@Test //TODO		
	//isPassable method is bugged, so this fails.			// X X X X
	public void test_isOnSolidGround_true() {				// . . . .
		worm.setPosition(2, 2, Math.PI / 4);				// . . w .
		assertTrue(world.isOnSolidGround(2, 2, 1));			// X X X X
	}
	
	@Test
	public void test_isOverLapping_true() {
		assertTrue(World.isOverlapping(1, 1, 1, 1, 2, 1));
	}
	@Test
	public void test_isOverLapping_false() {
		assertFalse(World.isOverlapping(1, 0, 1, 1, 3, 1));
	}
	
	// }}
	
// {{ Tests for adding food & worms
	
	@Test
	public void test_addNewFood() {
		world.addNewFood();
		assertFalse(world.getFood().isEmpty());
	}
	
	@Test
	public void test_addNewWorm() {
		world.addNewWorm();
		assertFalse(world.getWorms().isEmpty());
	}
	
	// }}
	
// {{ Runtime tests
	
	@Test
	public void test_setActiveWorm_LegalCase() {
		world.addWorm(worm);
		world.setActiveWorm(worm);
	}
	@Test(expected = ModelException.class)
	public void test_setActiveWorm_wormIsNull() {
		world.setActiveWorm(null);
	}
	
	@Test
	public void test_gameFinished_true() {
		world.addWorm(worm);
		assertTrue(world.isGameFinished());
	}
	@Test
	public void test_gameFinished_falseNoTeams() {
		Worm worm2 = new Worm(2, 2, Math.PI / 4, 1, "Testworm 2");
		world.addWorm(worm);
		world.addWorm(worm2);
		assertFalse(world.isGameFinished());
	}
	@Test
	public void test_gameFinished_falseWithTeams() {
		Team team2 = new Team("SecondTeam");
		Worm worm2 = new Worm(2, 2, Math.PI / 4, 1, "Testworm 2");
		worm.setTeam(team);
		worm2.setTeam(team2);
		world.addWorm(worm);
		world.addWorm(worm2);
		assertFalse(world.isGameFinished());
	}
	@Test
	public void test_gameFinished_trueWithTeams() {
		Worm worm2 = new Worm(2, 2, Math.PI / 4, 1, "Testworm 2");
		worm.setTeam(team);
		worm2.setTeam(team);
		world.addWorm(worm);
		world.addWorm(worm2);
		assertTrue(world.isGameFinished());
	}
	
	@Test
	public void test_getWinner() {
		world.addWorm(worm);
		assertEquals("Testworm", world.getWinner());
	}
	@Test
	public void test_getWinner_sameTeam() {
		Worm worm2 = new Worm(2, 2, Math.PI / 4, 1, "Testworm 2");
		worm.setTeam(team);
		worm2.setTeam(team);
		world.addWorm(worm);
		world.addWorm(worm2);
		assertEquals("FirstTeam", world.getWinner());
	}
	
	// }}
	
// {{ Tests for Worm association
	
	@Test
	public void test_addWorm_LegalCase() {
		world.addWorm(worm);
	}
	@Test(expected = ModelException.class)
	public void test_addWorm_wormNull() {
		world.addWorm(null);
	}
	@Test(expected = ModelException.class)
	public void test_addWorm_wormTerminated() {
		worm.terminate();
		world.addWorm(worm);
	}
	@Test(expected = ModelException.class)
	public void test_addWorm_hasAsWorm() {
		world.addWorm(worm);
		world.addWorm(worm);
	}
	
	@Test(expected = ModelException.class)
	public void test_removeWorm_wormNotInWorld() {
		world.removeWorm(worm);
	}
	@Test
	public void test_removeWorm() {
		world.addWorm(worm);
		world.removeWorm(worm);
		assertFalse(world.getWorms().contains(worm));
	}
	
	// }}
	
// {{ Tests for Team association
	
	 @Test
	 public void test_addTeam_LegalCase() {
		 world.addTeam(team);
	 }
	 @Test(expected = ModelException.class)
	 public void test_addTeam_teamNull() {
		 world.addTeam(null);
	 }
	 @Test(expected = ModelException.class)
	 public void test_addTeam_teamTerminated() {
		 team.terminate();
		 world.addTeam(team);
	 }
	 @Test(expected = ModelException.class)
	 public void test_addTeam_hasAsTeam() {
		 world.addTeam(team);
		 world.addTeam(team);
	 }
	 
	 @Test(expected = ModelException.class)
	 public void test_removeTeam_teamNotInWorld() {
		 world.removeTeam(team);
	 }
	 @Test
	 public void test_removeTeam() {
		 world.addTeam(team);
		 world.removeTeam(team);
		 assertFalse(world.getTeams().contains(team));
	 }
	 
	 // }}
	 
// {{ Tests for Food association
	 
	 @Test
	 public void test_addFood_LegalCase() {
		 world.addFood(food);
	 }
	 @Test(expected = ModelException.class)
	 public void test_addFood_foodNull() {
		 world.addFood(null);
	 }
	 @Test(expected = ModelException.class)
	 public void test_addFood_foodTerminated() {
		 food.terminate();
		 world.addFood(food);
	 }
	 @Test(expected = ModelException.class)
	 public void test_addFood_hasAsFood() {
		 world.addFood(food);
		 world.addFood(food);
	 }
	 
	 @Test(expected = ModelException.class)
	 public void test_removeFood_foodNotInWorld() {
		 world.removeFood(food);
	 }
	 @Test
	 public void test_removeFood() {
		 world.addFood(food);
		 world.removeFood(food);
		 assertFalse(world.getFood().contains(food));
	 }
	 
	 // }}
	 
// {{ Tests for Projectile association
	 
	 @Test
	 public void test_addProjectile_LegalCase() {
		 world.setProjectile(projectile);
	 }
	 @Test(expected = ModelException.class)
	 public void test_setProjectile_projectileNull() {
		 world.setProjectile(null);
	 }
	 @Test(expected = ModelException.class)
	 public void test_setProjectile_projectileTerminated() {
		 projectile.terminate();
		 world.setProjectile(projectile);
	 }
	 @Test(expected = ModelException.class)
	 public void test_setProjectile_hasAsProjectile() {
		 world.setProjectile(projectile);
		 world.setProjectile(projectile);
	 }
	 
	 @Test
	 public void test_removeProjectile() {
		 world.setProjectile(projectile);
		 world.removeProjectile();
		 assertEquals(null, world.getProjectile());
	 }
	 
}
