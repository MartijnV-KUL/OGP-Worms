package worms.model.statements;

import java.util.ArrayList;
import java.util.List;

import worms.model.Statement;

public class StatementSequence extends Statement {
	
	public StatementSequence(int line, int column, List<Statement> statements) {
		super(line, column, statements.toArray(new Statement[statements.size()]));
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
		
		for ( Statement s : getStatements() ) {
			s.execute();
		}
	}

}
