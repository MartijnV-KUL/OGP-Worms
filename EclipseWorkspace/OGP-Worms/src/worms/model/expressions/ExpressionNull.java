package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;

public class ExpressionNull extends Expression {
	
	public ExpressionNull(int line, int column) {
		super(line,column);
	}

	@Override
	public Type<Object> evaluate() {
		return new Type<Object>(null);
	}

}
