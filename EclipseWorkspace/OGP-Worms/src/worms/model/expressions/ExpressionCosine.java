package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;

public class ExpressionCosine extends Expression {
	
	public ExpressionCosine(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Double> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( !(val instanceof Number) ) {
			getRootProgram().typeErrorOccurred();
			return null;
		}
		return new Type<Double>(Math.cos((double)(val)));
	}

}
