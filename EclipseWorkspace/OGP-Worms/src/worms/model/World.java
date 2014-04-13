package worms.model;

import java.util.ArrayList;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class World{

	
	public World(double width, double height, boolean[][] passableMap, Random random) {
		setPassableMap(passableMap);
		setHeight(height);
		setWidth(width);
	}
	
// {{ Width

	private double width;

	@Basic
	public double getWidth() {
		return this.width;
	}
	
	public final void setWidth(double width) {
		if (! isValidWidth(width))
			throw new ModelException("Width is invalid.");
		this.width = width;
	}
	
	public boolean isValidWidth(double width) {
		if (width < 0 || width > Double.MAX_VALUE)
			return false;
		return true;
	}
	
	// }}
	
// {{ Height

	private double height;
	
	@Basic
	public double getHeight() {
		return this.height;
	}
	
	public final void setHeight(double height) {
		if (! isValidHeight(height))
			throw new ModelException("Height is invalid.");
		this.height = height;
	}
	
	public boolean isValidHeight(double height) {
		if (height < 0 || height > Double.MAX_VALUE)
			return false;
		return true;
	}
	
	// }}

// {{ PassableMap

	private boolean[][] passableMap;
	private int horizontalPixels;
	private int verticalPixels;
	private final double passabilityAngleResolution = 100; //check passability every 2*pi/res radians.
	private final double passabilityRadiusResolution = 10; //ceck passability every r/res meters
	
	@Basic
	public boolean[][] getPassableMap() {
		return passableMap;
	}
	
	@Basic
	public int getHorizontalPixels() {
		return horizontalPixels;
	}
	
	@Basic
	public int getVerticalPixels() {
		return verticalPixels;
	}
	
	public void setPassableMap(boolean[][] passableMap) {
		this.passableMap = passableMap;
		this.horizontalPixels  = passableMap.length;
		this.verticalPixels = passableMap[0].length;
	}
	
	public boolean isPassable(int pixelX, int pixelY) {
		return getPassableMap()[pixelX][pixelY];
	}
	
	public boolean isPassable(double x, double y) {
		if (isWithinBoundaries(x,y))
			return isPassable(World.positionToPixel(x),World.positionToPixel(y));
		return false;
	}
	
	public boolean isPassable(double x, double y, double radius) {
//TODO use a mathematical formula instead of sampling numerically.
		// Loop over the entire resolution
		for (int i=0; i<passabilityAngleResolution; i++) {
			for (int j=0; j<passabilityRadiusResolution; j++) {
				// Calculate the current radius and angle to be evaluated
				double testRadius = radius * (j/passabilityRadiusResolution);
				double testAngle = 2*Math.PI * (i/passabilityAngleResolution);
				
				// Calculate the x- and y-offsets at the current angle
				double deltaX = testRadius * Math.cos(testAngle);
				double deltaY = testRadius * Math.sin(testAngle);
				
				// Convert the x- and y-coordinates to pixels
				int pixelX = World.positionToPixel(x + deltaX);
				int pixelY = World.positionToPixel(y + deltaY);
				
				// Check if the pixels are passable or not
				if ( !isPassable(pixelX,pixelY) )
					return false;
			}
		}
		return true;
	}
	
	// }}
	
// {{ Other position-related methods
	
	/**
	 * Converts a position expressed as a double to its corresponding pixel.
	 * @param position
	 * @return
	 */
	public static int positionToPixel(double position) {
		//TODO is this the correct implementation?
		return (int) Math.floor(position);
	}
	
	public boolean isBorderPixel(int pixelX, int pixelY) {
		//TODO check if this implementation is correct.
		if ( pixelX==0 )
			return true;
		if ( pixelX==(getHorizontalPixels()-1) )
			return true;
		if ( pixelY==0 )
			return true;
		if ( pixelY==(getVerticalPixels()-1) )
			return true;
		return false;
	}
	
	//Location is adjacent if: location itself is passable + impassable at pixel next to it
	public boolean isAdjacent(double x, double y) {
		int pixelX = World.positionToPixel(x);
		int pixelY = World.positionToPixel(y);
		if ( isBorderPixel(pixelX,pixelY) )
			return false;
		if ( isPassable(pixelX,pixelY) ) {
			if ( ! isPassable( pixelX+1, pixelY   ) ) { return true; }
			if ( ! isPassable( pixelX-1, pixelY   ) ) { return true; }
			if ( ! isPassable( pixelX,   pixelY+1 ) ) { return true; }
			if ( ! isPassable( pixelX,   pixelY-1 ) ) { return true; }
		}
		return true;
	}
	
	public boolean isWithinBoundaries(double x,double y) {
		return ( x>=0 && x<=getWidth() && y>=0 && y<=getHeight() );
	}
	
	// }}

// {{ Terminated
	
	private boolean terminated;
	
	public boolean isTerminated() {
		return terminated;
	}
	
	public void terminate() {
		removeAllWorms();
		removeAllTeams();
		removeAllFood();
		removeProjectile();
		terminated = true;
	}
	
	// }}
	
// {{ Worm Association

	private ArrayList<Worm> worms = new ArrayList<Worm>();
	
	public ArrayList<Worm> getWorms() {
		return worms;
	}
	
	public void addNewWorm(Worm newWorm) throws ModelException {
		if (!isValidWorm(newWorm))
			throw new ModelException("Invalid worm specified");
		if (hasAsWorm(newWorm))
			throw new ModelException("Worm already in world.");
		newWorm.setWorld(this);
		worms.add(newWorm);
	}
	
	public static boolean isValidWorm(Worm worm) {
		if (worm==null)
			return false;
		if (worm.isTerminated())
			return false;
		return true;
	}
	
	public void removeWorm(Worm worm) {
		if (!hasAsWorm(worm)) {
			throw new ModelException("Worm not found.");
		}
		worm.removeWorld();
		worms.remove(worm);
	}
	
	public void removeAllWorms() {
		for ( Worm worm : worms ) {
			removeWorm(worm);
		}
	}
	
	public boolean hasAsWorm(Worm worm) {
		return worms.contains(worm);
	}
	
	// }}
	
// {{ Team Association
	
	private final int maxTeams = 10;
	private ArrayList<Team> teams = new ArrayList<Team>();
	
	public ArrayList<Team> getTeams() {
		return teams;
	}
	
	public void addTeam(Team newTeam) {
		if (!canHaveAsTeam(newTeam))
			throw new ModelException("Invalid team specified");
		if (hasAsTeam(newTeam))
			throw new ModelException("Team already in world.");
		if (teams.size()>=maxTeams)
			throw new ModelException("Maximum amount of teams reached");
		newTeam.setWorld(this);
		teams.add(newTeam);
	}
	
	public boolean canHaveAsTeam(Team newTeam) {
		if (newTeam==null)
			return false;
		if (newTeam.isTerminated())
			return false;
		return true;
	}
	
	public boolean hasAsTeam(Team team) {
		return teams.contains(team);
	}
	
	public void removeTeam(Team team) {
		if (!hasAsTeam(team))
			throw new ModelException("Team not found.");
		team.removeWorld();
		teams.remove(team);
	}
	
	public void removeAllTeams() {
		for ( Team team : teams ) {
			removeTeam(team);
		}
	}
	
	//}}
	
// {{ Food Association

	private ArrayList<Food> food = new ArrayList<Food>();
	
	public ArrayList<Food> getFood() {
		return food;
	}
	
	/**
	 * Method to add a food object to the world.
	 * @param newFood
	 * @throws ModelException
	 */
	public void addFood(Food newFood) throws ModelException {
		if (!canHaveAsFood(newFood))
			throw new ModelException("Invalid food");
		if (hasAsFood(newFood))
			throw new ModelException("Food already in world.");
		newFood.setWorld(this);
		food.add(newFood);
	}
	
	public boolean canHaveAsFood(Food newFood) {
		if (newFood==null)
			return false;
		if (newFood.isTerminated())
			return false;
		return true;
	}
	
	public boolean hasAsFood(Food food) {
		return this.food.contains(food);
	}
	
	public void removeFood(Food food) throws ModelException {
		if (!hasAsFood(food))
			throw new ModelException("Food not found in world");
		food.removeWorld();
		this.food.remove(food);
	}
	
	public void removeAllFood() {
		for ( Food loopFood : food ) {
			removeFood(loopFood);
		}
	}
	
	/**
	 * Checks if a worm and food overlap in the world.
	 * @param worm
	 * @param food
	 * @return
	 */
	public static boolean overlapWormFood(Worm worm, Food food) {
		double wormX = worm.getPosition().getX();
		double wormY = worm.getPosition().getY();
		double wormR = worm.getRadius();
		double foodX = food.getX();
		double foodY = food.getY();
		double foodR = food.getRadius();
		
		double deltaX = foodX - wormX;
		double deltaY = foodY - wormY;
		
		double normDelta = Math.sqrt( Math.pow(deltaX,2) + Math.pow(deltaY,2) );
		double sumRadii = wormR + foodR;
		
		return (normDelta < sumRadii);
	}
	
// }}
	
// {{ Projectile Association

	private Projectile projectile;
	
	public Projectile getProjectile() {
		return projectile;
	}
	
	public void setProjectile(Projectile projectile) throws ModelException {
		if (!canHaveAsProjectile(projectile))
			throw new ModelException("Invalid world specified");
		if (hasAProjectile())
			throw new ModelException("Already has a world.");
		projectile.setWorld(this);
		this.projectile = projectile;
	}
	
	public boolean canHaveAsProjectile(Projectile projectile) {
		if (projectile==null)
			return false;
		if (projectile.isTerminated())
			return false;
		return true;
	}
	
	public boolean hasAProjectile() {
		return(!(projectile==null));
	}
	
	public boolean hasAsProjectile(Projectile projectile) {
		return (this.projectile==projectile);
	}
	
	public void removeProjectile() {
		if (hasAProjectile()) {
			projectile.removeWorld();
			projectile = null;
		}
	}
	
	
	
	
	
	// }}
	
	
// {{ Constants

	private static final double gravitationalAcceleration = 9.80665;

	/**
	 * Basic inspector that returns the gravitational acceleration.
	 * 
	 * @return Returns the gravitational acceleration.
	 */
	@Basic @Immutable
	public static double getGravitationalAcceleration() {
		return gravitationalAcceleration;
	}
	// }}
	
}
	
