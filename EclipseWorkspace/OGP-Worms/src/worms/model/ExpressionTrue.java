package worms.model;

public class ExpressionTrue extends Expression {
	
	public ExpressionTrue(int line, int column) {
		super(line,column);
	}

	@Override
	public Type<Boolean> evaluate() {
		return new Type<Boolean>(true);
	}

}
