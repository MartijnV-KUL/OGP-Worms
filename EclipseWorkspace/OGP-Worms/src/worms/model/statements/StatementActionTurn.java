package worms.model.statements;

import worms.model.Expression;
import worms.model.Program;
import worms.model.Worm;

public class StatementActionTurn extends StatementAction {
	
	public StatementActionTurn(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	protected void doAction() {
		Program program = getRootProgram();
		Worm worm = program.getWorm();
		Object val = getExpressions().get(0).evaluate().getValue(); 
		if ( !(val instanceof Number) ) { //TODO dynamic-static type: which is checked here?
			program.typeErrorOccurred();
			return;
		}
		if ( !worm.canTurn((double)val) ) {
			program.stopProgram();
			return;
		}
		program.getHandler().turn(worm, (double)(val));
	}

}
