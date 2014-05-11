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

	private Object lastObject;
	
	@Override
	public void execute() {
		preExecute();
		Program program = getRootProgram();
		
		/* TODO	Starting from lastObject is easily done with a regular for-loop instead of an enhanced for-loop.
		 * 		I don't know how to do it with an enhanced for-loop, but I guess that must be possible.
		 * 		I have set the old code in comments.
		 */
		//For-loop for worms.
		if ( type == ForeachType.WORM ) {
			int counter = 0;
			ArrayList<Worm> worms = new ArrayList<Worm>();
			worms.addAll(getProgram().getWorm().getWorld().getWorms());
			if (getProgramContinues() == false) { //The next for-loop is executed for the first time, getProgramContinues = default FALSE
				for (int i = 0; i < worms.size(); i++ ) {
					lastObject = worms.get(i);
					counter = i;
					program.assignVariable(variable, new Type<Entity>(new Entity(lastObject)));
					getStatements().get(0).execute();
				}
			}
			else {	//The program was interrupted and continues from the last worm in the loop.
				for (int i = counter; i < worms.size(); i++) {
					lastObject = worms.get(i);
					counter = i;
					program.assignVariable(variable, new Type<Entity>(new Entity(lastObject)));
					getStatements().get(0).execute();
				}
			}
		}
//		if ( type == ForeachType.WORM ) {
//			if (getProgramContinues() == false) {	//The for-loop is executed for the first time.
//				for ( Worm worm : getProgram().getWorm().getWorld().getWorms() ) {
//					lastObject = worm;
//					program.assignVariable(variable, new Type<Entity>(new Entity(worm)));
//					getStatements().get(0).execute();
//				}
//			}
//			else {
//				for ( Worm worm : getProgram().getWorm().getWorld().getWorms() ) {
//					lastObject = worm;
//					program.assignVariable(variable, new Type<Entity>(new Entity(worm)));
//					getStatements().get(0).execute();
//				}
//			}
//		}
		
		//For-loop for food.
		if ( type == ForeachType.FOOD ) {
			int counter = 0;
			ArrayList<Food> foods = new ArrayList<Food>();
			foods.addAll(getProgram().getWorm().getWorld().getFood());
			if (getProgramContinues() == false) {
				for (int i = 0; i < foods.size(); i++ ) {
					lastObject = foods.get(i);
					counter = i;
					program.assignVariable(variable, new Type<Entity>(new Entity(lastObject)));
					getStatements().get(0).execute();
				}
			}
			else {
				for (int i = counter; i < foods.size(); i++) {
					lastObject = foods.get(i);
					counter = i;
					program.assignVariable(variable, new Type<Entity>(new Entity(lastObject)));
					getStatements().get(0).execute();
				}
			}
		}
//		if ( type == ForeachType.FOOD ) {
//			if (getProgramContinues() == false) {
//				for ( Food food : getProgram().getWorm().getWorld().getFood() ) {
//					lastObject = food;
//					program.assignVariable(variable, new Type<Entity>(new Entity(food)));
//					getStatements().get(0).execute();
//				}
//			}
//			else {
//				for (Food food : getProgram().getWorm().getWorld().getFood() ) {
//					lastObject = food;
//					program.assignVariable(variable, new Type<Entity>(new Entity(food)));
//					getStatements().get(0).execute();
//				}
//			}
//		}
		//For-loop for objects.
		if ( type == ForeachType.ANY ) {
			int counter = 0;
			ArrayList<Object> objects = new ArrayList<Object>();
			objects.addAll(getProgram().getWorm().getWorld().getWorms());
			objects.addAll(getProgram().getWorm().getWorld().getFood());
			if (getProgramContinues() == false) {
				for (int i = counter; i < objects.size(); i++) {
					lastObject = objects.get(i);
					counter = i;
					program.assignVariable(variable, new Type<Entity>(new Entity(lastObject)));
					getStatements().get(0).execute();
				}
			}
			else {
				for (int i = counter; i < objects.size(); i++) {
					lastObject = objects.get(i);
					counter = i;
					program.assignVariable(variable, new Type<Entity>(new Entity(lastObject)));
					getStatements().get(0).execute();
				}
			}
//			if (getProgramContinues() == false) {
//				for ( Object obj : objects ) {
//					lastObject = obj;
//					program.assignVariable(variable, new Type<Entity>(new Entity(obj)));
//					getStatements().get(0).execute();
//				}
//			}
//			else {
//				for (Object obj : objects ) {
//					lastObject = obj;
//					program.assignVariable(variable, new Type<Entity>(new Entity(obj)));
//					getStatements().get(0).execute();
//				}
//			}
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
