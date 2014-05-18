package worms.model.statements;

import java.util.ArrayList;

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

	@Override
	public void execute() {
		if (!getRootProgram().continueExecution())
			return;
		if (getRootProgram().getCurrentLine() > getLine())
			return;
		if (getRootProgram().getCurrentColumn() > getColumn())
			return;
		preExecute();
		Program program = getRootProgram();
		
		if ( type == ForeachType.WORM ) {
			for ( Worm w : program.getWorm().getWorld().getWorms() ) {
				System.out.println("Next execution.");
				getRootProgram().setCurrentLine(getLine());
				getRootProgram().setCurrentColumn(getColumn());
				
				program.assignVariable(variable, new Type<Worm>(w));
				getStatements().get(0).execute();
				System.out.println("reached end: " + program.getHasReachedEnd());
				System.out.println("program continues: " + program.continueExecution() + "\n");
			}
		}
		if ( type == ForeachType.FOOD ) {
			for ( Food f : program.getWorm().getWorld().getFood() ) {
				getRootProgram().setCurrentLine(getLine());
				getRootProgram().setCurrentColumn(getColumn());
				
				program.assignVariable(variable, new Type<Food>(f));
				getStatements().get(0).execute();
			}
		}
		if ( type == ForeachType.ANY ) {
			ArrayList<Object> objects = new ArrayList<Object>();
			objects.addAll(program.getWorm().getWorld().getWorms());
			objects.addAll(program.getWorm().getWorld().getFood());
			for ( Object obj : objects ) {
				getRootProgram().setCurrentLine(getLine());
				getRootProgram().setCurrentColumn(getColumn());
				
				program.assignVariable(variable, new Type<Object>(obj));
				getStatements().get(0).execute();
			}
		}
	}
	
	/*

	private Object lastObject;
	private int counter = 0;
	
	@Override
	public void execute() {
		if (!getRootProgram().continueExecution())
			return;
		if (getRootProgram().getCurrentLine() > getLine())
			return;
		if (getRootProgram().getCurrentColumn() > getColumn())
			return;
		preExecute();
		Program program = getRootProgram();
		
//		 	Starting from lastObject is easily done with a regular for-loop instead of an enhanced for-loop.
//		 		I don't know how to do it with an enhanced for-loop, but I guess that must be possible.
//		 		I have set the old code in comments.
//		 
		//For-loop for worms.
		if ( type == ForeachType.WORM ) {
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
	*/
	
	@Override
	public boolean isWellFormed() {
		for ( Statement s : getAllStatements() ) {
			if ( s instanceof StatementAction ) //TODO check static and dynamic type. What does "instanceof" check? SAction is an abstract class, so only object of its children classes can exist. Does that mean that this always retrn false? or does it return true also if an object is an object of a childclass?
				return false;
		}
		return true;
	}

}
