package worms.model.statements;

import worms.model.Expression;
import worms.model.Statement;


public class StatementPrint extends Statement {
	
	public StatementPrint(int line, int column, Expression e) {
		super(line, column, new Expression[] {e});
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
		System.out.println(getExpressions().get(0).evaluate().getValue());
		//TODO this kind of print? perhaps somewhere on the GUI?
	}

}
