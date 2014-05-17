package worms.model.expressions;

import worms.model.Expression;
import worms.model.Food;
import worms.model.Type;

public class ExpressionIsFood extends Expression {
	
	public ExpressionIsFood(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Boolean> evaluate() {
		return new Type<Boolean>(getExpressions().get(0).evaluate().getValue() instanceof Food);
	}

}
