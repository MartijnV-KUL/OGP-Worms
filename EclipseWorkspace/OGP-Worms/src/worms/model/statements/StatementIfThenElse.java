package worms.model.statements;

import java.util.ArrayList;

import worms.model.Expression;
import worms.model.Statement;


public class StatementIfThenElse extends Statement {
	
	public StatementIfThenElse(int line, int column, Expression e, Statement s1, Statement s2) {
		super(line, column, new Statement[] {s1,s2}, new Expression[] {e});
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
		if ( !(val instanceof Boolean) )
			getProgram().typeErrorOccurred();
		if ((boolean)val)
			getStatements().get(0).execute();
		else
			getStatements().get(1).execute();
	}

}
