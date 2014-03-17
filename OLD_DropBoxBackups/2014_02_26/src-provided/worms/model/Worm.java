package worms.model;

public class Worm {
	
	private double mass;
	private double radius;
	private double minimalRadius = 0.25;
	private double wormDensity = 1062;
	private double distanceOfStep = radius;
	
	
	
	
	/**
	 * Inspects the name of the selected worm.
	 * @param	name
	 * 			The name of the worm.
	 * @pre		The initial name contains no numbers or special characters.
	 * @return	The new name contains no numbers or special characters, starts with an upper case and contains at least two characters.
	 * 			| if ( !name.matches("[a-zA-Z\"\' ]*") && !character.isUpperCase(name.charAt(0) && name.length() < 2 ) return true
	 * @throws	IllegalArgumentException
	 * 			The new name can not contain numbers.
	 * 			| if (name.matches(".*[0-9].*")
	 * 			|		throw new IllegalArgumentException("The name can not contain numbers")
	 * @throws	IllegalArgumentException
	 * 			The new name must begin with an upper case.
	 * 			| if (!character.isUpperCase(name.charAt(0)))
	 * 			|		throw new IllegalArgumentException("The name must begin with an upper case.")
	 * @throws	IllegalArgumentException
	 * 			The new name must be at least two characters long.
	 * 			| if(name.length() < 2)
	 * 			|		throw new IllegalArgumentException("The name must contain at least two characters.)
	 */
	public boolean isValidName(String name) {
		if (!name.matches("[a-zA-Z\"\' ]*")) {
			throw new IllegalArgumentException("The name can not contain numbers or special characters.");
		}
		if (!Character.isUpperCase(name.charAt(0))) {
			throw new IllegalArgumentException("The name must start with an upper case.");
		}
		if (name.length() < 2) {
			throw new IllegalArgumentException("The name must contain at least two characters.");
		}
		else return true;
	}
	
	
	
	//TODO
	/**
	 * Inspects the position of the selected worm.
	 * @param 	x
	 * 			The x-coördinate of the worm.
	 * @param 	y
	 * 			The y-coördinate of the worm.
	 * @return	The coördinates must be valid.
	 */
	public boolean isValidPosition(double x, double y) {
		/*position has to lay within playfield boundaries
		 * => What are the boundaries?
		 * => has to give out of bounds exception when false */
		return true;
	}
	
	
	
	
	/**
	 * Inspects the direction of the selected worm.
	 * @param	direction
	 * 			The direction of the worm, given as an angle in radians.
	 * @return	The direction must lay between 0 and 2*PI.
	 * 			| if (direction >=0 && direction <= 2*Math.PI) return true
	 * @throws	IllegalArgumentException
	 * 			The direction is not valid.
	 * 			| if (direction < 0 || direction > 2*Math.PI)
	 * 			|		throw new IllegalArgumentException("The direction has to lay between 0 and 2*Pi)
	 */
	public boolean isValidDirection(double direction) {
		if (direction <= 0 && direction >= 2*Math.PI) {
			throw new IllegalArgumentException("The direction has to lay between 0 and 2*Pi");
		}
		else
			return true;
	}
	
	
	

	/**
	 * Inspects the radius of the selected worm.
	 * @param	radius
	 * 			The radius of the worm.
	 * @return	The radius must be at least 0.25 m.
	 * 			| if (radius >= 0.25) return true
	 * @throws	IllegalArgumentException
	 * 			The radius is less than 0.25 m.
	 * 			| if (radius < minimalRadius)
	 * 			|		throw new IllegalArgumentException("The radius must be at least 0.25 m.")
	 */
	public boolean isValidRadius(double radius) {
		if (radius < minimalRadius) {
			throw new IllegalArgumentException("The radius must be at least 0.25 m.");
		}
		else {
			this.radius = radius;
			return true;
		}
	}
	
	
	

	/**
	 * Inspects the mass of the selected worm.
	 * @param	mass
	 * 			The mass of the worm.
	 * @return	The mass must be valid, so it has to be positive.
	 * 			| if (mass > 0) return true
	 * @throws	IllegalArgumentException
	 * 			| if (mass <= 0)
	 * 			|		throw new IllegalArgumentException("The mass of the worm has to be positive.");
	 */
	public boolean isValidMass(double mass) {
		if (mass <= 0) {
			throw new IllegalArgumentException("The mass of the worm has to be positive.");
		}
		else return true;
	}
	
	
	
	//TODO
	/**
	 * Inspects the amounts of action points of the selected worm.
	 * @param	actionPoints
	 * 			The number of actionpoints of the worm.
	 * @return	The number of actionpoints must be valid.
	 */
	public boolean isValidActionPoints(int actionPoints) {
		return true;
	}
	
	
	
	//TODO	DEFENSIEVE UITWERKING
	/**
	 * Moves the selected worm to the given coördinates.
	 * @param	x
	 * 			The x-coördinate.
	 * @param 	y
	 * 			The y-coördinate.
	 */
	public void move(double x, double y, int nbSteps, int nbActionPoints) {
		
	}
	
	
	
	//TODO	NOMINALE UITWERKING
	/**
	 * Turns the selected worm to the given direction.
	 * @param	direction
	 * 			The direction of the worm.
	 */
	public void turn(double direction) {
		
	}
	
	
	
	//TODO
	/**
	 * Makes the selected worm jump.
	 * @param	x
	 * 			The x-coördinate of the worm.
	 * @param 	y
	 * 			The y-coördinate of the worm.
	 * @param 	direction
	 * 			The direction of the worm.
	 * @param 	actionPoints
	 * 			The current number of actionpoints of the worm.
	 * @post	
	 */
	public void jump(double x, double y, double direction, int actionPoints) {
		
	}
	
	
	
	//TODO
	/**
	 * Calculates the time needed to perform the jump of the selected worm.
	 * @param	time
	 * @return	The time needed to perform the jump.
	 */
	public double jumpTime(double time) {
		return time;
		/* Method is incorrect => first implement methods to move, jump and turn. */
	}
	
	
	
	
	/**
	 * Calculates the mass of the selected worm.
	 * @param	radius
	 * 			The radius of the selected worm.
	 * @return	The new mass of the selected worm.
	 * 			| new.mass = wormDensity * ((4/3) * Math.PI * Math.pow(radius, 3))
	 */
	public double calculateMass(double radius) {
		this.radius = radius;
		this.mass = wormDensity * ((4/3) * Math.PI * Math.pow(radius, 3));
		return mass;
	}
	
	

}
