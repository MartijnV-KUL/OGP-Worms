package worms.model;

import java.util.List;

public class StatementSequence extends Statement {
	
	public StatementSequence(int line, int column, List<Statement> statements) {
		super(line, column, statements.toArray(new Statement[statements.size()]));
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
		for ( Statement s : getStatements() ) {
			s.execute();
		}
	}

}
