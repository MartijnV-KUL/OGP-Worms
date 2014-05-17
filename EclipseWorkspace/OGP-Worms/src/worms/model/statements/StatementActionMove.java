package worms.model.statements;

import worms.model.Program;
import worms.model.Worm;


public class StatementActionMove extends StatementAction {
	
	public StatementActionMove(int line, int column) {
		super(line,column);
	}

	@Override
	protected void doAction() {
		Program program = getRootProgram();
		Worm worm = program.getWorm();
		if ( !worm.canMove() ) {
			program.stopProgram();
			return;
		}
		program.getHandler().move(worm);
	}

}
