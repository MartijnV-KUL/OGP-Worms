package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;

public class ExpressionNegation extends Expression {
	
	public ExpressionNegation(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Boolean> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( !(val instanceof Boolean) ) {
			getRootProgram().typeErrorOccurred();
			return null;
		}
		return new Type<Boolean>(!((boolean)val));
	}

}
