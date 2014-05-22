package worms.model.expressions;

import worms.model.Entity;
import worms.model.Expression;
import worms.model.Food;
import worms.model.Type;

public class ExpressionIsFood extends Expression {
	
	public ExpressionIsFood(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Boolean> evaluate() {
		boolean outcome = false;
		Object val = getExpressions().get(0).evaluate().getValue();
		if (val instanceof Entity) {
			Object entity = ((Entity) val).getEntity();
			outcome = (entity instanceof Food);
		}
		return new Type<Boolean>(outcome);
	}

}
