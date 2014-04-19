package worms.model;

import java.util.ArrayList;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

//TODO invariants
//TODO association invariants
//TODO "raw" methods for associations
public class World{

// {{ Constructors
	
	/**
	 * Default constructor for the World class. Sets all attributes to dummy values.
	 * 	
	 * @effect	The width of the world is set to 1.
	 * 			| setWidth(1)
	 * @effect	The height of the world is set to 1.
	 * 			| setHeight(1)
	 * @effect	The passable map is set to a 10x10 false array.
	 * 			| setPassableMap(new boolean[10][10])
	 * @effect	The random seed is set to a new random object.
	 * 			| setRandomSeed(new Random())
	 * @post	The maximum amount of teams is set to 10.
	 * 			| new.getMaxTeams()=10;
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
	 * 			The boolean[][] type passable map.
	 * @param 	random
	 * 			A Random object.
	 * 
	 * @effect	The width of the world is set to the input width.
	 * 			| setWidth(width)
	 * @effect	The height of the world is set to the input height.
	 * 			| setHeight(height)
	 * @effect	The passable map is set to the input passable map.
	 * 			| setPassableMap(passableMap)
	 * @effect	The random seed is set to the input random object.
	 * 			| setRandomSeed(random)
	 * @post	The maximum amount of teams is set to 10.
	 * 			| new.getMaxTeams()=10;
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

	// {{ Gravitational Acceleration

	/**
	 * The gravitational acceleration of the world. This attribute is only used for ballistic bodies, but it is an attribute of the world, not the ballistic bodies.
	 */
	private static final double gravitationalAcceleration = 9.80665;

	/**
	 * Basic inspector that returns the gravitational acceleration.
	 * 
	 * @return The gravitational acceleration.
	 */
	@Basic @Immutable
	public static double getGravitationalAcceleration() {
		return gravitationalAcceleration;
	}
	
	// }}
	
	// {{ Width

	/**
	 * The width of the game world in meters.
	 */
	private double width;

	/**
	 * Basic inspector that returns the width of the world.
	 * 
	 * @return	The width of the world.
	 */
	@Basic
	public double getWidth() {
		return this.width;
	}
	
	/**
	 * Method to set the width of the world.
	 * 
	 * @param 	width
	 * 			The new width of the world.
	 * 
	 * @post	Sets the width of the world to the input width.
	 * 			| new.getWidth() == width
	 * 
	 * @throws	ModelException
	 * 			Throws a "ModelException" if the specified width isn't valid.
	 * 			| if (!isValidWidth(width))
	 */
	private void setWidth(double width) throws ModelException {
		if (!isValidWidth(width))
			throw new ModelException("Width is invalid.");
		this.width = width;
	}
	
	/**
	 * Checks if the given width is valid.
	 * 
	 * @param	width
	 * 			The given width.
	 * 
	 * @return	The width is valid if it lies between 0 (inclusive) and the maximal value a double can represent (inclusive).
	 * 			| if (width < 0)
	 * 			|	then return false;
	 * 			| elseif (width > Double.MAX_VALUE)
	 * 			|	then return false
	 * 			| else return true;
	 */
	private boolean isValidWidth(double width) {
		if (width < 0)
			return false;
		if (width > Double.MAX_VALUE)
			return false;
		return true;
	}
	
	// }}
	
	// {{ Height

	/**
	 * The height of the game world in meters.
	 */
	private double height;
	
	/**
	 * Basic inspector that returns the height of the world.
	 * 
	 * @return	The height of the world.
	 */
	@Basic
	public double getHeight() {
		return this.height;
	}
	
	/**
	 * Method to set the height of the world.
	 * 
	 * @param 	height
	 * 			The new height of the world.
	 * 
	 * @post	Sets the height of the game world to the input height.
	 * 			| new.getHeight() == height
	 * @throws	ModelException
	 * 			Throws a ModelException if the specified height is invalid.
	 * 			|if (!isValidHeight(height))
	 */
	private void setHeight(double height) throws ModelException {
		if (!isValidHeight(height))
			throw new ModelException("Height is invalid.");
		this.height = height;
	}
	
	/**
	 * Checks if the given height is valid.
	 * 
	 * @param 	height
	 *			The given height.
	 *
	 * @return	The height is valid if it lies between 0 (inclusive) and the maximal value a double can represent (inclusive).
	 * 			| if (height < 0)
	 * 			|	then return false;
	 * 			| elseif (height > Double.MAX_VALUE)
	 * 			|	then return false
	 * 			| else return true;
	 */
	private boolean isValidHeight(double height) {
		if (height < 0)
			return false;
		if (height > Double.MAX_VALUE)
			return false;
		return true;
	}
	
