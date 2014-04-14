package worms.model;

import java.util.ArrayList;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class World{

// {{ Constructors
	
	public World() {
		setHeight(1);
		setWidth(1);
		setPassableMap(new boolean[10][10]);
		maxTeams = 10;
		setRandomSeed(new Random());
	}
	
	public World(double width, double height, boolean[][] passableMap, Random random) {
		setHeight(height);
		setWidth(width);
		setPassableMap(passableMap);
		maxTeams = 10;
		setRandomSeed(random);
	}
	
	// }}
	
// {{ Attributes and their Getters, Setters and Checkers

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
		if (isValidPassableMap(passableMap)) {
			this.passableMap = passableMap;
			this.horizontalPixels  = passableMap.length;
			this.verticalPixels = passableMap[0].length;
		}
	}
	
	public boolean isValidPassableMap(boolean[][] passableMap) {
		if (passableMap==null)
			return false;
		return true;
	}
	
	// }}
	
// }}
	
// {{ Map-related methods
	
	/**
	 * Converts a position expressed as a double to its corresponding pixel.
	 * @param position
	 * @return
	 */
	public int[] positionToPixel(double x, double y) {
		//TODO is this the correct implementation?
		int pixelX = (int) Math.round( x * ((double)getHorizontalPixels()-1)/getWidth() );
		int pixelY = (int) Math.round( x * ((double)  getVerticalPixels()-1)/getHeight() );
		int[] pixelPosition = new int[2];
		pixelPosition[0] = pixelX;
		pixelPosition[1] = pixelY;
		return pixelPosition;
	}
	
	public boolean isPassable(int pixelX, int pixelY) {
		return getPassableMap()[pixelX][pixelY];
	}
	
	public boolean isPassable(double x, double y) {
		if (isWithinBoundaries(x,y)) {
			int[] pixelXY = positionToPixel(x,y);
			return isPassable(pixelXY[0],pixelXY[1]);
		}
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
	

	
	public boolean isAdjacent(double x, double y, double radius) {
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
				isAdjacent(x+deltaX,y+deltaY);
			}
		}
		return true;
	}
	
	public boolean isAdjacent(double x, double y) {
		int[] pixelXY = positionToPixel(x,y);
		int pixelX = pixelXY[0];
		int pixelY = pixelXY[1];

		if ( isBorderPixel(pixelX,pixelY) )
			return false;
		if ( isPassable(pixelX,pixelY) ) {
			if ( ! isPassable( pixelX+1, pixelY   ) ) { return true; }
			if ( ! isPassable( pixelX-1, pixelY   ) ) { return true; }
			if ( ! isPassable( pixelX,   pixelY+1 ) ) { return true; }
			if ( ! isPassable( pixelX,   pixelY-1 ) ) { return true; }
		}
		return false;
	}
	
	public boolean isWithinBoundaries(double x,double y) {
		return ( x>=0 && x<=getWidth() && y>=0 && y<=getHeight() );
	}
	
	public static boolean isOverlapping(double x1, double y1, double r1, double x2, double y2, double r2) {
		double deltaX = x2 - x1;
		double deltaY = y2 - y1;
		
		double normDelta = Math.sqrt( Math.pow(deltaX,2) + Math.pow(deltaY,2) );
		double sumRadii = r1 + r2;
		
		return (normDelta < sumRadii);
	}
	
	// }}
	
// {{ New food/worm stuff
	
	public double[] getRandomXY() {
		double xMin = 0;
		double xMax = getWidth();
		double yMin = 0;
		double yMax = getHeight();
		double[] output = new double[2];
		output[0] = xMin + (xMax-xMin) * getRandomSeed().nextDouble();
		output[1] = yMin + (yMax-yMin) * getRandomSeed().nextDouble();
		return output;
	}
	
	public void addNewFood() {
		double randX = 0;
		double randY = 0;
		do {
			double[] randomXY = getRandomXY();
			randX = randomXY[0];
			randY = randomXY[1];
		} while( false ); // don't check anything
		//} while( !isPassable(randX,randY) ); // check if x&y is passable
		//} while( !isAdjacent(randX,randY) ); // check if x&y is adjacent
		
		//TODO check if x&y is passable? or adjacent?
		
		addFood( new Food( randX, randY ) );
	}
	
	public Food addNewFood(double x,double y) {
		Food newFood = new Food(x,y);
		addFood(newFood);
		return newFood;
	}
	
	public void addNewWorm() {
		double randX = 0;
		double randY = 0;
		do {
			double[] randomXY = getRandomXY();
			randX = randomXY[0];
			randY = randomXY[1];
		//} while( false ); // don't check anything
		//} while( !isPassable(randX,randY) ); // check if x&y is passable
		} while( !isAdjacent(randX,randY) ); // check if x&y is adjacent
		
		addWorm(new Worm(randX,randY,0,1,"JohnDoe"));
	}
	
	 public Worm addNewWorm(double x, double y, double direction, double radius, String name) {
		 Worm newWorm = new Worm(x,y,direction,radius,name);
		 addWorm(newWorm);
		 return newWorm;
	 }
	
	// }}

