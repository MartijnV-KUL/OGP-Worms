package worms.model;

public class ExpressionSingular extends Expression {

	private final Expression e;
	
	protected ExpressionSingular(int line, int column, Expression.Types type, Expression e) {
		super(line,column,type);
		this.e = e;
	}

	@SuppressWarnings("unchecked")
	public Type<?> evaluate() {
		if (getType()==Expression.Types.SQUAREROOT) {
			if ( !(e.evaluate().getValue() instanceof Number) )
				getStatement().getProgram().typeErrorOccurred();
			return new Type<Double>(Math.sqrt((double)((Type<Double>)e.evaluate()).getValue()));
		}
		if (getType()==Expression.Types.SINE) {
			if ( !(e.evaluate().getValue() instanceof Number) )
				getStatement().getProgram().typeErrorOccurred();
			return new Type<Double>(Math.sin((double)((Type<Double>)e.evaluate()).getValue()));
		}
		if (getType()==Expression.Types.COSINE) {
			if ( !(e.evaluate().getValue() instanceof Number) )
				getStatement().getProgram().typeErrorOccurred();
			return new Type<Double>(Math.cos((double)((Type<Double>)e.evaluate()).getValue()));
		}
		if (getType()==Expression.Types.NEGATION) {
			if ( !(e.evaluate().getValue() instanceof Boolean) )
				getStatement().getProgram().typeErrorOccurred();
			return new Type<Boolean>(!((boolean)((Type<Boolean>)e.evaluate()).getValue()));
		}
		if (getType()==Expression.Types.GETX) {
			if ( e.evaluate().getValue() instanceof Worm )
				return new Type<Double>(((Worm)e.evaluate().getValue()).getX());
			if ( e.evaluate().getValue() instanceof Food )
				return new Type<Double>(((Food)e.evaluate().getValue()).getX());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.GETY) {
			if ( e.evaluate().getValue() instanceof Worm )
				return new Type<Double>(((Worm)e.evaluate().getValue()).getY());
			if ( e.evaluate().getValue() instanceof Food )
				return new Type<Double>(((Food)e.evaluate().getValue()).getY());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.GETRADIUS) {
			if ( e.evaluate().getValue() instanceof Worm )
				return new Type<Double>(((Worm)e.evaluate().getValue()).getRadius());
			if ( e.evaluate().getValue() instanceof Food )
				return new Type<Double>(Food.getRadius());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.GETDIR) {
			if ( e.evaluate().getValue() instanceof Worm )
				return new Type<Double>(((Worm)e.evaluate().getValue()).getDirection());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.GETAP) {
			if ( e.evaluate().getValue() instanceof Worm )
				return new Type<Double>((double) ((Worm)e.evaluate().getValue()).getActionPoints());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.GETMAXAP) {
			if ( e.evaluate().getValue() instanceof Worm )
				return new Type<Double>((double) ((Worm)e.evaluate().getValue()).getMaxActionPoints());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.GETHP) {
			if ( e.evaluate().getValue() instanceof Worm )
				return new Type<Double>((double) ((Worm)e.evaluate().getValue()).getHitPoints());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.GETMAXHP) {
			if ( e.evaluate().getValue() instanceof Worm )
				return new Type<Double>((double) ((Worm)e.evaluate().getValue()).getMaxHitPoints());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.SAMETEAM) {
			if ( e.evaluate().getValue() instanceof Worm )
				return new Type<Boolean>(getStatement().getProgram().getWorm().getTeam()==((Worm)e.evaluate().getValue()).getTeam());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.SEARCHOBJ) {
			double x = getStatement().getProgram().getWorm().getX();
			double y = getStatement().getProgram().getWorm().getY();
			double angle = getStatement().getProgram().getWorm().getDirection() + ((double)((Type<Double>)e.evaluate()).getValue());
			double resolution = 0.1*getStatement().getProgram().getWorm().getRadius();
			while ( getStatement().getProgram().getWorm().getWorld().isWithinBoundaries(x,y) ) {
				for ( Worm worm : getStatement().getProgram().getWorm().getWorld().getAliveWorms() ) {
					if ( World.isOverlapping(worm.getX(), worm.getY(), worm.getRadius(), x, y, 0) )
						return new Type<Entity>(new Entity(worm));
					x = x + resolution*Math.cos(angle);
					y = y + resolution*Math.sin(angle);
				}
			}
			return new Type<Entity>();
		}
		if (getType()==Expression.Types.ISWORM)
			return new Type<Boolean>(e.evaluate().getValue() instanceof Worm);
		if (getType()==Expression.Types.ISFOOD)
			return new Type<Boolean>(e.evaluate().getValue() instanceof Food);

		getStatement().getProgram().typeErrorOccurred();
		return null;
	}
	
}
