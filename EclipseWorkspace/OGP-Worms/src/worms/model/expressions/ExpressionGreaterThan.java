package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;

public class ExpressionGreaterThan extends Expression {
	
	public ExpressionGreaterThan(int line, int column, Expression e1, Expression e2) {
		super(line,column,e1,e2);
	}

	@Override
	public Type<Boolean> evaluate() {
		Object val1 = getExpressions().get(0).evaluate().getValue();
		Object val2 = getExpressions().get(1).evaluate().getValue();
		if ( (val1 instanceof Number) && (val2 instanceof Number) )
			return new Type<Boolean>( (double)val1 > (double)val2 );
		getRootProgram().typeErrorOccurred();
		return null;
	}

}
