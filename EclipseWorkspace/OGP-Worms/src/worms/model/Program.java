package worms.model;

import java.util.HashMap;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import worms.gui.game.IActionHandler;
import worms.model.ModelException;
import worms.model.programs.ParseOutcome;
import worms.model.programs.ProgramParser;

public class Program {
	
	private  IActionHandler handler;
	
	public IActionHandler getHandler() {
		return handler;
	}
	
	public Program() {
		System.out.println("This class has no implementation yet!");
	}

	public ParseOutcome<?> parseProgram(String programText, IActionHandler handler) {
		this.handler = handler;
		ProgramParser<Expression,Statement,Type<?>> parser = new ProgramParser<Expression,Statement,Type<?>>(new ProgramFactoryImpl());
		parser.parse(programText);
		return null; //TODO this seems fishy... what else is there to return. What can be returned as a ParseOutcom?
	}

	public boolean isWellFormed() {
		return getStatement().isWellFormed();
	}
	
	public void typeErrorOccurred() {
		//TODO
		// Page 16 of assignment about type errors:
//		Such errors must be detected at runtime, i.e. during the execution of the
//		program. As explained before, runtime errors should be handled in a total
//		manner. That is, execution of the program stops and subsequent runs of the
//		program return immediately.
	}
	
	private HashMap<String,Type<?>> variables;
	
	public void assignVariable(String variable, Type<?> t ) {
		variables.put(variable, t);
	}
	
	public Type<?> getVariable(String variable) {
		Type<?> t = variables.get(variable);
		if (t==null)
			return new Type<Entity>(null);
		return t;
	}
	
	private int currentLine;
	private int currentColumn;
	private int nbStatementsExecuted;

	public int getCurrentLine() {
		return currentLine;
	}
	public int getCurrentColumn() {
		return currentColumn;
	}
	public void setCurrentLine(int line) {
		currentLine = line;
	}
	public void setCurrentColumn(int column) {
		currentColumn = column;
	}
	public int getNbStatementsExecuted() {
		return nbStatementsExecuted;
	}
	public void incNbStatementsExecuted() {
		nbStatementsExecuted++;
	}
	public int getMaxNbStatementsExecutions() {
		return 1000;
	}
	
	public void runProgram() {
		if (getNbStatementsExecuted()>=getMaxNbStatementsExecutions()) {
			stopProgram();
		} else if (getCurrentLine()>0) {
			//TODO continue at specified line.
			getStatement().execute(); // continuing at specified line is handled in the "execute" command
		} else {
			getStatement().execute(); //NOTE this should start by loading the variables and then running the main statement. Which means the statement referred to here, should be a sequence starting with the variable assignment and then the other lines.
		}
	}
	
	public void stopProgram() {
		// an action command was executed, but the worm couldn't perform the action (canMove, canJump, canShoot, ... was FALSE)
		// or too many lines were already executed
		//TODO
	}
	

	
// {{ Associations
	
// {{ Worm Association
	
	private Worm worm;
	
	/**
	 * Returns the worm the weapon belongs to.
	 * 
	 * @return	The worm the weapon belongs to.
	 */
	@Basic
	public Worm getWorm() {
		return worm;
	}
	
	/**
	 * Method to set a new worm to a program.
	 * 
	 * @param 	worm
	 * 			The new worm.
	 * 
	 * @post	| new.getWorm() == worm
	 * @throws 	ModelException
	 * 			| if (!canHaveAsWorm(worm)
	 * 			|	throw new ModelException
	 * 			| if (hasAWorm())
	 * 			|	throw new ModelException
	 */
	@Raw
	void setWorm(Worm worm) throws ModelException {
		if (!canHaveAsWorm(worm))
			throw new ModelException("Invalid worm specified.");
		if (hasAWorm())
			throw new ModelException("Already has a worm.");
		this.worm = worm;
	}
	
	/**
	 * Checks if the given worm is valid.
	 * 
	 * @param 	worm
	 * 			The given worm.
	 * 
	 * @return	| if (worm == null)
	 * 			| 	return false
	 * 			| if (worm.isTerminated())
	 * 			|	return false
	 * 			| else
	 * 			|	return true
	 */
	private static boolean canHaveAsWorm(Worm worm) {
		if (worm==null)
			return false;
		if (worm.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Checks if a worm is not null.
	 * 
	 * @return	(!(worm == null))
	 */
	private boolean hasAWorm() {
		return(!(worm==null));
	}
	
	/**
	 * Method to remove a worm.
	 * 
	 * @post	| new.getWorm() == null
	 */
	@Raw
	void removeWorm() {
		worm = null;
	}
	
// }}
	
// {{ Statement Association

	private Statement statement;
	
	@Basic
	public Statement getStatement() {
		return statement;
	}
	
	void setStatement(Statement statement) {
		if (!canHaveAsStatement(statement))
			throw new ModelException("Invalid statement specified.");
		if (hasAStatement())
			throw new ModelException("Already has a statement");
		statement.setProgram(this);
		this.statement = statement;
	}
	
	private static boolean canHaveAsStatement(Statement statement) {
		if (statement == null)
			return false;
		if (statement.isTerminated())
			return false;
		return true;
	}
	
	protected boolean hasAStatement() {
		return (!(statement == null));
	}
	
	void removeStatement() {
		if (hasAStatement())
			statement.removeProgram();
		statement = null;
	}
	
// }}
	
// }}
		
// {{ Terminated
	
	private boolean terminated;
	
	@Basic
	public boolean isTerminated() {
		return terminated;
	}
	
	public void terminate() {
		if (hasAWorm())
			worm.removeProgram();
		removeStatement();
		terminated = true;
	}
	
	
	// }}
	
	
	
}