	// }}

	// {{ PassableMap

	/**
	 * The boolean array passable map.
	 */
	private boolean[][] passableMap;
	
	/**
	 * Basic inspector that returns the passable map.
	 * 
	 * @return	The passable map.
	 */
	@Basic
	public boolean[][] getPassableMap() {
		return passableMap;
	}
	
	/**
	 * Inspector that returns the amont of horizontal pixels of the world.
	 * 
	 * @return	The amount of horizontal pixels of the world.
	 */
	public int getHorizontalPixels() {
		return getPassableMap()[0].length;
	}
	
	/**
	 * Inspector that returns the amount of vertical pixels of the world.
	 * 
	 * @return	The amount of vertical pixels of the world.
	 */
	public int getVerticalPixels() {
		return getPassableMap().length;
	}
	
	/**
	 * Inspector that returns the horizontal resolution the passable map has in meters per pixel.
	 * 
	 * @return	The horizontal resolution in meters per pixel.
	 */
	public double getResolutionX() {
		return ( getWidth() / ((double) getHorizontalPixels()) );
	}

	/**
	 * Inspector that returns the vertical resolution the passable map has in meters per pixel.
	 * 
	 * @return	The vertical resolution in meters per pixel.
	 */
	public double getResolutionY() {
		return ( getHeight() / ((double) getVerticalPixels()) );
	}
	
	/**
	 * Method to set the passable map.
	 * 
	 * @param 	passableMap
	 * 			The new passable map.
	 * 
	 * @post	Sets the passable map to the input passable map.
	 * 			| new.getPassableMap() == passableMap
	 * @throws	ModelException
	 * 			Throws a ModelException if the specified passable map is invalid.
	 * 			| if (!isValidPassableMap(passableMap))
	 */
	private void setPassableMap(boolean[][] passableMap) throws ModelException {
		if (!isValidPassableMap(passableMap))
			throw new ModelException("Invalid passable map specified.");
		this.passableMap = passableMap;
	}
	
	/**
	 * Checks if the passable map is valid.
	 * 
	 * @param 	passableMap
	 * 			The passable map to check.
	 * 
	 * @return	The passable map is valid if it isn't null.
	 * 			| return (passableMap != null)
	 */
	private boolean isValidPassableMap(boolean[][] passableMap) {
		return (passableMap!=null);
	}
	
	// }}
	
// }}
	
// {{ Map-related methods
	
	/**
	 * Converts a position expressed in meters to its corresponding pixel.
	 * The x- and y-coordinates have their origin in the lower left corner of the game world.
	 * The pixels have their origin in the upper left corner of the game world.
	 * This method converts the near-continuous x- and y-coordinates to discrete pixel locations. 
	 * 
	 * @param 	x
	 * 			The x-coordinate.
	 * @param	y
	 * 			The y-coordinate
	 * 
	 * @return	The pixel the specified x- and y-coordinates are in.
	 * 			| return {                           (int) round(x*((double)getHorizontalPixels()-1)/getWidth()), 
	 * 					   (getVerticalPixels()-1) - (int) round(y*((double)getVerticalPixels()-1)/geHeight()) }
	 * @throws	ModelException
	 * 			Throws a ModelException if the specified x- and y-coordinates are outside the boundaries of the game world.
	 * 			| if (!isWithinBoundaries(x,y))
	 */
	private int[] positionToPixel(double x, double y) throws ModelException { //TODO update documentation
		if (!isWithinBoundaries(x,y))
			throw new ModelException("Specified x & y not within boundaries!");
		int pixelX =                           (int) Math.round( x * ((double) getHorizontalPixels()-1 ) / getWidth() );
		int pixelY = (getVerticalPixels()-1) - (int) Math.round( y * ((double) getVerticalPixels()  -1 ) / getHeight() );
		int[] pixelPosition = new int[2];
		pixelPosition[0] = pixelX;
		pixelPosition[1] = pixelY;
		return pixelPosition;
	}
	
	/**
	 * Checks if a position is within the boundaries of the map.
	 * 
	 * @param 	x
	 * 			The x-coordinate.
	 * @param 	y
	 * 			The y-coordinate.
	 * 
	 * @return	Return true if the coordinates lie within the boundaries of the map.
	 * 			| if (x<0)
	 * 			| 	return false;
	 * 			| elseif (x>getWidth))
	 * 			| 	return false;
	 * 			| elseif (y<0)
	 * 			| 	return false;
	 * 			| elseif (y>getHeight())
	 * 			| 	return false;
	 * 			| else
	 * 			| 	return true;
	 */
	public boolean isWithinBoundaries(double x, double y) {
		if (x< (double) 0)
			return false;
		if (x>getWidth())
			return false;
		if (y< (double) 0)
			return false;
		if (y>getHeight())
			return false;
		return true;
	}
	
