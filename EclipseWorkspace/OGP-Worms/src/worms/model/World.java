package worms.model;

import java.util.ArrayList;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class World{

// {{ Constructors
	
	/**
	 * Constructor for the World class.
	 * 	
	 * @effect	| setHeight(1)
	 * @effect	| setWidth(1)
	 * @effect	| setPassableMap(new boolean[10][10])
	 * @effect	| setRandomSeed(new Random())
	 */
	public World() {
		setHeight(1);
		setWidth(1);
		setPassableMap(new boolean[10][10]);
		maxTeams = 10;
		setRandomSeed(new Random());
	}
	
	/**
	 * Constructor for the World class.
	 * 
	 * @param 	width
	 * 			The width of the world.
	 * @param 	height
	 * 			The height of the world.
	 * @param 	passableMap
	 * 			The boolean[][] type passablemap.
	 * @param 	random
	 * 			A random value.
	 * 
	 * @effect	| setHeight(height)
	 * @effect	| setWidth(width)
	 * @effect	| setPassableMap(passableMap)
	 * @effect	| setRandomSeed(random)
	 */
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
	/**
	 * Basic inspector that returns the width of the world.
	 * 
	 * @return	The width of the world.
	 */
	public double getWidth() {
		return this.width;
	}
	
	/**
	 * Method to set the width of the world.
	 * 
	 * @param 	width
	 * 			The new width of the world.
	 * 
	 * @post	| new.getWidth() == width
	 * @throws	ModelException
	 * 			| if (!isValidWidth(width))
	 */
	public final void setWidth(double width) throws ModelException {
		if (! isValidWidth(width))
			throw new ModelException("Width is invalid.");
		this.width = width;
	}
	
	/**
	 * Checks if the given width is valid.
	 * 
	 * @param	width
	 * 			The given width.
	 * 
	 * @return	(width > 0 && width <= Double.MAX_VALUE)
	 */
	public boolean isValidWidth(double width) {
		if (width < 0 || width > Double.MAX_VALUE)
			return false;
		return true;
	}
	
	// }}
	
// {{ Height

	private double height;
	
	@Basic
	/**
	 * Basic inspector that returns the height of the world.
	 * 
	 * @return	The height of the world.
	 */
	public double getHeight() {
		return this.height;
	}
	
	/**
	 * Method to set the height of the world.
	 * 
	 * @param 	height
	 * 			The new height of the world.
	 * 
	 * @post	| new.getHeight() == height
	 * @throws	ModelException
	 * 			if (!isValidHeight(height))
	 */
	public final void setHeight(double height) throws ModelException{
		if (! isValidHeight(height))
			throw new ModelException("Height is invalid.");
		this.height = height;
	}
	
	/**
	 * Checks if the given height is valid.
	 * 
	 * @param 	height
	 *			The given width
	 *
	 * @return	(height > 0 && height <= Double.MAX_VALUE)
	 */
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
	private final double passabilityRadiusResolution = 10; //check passability every r/res meters
	
	@Basic
	/**
	 * Basic inspector that returns the passable map.
	 * 
	 * @return	The passable map.
	 */
	public boolean[][] getPassableMap() {
		return passableMap;
	}
	
	@Basic
	/**
	 * Basic inspector that returns the horizontal pixels of the world.
	 * 
	 * @return	The horizontal pixels of the world.
	 */
	public int getHorizontalPixels() {
		return horizontalPixels;
	}
	
	@Basic
	/**
	 * Basic inspector that returns the vertical pixels of the world.
	 * 
	 * @return	The vertical pixels of the world.
	 */
	public int getVerticalPixels() {
		return verticalPixels;
	}
	
	/**
	 * Method to set the passable map if it is valid.
	 * 
	 * @param 	passableMap
	 * 			The new passable map.
	 * 
	 * @post	| new.getPassableMap == passableMap
	 * @post	| new.getHorizontalPixels == passableMap.length
	 * @post	| new.getVerticalPixels == passableMap[0].length
	 */
	public void setPassableMap(boolean[][] passableMap) {
		if (isValidPassableMap(passableMap)) {
			this.passableMap = passableMap;
			this.horizontalPixels  = passableMap.length;
			this.verticalPixels = passableMap[0].length;
		}
	}
	
	/**
	 * Checks if the passable map is valid.
	 * 
	 * @param 	passableMap
	 * 			The passable map to check.
	 * 
	 * @return	(passableMap != null)
	 */
	public boolean isValidPassableMap(boolean[][] passableMap) {
		if (passableMap==null)
			return false;
		return true;
	}
	
	// }}
	
// }}
	
// {{ Map-related methods
	
	/**
	 * Converts a position expressed as a double to it's corresponding pixels.
	 * 
	 * @param 	x
	 * 			The x-coordinate.
	 * @param	y
	 * 			The y-coordinate
	 * 
	 * @return	The position in pixels, expressed in an array.
	 */
	public int[] positionToPixel(double x, double y) {
		int pixelX = (int) Math.round( x * ((double) getHorizontalPixels() -1) / getWidth() );
		int pixelY = (int) Math.round( x * ((double) getVerticalPixels() -1) / getHeight() );
		int[] pixelPosition = new int[2];
		pixelPosition[0] = pixelX;
		pixelPosition[1] = pixelY;
		return pixelPosition;
	}
	
	/**
	 * Checks if a position in pixels is passable.
	 * 
	 * @param 	pixelX
	 * 			The x-coordinate in pixels.
	 * @param 	pixelY
	 * 			The y-coordinate in pixels.
	 * 
	 * @return	| getPassableMap()[pixelX][pixelY]
	 */
	public boolean isPassable(int pixelX, int pixelY) {
		return getPassableMap()[pixelX][pixelY];
	}
	
	/**
	 * Checks if a position in doubles is passable.
	 * 
	 * @param 	x
	 * 			The x-coordinate in a double.
	 * @param 	y
	 * 			The y-coordinate in a double.
	 * 
	 * @return	| if (isWithinBoundaries(x, y))
	 * 			|	int[] pixelXY = positionToPixel(x, y)
	 * 			|	return isPassable(pixelXY[0], pixelXY[1])
	 * 			| else
	 * 			|	return false
	 */
	public boolean isPassable(double x, double y) {
		if (isWithinBoundaries(x,y)) {
			int[] pixelXY = positionToPixel(x,y);
			return isPassable(pixelXY[0],pixelXY[1]);
		}
		return false;
	}
	
	/**
	 * Checks if a circle around the given location is passable.
	 * 
	 * @param 	x
	 * 			The x-coordinate.
	 * @param 	y
	 * 			The y-coordinate.
	 * @param 	radius
	 * 			The radius.
	 * 
	 * @return	| for(int i=0; i<passabilityAngleResolution; i++)
	 * 			|	for(int j=0; j<passabilityRadiusResolution; j++)
	 * 			| 		if (!isPassable(x + deltaX, y + deltaY))
	 * 			|			return false
	 * 			| return true
	 * @note	deltaX and deltaY are calculated in the for-loops, then the method iterates over every pixel in the circle.
	 */
	public boolean isPassable(double x, double y, double radius) {
		// Loop over the entire resolution
		for (int i=0; i<passabilityAngleResolution; i++) {
			for (int j=0; j<passabilityRadiusResolution; j++) {
				// Calculate the current radius and angle to be evaluated
				double testRadius = radius * (j/passabilityRadiusResolution);
				double testAngle = 2*Math.PI * (i/passabilityAngleResolution);
				
				// Calculate the x- and y-offsets at the current angle
				double deltaX = testRadius * Math.cos(testAngle);
				double deltaY = testRadius * Math.sin(testAngle);
				
				//TODO  Does this work instead of the next (in blue comment) lines of code? The "positionToPixel" method is called in "isPassable",
				//		why call it again and calculate pixelX and pixelY again?
				if ( !isPassable(x + deltaX, y + deltaY))
					return false;
				
				
				/**
				// Convert the x- and y-coordinates to pixels
				int pixelX = positionToPixel(x + deltaX);
				int pixelY = positionToPixel(y + deltaY);
				
				// Check if the pixels are passable or not
				if ( !isPassable(pixelX,pixelY) )
					return false;
				*/
			}
		}
		return true;
	}
	
	/**
	 * Method to check if a coordinate pair of pixels is at the edge of the map.
	 * 
	 * @param 	pixelX
	 * 			The x-coordinate in pixels.
	 * @param 	pixelY
	 * 			The y-coordinate in pixels.
	 * 
	 * @return	| if ( pixelX==0 )
	 *			|	return true
	 *			| if ( pixelX==(getHorizontalPixels()-1) )
	 *			|	return true
	 *			| if ( pixelY==0 )
	 *			|	return true
	 *			| if ( pixelY==(getVerticalPixels()-1) )
	 *			|	return true
	 *			| else
	 *			|	return false
	 */
	public boolean isBorderPixel(int pixelX, int pixelY) {
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
	

	/**
	 * Checks if a circle around a position is adjacent to impassable terrain.
	 * 
	 * @param 	x
	 * 			The x-coordinate.
	 * @param 	y
	 * 			The y-coordinate.
	 * @param 	radius
	 * 			The radius.
	 * 
	 * @return	| for(int i=0; i<passabilityAngleResolution; i++)
	 * 			|	for (int j=0; j<passabilityRadiusResolution; j++)
	 * 			|		isAdjacent(x + deltaX, y + deltaY)
	 * 			| return true
	 * @note	deltaX and deltaY are calculated in the for-loops, then the method iterates over every pixel in the circle.
	 */
	public boolean isAdjacent(double x, double y, double radius) {
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
	
	/**
	 * Checks if a position is adjacent to impassable terrain.
	 * 
	 * @param 	x
	 * 			The x-coordinate
	 * @param 	y
	 * 			The y-coordinate
	 * 
	 * @return	| if ( isBorderPixel(pixelX,pixelY) )
	 *			| 	return false
	 *			| if ( isPassable(pixelX,pixelY) ) {
	 *			|	if ( ! isPassable( pixelX+1, pixelY   ) )
	 *			|		return true
	 *			|	if ( ! isPassable( pixelX-1, pixelY   ) )
	 *			|		return true
	 *			|	if ( ! isPassable( pixelX,   pixelY+1 ) )
	 *			| 		return true
	 *			|	if ( ! isPassable( pixelX,   pixelY-1 ) )
	 *			|		return true
	 *			| }
	 *			| else
	 *			|	return false;
	 */
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
	
	/**
	 * Checks if a position is within the boundaries of the map.
	 * 
	 * @param 	x
	 * 			The x-coordinate.
	 * @param 	y
	 * 			The y-coordinate.
	 * 
	 * @return	| return ( x>=0 && x<=getWidth() && y>=0 && y<=getHeight() )
	 */
	public boolean isWithinBoundaries(double x,double y) {
		return ( x>=0 && x<=getWidth() && y>=0 && y<=getHeight() );
	}
	
	/**
	 * Checks if two locations (circle around each location) are overlapping.
	 * 
	 * @param 	x1
	 * 			The x-coordinate of the first location.
	 * @param 	y1
	 * 			The y-coordinate of the first location.
	 * @param 	r1
	 * 			The radius of the first location.
	 * @param 	x2
	 * 			The x-coordinate of the second location.
	 * @param 	y2
	 * 			The y-coordinate of the second location.
	 * @param 	r2
	 * 			The radius of the second location.
	 * 
	 * @return | (normDelta < sumRadii)
	 * @note	normDelta is calculated as follows: Math.sqrt( Math.pow(deltaX,2) + Math.pow(deltaY,2) ), with deltaX and deltaY the
	 * 			difference between x1, x2 and the difference between y1, y2.
	 * @note	sumRadii is the sum of the two radii, so this is equal to r1 + r2.
	 */
	public static boolean isOverlapping(double x1, double y1, double r1, double x2, double y2, double r2) {
		double deltaX = x2 - x1;
		double deltaY = y2 - y1;
		
		double normDelta = Math.sqrt( Math.pow(deltaX,2) + Math.pow(deltaY,2) );
		double sumRadii = r1 + r2;
		
		return (normDelta < sumRadii);
	}
	
	// }}
	
// {{ New food/worm stuff
	
	/**
	 * Method to randomly create a position.
	 * 
	 * @return	An array with a random created position.
	 */
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
	
	/**
	 * Method to add food to the map at a random location.
	 */
	public void addNewFood() {
		double randX = 0;
		double randY = 0;
		do {
			double[] randomXY = getRandomXY();
			randX = randomXY[0];
			randY = randomXY[1];
		} while( !isPassable(randX, randY) && !isAdjacent(randX, randY) ); //TODO Is this correct instead??
		//} while( !isPassable(randX,randY) ); // check if x&y is passable
		//} while( !isAdjacent(randX,randY) ); // check if x&y is adjacent
		
		addFood( new Food( randX, randY ) );
	}
	
	/**
	 * Method to add food to the map at a given location.
	 * 
	 * @param 	x
	 * 			The x-coordinate.
	 * @param 	y
	 * 			The y-coordinate.
	 * 
	 * @effect	| addFood(newFood)
	 * @return	A food object at the specified location.
	 */
	public Food addNewFood(double x,double y) {
		Food newFood = new Food(x,y);
		addFood(newFood);
		return newFood;
	}
	
	/**
	 * Method to add a new worm at a random location.
	 */
	public void addNewWorm() {
		double randX = 0;
		double randY = 0;
		do {
			double[] randomXY = getRandomXY();
			randX = randomXY[0];
			randY = randomXY[1];
		//} while( false ); // don't check anything
		//} while( !isPassable(randX,randY) ); // check if x&y is passable
		} while( !isAdjacent(randX,randY) && !isPassable(randX, randY) ); // TODO Is this correct instead?
		
		addWorm(new Worm(randX,randY,0,1,"JohnDoe"));
	}
	
	/**
	 * Method to add a worm with a specified position, direction, radius and name to the map.
	 * 
	 * @param 	x
	 * 			The x-coordinate of the worm.
	 * @param 	y
	 * 			The y-coordinate of the worm.
	 * @param 	direction
	 * 			The direction of the worm.
	 * @param 	radius
	 * 			The radius of the worm.
	 * @param 	name
	 * 			The name of the worm.
	 * 
	 * @effect	| addWorm(newWorm)
	 * @return	A worm object with a specified location, direction, radius and name.
	 */
	public Worm addNewWorm(double x, double y, double direction, double radius, String name) {
		Worm newWorm = new Worm(x,y,direction,radius,name);
		addWorm(newWorm);
		return newWorm;
	 }
	
	// }}

// {{ runtime stuff
	
	private Worm activeWorm;
	
	@Basic
	/**
	 * Basic inspector to return the currently active worm.
	 * 
	 * @return	The currently active worm.
	 */
	public Worm getActiveWorm() {
		return activeWorm;
	}
	
	/**
	 * Method to set the given worm to active.
	 * 
	 * @param 	worm
	 * 			The given worm.
	 * 
	 * @post	| new.getActiveWorm() == worm
	 * @throws 	ModelException
	 * 			if (!isValidActiveWorm(worm)
	 */
	public void setActiveWorm(Worm worm) throws ModelException {
		if (!isValidActiveWorm(worm))
			throw new ModelException("Invalid worm.");
		activeWorm = worm;
	}
	
	/**
	 * Checks if the given worm is a valid active worm.
	 * 
	 * @param 	worm
	 * 			The given worm.
	 * 
	 * @return	| hasAsWorm(worm)
	 * @note	The method hasAsWorm(worm) verifies null and isTerminated().
	 */
	public boolean isValidActiveWorm(Worm worm) {
		if (!hasAsWorm(worm))
			return false;
		return true;
	}
	
	/**
	 * Checks whether or not the game is finished.
	 * 
	 * @return	True if the game is finished.
	 */
	public boolean isGameFinished() {
		Worm aliveWorm = null;
		for ( Worm testWorm : getWorms() ) {
			if (testWorm.isAlive()) {
				aliveWorm = testWorm;
				break;
			}
		}
		for ( Worm testWorm : getWorms() ) {
			if (testWorm != aliveWorm && testWorm.isAlive()) {
				try {
					if (!(aliveWorm.getTeam()==testWorm.getTeam()))
						return false; // A second worm of a different team is still alive as well. 
				} catch (NullPointerException exc) {};
			}
		}
		setWinningName(aliveWorm.getName());
		return true; // Default
	}
	
	/**
	 * Method to start the game.
	 */
	public void startGame() {
		//TODO should worms still be added?
		setActiveWorm(getWorms().get(0));
	}
	
	/**
	 * Method to initiate the next turn (select the next worm).
	 */
	public void nextTurn() {
		int index = getWorms().indexOf(getActiveWorm());
		if (index==(getWorms().size()-1))
			index = 0;
		else
			index += 1;
		setActiveWorm(getWorms().get(index));
	}
	
	private String winningName;
	
	/**
	 * Method to set the name of the winning worm.
	 * 
	 * @param 	name
	 * 			The name of the winning worm.
	 */
	public void setWinningName(String name) {
		name = winningName;
	}
	
	/**
	 * Method to return the name of the winning worm.
	 * 
	 * @return	The name of the winning worm.
	 */
	public String getWinner() {
		return winningName;
	}
	
	
// }}


// {{ Random Seed
	
	private Random randomSeed;
	
	@Basic
	/**
	 * Basic inspector to return a random generated number.
	 * 
	 * @return	A random generated number.
	 */
	public Random getRandomSeed() {
		return randomSeed;
	}
	
	/**
	 * Method to set a random generated number.
	 * 
	 * @param 	random
	 * 			The random generated number.
	 * 
	 * @post	| new.getRandomSeed() == random
	 * @throws 	ModelException
	 * 			| if (!isValidRandomSeed(random)
	 */
	public void setRandomSeed(Random random) throws ModelException {
		if (!isValidRandomSeed(random))
			throw new ModelException("Invalid random seed.");
		randomSeed = random;
	}
	
	/**
	 * Checks if the random generated number is valid.
	 * 
	 * @param 	random
	 * 			The random generated number.
	 * 
	 * @return	if (random != null)
	 */
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
	 * @param 	newworm
	 * 			The new worm that has to be added.
	 * 
	 * @effect	| wormCollection.add(newworm)
	 * @throws 	ModelException
	 * 			| if (!isValidWorm(newWorm))
	 * 			| if (hasAsWorm(newWorm)
	 */
	public void addWorm(Worm newWorm) throws ModelException {
		if (!isValidWorm(newWorm))
			throw new ModelException("Invalid worm specified.");
		if (hasAsWorm(newWorm))
			throw new ModelException("Worm already in world.");
		newWorm.setWorld(this);
		wormCollection.add(newWorm);
	}
	
	/**
	 * Checks if the given worm is a valid worm.
	 * 
	 * @param 	worm
	 * 			The given worm.
	 * 
	 * @return	| if (worm == null)
	 * 			|	return false
	 * 			| if (worm.isTerminated())
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public static boolean isValidWorm(Worm worm) {
		if (worm==null)
			return false;
		if (worm.isTerminated())
			return false;
		return true;
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
		if (!hasAsWorm(worm)) {
			throw new ModelException("Worm not found.");
		}
		worm.removeWorld();
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
	
	// }}
	
// {{ Team Association
	
	private final ArrayList<Team> teamCollection = new ArrayList<Team>();
	
	@Basic
	/**
	 * Basic inspector to return the collection of teams.
	 * 
	 * @return	The collection of teams.
	 */
	public ArrayList<Team> getTeams() {
		return teamCollection;
	}

	private final int maxTeams;
	
	@Basic
	/**
	 * Basic inspector to return the maximum number of teams.
	 * 
	 * @return	The maximum number of teams.
	 */
	public int getMaxTeams() {
		return maxTeams;
	}
	
	/**
	 * Method to add a new team to the existing collection of teams.
	 * 
	 * @param 	newTeam
	 * 			The new team that has to be added.
	 * 
	 * @effect	| teamCollection.add(newTeam)
	 * @throws 	ModelException
	 * 			| if (!canHaveAsTeam(newTeam))
	 * 			| if (hasAsTeam(newTeam))
	 * 			| if (teamCollection.size() >= getMaxTeams())
	 */
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
	
	/**
	 * Checks if the given team is valid.
	 * 
	 * @param 	newteam
	 * 			The given team.
	 * @return	True if the given team is not null and if it is not terminated.
	 * 			| if (newTeam == null)
	 * 			|	return false
	 * 			| if (newTeam.isTerminated())
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public boolean canHaveAsTeam(Team newTeam) {
		if (newTeam==null)
			return false;
		if (newTeam.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Checks if the collection of teams contains the given team.
	 * 
	 * @param 	team
	 * 			The given team.
	 * 
	 * @return	| teamCollection.contains(team)
	 */
	public boolean hasAsTeam(Team team) {
		return teamCollection.contains(team);
	}
	
	/**
	 * Method to remove the given team from the team.
	 * 
	 * @param 	team
	 * 			The team that has to be removed.
	 * 
	 * @effect	| teamCollection.remove(team)
	 * @throws 	ModelException
	 * 			| if (!hasAsTeam(team)
	 */
	public void removeTeam(Team team) throws ModelException {
		if (!hasAsTeam(team))
			throw new ModelException("Team not found.");
		team.removeWorld();
		teamCollection.remove(team);
	}
	
	/**
	 * Method to remove all teams from the collection.
	 */
	public void removeAllTeams() {
		for ( Team team : teamCollection ) {
			removeTeam(team);
		}
	}
	
	//}}
	
// {{ Food Association

	private final ArrayList<Food> foodCollection = new ArrayList<Food>();
	
	@Basic
	/**
	 * Basic inspector to return the collection of food.
	 * 
	 * @return	The collection of food.
	 */
	public ArrayList<Food> getFood() {
		return foodCollection;
	}
	
	/**
	 * Method to add a food object to the world.
	 * 
	 * @param 	newFood
	 * 			The food that has to be added.
	 * 
	 * @effect	| foodCollection.add(newFood)
	 * @throws 	ModelException
	 * 			| if (!canHaveAsFood(newFood))
	 * 			| if (hasAsFood(newFood))
	 */
	public void addFood(Food newFood) throws ModelException {
		if (!canHaveAsFood(newFood))
			throw new ModelException("Invalid food specified.");
		if (hasAsFood(newFood))
			throw new ModelException("Food already in world.");
		newFood.setWorld(this);
		foodCollection.add(newFood);
	}
	
	/**
	 * Checks if the given food is valid.
	 * 
	 * @param 	food
	 * 			The given food.
	 * 
	 * @return	| if (food == null)
	 * 			| 	return false
	 * 			| if (food.isTerminated())
	 * 			|	return false
	 * 			| else
	 * 			| 	return true
	 */
	public boolean canHaveAsFood(Food food) {
		if (food==null)
			return false;
		if (food.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Checks if the collecion of food contains the given foodobject.
	 * 
	 * @param 	food
	 * 			The given foodobject.
	 * 
	 * @return	| this.foodCollection.contains(food)
	 */
	public boolean hasAsFood(Food food) {
		return this.foodCollection.contains(food);
	}
	
	/**
	 * Method to remove food from the collection.
	 * 
	 * @param	food
	 * 			The food that has to be removed.
	 * 
	 * @effect	| foodCollection.remove(food)
	 * @throws 	ModelException
	 * 			| if (!hasAsFood(food))
	 */
	public void removeFood(Food food) throws ModelException {
		if (!hasAsFood(food))
			throw new ModelException("Food not found.");
		food.removeWorld();
		foodCollection.remove(food);
	}
	
	/**
	 * Method to remove all food from the collection.
	 */
	public void removeAllFood() {
		for ( Food loopFood : foodCollection ) {
			removeFood(loopFood);
		}
	}
	
// }}
	
// {{ Projectile Association

	private Projectile projectile;
	
	@Basic
	/**
	 * Basic inspector to return the current projectile.
	 * 
	 * @return	The current projectile.
	 */
	public Projectile getProjectile() {
		return projectile;
	}
	
	/**
	 * Method to set the projectile to the given projectile if it is valid.
	 * 
	 * @param 	projectile
	 * 			The given projectile.
	 * 
	 * @post	| new.getProjectile() == projectile
	 * @effect	| projectile.setWorld(this)
	 * @throws 	ModelException
	 * 			| if (!canHaveAsProjectile(projectile))
	 * 			| if (hasAProjectile())
	 */
	public void setProjectile(Projectile projectile) throws ModelException {
		if (!canHaveAsProjectile(projectile))
			throw new ModelException("Invalid projectile specified.");
		if (hasAProjectile())
			throw new ModelException("Already has a projectile.");
		projectile.setWorld(this);
		this.projectile = projectile;
	}
	
	/**
	 * Checks if the given projectile is valid.
	 * 
	 * @param 	projectile
	 * 			The given projectile.
	 * 
	 * @return	| if (projectile == null)
	 * 			|	return false
	 * 			| if (projectile.isTerminated())
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	public boolean canHaveAsProjectile(Projectile projectile) {
		if (projectile==null)
			return false;
		if (projectile.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Checks if a projectile is not null.
	 * 
	 * @return	| return (!(projectile == null))
	 */
	public boolean hasAProjectile() {
		return(!(projectile==null));
	}
	
	/**
	 * Checks if the current projectile is equal to the given projectile.
	 * 
	 * @param 	projectile
	 * 			The given projectile.
	 * 
	 * @return	| return (this.projectile == projectile)
	 */
	public boolean hasAsProjectile(Projectile projectile) {
		return (this.projectile==projectile);
	}
	
	/**
	 * Removes the current projectile and the weapon associated with it.
	 * 
	 * @post	| new.getProjectile() == null
	 */
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
	/**
	 * Returns the boolean-type terminated.
	 * 
	 * @return	The boolean-type terminated.
	 */
	public boolean isTerminated() {
		return terminated;
	}
	
	/**
	 * Method to terminate the world and all objects associated with it.
	 */
	public void terminate() {
		removeAllWorms();
		removeAllTeams();
		removeAllFood();
		removeProjectile();
		terminated = true;
	}
	
	// }}

}
	