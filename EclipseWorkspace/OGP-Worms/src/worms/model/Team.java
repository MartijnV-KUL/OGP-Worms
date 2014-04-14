package worms.model;

import java.util.ArrayList;
import be.kuleuven.cs.som.annotate.*;

public class Team {
	
// {{ Constructors
	
	public Team() {
		setName("DummyTeamName");
	}
	
	public Team(String name) {
		setName(name);
	}
	
// }}
	
// {{ Name
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public static boolean isValidName(String name) {
		if (!name.matches("[a-zA-Z]*"))
			return false;
		if (!Character.isUpperCase(name.charAt(0)))
			return false;
		if (name.length() < 2)
			return false;
		return true;
	}
	
	public void setName(String name) {
		if (!isValidName(name))
			throw new ModelException("Invalid team name.");
		this.name = name;
	}
	
// }}
	
// {{ Associations
	
	// {{ World Association
	
	private World world;
	
	@Basic
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) throws ModelException {
		if (!canHaveAsWorld(world))
			throw new ModelException("Invalid world specified.");
		if (hasAWorld())
			throw new ModelException("Already has a world.");
		this.world = world;
	}
	
	public boolean canHaveAsWorld(World world) {
		if (world==null)
			return false;
		if (world.isTerminated())
			return false;
		return true;
	}
	
	public boolean hasAWorld() {
		return(!(world==null));
	}
	
	public boolean hasAsWorld(World world) {
		return (this.world==world);
	}
	
	public void removeWorld() {
		world = null;
	}
	
	// }}
	
	// {{ Worm Association
	
	private final ArrayList<Worm> wormCollection = new ArrayList<Worm>();
	
	@Basic
	public ArrayList<Worm> getWorms() {
		return wormCollection;
	}
	
	public void addWorm(Worm worm) throws ModelException {
		if (canHaveAsWorm(worm))
			throw new ModelException("Invalid worm specified.");
		if (hasAsWorm(worm))
			throw new ModelException("Worm already in team.");
		worm.setTeam(this);
		wormCollection.add(worm);
	}
	
	public boolean canHaveAsWorm(Worm worm) {
		if (worm==null)
			return false;
		if (worm.isTerminated())
			return false;
		return true;
	}
	
	public boolean hasAsWorm(Worm worm) {
		return wormCollection.contains(worm);
	}
	
	public void removeWorm(Worm worm) throws ModelException {
		if (!hasAsWorm(worm))
			throw new ModelException("Worm not found.");
		worm.removeTeam();
		wormCollection.remove(worm);
	}
	
	public void removeAllWorms() {
		for ( Worm worm : wormCollection ) {
			removeWorm(worm);
		}
	}
	
	/**
	 * Returns an ArrayList of all worms that are alive. The ArrayList contains references to the original worms, not cloned worms.
	 * @return
	 */
	public ArrayList<Worm> getLiveWorms() {
		ArrayList<Worm> liveWorms = new ArrayList<Worm>();
		for ( Worm worm : wormCollection ) {
			if (worm.isAlive())
				liveWorms.add(worm);
		}
		return liveWorms;
	}
	
	// }}
	
// }}

// {{ Terminated
	
	private boolean terminated;
	
	@Basic
	public boolean isTerminated() {
		return terminated;
	}
	
	public void terminate() {
		if (hasAWorld())
			world.removeTeam(this);
		removeAllWorms();
		terminated = true;
	}
	
// }}
	
}