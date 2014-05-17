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
		if ( val instanceof Worm ) //TODO static or dynamic type checked? should be dynamic type, because static type is "Object"
			return new Type<Double>((double)((Worm)val).getActionPoints());
		getStatement().getRootProgram().typeErrorOccurred();
		return null;
	}

}
