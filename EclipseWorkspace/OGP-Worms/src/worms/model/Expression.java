package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class Expression {
	
	private final int line;
	private final int column;
	private final Expression.Types type;
	
	public Expression(int line, int column, Expression.Types type) {
		this.line = line;
		this.column = column;
		this.type = type;
	}
	
	public int getLine() {
		return line;
	}
	public int getColumn() {
		return column;
	}
	
	public Type<?> evaluate() {
		if (getType()==Expression.Types.TRUE)
			return new Type<Boolean>(true);
		if (getType()==Expression.Types.FALSE)
			return new Type<Boolean>(false);
		if (getType()==Expression.Types.NULL)
			return new Type<Entity>(null);
		if (getType()==Expression.Types.SELF)
			return new Type<Entity>(new Entity(getStatement().getProgram().getWorm()));
		
		getStatement().getProgram().typeErrorOccurred();
		return null;
	}
	
	
	
	public Types getType() {
		return type;
	}
	
	public static enum Types {
		VARIABLE,
		DOUBLE,
		TRUE,
		FALSE,
		NULL,
		SELF,
		ADDITION,
		SUBTRACTION,
		MULTIPLICATION,
		DIVISION,
		SQUAREROOT,
		SINE,
		COSINE,
		CONJUNCTION,
		DISJUNCTION,
		NEGATION,
		LESSTHAN,
		LESSTHANOREQUALTO,
		GREATERTHAN,
		GREATERTHANOREQUALTO,
		EQUALTO,
		DIFFERENTFROM,
		GETX,
		GETY,
		GETRADIUS,
		GETDIR,
		GETAP,
		GETMAXAP,
		GETHP,
		GETMAXHP,
		SAMETEAM,
		SEARCHOBJ,
		ISWORM,
		ISFOOD;
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
