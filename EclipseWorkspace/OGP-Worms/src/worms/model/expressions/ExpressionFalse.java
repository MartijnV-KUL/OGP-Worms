package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;

public class ExpressionFalse extends Expression {
	
	public ExpressionFalse(int line, int column) {
		super(line,column);
	}

	@Override
	public Type<Boolean> evaluate() {
		return new Type<Boolean>(false);
	}

}
