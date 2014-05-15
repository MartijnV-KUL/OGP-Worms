package worms.model;

public class ExpressionGetY extends Expression {
	
	public ExpressionGetY(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Double> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( val instanceof Worm ) //TODO static or dynamic type checked? should be dynamic type, because static type is "Object"
			return new Type<Double>(((Worm)val).getY());
		if ( val instanceof Food ) //TODO static or dynamic type checked? should be dynamic type, because static type is "Object"
			return new Type<Double>(((Food)val).getY());
		getStatement().getRootProgram().typeErrorOccurred();
		return null;
	}

}
