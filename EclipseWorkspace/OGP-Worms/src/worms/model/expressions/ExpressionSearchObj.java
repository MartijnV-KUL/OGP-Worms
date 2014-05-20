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
		double x = getStatement().getRootProgram().getWorm().getX();
		double y = getStatement().getRootProgram().getWorm().getY();
		double angle = getStatement().getRootProgram().getWorm().getDirection() + (double)val;
		double resolution = 0.1*getStatement().getRootProgram().getWorm().getRadius();
		
		if ( val instanceof Worm ) { //TODO static or dynamic type checked? should be dynamic type, because static type is "Object"
			while ( getStatement().getRootProgram().getWorm().getWorld().isWithinBoundaries(x,y) ) {
				for ( Worm worm : getStatement().getRootProgram().getWorm().getWorld().getAliveWorms() ) {
					if ( World.isOverlapping(worm.getX(), worm.getY(), worm.getRadius(), x, y, 0) )
						return new Type<Worm>(worm);
					}
					x = x + resolution*Math.cos(angle);
					y = y + resolution*Math.sin(angle);
				}
			return new Type<Worm>();
		}
		if (val instanceof Food) {
			while ( getStatement().getRootProgram().getWorm().getWorld().isWithinBoundaries(x, y)) {
				for (Food food : getStatement().getRootProgram().getWorm().getWorld().getFood()) {
					if ( World.isOverlapping(food.getX(), food.getY(), Food.getRadius(), x, y, 0) )
						return new Type<Food>(food);
				}
				x = x + resolution * Math.cos(angle);
				y = y + resolution * Math.sin(angle);
			}
		}
		if ((double) val == 0)
			System.out.println("The searchobject commando could not find any objects in a straight line.");
		else
			getRootProgram().typeErrorOccurred();
		return new Type<Object>(null);
		
	}

}
