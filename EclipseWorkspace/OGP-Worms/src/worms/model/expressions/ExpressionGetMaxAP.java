package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;
import worms.model.Worm;

public class ExpressionGetMaxAP extends Expression {
	
	public ExpressionGetMaxAP(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Double> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( val instanceof Worm )
			return new Type<Double>((double)((Worm)val).getMaxActionPoints());
		getRootProgram().typeErrorOccurred();
		return null;
	}

}
