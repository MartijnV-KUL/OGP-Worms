package worms.model.expressions;

import worms.model.Entity;
import worms.model.Expression;
import worms.model.Type;
import worms.model.Worm;

public class ExpressionIsWorm extends Expression {
	
	public ExpressionIsWorm(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Boolean> evaluate() {
		boolean outcome = false;
		Object val = getExpressions().get(0).evaluate().getValue();
		if (val instanceof Entity) {
			Object entity = ((Entity) val).getEntity();
			outcome = (entity instanceof Worm);
		}
		return new Type<Boolean>(outcome);
	}

}
