package worms.model.statements;

import worms.model.Expression;
import worms.model.Program;
import worms.model.Worm;

public class StatementActionFire extends StatementAction {
	
	public StatementActionFire(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	protected void doAction() {
		Program program = getRootProgram();
		Worm worm = program.getWorm();
		Object val = getExpressions().get(0).evaluate().getValue(); 
		if ( !(val instanceof Number) ) {
			program.typeErrorOccurred();
			return;
		}
		if ( !worm.canShoot() ) {
			program.stopProgram();
			return;
		}
		System.out.println("WORM FIRING, " + (int)Math.floor((double)(val)) + " YIELD.");
		program.getHandler().fire(worm, (int)Math.floor((double)(val)));
	}

}
