package worms.model;

public abstract class StatementAction extends Statement {
	
	public StatementAction(int line, int column) {
		super(line, column);
	}
	
	public StatementAction( int line, int column, Expression e ) {
		super(line, column, new Expression[] {e});
	}
	
	protected abstract void doAction();
	
	@Override
	public void execute() {
		System.out.println("test");
		if (!getRootProgram().continueExecution())
			return;
		if (getRootProgram().getCurrentLine() > getLine())
			return;
		if (getRootProgram().getCurrentColumn() > getColumn())
			return;
		preExecute();
		doAction();
	}

}
