package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;
import worms.model.Worm;

public class ExpressionIsWorm extends Expression {
	
	public ExpressionIsWorm(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Boolean> evaluate() {
		return new Type<Boolean>(getExpressions().get(0).evaluate().getValue() instanceof Worm);
	}

}
