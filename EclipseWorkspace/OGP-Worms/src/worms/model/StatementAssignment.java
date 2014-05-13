package worms.model;

public class StatementAssignment extends Statement {
	
	private String variable;
	
	public StatementAssignment(int line, int column, String variable, Expression e) {
		super(line, column, new Statement[0], new Expression[] {e});
		this.variable = variable;
	}

	@Override
	public void execute() {
		if (!getRootProgram().continueExecution())
			return;
		if (getRootProgram().getCurrentLine() > getLine())
			return;
		if (getRootProgram().getCurrentColumn() > getColumn())
			return;
		preExecute();
		getRootProgram().assignVariable(variable, getExpressions().get(0).evaluate());
	}

}
