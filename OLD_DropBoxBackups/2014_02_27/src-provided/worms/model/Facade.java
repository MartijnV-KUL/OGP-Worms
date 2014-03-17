package worms.model;

public class Facade implements IFacade {
	
//	should not be in the facade class, but rather in the Worm class.
//	private Worm worm;
//	private String name;
//	private double x;
//	private double y;
//	private double direction;
//	private double radius;
//	private double minimalRadius = 0.25;
	
	/**
	 * Basic constructor for the class Facade.
	 */
	public Facade() {}
	
	
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
		return new Worm( x, y, direction, radius, name );
		
		
		/**
		 * @note	In de klasse "GameState" staat methode "createRandomWorms". Alles wordt random gegenereerd en naar IFacade gestuurd,
		 * 			vervolgens naar deze methode. Hier moet dus een worm gecreërd worden en teruggegeven, wordt dan in "createRandomWorms" toegevoegd
		 * 			aan de lijst van beschikbare worms.		
		 */
//		this.x = x;
//		this.y = y;
//		this.name = name;
//		this.direction = direction;
//		this.radius = radius;
//		
//		return worm; /** @note WELK SOORT OBJECT IS WORM? => HOE CIRKEL IN LIJST STOPPEN???*/
	}

	
	
	//TODO
	@Override
	public boolean canMove(Worm worm, int nbSteps) {
		return false;
	}

	
	
	//TODO
	@Override
	public void move(Worm worm, int nbSteps) {
	}
	
	

	//TODO
	@Override
	public boolean canTurn(Worm worm, double angle) {
		return false;
	}

	
	
	//TODO
	@Override
	public void turn(Worm worm, double angle) {
		
	}

	
	
	//TODO
	@Override
	public void jump(Worm worm) {
		
	}
	
	
	
	//TODO
	@Override
	public double getJumpTime(Worm worm) {
		return 0;
	}

	
	
	//TODO
	@Override
	public double[] getJumpStep(Worm worm, double t) {
		return null;
	}

	
	
	//TODO
	@Override
	public double getX(Worm worm) {
		return x;
	}

	
	
	//TODO
	@Override
	public double getY(Worm worm) {
		return y;
	}

	
	
	//TODO
	@Override
	public double getOrientation(Worm worm) {
		return direction;
	}

	
	
	//TODO
	@Override
	public double getRadius(Worm worm) {
		return radius;
	}

	
	
	//TODO
	@Override
	public void setRadius(Worm worm, double newRadius) {
		this.radius = newRadius;
	}

	
	
	//TODO
	@Override
	public double getMinimalRadius(Worm worm) {
		return minimalRadius;
	}

	
	
	//TODO
	@Override
	public int getActionPoints(Worm worm) {
		return 0;
	}

	
	
	//TODO
	@Override
	public int getMaxActionPoints(Worm worm) {
		return 0;
	}

	
	
	//TODO
	@Override
	public String getName(Worm worm) {
		return name;
	}

	
	
	//TODO
	@Override
	public void rename(Worm worm, String newName) {
		this.name = newName;
	}

	
	
	//TODO
	@Override
	public double getMass(Worm worm) {
		return 0;
	}
}
