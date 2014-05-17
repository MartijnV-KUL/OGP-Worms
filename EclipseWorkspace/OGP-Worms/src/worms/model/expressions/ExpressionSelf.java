package worms.model.expressions;

import worms.model.Entity;
import worms.model.Expression;
import worms.model.Type;

public class ExpressionSelf extends Expression {
	
	public ExpressionSelf(int line, int column) {
		super(line,column);
	}

	@Override
	public Type<Entity> evaluate() {
		return new Type<Entity>(new Entity(getStatement().getRootProgram().getWorm()));
	}

}
