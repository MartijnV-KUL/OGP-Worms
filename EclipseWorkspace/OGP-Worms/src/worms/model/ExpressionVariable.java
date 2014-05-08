package worms.model;

public class ExpressionVariable extends Expression {
	
	private final String variable;
	
	public ExpressionVariable( int line, int column, String variable ) {
		super(line,column,Expression.Types.VARIABLE);
		this.variable = variable;
	}
	
	public Type<?> evaluate() {
		return getStatement().getProgram().getVariable(variable);
	}

}
