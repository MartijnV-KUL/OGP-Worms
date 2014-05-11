package worms.model;

import java.util.List;

public class StatementSequence extends Statement {
	
	public StatementSequence(int line, int column, List<Statement> statements) {
		super(line, column, StatementSequence.convertToArray(statements), new Expression[0]);
	}

	private static Statement[] convertToArray(List<Statement> list) {
		return list.toArray(new Statement[list.size()]);
	}
	
	@Override
	public void execute() {
		preExecute();
		for ( Statement s : getStatements() ) {
			s.execute();
		}
	}

	@Override
	public boolean containsActionStatement() {
		for ( Statement s : getStatements() ) {
			if (s.containsActionStatement())
				return true;
		}
		return false;
	}

}
