package worms.model;

public class ExpressionSingular extends Expression {

	private final Type<?> t;
	
	protected ExpressionSingular(Expression.Types type, Type<?> t) {
		super(type);
		this.t = t;
	}
	

	public Type<?> evaluate() {
		if (getType()==Expression.Types.VARIABLE)
			return t;
		if (getType()==Expression.Types.DOUBLE)
			return t;
		if (getType()==Expression.Types.TRUE)
			return new Type<Boolean>(true);
		if (getType()==Expression.Types.FALSE)
			return new Type<Boolean>(false);
		if (getType()==Expression.Types.NULL)
			return new Type<Entity>();
		if (getType()==Expression.Types.SELF)
			return t; //TODO this brings a reference to the actual object out. is that desired?
		if (getType()==Expression.Types.SQUAREROOT) {
			if ( !(t.getValue() instanceof Number) ) //TODO instance of "Double" or "Number"?
				getStatement().getProgram().typeErrorOccurred();
			return new Type<Double>(Math.sqrt((double)t.getValue()));
		}
		if (getType()==Expression.Types.SINE) {
			if ( !(t.getValue() instanceof Number) ) //TODO instance of "Double" or "Number"?
				getStatement().getProgram().typeErrorOccurred();
			return new Type<Double>(Math.sin((double)t.getValue()));
		}
		if (getType()==Expression.Types.COSINE) {
			if ( !(t.getValue() instanceof Number) ) //TODO instance of "Double" or "Number"?
				getStatement().getProgram().typeErrorOccurred();
			return new Type<Double>(Math.cos((double)t.getValue()));
		}
		if (getType()==Expression.Types.NEGATION) {
			if ( !(t.getValue() instanceof Boolean) )
				getStatement().getProgram().typeErrorOccurred();
			return new Type<Boolean>(~((boolean)t.getValue()));
		}
		if (getType()==Expression.Types.GETX) {
			if ( t.getValue() instanceof Worm )
				return new Type<Double>(((Worm)t.getValue()).getX());
			if ( t.getValue() instanceof Food )
				return new Type<Double>(((Food)t.getValue()).getX());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.GETY) {
			if ( t.getValue() instanceof Worm )
				return new Type<Double>(((Worm)t.getValue()).getY());
			if ( t.getValue() instanceof Food )
				return new Type<Double>(((Food)t.getValue()).getY());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.GETRADIUS) {
			if ( t.getValue() instanceof Worm )
				return new Type<Double>(((Worm)t.getValue()).getRadius());
			if ( t.getValue() instanceof Food )
				return new Type<Double>(Food.getRadius());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.GETDIR) {
			if ( t.getValue() instanceof Worm )
				return new Type<Double>(((Worm)t.getValue()).getDirection());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.GETAP) {
			if ( t.getValue() instanceof Worm )
				return new Type<Double>((double) ((Worm)t.getValue()).getActionPoints());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.GETMAXAP) {
			if ( t.getValue() instanceof Worm )
				return new Type<Double>((double) ((Worm)t.getValue()).getMaxActionPoints());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.SAMETEAM) {
			if ( t.getValue() instanceof Worm )
				return new Type<Boolean>(getStatement().getProgram().getWorm().getTeam()==((Worm)t.getValue()).getTeam());
			getStatement().getProgram().typeErrorOccurred();
		}
		if (getType()==Expression.Types.SEARCHOBJ) {
			double x = getStatement().getProgram().getWorm().getX();
			double y = getStatement().getProgram().getWorm().getY();
			double angle = getStatement().getProgram().getWorm().getDirection() + ((double)t.getValue());
			double resolution = 0.1*getStatement().getProgram().getWorm().getRadius();
			while ( getStatement().getProgram().getWorm().getWorld().isWithinBoundaries(x,y) ) {
				for ( Worm worm : getStatement().getProgram().getWorm().getWorld().getAliveWorms() ) {
					if ( World.isOverlapping(worm.getX(), worm.getY(), worm.getRadius(), x, y, 0) )
						return new Type<Entity>(new Entity(worm));
				}
			}
			return new Type<Entity>();
		}
		if (getType()==Expression.Types.ISWORM)
			return new Type<Boolean>(t.getValue() instanceof Worm);
		if (getType()==Expression.Types.ISFOOD)
			return new Type<Boolean>(t.getValue() instanceof Food);

		getStatement().getProgram().typeErrorOccurred();
	}
	
}
