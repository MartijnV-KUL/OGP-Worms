package worms.model.statements;

import worms.model.Expression;
import worms.model.Statement;

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
