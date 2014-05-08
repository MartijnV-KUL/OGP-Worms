package worms.model;

import java.util.ArrayList;

import worms.model.programs.ProgramFactory.ForeachType;

public class StatementForEach extends Statement {
	
	private final ForeachType type;
	private final String variable;
	
	public StatementForEach(int line, int column, ForeachType type, String variable, Statement s) {
		super(line, column, new Statement[] {s}, new Expression[0]);
		this.type = type;
		this.variable = variable;
	}

	@Override
	public void execute() {
		preExecute();
		Program program = getRootProgram();
		
		if ( type == ForeachType.WORM ) {
			for ( Worm worm : getProgram().getWorm().getWorld().getWorms() ) {
				program.assignVariable(variable, new Type<Entity>(new Entity(worm)));
				getStatements().get(0).execute();
			}
		}
		if ( type == ForeachType.FOOD ) {
			for ( Food food : getProgram().getWorm().getWorld().getFood() ) {
				program.assignVariable(variable, new Type<Entity>(new Entity(food)));
				getStatements().get(0).execute();
			}
		}
		if ( type == ForeachType.ANY ) {
			ArrayList<Object> objects = new ArrayList<Object>();
			objects.addAll(getProgram().getWorm().getWorld().getWorms());
			objects.addAll(getProgram().getWorm().getWorld().getFood());
			for ( Object obj : objects ) {
				program.assignVariable(variable, new Type<Entity>(new Entity(obj)));
				getStatements().get(0).execute();
			}
		}
	}

	@Override
	public boolean containsActionStatement() {
		return getStatements().get(0).containsActionStatement();
	}
	
	@Override
	public boolean isWellFormed() {
		return (!containsActionStatement());
	}

}
