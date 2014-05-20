package worms.model.statements;

public class StatementActionSkip extends StatementAction {
	
	public StatementActionSkip(int line, int column) {
		super(line,column);
	}

	@Override
	protected void doAction() {
		System.out.println("WORM IS SKIPPING");
		// do nothing
	}

}
