package worms.model;

public class StatementWhile extends Statement {
	
	public StatementWhile(int line, int column, Expression e, Statement s) {
		super(line, column, new Statement[] {s}, new Expression[] {e});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		if (!getRootProgram().continueExecution())
			return;
		if (getRootProgram().getCurrentLine() > getLine())
			return;
		if (getRootProgram().getCurrentColumn() > getColumn())
			return;
		preExecute();
		if ( !(getExpressions().get(0).evaluate().getValue() instanceof Boolean) )
			getProgram().typeErrorOccurred();
		while ((boolean) ((Type<Boolean>)getExpressions().get(0).evaluate()).getValue()) {
			getStatements().get(0).execute();
		}
	}

}
