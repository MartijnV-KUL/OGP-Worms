package worms.model.statements;

import worms.model.Expression;
import worms.model.Statement;


public class StatementWhile extends Statement {
	
	public StatementWhile(int line, int column, Expression e, Statement s) {
		super(line, column, new Statement[] {s}, new Expression[] {e});
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
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( !(val instanceof Boolean) ) {
			getProgram().typeErrorOccurred();
			return;
		}
		while ((boolean)val) {
			getStatements().get(0).execute();
		}
	}

}
