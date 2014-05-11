package worms.model;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public abstract class Statement {

	private int line;
	private int column;
	
	public Statement(int line, int column, Statement[] statements, Expression[] expressions) {
		//this.line = line;
		//this.column = column;
		setLine(line);
		setColumn(column);
		
		for ( int i=0; i<statements.length; i++ ) {
			this.addStatement(statements[i]);
		}
		for ( int i=0; i<expressions.length; i++ ) {
			this.addExpression(expressions[i]);
		}
	}
	
	//TODO	I added setters for line and column to continue at the next line when the program stopped.
	//		Could not do that in program through setCurrentLine, as the method execute() calls preExecute()
	//		and that overrules the setCurrentLine() method in program through the program.setCurrentLine(getLine()) method.
	public void setLine(int line) {
		this.line = line;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public int getLine() {
		return line;
	}
	public int getColumn() {
		return column;
	}
	
	public abstract void execute();
	
	private Boolean programContinues = false;
	
	public void setProgramContinues(Boolean bool) {
		this.programContinues = bool;
	}
	
	public Boolean getProgramContinues() {
		return programContinues;
	}
	
	public void preExecute() {
		Program program = getRootProgram();
		program.setCurrentLine(getLine());
		program.setCurrentColumn(getColumn());
		program.incNbStatementsExecuted();
	}
	
	public Program getRootProgram() {
		Statement statement = this;
		while ( statement.hasAParentStatement() ) {
			statement = statement.getParentStatement();
		}
		return statement.getProgram();
	}

	public boolean isWellFormed() {
		return true; //NOTE dummy implementation, is overridden by the for each statement 
	}
	
	public abstract boolean containsActionStatement();
	
// {{ Program Association

	private Program program;
	
	@Basic
	public Program getProgram() {
		return program;
	}
	
	@Raw
	void setProgram(Program program) {
		if (!canHaveAsProgram(program))
			throw new ModelException("Invalid program specified.");
		if (hasAProgram())
			throw new ModelException("Already has a program");
		this.program = program;
	}
	
	private static boolean canHaveAsProgram(Program program) {
		if (program == null)
			return false;
		if (program.isTerminated())
			return false;
		return true;
	}
	
	protected boolean hasAProgram() {
		return (!(program == null));
	}
	
	@Raw
	void removeProgram() {
		program = null;
	}
	
// }}
	
// {{ Parent Statement Association

	private Statement parentStatement;
	
	@Basic
	public Statement getParentStatement() {
		return parentStatement;
	}
	
	@Raw
	void setParentStatement(Statement statement) {
		if (!canHaveAsParentStatement(statement))
			throw new ModelException("Invalid statement specified.");
		if (hasAParentStatement())
			throw new ModelException("Already has a statement");
		this.parentStatement = statement;
	}
	
	private static boolean canHaveAsParentStatement(Statement statement) {
		if (statement == null)
			return false;
		if (statement.isTerminated())
			return false;
		return true;
	}
	
	protected boolean hasAParentStatement() {
		return (!(parentStatement == null));
	}
	
	@Raw
	void removeParentStatement() {
		parentStatement = null;
	}
	
	// }}
	
	// {{ Statements Association
	
	private final ArrayList<Statement> statements = new ArrayList<Statement>();
	
	@Basic
	public ArrayList<Statement> getStatements() {
		return statements;
	}
	
	public void addStatement(Statement statement) throws ModelException {
		if (!canHaveAsStatement(statement))
			throw new ModelException("Invalid statement specified.");
		if (hasAsStatement(statement))
			throw new ModelException("Statement already in world.");
		statement.setParentStatement(this);
		statements.add(statement);
	}
	
	private static boolean canHaveAsStatement(Statement statement) {
		if (statement==null)
			return false;
		if (statement.isTerminated())
			return false;
		return true;
	}
	
	private boolean hasAsStatement(Statement statement) {
		return statements.contains(statement);
	}
	
	public void removeStatement(Statement statement) throws ModelException {
		if (!hasAsStatement(statement))
			throw new ModelException("Statement not found.");
		statement.removeParentStatement();
		statements.remove(statement);
	}
	
	private void removeAllStatements() {
		for ( Statement statement : statements ) {
			statement.removeParentStatement();
			removeStatement(statement);
		}
	}
	
	// }}
	
	// {{ Expressions Association
	
	private final ArrayList<Expression> expressions = new ArrayList<Expression>();
	
	@Basic
	public ArrayList<Expression> getExpressions() {
		return expressions;
	}
	
	public void addExpression(Expression expression) throws ModelException {
		if (!canHaveAsExpression(expression))
			throw new ModelException("Invalid expression specified.");
		if (hasAsExpression(expression))
			throw new ModelException("Expression already in world.");
		expression.setStatement(this);
		expressions.add(expression);
	}
	
	private static boolean canHaveAsExpression(Expression expression) {
		if (expression==null)
			return false;
		if (expression.isTerminated())
			return false;
		return true;
	}
	
	private boolean hasAsExpression(Expression expression) {
		return expressions.contains(expression);
	}
	
	public void removeExpression(Expression expression) throws ModelException {
		if (!hasAsExpression(expression))
			throw new ModelException("Statement not found.");
		expression.removeStatement();
		statements.remove(expression);
	}
	
	private void removeAllExpressions() {
		for ( Expression expression : expressions ) {
			expression.removeStatement();
			removeExpression(expression);
		}
	}
	
	// }}
	
// {{ Terminated

	private boolean terminated;
	
	@Basic
	public boolean isTerminated() {
		return terminated;
	}
	
	public void terminate() {
		removeAllStatements();
		removeAllExpressions();
		terminated = true;
	}
	
	
	// }}

}
