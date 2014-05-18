package worms.model.statements;

public class StatementActionSkip extends StatementAction {
	
	public StatementActionSkip(int line, int column) {
		super(line,column);
	}

	@Override
	protected void doAction() {
		// do nothing
	}

}
