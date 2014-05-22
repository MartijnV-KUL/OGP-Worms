package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;
import worms.model.Worm;

public class ExpressionGetAP extends Expression {
	
	public ExpressionGetAP(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Double> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( val instanceof Worm )
			return new Type<Double>((double)((Worm)val).getActionPoints());
		getRootProgram().typeErrorOccurred();
		return null;
	}

}
