package worms.model;

public class ExpressionGetRadius extends Expression {
	
	public ExpressionGetRadius(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Double> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( val instanceof Worm ) //TODO static or dynamic type checked? should be dynamic type, because static type is "Object"
			return new Type<Double>(((Worm)val).getRadius());
		if ( val instanceof Food ) //TODO static or dynamic type checked? should be dynamic type, because static type is "Object"
			return new Type<Double>(Food.getRadius());
		getStatement().getRootProgram().typeErrorOccurred();
		return null;
	}

}
