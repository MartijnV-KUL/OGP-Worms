package worms.model;

public class StatementActionFire extends StatementAction {
	
	public StatementActionFire(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	protected void doAction() {
		Program program = getRootProgram();
		Worm worm = program.getWorm();
		Object val = getExpressions().get(0).evaluate(); 
		if ( !(val instanceof Number) ) { //TODO dynamic-static type: which is checked here?
			program.typeErrorOccurred();
			return;
		}
		if ( !worm.canShoot() ) {
			program.stopProgram();
			return;
		}
		program.getHandler().fire(worm, (int)Math.floor((double)(val)));
	}

}
