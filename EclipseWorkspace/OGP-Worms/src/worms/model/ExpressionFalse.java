package worms.model;

public class ExpressionFalse extends Expression {
	
	public ExpressionFalse(int line, int column) {
		super(line,column);
	}

	@Override
	public Type<Boolean> evaluate() {
		return new Type<Boolean>(false);
	}

}
