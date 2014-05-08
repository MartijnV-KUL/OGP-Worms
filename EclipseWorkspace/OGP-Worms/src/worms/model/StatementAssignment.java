package worms.model;

public class StatementAssignment extends Statement {
	
	private String variable;
	
	public StatementAssignment(int line, int column, String variable, Expression e) {
		super(line, column, new Statement[0], new Expression[] {e});
		this.variable = variable;
	}

	@Override
	public void execute() {
		preExecute();
		getProgram().assignVariable(variable, getExpressions().get(0).evaluate());
	}

	@Override
	public boolean containsActionStatement() {
		return false;
	}

}
