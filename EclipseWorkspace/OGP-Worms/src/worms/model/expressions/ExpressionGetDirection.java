package worms.model.expressions;

import worms.model.Entity;
import worms.model.Expression;
import worms.model.Type;
import worms.model.Worm;

public class ExpressionGetDirection extends Expression {
	
	public ExpressionGetDirection(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Double> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( val instanceof Entity ) {
			Object entity = ((Entity) val).getEntity();
			if ( entity instanceof Worm )
				return new Type<Double>(((Worm)entity).getDirection());
		}
		getRootProgram().typeErrorOccurred();
		return null;
	}

}
