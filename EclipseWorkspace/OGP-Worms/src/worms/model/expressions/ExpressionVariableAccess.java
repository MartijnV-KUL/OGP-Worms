package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;

public class ExpressionVariableAccess extends Expression {
	
	private String variable;
	private Type<?> type;
	
	public ExpressionVariableAccess(int line, int column, String variable, Type<?> type) {
		super(line, column);
		this.variable = variable;
		this.type = type;
	}

	@Override
	public Type<?> evaluate() {
		// note: isAssignableFrom checks if the argument class extends the class it's called on.
		// In this case, it checks if the class of the variable extends the class of the type object
		if (type.getValue().getClass().isAssignableFrom(getRootProgram().getVariable(variable).getValue().getClass()))
			return getRootProgram().getVariable(variable);		
		getRootProgram().typeErrorOccurred();
		return null;
	}

}
