package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;

public class ExpressionEqualTo extends Expression {
	
	public ExpressionEqualTo(int line, int column, Expression e1, Expression e2) {
		super(line,column,e1,e2);
	}

	@Override
	public Type<Boolean> evaluate() {
		Object val1 = getExpressions().get(0).evaluate().getValue();
		Object val2 = getExpressions().get(1).evaluate().getValue();
		return new Type<Boolean>( val1 == val2 );
	}

}
