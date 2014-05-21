package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;

public class ExpressionVariable extends Expression {
	
	private final String variable;
	
	public ExpressionVariable( int line, int column, String variable ) {
		super(line,column);
		this.variable = variable;
	}
	
	public Type<?> evaluate() {
		return getRootProgram().getVariable(variable);
	}

}