	/**
	 * Checks if a pixel is passable.
	 * 
	 * @param 	pixelX
	 * 			The x-location of a pixel.
	 * @param 	pixelY
	 * 			The y-location of a pixel.
	 * 
	 * @return	Return true if the pixel is passable.
	 * 			| return (getPassableMap()[pixelY][pixelX]);
	 */
	private boolean isPassable(int pixelX, int pixelY) {
		return getPassableMap()[pixelY][pixelX];
	}
	
	/**
	 * Checks if a position in x- and y-coordinates is passable.
	 * 
	 * @param 	x
	 * 			The x-coordinate in meters.
	 * @param 	y
	 * 			The y-coordinate in meters.
	 * 
	 * @return	Rteurns true if the x- and y-coordinates lie within the boundaries of the map and the corresponding pixel is passable.
	 * 			| if (!isWithinBoundaries(x,y))
	 * 			|	return false;
	 * 			| else {
	 * 			|	int[] pixelXY = positionToPixel(x, y);
	 * 			|	return isPassable(pixelXY[0], pixelXY[1]);
				| }
	 */
	private boolean isPassable(double x, double y) {
		if (!isWithinBoundaries(x,y))
			return false;
		int[] pixelXY = positionToPixel(x,y);
		return isPassable(pixelXY[0],pixelXY[1]);
	}
	
