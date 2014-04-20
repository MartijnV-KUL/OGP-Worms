package worms.model;

import org.junit.Before;
import org.junit.Test;

public class TeamTest {
	
// {{ Objects
	
	private Team team;
	
	// }}
	
// {{ Setup
	
	@Before
	public void Setup() {
		team = new Team("FirstTeam");
	}
	
	// }}
	
// {{ Tests for teamnames
	
	@Test
	public void test_setName_LegalCase() {
		team.setName("Team");
	}
	@Test(expected = ModelException.class)
	public void test_setName_containsNumbers() {
		team.setName("Team 1");
	}
	@Test(expected = ModelException.class)
	public void test_setName_noUpperCase() {
		team.setName("team");
	}
	@Test(expected = ModelException.class)
	public void test_setName_tooShort() {
		team.setName("T");
	}
	
	// }}
	
	/**
	 * @note	Associations doesn't have to be tested, they are the same as in the world and worm classes where they are already tested.
	 */

	
	

}
