package worms.model.expressions;

import worms.model.Expression;
import worms.model.Food;
import worms.model.Type;
import worms.model.Worm;

public class ExpressionGetY extends Expression {
	
	public ExpressionGetY(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Double> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( val instanceof Worm )
			return new Type<Double>(((Worm)val).getY());
		if ( val instanceof Food )
			return new Type<Double>(((Food)val).getY());
		getRootProgram().typeErrorOccurred();
		return null;
	}

}