	/**
	 * Checks if a circular area around the given location is passable.
	 * The area that is checked has its origin in the specified x- and y-coordinates and has a radius of one tenth of the specified radius.
	 * First, it is calculated what the size of the area to be checked is in pixels.
	 * Then, the method loops over every pixel and chacks the passability of that pixels if that pixel lies within the search area.
	 * 
	 * @param 	x
	 * 			The x-coordinate.
	 * @param 	y
	 * 			The y-coordinate.
	 * @param 	radius
	 * 			The radius.
	 * 
	 * @return	Returns false if the specified location itself isn't passable.
	 * 			| if (!isPassable(x,y))
	 * 			| 	return false;
	 * 			Loops over every pixel within the search area and return false if one of those pixels is impassable.
	 * 			| searchRadius = 0.1*radius;
	 * 			| pixelsX = (int) ceil(searchRadius = getResolutionX());
	 * 			| pixelsY = (int) ceil(searchRadius = getResolutionY());
	 * 			| double testX = 0;
	 * 			| double testY = 0;
	 * 			| for (int i=0; i<pixelsX; i++) {
	 * 			| 	for (int j=0; j<pixelsY; j++) {
	 * 			| 		testX = i*getResolutionX();
	 * 			| 		testY = j*getResolutionY();
	 * 			| 		if (Math.sqrt(Math.pow(testX,2)+Math.pow(testY,2))<=searchRadius) {
	 * 			| 			if (!isPassable(x+testX,y+testY))
	 * 			| 				return false;
	 * 			| 			if (!isPassable(x-testX,y+testY))
	 * 			| 				return false;
	 * 			| 			if (!isPassable(x+testX,y-testY))
	 * 			| 				return false;
	 * 			| 			if (!isPassable(x-testX,y-testY))
	 * 			| 				return false;
	 * 			| 		}
	 * 			| 	}
	 * 			| }
	 */
	public boolean isPassable(double x, double y, double radius) { //TODO The formal documentation seems quite extensive. How can this be written shorter? 
		if (!isPassable(x, y))
			return false;
		
		double searchRadius = 0.1*radius;
		
		int pixelsX = (int) Math.ceil( searchRadius / getResolutionX() );
		int pixelsY = (int) Math.ceil( searchRadius / getResolutionY() );

		double testX = 0;
		double testY = 0;
		for (int i=0; i<pixelsX; i++) {
			for (int j=0; j<pixelsY; j++) {
				testX = i*getResolutionX();
				testY = j*getResolutionY();
				if (Math.sqrt(Math.pow(testX,2)+Math.pow(testY,2))<=searchRadius) {
					if (!isPassable(x+testX,y+testY))
						return false;
					if (!isPassable(x-testX,y+testY))
						return false;
					if (!isPassable(x+testX,y-testY))
						return false;
					if (!isPassable(x-testX,y-testY))
						return false;
				}
			}
		}
		return true;
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
	public boolean isAdjacent(double x, double y, double radius) {//TODO update documentation
		//note: a different technique is suggested in the assignment. This implementation results in a more homogeneous distribution of objects and objects can get into the nooks and crannies of a map, somthing that is impossible with the suggested implementation..
		if (!isPassable(x,y,radius))
			return false;
		
		double testAngleInterval = 2*Math.PI/41;
		// Loop over the entire resolution
		for (double testAngle=0; testAngle<2*Math.PI; testAngle+=testAngleInterval) {
			// Calculate the x- and y-offsets at the current angle
			double deltaX = (0.1*radius+getResolutionX()) * Math.cos(testAngle);
			double deltaY = (0.1*radius+getResolutionY()) * Math.sin(testAngle);
			
			if (isWithinBoundaries(x+deltaX, y+deltaY)) {
				if (!isPassable(x+deltaX,y+deltaY))
					return true;
			}
		}
		return false;
	}
	
	public boolean isOnSolidGround(double x, double y, double radius) { //TODO documentation
		if (!isWithinBoundaries(x, y-0.1*radius-getResolutionY()))
			return false;
		if (isPassable(x,y-0.1*radius-getResolutionY()))
			return false;
		return true;
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
		double[] output = new double[2];
		output[0] = getWidth()  * getRandomSeed().nextDouble();
		output[1] = getHeight() * getRandomSeed().nextDouble();
		
		
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
		} while( !isAdjacent(randX, randY, Food.getRadius()) );
		//} while( !isPassable(randX,randY,Food.getRadius()) || !isOnSolidGround(randX,randY,Food.getRadius()) ); //TODO more sensible implementation for future iterations.
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
		double newX = 0;
		double newY = 0;
		Worm newWorm;
		do {
			double[] randomXY = getRandomXY();
			newX = randomXY[0];
			newY = randomXY[1];
			newWorm = new Worm(newX,newY,0,"JohnDoe");
		} while( !isAdjacent(newWorm.getX(),newWorm.getY(),newWorm.getRadius()) );
		//} while( !isPassable(newWorm.getX(),newWorm.getY(),newWorm.getRadius()) || !isOnSolidGround(newWorm.getX(),newWorm.getY(),newWorm.getRadius()) );//TODO more sensible implementation for future iterations.
		addWorm(newWorm);
		if (getTeams().size()>0)
			getTeams().get(getRandomSeed().nextInt(getTeams().size())).addWorm(newWorm);
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
	 * Checks whether or not the game is finished. The game is finished unless two or more worms are still alive and if some worms belong to different teams or if at least one of the alive worms does not belong to a team.
	 * 
	 * @return
	 */
	public boolean isGameFinished() { //TODO update documentation
		for ( Worm aliveWorm : getAliveWorms() ) {
			if (aliveWorm!=getAliveWorms().get(0)) { // There is a second worm alive.
				if (aliveWorm.getTeam()!=getAliveWorms().get(0).getTeam()) // The second worm belongs to a different team.
					return false;
				if (aliveWorm.getTeam()==null) // The second worm does not belong to a team.
					return false;
			}
		}
		return true; // Default
	}
	
	/**
	 * Method to start the game.
	 */
	public void startGame() {
		//TODO should worms still be added?
		if (getWorms().size()>0)
			setActiveWorm(getWorms().get(0));
	}
	
	/**
	 * Method to initiate the next turn (select the next worm).
	 */
	public void nextTurn() {
		int index = getWorms().indexOf(getActiveWorm());
		if (!getActiveWorm().isAlive()){
			getActiveWorm().terminate();
		}
		else {
			if (index==(getWorms().size()-1))
				index = 0;
			else
				index += 1;
		}
		setActiveWorm(getWorms().get(index));
	}
	
	/**
	 * Method to return the name of the winning worm.
	 * 
	 * @return	The name of the winning worm.
	 */
	public String getWinner() {
		//return winningName;
		//TODO if gamefinished, return getalive(0).getName()
		if (isGameFinished()) {
			ArrayList<Worm> aliveWorms = getAliveWorms();
			if (aliveWorms.size()==0)
				return "";//TODO all worms died (suicide action or something, may be possible with later iterations, not right now)
			else if (aliveWorms.size()==1)
				return aliveWorms.get(0).getName();
			else
				return aliveWorms.get(0).getName(); //TODO a team won, not a single worm.
		}
		return ""; //TODO game isn't finished yet.
	}
	
	public ArrayList<Worm> getAliveWorms() {
		ArrayList<Worm> aliveWorms = new ArrayList<Worm>();
		for (Worm testWorm : getWorms()) {
			if (testWorm.isAlive())
				aliveWorms.add(testWorm);
		}
		return aliveWorms;
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
	
