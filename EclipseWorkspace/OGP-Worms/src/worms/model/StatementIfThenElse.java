package worms.model;

public class StatementIfThenElse extends Statement {
	
	public StatementIfThenElse(int line, int column, Expression e, Statement s1, Statement s2) {
		super(line, column, new Statement[] {s1,s2}, new Expression[] {e});
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
		if ( (boolean) ((Type<Boolean>)getExpressions().get(0).evaluate()).getValue() )
			getStatements().get(0).execute();
		else
			getStatements().get(1).execute();
	}

}
