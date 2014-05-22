package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.gui.game.IActionHandler;
import worms.model.programs.ParseOutcome;
import worms.model.programs.ParseOutcome.Success;
import worms.util.Util;

public class ProgramTest {
	
// {{ Objects
	
	private static final double EPS = Util.DEFAULT_EPSILON;
	
	private IFacade facade;
	
	private String program1 = "double x;\n x := 1;\n turn x;";
	private String program2 = "double x := 0;\n entity w;\n foreach(worm, w) {\n x := x + 1;}\n turn x; ";
	
	private Random random;
	
	private IActionHandler handler;
	
	private Worm worm;
	
	private World world;
	
	private boolean[][] passableMap = new boolean[][] {	{false, false, false, false},			// X X X X
														{true,  true,  true,  true},			// . . . .
														{true,  true,  true,  true },			// . . . .
														{false, false, false, false}};			// X X X X
	
	// }}
	
// {{ Setup
	
	@Before
	public void Setup() {
		facade = new Facade();
		random = new Random(7357);
		world = facade.createWorld(4, 4, passableMap, random);
		handler = new SimpleActionHandler(facade);
	}
	
	// }}
	
// {{ Tests
	
	@Test
	public void test_program1() {
		ParseOutcome<?> outcome = facade.parseProgram(program1, handler);
		assertTrue(outcome.isSuccess());
	}
	
	@Test
	public void test_program1_execution() {
		ParseOutcome<?> outcome = facade.parseProgram(program1, handler);
		Program program = ((Success) outcome).getResult();
		worm = facade.createWorm(world, 1, 2, Math.PI, 1, "Test", program);
		
		facade.addNewWorm(world, null); // add a second worm.
		double oldOrientation = facade.getOrientation(worm);
		facade.startGame(world); // run the game and start execution of the program.
		double newOrientation = facade.getOrientation(worm);
		assertEquals(oldOrientation + 1, newOrientation, EPS);
		assertNotEquals(worm, facade.getCurrentWorm(world));
	}
	
	@Test
	public void test_program2() {
		ParseOutcome<?> outcome = facade.parseProgram(program2, handler);
		assertTrue(outcome.isSuccess());
	}
	
	@Test
	public void test_program2_execution() {
		ParseOutcome<?> outcome = facade.parseProgram(program2, handler);
		Program program = ((Success) outcome).getResult();
		worm = facade.createWorm(world, 1, 2, 0, 1, "Test", program);
		
		facade.addNewWorm(world, null);		//add second worm.
		facade.addNewWorm(world, null);		//add third worm.
		facade.addNewWorm(world, null);		//add fourth worm.
		double oldOrientation = facade.getOrientation(worm);
		facade.startGame(world);
		double newOrientation = facade.getOrientation(worm);
		assertEquals(oldOrientation + 4, newOrientation, EPS);
		assertNotEquals(worm, facade.getCurrentWorm(world));
	}
	
	
	// }}

}
