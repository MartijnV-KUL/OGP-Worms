package worms.model;

public class StatementActionJump extends StatementAction {
	
	public StatementActionJump(int line, int column) {
		super(line,column);
	}

	@Override
	protected void doAction() {
		Program program = getRootProgram();
		Worm worm = program.getWorm();
		if ( !worm.canJump() ) {
			program.stopProgram();
			return;
		}
		program.getHandler().jump(worm);
	}

}