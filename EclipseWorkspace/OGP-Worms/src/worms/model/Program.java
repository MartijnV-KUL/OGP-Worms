package worms.model;

import java.util.Map;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import worms.gui.game.IActionHandler;
import worms.model.ModelException;
import worms.model.programs.ParseOutcome;
import worms.model.programs.ProgramParser;

public class Program {
	
	private IActionHandler handler;
	
	public IActionHandler getHandler() {
		return handler;
	}
	
	public Program() {}		//Empty constructor.
	
	private ProgramParser<Expression,Statement,Type<?>> parser;

	public ParseOutcome<?> parseProgram(String programText, IActionHandler handler) {
		this.handler = handler;
		parser = new ProgramParser<Expression,Statement,Type<?>>(new ProgramFactoryImpl());
		parser.parse(programText);
		setVariables(parser.getGlobals());
		setStatement(parser.getStatement());
		if (parser.getErrors().isEmpty())
			return ParseOutcome.success(this);
		else
			return ParseOutcome.failure(parser.getErrors());
	}

	public boolean isWellFormed() {
		return getStatement().isWellFormed();
	}
	
	private boolean typeErrorOccurred;
	
	public void typeErrorOccurred() {
		typeErrorOccurred=true;
		System.err.println("A type error occurred in the specified worm program.");
		System.err.println("This happened at line " + getCurrentLine() + " and column " + getCurrentColumn() + ".");
		stopProgram();
//		When a type error occurs, the program stops and a message is displayed on the console in which line and column
//		it occurred. Afterwards subsequent runs of the program return again.
		
		/* Page 16 of assignment about type errors:
		 * Such errors must be detected at runtime, i.e. during the execution of the
		 * program. As explained before, runtime errors should be handled in a total
		 * manner. That is, execution of the program stops and subsequent runs of the
		 * program return immediately. */
	}
	
	private Map<String,Type<?>> variables;
	
	public void assignVariable( String variable, Type<?> t ) {
		variables.put(variable, t);
	}
	
	public void setVariables( Map<String,Type<?>> map ) {
		variables = map;
	}
	
	public Type<?> getVariable(String variable) {
		Type<?> t = variables.get(variable);
		if (t==null)
			return new Type<Object>(null);
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
	
	private final int maxNbStatementsExecutions = 1000;
	
	public int getMaxNbStatementsExecutions() {
		return maxNbStatementsExecutions;
	}
	
	private boolean continueExecution;
	
	public boolean continueExecution() {
		return continueExecution;
	}
	
	public void setContinueExecution(boolean bool) {
		this.continueExecution = bool;
	}
	
	private boolean hasReachedEnd;
	
	public boolean getHasReachedEnd() {
		return hasReachedEnd;
	}
	
	public void runProgram() {
		nbStatementsExecuted=0;
		if (typeErrorOccurred) {
			typeErrorOccurred(); // this also stops the program
			getWorm().getWorld().nextTurn();
			return;
		}
		if (hasReachedEnd) {
			hasReachedEnd = false;
			setVariables(parser.getGlobals());
			setCurrentLine(0);
			setCurrentColumn(0);
		}
		continueExecution = true;

		getStatement().execute();
		if (continueExecution)
			hasReachedEnd=true;
		System.out.println("runProgram() has reached end. Next turn initiating.");
		getWorm().getWorld().nextTurn();
	}
	
	public void stopProgram() {
		continueExecution = false;
		hasReachedEnd = false;
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