// {{ runtime stuff
	
	private Worm activeWorm;
	
	@Basic
	public Worm getActiveWorm() {
		return activeWorm;
	}
	
	public void setActiveWorm(Worm worm) throws ModelException {
		if (!isValidActiveWorm(worm))
			throw new ModelException("Invalid worm.");
		activeWorm = worm;
	}
	
	public boolean isValidActiveWorm(Worm worm) {
		if (!hasAsWorm(worm))
			// note: this automatically verifies null and isTerminated()
			return false;
		return true;
	}
	
	public boolean isGameFinished() {
		Worm aliveWorm = null;
		for ( Worm testWorm : getWorms() ) {
			if (testWorm.isAlive()) {
				aliveWorm = testWorm;
				break;
			}
		}
		for ( Worm testWorm : getWorms() ) {
			if (testWorm!=aliveWorm && testWorm.isAlive()) {
				try {
					if (!(aliveWorm.getTeam()==testWorm.getTeam()))
						return false; // A second worm of a different team is still alive as well. 
				} catch (NullPointerException e) {};
			}
		}
		return true; // Default
	}
	
	public void startGame() {
		//TODO should worms still be added?
		setActiveWorm(getWorms().get(0));
	}
	
	public void nextTurn() {
		int index = getWorms().indexOf(getActiveWorm());
		if (index==(getWorms().size()-1))
			index = 0;
		else
			index += 1;
		setActiveWorm(getWorms().get(index));
	}
	
	
// }}


// {{ Random Seed
	
	private Random randomSeed;
	
	@Basic
	public Random getRandomSeed() {
		return randomSeed;
	}
	
	public void setRandomSeed(Random random) {
		if (!isValidRandomSeed(random))
			throw new ModelException("Invalid random seed.");
		randomSeed = random;
	}
	
	public boolean isValidRandomSeed(Random random) {
		if (random==null)
			return false;
		return true;
	}
	
	// }}
	
// {{ Associations
	
// {{ Worm Association

	private final ArrayList<Worm> wormCollection = new ArrayList<Worm>();
	
	@Basic
	public ArrayList<Worm> getWorms() {
		return wormCollection;
	}
	
	public void addWorm(Worm newWorm) throws ModelException {
		if (!isValidWorm(newWorm))
			throw new ModelException("Invalid worm specified.");
		if (hasAsWorm(newWorm))
			throw new ModelException("Worm already in world.");
		newWorm.setWorld(this);
		wormCollection.add(newWorm);
	}
	
	public static boolean isValidWorm(Worm worm) {
		if (worm==null)
			return false;
		if (worm.isTerminated())
			return false;
		return true;
	}
	
	public void removeWorm(Worm worm) throws ModelException {
		if (!hasAsWorm(worm)) {
			throw new ModelException("Worm not found.");
		}
		worm.removeWorld();
		wormCollection.remove(worm);
	}
	
	public void removeAllWorms() {
		for ( Worm worm : wormCollection ) {
			removeWorm(worm);
		}
	}
	
	public boolean hasAsWorm(Worm worm) {
		return wormCollection.contains(worm);
	}
	
	// }}
	
// {{ Team Association
	
	private final ArrayList<Team> teamCollection = new ArrayList<Team>();
	
	@Basic
	public ArrayList<Team> getTeams() {
		return teamCollection;
	}

	private final int maxTeams;
	
	@Basic
	public int getMaxTeams() {
		return maxTeams;
	}
	
	public void addTeam(Team newTeam) throws ModelException {
		if (!canHaveAsTeam(newTeam))
			throw new ModelException("Invalid team specified.");
		if (hasAsTeam(newTeam))
			throw new ModelException("Team already in world.");
		if (teamCollection.size()>=getMaxTeams())
			throw new ModelException("Maximum amount of teams reached.");
		newTeam.setWorld(this);
		teamCollection.add(newTeam);
	}
	
	public boolean canHaveAsTeam(Team newTeam) {
		if (newTeam==null)
			return false;
		if (newTeam.isTerminated())
			return false;
		return true;
	}
	
	public boolean hasAsTeam(Team team) {
		return teamCollection.contains(team);
	}
	
	public void removeTeam(Team team) throws ModelException {
		if (!hasAsTeam(team))
			throw new ModelException("Team not found.");
		team.removeWorld();
		teamCollection.remove(team);
	}
	
	public void removeAllTeams() {
		for ( Team team : teamCollection ) {
			removeTeam(team);
		}
	}
	
	//}}
	
// {{ Food Association

	private final ArrayList<Food> foodCollection = new ArrayList<Food>();
	
	@Basic
	public ArrayList<Food> getFood() {
		return foodCollection;
	}
	
	/**
	 * Method to add a food object to the world.
	 * @param newFood
	 * @throws ModelException
	 */
	public void addFood(Food newFood) throws ModelException {
		if (!canHaveAsFood(newFood))
			throw new ModelException("Invalid food specified.");
		if (hasAsFood(newFood))
			throw new ModelException("Food already in world.");
		newFood.setWorld(this);
		foodCollection.add(newFood);
	}
	
	public boolean canHaveAsFood(Food food) {
		if (food==null)
			return false;
		if (food.isTerminated())
			return false;
		return true;
	}
	
	public boolean hasAsFood(Food food) {
		return this.foodCollection.contains(food);
	}
	
	public void removeFood(Food food) throws ModelException {
		if (!hasAsFood(food))
			throw new ModelException("Food not found.");
		food.removeWorld();
		foodCollection.remove(food);
	}
	
	public void removeAllFood() {
		for ( Food loopFood : foodCollection ) {
			removeFood(loopFood);
		}
	}
	
// }}
	
// {{ Projectile Association

	private Projectile projectile;
	
	@Basic
	public Projectile getProjectile() {
		return projectile;
	}
	
	public void setProjectile(Projectile projectile) throws ModelException {
		if (!canHaveAsProjectile(projectile))
			throw new ModelException("Invalid projectile specified.");
		if (hasAProjectile())
			throw new ModelException("Already has a projectile.");
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
	
	// }}

// {{ Terminated
	
	private boolean terminated;
	
	@Basic
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

}
	
