package worms.model.expressions;

import worms.model.Entity;
import worms.model.Expression;
import worms.model.Type;
import worms.model.World;
import worms.model.Worm;

public class ExpressionSearchObj extends Expression {
	
	public ExpressionSearchObj(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Entity> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( val instanceof Worm ) { //TODO static or dynamic type checked? should be dynamic type, because static type is "Object"
			double x = getStatement().getRootProgram().getWorm().getX();
			double y = getStatement().getRootProgram().getWorm().getY();
			double angle = getStatement().getRootProgram().getWorm().getDirection() + (double)val;
			double resolution = 0.1*getStatement().getRootProgram().getWorm().getRadius();
			while ( getStatement().getRootProgram().getWorm().getWorld().isWithinBoundaries(x,y) ) {
				for ( Worm worm : getStatement().getRootProgram().getWorm().getWorld().getAliveWorms() ) {
					if ( World.isOverlapping(worm.getX(), worm.getY(), worm.getRadius(), x, y, 0) )
						return new Type<Entity>(new Entity(worm));
					x = x + resolution*Math.cos(angle);
					y = y + resolution*Math.sin(angle);
				}
			}
			return new Type<Entity>();
		}
		getStatement().getRootProgram().typeErrorOccurred();
		return null;
	}

}
