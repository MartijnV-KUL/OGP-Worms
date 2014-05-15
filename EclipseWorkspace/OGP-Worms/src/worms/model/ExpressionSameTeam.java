package worms.model;

public class ExpressionSameTeam extends Expression {
	
	public ExpressionSameTeam(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Boolean> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( val instanceof Worm ) //TODO static or dynamic type checked? should be dynamic type, because static type is "Object"
			return new Type<Boolean>(getStatement().getRootProgram().getWorm().getTeam()==((Worm)val).getTeam());
		getStatement().getRootProgram().typeErrorOccurred();
		return null;
	}

}