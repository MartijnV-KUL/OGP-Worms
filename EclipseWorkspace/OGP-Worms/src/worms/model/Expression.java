package worms.model;

import java.util.ArrayList;

import worms.model.ModelException;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public abstract class Expression {
	
	private final int line;
	private final int column;
	private final ArrayList<Expression> expressions = new ArrayList<Expression>();
	
	public Expression(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	public Expression(int line, int column, Expression e) {
		this.line = line;
		this.column = column;
		expressions.add(e);
	}
	
	public Expression(int line, int column, Expression e1, Expression e2) {
		this.line = line;
		this.column = column;
		expressions.add(e1);
		expressions.add(e2);
	}
	
	public Expression(int line, int column, Expression[] expressions) {
		this.line = line;
		this.column = column;
		for ( int i=0; i<expressions.length; i++ ) {
			this.expressions.add(expressions[i]);
		}
	}
	
	public int getLine() {
		return line;
	}
	public int getColumn() {
		return column;
	}
	public ArrayList<Expression> getExpressions() {
		return expressions;
	}
	
	public abstract Type<?> evaluate(); 
	
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
		
// {{ Terminated

	private boolean terminated;
	
	@Basic
	public boolean isTerminated() {
		return terminated;
	}
	
	public void terminate() {
		terminated = true;
	}
	
	
	// }}

}
