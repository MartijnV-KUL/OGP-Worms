package worms.model;

public class ExpressionDouble extends Expression {
	
	private final double d;
	
	public ExpressionDouble(int line, int column, double d) {
		super(line,column,Expression.Types.DOUBLE);
		this.d = d;
	}
	
	public Type<?> evaluate() {
		return new Type<Double>(d);
	}

}