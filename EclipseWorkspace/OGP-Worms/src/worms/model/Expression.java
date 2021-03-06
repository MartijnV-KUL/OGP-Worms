package worms.model;

import java.util.ArrayList;

import worms.model.ModelException;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;


public abstract class Expression {
	
	private final int line;
	private final int column;
	
	public Expression(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	public Expression(int line, int column, Expression e) {
		this.line = line;
		this.column = column;
		addExpression(e);
	}
	
	public Expression(int line, int column, Expression e1, Expression e2) {
		this.line = line;
		this.column = column;
		addExpression(e1);
		addExpression(e2);
	}
	
	public Expression(int line, int column, Expression[] expressions) {
		this.line = line;
		this.column = column;
		for ( int i=0; i<expressions.length; i++ ) {
			addExpression(expressions[i]);
		}
	}
	
	public int getLine() {
		return line;
	}
	public int getColumn() {
		return column;
	}
	
	public abstract Type<?> evaluate(); 
	
	public Program getRootProgram() {
		Expression expression = this;
		while ( expression.hasAParentExpression() ) {
			expression = expression.getParentExpression();
		}
		return expression.getStatement().getRootProgram();
	}
	
// {{ Statement Association

	private Statement statement;
	
	@Basic
	public Statement getStatement() {
		return statement;
	}
	
	@Raw
	void setStatement(Statement statement) {
		if (!canHaveAsStatement(statement))
			throw new ModelException("Invalid statement specified.");
		if (hasAStatement())
			throw new ModelException("Already has a statement");
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
	
	@Raw
	void removeStatement() {
		statement = null;
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
		expression.setParentExpression(this);
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
			throw new ModelException("Expression not found.");
		expression.removeParentExpression();
		expressions.remove(expression);
	}
	
	private void removeAllExpressions() {
		while (!expressions.isEmpty()) {
			removeExpression(expressions.get(0));
		}
	}
	
// }}
	
// {{ Parent Expression Association

	private Expression parentExpression;
	
	@Basic
	public Expression getParentExpression() {
		return parentExpression;
	}
	
	@Raw
	void setParentExpression(Expression expression) {
		if (!canHaveAsParentExpression(expression))
			throw new ModelException("Invalid expression specified.");
		if (hasAParentExpression())
			throw new ModelException("Already has a parent expression");
		this.parentExpression = expression;
	}
	
	private static boolean canHaveAsParentExpression(Expression expression) {
		if (expression == null)
			return false;
		if (expression.isTerminated())
			return false;
		return true;
	}
	
	protected boolean hasAParentExpression() {
		return (!(parentExpression == null));
	}
	
	@Raw
	void removeParentExpression() {
		parentExpression = null;
	}
	
// }}
		
// {{ Terminated

	private boolean terminated;
	
	@Basic
	public boolean isTerminated() {
		return terminated;
	}
	
	public void terminate() {
		terminated = true;
		removeAllExpressions();
	}
	
	
	// }}

}
