package worms.model;

public class ExpressionSelf extends Expression {
	
	public ExpressionSelf(int line, int column) {
		super(line,column);
	}

	@Override
	public Type<Worm> evaluate() {
		return new Type<Worm>(getRootProgram().getWorm());
	}

}
