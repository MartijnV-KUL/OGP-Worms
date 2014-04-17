package worms.model;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;

public class Team {
	
// {{ Constructors
	
	/**
	 * Constructor for the Team class.
	 * 
	 * @effect	| setName("DummyTeamName")
	 */
	public Team() {
		setName("DummyTeamName");
	}
	
	/**
	 * Constructor for the Team class.
	 * 
	 * @param 	name
	 * 			The name of the team that has to be set.
	 * 
	 * @effect	| setName(name)
	 */
	public Team(String name) {
		setName(name);
	}
	
// }}
	
// {{ Name
	
	private String name;
	
	/**
	 * Returns the name of the team.
	 * 
	 * @return	The name of the team.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Checks if the given name is valid.
	 * 
	 * @param 	name
	 * 			The name that has to be checked.
	 * 
	 * @return	| if (!name.matches("[a-zA-Z]*"))
	 * 			|	return false
	 * 			| if (!Character.isUpperCase(name.charAt(0)))
	 * 			|	return false
	 * 			| if (name.length() < 2)
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public static boolean isValidName(String name) {
		if (!name.matches("[a-zA-Z]*"))
			return false;
		if (!Character.isUpperCase(name.charAt(0)))
			return false;
		if (name.length() < 2)
			return false;
		return true;
	}
	
	/**
	 * Method to set the name of a team to the given name (if it is valid).
	 * 
	 * @param 	name
	 * 			The new name of the team.
	 * 
	 * @post	| new.getName() == name
	 * @throws	ModelException
	 * 			(!isValidName(name))
	 */
	public void setName(String name) throws ModelException {
		if (!isValidName(name))
			throw new ModelException("Invalid team name.");
		this.name = name;
	}
	
// }}
	
// {{ Associations
	
	// {{ World Association
	
	private World world;
	
	@Basic
	/**
	 * Method to return the world.
	 * 
	 * @return	this.world
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * Method to set the world.
	 * 
	 * @param 	world
	 * 			The given world.
	 * 
	 * @post	|new.getWorld() == world
	 * @throws 	ModelException
	 * 			| if (!canHaveAsWorld(world))
	 *			| if (hasAWorld())
	 */
	public void setWorld(World world) throws ModelException {
		if (!canHaveAsWorld(world))
			throw new ModelException("Invalid world specified.");
		if (hasAWorld())
			throw new ModelException("Already has a world.");
		this.world = world;
	}
	
	/**
	 * Checks if the given world is valid.
	 * 
	 * @param 	world
	 * 			The given world.
	 * 
	 * @return	| if (world == null)
	 * 			|	return false
	 * 			| if (world.isTerminated())
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public boolean canHaveAsWorld(World world) {
		if (world==null)
			return false;
		if (world.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Checks if the world is not null.
	 * 
	 * @return	| return (!(world == null))
	 */
	public boolean hasAWorld() {
		return(!(world==null));
	}
	
	/**
	 * Checks if the given world is already set.
	 * 
	 * @param 	world
	 * 			The given world.
	 * 
	 * @return	| return (this.world == world)
	 */
	public boolean hasAsWorld(World world) {
		return (this.world==world);
	}
	
	/**
	 * Removes the current world.
	 * 
	 * @post	| new.getWorld() == null
	 */
	public void removeWorld() {
		world = null;
	}
	
	// }}
	
	// {{ Worm Association
	
	private final ArrayList<Worm> wormCollection = new ArrayList<Worm>();
	
	@Basic
	/**
	 * Returns the collection of worms.
	 * 
	 * @return	The collection of worms.
	 */
	public ArrayList<Worm> getWorms() {
		return wormCollection;
	}
	
	/**
	 * Method to add a new worm to the existing collection of worms.
	 * 
	 * @param 	worm
	 * 			The new worm that has to be added.
	 * 
	 * @effect	| wormCollection.add(worm)
	 * @throws 	ModelException
	 * 			| if (canHaveAsWorm(worm)
	 * 			| if (hasAsWorm(worm)
	 */
	public void addWorm(Worm worm) throws ModelException {
		if (canHaveAsWorm(worm))
			throw new ModelException("Invalid worm specified.");
		if (hasAsWorm(worm))
			throw new ModelException("Worm already in team.");
		worm.setTeam(this);
		wormCollection.add(worm);
	}
	
	/**
	 * Checks if the given world is valid.
	 * 
	 * @param 	worm
	 * 			The worm that has to be checked.
	 * 
	 * @return	| if (worm == null)
	 * 			|	return false
	 * 			| if (worm.isTerminated()
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public boolean canHaveAsWorm(Worm worm) {
		if (worm==null)
			return false;
		if (worm.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Checks if the collection of worms contains the given worm.
	 * 
	 * @param 	worm
	 * 			The given worm.
	 * 
	 * @return	| wormCollection.contains(worm)
	 */
	public boolean hasAsWorm(Worm worm) {
		return wormCollection.contains(worm);
	}
	
	/**
	 * Method to remove a worm from the collection of worms.
	 * 
	 * @param 	worm
	 * 			The worm that has to be removed.
	 * 
	 * @effect	| wormCollection.remove(worm)
	 * @throws 	ModelException
	 * 			| if (!hasAsWorm(worm))
	 */
	public void removeWorm(Worm worm) throws ModelException {
		if (!hasAsWorm(worm))
			throw new ModelException("Worm not found.");
		worm.removeTeam();
		wormCollection.remove(worm);
	}
	
	/**
	 * Method to remove all worms from the collection.
	 */
	public void removeAllWorms() {
		for ( Worm worm : wormCollection ) {
			removeWorm(worm);
		}
	}
	
	/**
	 * Returns an ArrayList of all worms that are alive. The ArrayList contains references to the original worms, not cloned worms.
	 * 
	 * @return	The collection of living worms.
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
	/**
	 * Returns the boolean-type terminated.
	 * 
	 * @return	The boolean-type terminated.
	 */
	public boolean isTerminated() {
		return terminated;
	}
	
	/**
	 * Method to terminate the team and all corresponding objects from the world.
	 */
	public void terminate() {
		if (hasAWorld())
			world.removeTeam(this);
		removeAllWorms();
		terminated = true;
	}
	
// }}
	
}