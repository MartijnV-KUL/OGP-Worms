package worms.model.expressions;

import worms.model.Expression;
import worms.model.Food;
import worms.model.Type;
import worms.model.World;
import worms.model.Worm;

public class ExpressionSearchObj extends Expression {
	
	public ExpressionSearchObj(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<?> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if (!(val instanceof Number)) {
			getRootProgram().typeErrorOccurred();
			return new Type<Object>(new Object());
		}
		double x = getStatement().getRootProgram().getWorm().getX();
		double y = getStatement().getRootProgram().getWorm().getY();
		double angle = getStatement().getRootProgram().getWorm().getDirection() + (double)val;
		double resolution = 0.1*getStatement().getRootProgram().getWorm().getRadius();
		
		while ( getStatement().getRootProgram().getWorm().getWorld().isWithinBoundaries(x,y) ) {
			for ( Worm worm : getStatement().getRootProgram().getWorm().getWorld().getAliveWorms() ) {
				if ( World.isOverlapping(worm.getX(), worm.getY(), worm.getRadius(), x, y, 0) )
					return new Type<Worm>(worm);
			}
			for (Food food : getStatement().getRootProgram().getWorm().getWorld().getFood()) {
				if ( World.isOverlapping(food.getX(), food.getY(), Food.getRadius(), x, y, 0) )
					return new Type<Food>(food);
			}
			x = x + resolution*Math.cos(angle);
			y = y + resolution*Math.sin(angle);
		}
		System.out.println("The searchobject commando could not find any objects at a angle of " + (double)val + ".");
		return new Type<Object>(new Object());
		
	}

}
