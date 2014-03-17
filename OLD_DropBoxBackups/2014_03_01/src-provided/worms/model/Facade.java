package worms.model;

public class Facade implements IFacade {
	
	/**
	 * Basic constructor for the class Facade.
	 */
	public Facade() {}
	
	/**
	 * Object of the Worm class.
	 */
	Worm wormObject = new Worm();
	
	
	//TODO	Should this class be implemented nominally, defensively or totally? Or should the exceptions be handled in the Worm constructor?
	@Override
	/**
	 * Creates a new worm with given parameters.
	 * @param	x
	 * 			The x-coordinate of the created worm.
	 * @param	y
	 * 			The y-coordinate of the created worm.
	 * @param	direction
	 * 			The direction the created worm is facing.
	 * @param	radius
	 * 			The radius of the created worm.
	 * @param	name
	 * 			The name of the created worm.
	 */
	public Worm createWorm(double x, double y, double direction, double radius, String name) {
		/*System.out.println(x);
		System.out.println(y);
		System.out.println(direction);
		System.out.println(radius);
		System.out.println(name);
		System.out.println(""+"");	*/						// TODO alle waarden worden correct meegegeven, alles loopt fout bij oproepen
		return new Worm( x, y, direction, radius, name );
	}

	
	
	
	@Override
	public boolean canMove(Worm worm, int nbSteps) {
		return wormObject.canMove(nbSteps);
	}

	
	
	
	@Override
	public void move(Worm worm, int nbSteps) {
		wormObject.activeMove(nbSteps);
	}
	
	

	
	@Override
	public boolean canTurn(Worm worm, double angle) {
		return wormObject.canTurn(angle);
	}

	
	
	
	@Override
	public void turn(Worm worm, double angle) {
		//System.out.println(angle);						// TODO deze methode werkt wel bij het veranderen van de hoek
		wormObject.turn(angle);
		
	}

	
	
	
	@Override
	public void jump(Worm worm) {
		wormObject.jump();
		
	}
	
	
	
	
	@Override
	public double getJumpTime(Worm worm) {
		return wormObject.jumpTime();
	}

	
	
	
	@Override
	public double[] getJumpStep(Worm worm, double t) {
		return wormObject.jumpStep(t);
	}

	
	
	
	@Override
	public double getX(Worm worm) {
		//System.out.println(wormObject.getX());			//	TODO x = 0 elke keer bij oproepen. Geeft 10 waarden terug, 1 voor elke worm.
		return wormObject.getX();
	}

	
	
	
	@Override
	public double getY(Worm worm) {
		//System.out.println(wormObject.getY());			// TODO y = 0 elke keer bij oproepen. Geeft 10 waarden terug, 1 voor elke worm.
		return wormObject.getY();
	}
	
	//CONCLUSIE: ALLE 8 WORMEN WORDEN AFGEBEELD OP HET SCHERM, MAAR ALLEMAAL OP DEZELFDE PLAATS (x=0, y=0)

	
	
	
	@Override
	public double getOrientation(Worm worm) {
		//System.out.println(wormObject.getDirection());	// TODO deze methode geeft telkens direction 0 terug
		return wormObject.getDirection();
	}

	

	
	@Override
	public double getRadius(Worm worm) {
		//System.out.println(wormObject.getRadius());		// TODO deze methode geeft telkens radius 0 terug
		return wormObject.getRadius();
	}

	
	
	
	@Override
	public void setRadius(Worm worm, double newRadius) {
		//System.out.println(newRadius);					// TODO newRadius = 0 indien er op "+" of "-" wordt geduwt
		wormObject.setRadius(newRadius);
	}

	
	
	
	@Override
	public double getMinimalRadius(Worm worm) {
		return wormObject.getMinimalRadius();
	}

	
	
	
	@Override
	public int getActionPoints(Worm worm) {
		//System.out.println(wormObject.getActionPoints());		// TODO Geeft elke keer 0 terug
		return wormObject.getActionPoints();
	}

	
	
	
	@Override
	public int getMaxActionPoints(Worm worm) {
		//System.out.println(wormObject.getMaxActionPoints());	// TODO maxActionPoints is 0 elke keer
		return wormObject.getMaxActionPoints();
	}

	
	
	
	@Override
	public String getName(Worm worm) {
		//System.out.println(wormObject.getName());				// TODO Elke naam = "null"
		return wormObject.getName();
	}

	
	
	
	@Override
	public void rename(Worm worm, String newName) {
		wormObject.setName(newName);							// Werkt wel
	}

	
	
	
	@Override
	public double getMass(Worm worm) {
		return wormObject.getMass();
	}
}
