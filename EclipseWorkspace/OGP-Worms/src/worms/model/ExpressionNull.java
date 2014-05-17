package worms.model;

public class ExpressionNull extends Expression {
	
	public ExpressionNull(int line, int column) {
		super(line,column);
	}

	@Override
	public Type<Object> evaluate() {
		return new Type<Object>(null);
	}

}
