package worms.model;

public class ExpressionNull extends Expression {
	
	public ExpressionNull(int line, int column) {
		super(line,column);
	}

	@Override
	public Type<Entity> evaluate() {
		return new Type<Entity>(null);
	}

}
