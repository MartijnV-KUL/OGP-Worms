package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;

public class ExpressionTrue extends Expression {
	
	public ExpressionTrue(int line, int column) {
		super(line,column);
	}

	@Override
	public Type<Boolean> evaluate() {
		return new Type<Boolean>(true);
	}

}
