package worms.model.statements;

import java.util.ArrayList;

import worms.model.Entity;
import worms.model.Food;
import worms.model.Program;
import worms.model.Statement;
import worms.model.Type;
import worms.model.Worm;
import worms.model.programs.ProgramFactory.ForeachType;

public class StatementForEach extends Statement {
	
	private final ForeachType type;
	private final String variable;
	
	public StatementForEach(int line, int column, ForeachType type, String variable, Statement s) {
		super(line, column, new Statement[] {s});
		this.type = type;
		this.variable = variable;
	}

	private Object lastObject;
	private int counter = 0;
	
	@Override
	public void execute() {
		if (!getRootProgram().continueExecution())
			return;
		
		ArrayList<Statement> allStatements = getAllStatements();
		boolean cont = false;
		for (Statement s : allStatements) {
			if (s.getLine() >= getRootProgram().getCurrentLine())
				cont = true;
		}
		if (!cont)
			return;
		
		if (getLine() > getRootProgram().getCurrentLine())
			preExecute();
		
		Program program = getRootProgram();
		
		if ( type == ForeachType.WORM ) {
			ArrayList<Worm> worms = new ArrayList<Worm>();
			worms.addAll(program.getWorm().getWorld().getWorms());
			
			if ( counter >= worms.size() ) // counter looped to the end
				counter = 0;
			else if ( worms.get(counter) != lastObject ) // the object at the specified index changed --> start from the beginning again.
				counter = 0;
			
			// loop over all remaining worms
			for (int i = counter; i < worms.size(); i++) {
				if (!getRootProgram().continueExecution())
					return;
				lastObject = worms.get(i);
				counter = i;
				program.assignVariable(variable, new Type<Entity>(new Entity((Worm) lastObject)));
				getStatements().get(0).execute();
			}
		}

		if ( type == ForeachType.FOOD ) {
			ArrayList<Food> foods = new ArrayList<Food>();
			foods.addAll(program.getWorm().getWorld().getFood());

			if ( counter >= foods.size() ) // counter looped to the end
				counter = 0;
			else if ( foods.get(counter) != lastObject ) // the object at the specified index changed --> start from the beginning again.
				counter = 0;

			// loop over all remaining foods
			for (int i = counter; i < foods.size(); i++) {
				if (!getRootProgram().continueExecution())
					return;
				lastObject = foods.get(i);
				counter = i;
				
				program.assignVariable(variable, new Type<Entity>(new Entity((Food) lastObject)));
				getStatements().get(0).execute();
			}
		}

		if ( type == ForeachType.ANY ) {
			ArrayList<Object> objects = new ArrayList<Object>();
			objects.addAll(program.getWorm().getWorld().getWorms());
			objects.addAll(program.getWorm().getWorld().getFood());

			if ( counter >= objects.size() ) // counter looped to the end
				counter = 0;
			else if ( objects.get(counter) != lastObject ) // the object at the specified index changed --> start from the beginning again.
				counter = 0;

			// loop over all remaining objects
			for (int i = counter; i < objects.size(); i++) {
				if (!getRootProgram().continueExecution())
					return;
				lastObject = objects.get(i);
				counter = i;

				if (lastObject instanceof Worm)
					program.assignVariable(variable, new Type<Entity>(new Entity((Worm)lastObject)));
				else if (lastObject instanceof Food)
					program.assignVariable(variable, new Type<Entity>(new Entity((Food)lastObject)));
				else
					program.typeErrorOccurred();
				
				getStatements().get(0).execute();
			}
		}
	}

	
	@Override
	public boolean isWellFormed() {
		for ( Statement s : getAllStatements() ) {
			if ( s instanceof StatementAction )
				return false;
		}
		return true;
	}

}
