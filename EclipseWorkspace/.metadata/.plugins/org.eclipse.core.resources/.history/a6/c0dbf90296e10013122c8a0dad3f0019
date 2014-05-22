package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;
import worms.model.Worm;

public class ExpressionSelf extends Expression {
	
	public ExpressionSelf(int line, int column) {
		super(line,column);
	}

	@Override
	public Type<Worm> evaluate() {
		return new Type<Worm>(getRootProgram().getWorm());
	}

}
