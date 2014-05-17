package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;

public class ExpressionSquareRoot extends Expression {
	
	public ExpressionSquareRoot(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Double> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( !(val instanceof Number) ) { //TODO static or dynamic type checked? should be dynamic type, because static type is "Object"
			getStatement().getRootProgram().typeErrorOccurred();
			return null;
		}
		return new Type<Double>(Math.sqrt((double)(val)));
	}

}
