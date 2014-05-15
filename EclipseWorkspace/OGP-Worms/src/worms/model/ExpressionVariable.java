package worms.model;

public class ExpressionVariable extends Expression {
	
	private final String variable;
	
	public ExpressionVariable( int line, int column, String variable ) {
		super(line,column);
		this.variable = variable;
	}
	
	public Type<?> evaluate() { //TODO this returns a "?", how can this be avoided?
		return getStatement().getRootProgram().getVariable(variable);
	}

}
