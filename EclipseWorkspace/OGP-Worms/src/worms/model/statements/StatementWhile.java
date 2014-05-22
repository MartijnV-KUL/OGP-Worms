package worms.model.statements;

import java.util.ArrayList;

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
		
		ArrayList<Statement> allStatements = getAllStatements();
		boolean cont = false;
		for (Statement s : allStatements) {
			if (s.getLine() >= getRootProgram().getCurrentLine())
				cont = true;
		}
		if (!cont)
			return;
		
		if (getLine() > getRootProgram().getCurrentLine())
			preExecute();
		
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( !(val instanceof Boolean) ) {
			getProgram().typeErrorOccurred();
			return;
		}
		while ((boolean)val) {
			if (!getRootProgram().continueExecution())
				return;
			getStatements().get(0).execute();
			getRootProgram().setCurrentLine(getLine());
			getRootProgram().setCurrentColumn(getColumn());

			// Update while-condition
			val = getExpressions().get(0).evaluate().getValue();
			if ( !(val instanceof Boolean) ) {
				getProgram().typeErrorOccurred();
				return;
			}
			
		}
	}

}
