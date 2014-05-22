package worms.model.expressions;

import worms.model.Entity;
import worms.model.Expression;
import worms.model.Food;
import worms.model.Type;
import worms.model.Worm;

public class ExpressionGetX extends Expression {
	
	public ExpressionGetX(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Double> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( val instanceof Entity ) {
			Object entity = ((Entity) val).getEntity();
			if ( entity instanceof Worm )
				return new Type<Double>(((Worm)entity).getX());
			if ( entity instanceof Food )
				return new Type<Double>(((Food)entity).getX());
		}
		getRootProgram().typeErrorOccurred();
		return null;
		
		
		
		
		
	}

}
