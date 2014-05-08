package worms.model;

public class StatementPrint extends Statement {
	
	public StatementPrint(int line, int column, Expression e) {
		super(line, column, new Statement[0], new Expression[] {e});
	}

	@Override
	public void execute() {
		preExecute();
		System.out.println(getExpressions().get(0).evaluate().getValue());
	}

	@Override
	public boolean containsActionStatement() {
		return false;
	}

}
