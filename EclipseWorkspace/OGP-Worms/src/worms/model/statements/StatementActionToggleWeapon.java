package worms.model.statements;

import worms.model.Program;


public class StatementActionToggleWeapon extends StatementAction {
	
	public StatementActionToggleWeapon(int line, int column) {
		super(line,column);
	}

	@Override
	protected void doAction() {
		Program program = getRootProgram();
		program.getHandler().toggleWeapon(program.getWorm());
	}

}