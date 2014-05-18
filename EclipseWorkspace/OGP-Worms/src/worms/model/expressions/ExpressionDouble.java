package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;

public class ExpressionDouble extends Expression {
	
	private final double d;
	
	public ExpressionDouble(int line, int column, double d) {
		super(line,column);
		this.d = d;
	}
	
	@Override
	public Type<Double> evaluate() {
		return new Type<Double>(d);
	}

}
