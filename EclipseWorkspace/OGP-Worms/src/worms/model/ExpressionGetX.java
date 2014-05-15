package worms.model;

public class ExpressionGetX extends Expression {
	
	public ExpressionGetX(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Double> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( val instanceof Worm ) //TODO static or dynamic type checked? should be dynamic type, because static type is "Object"
			return new Type<Double>(((Worm)val).getX());
		if ( val instanceof Food ) //TODO static or dynamic type checked? should be dynamic type, because static type is "Object"
			return new Type<Double>(((Food)val).getX());
		getStatement().getRootProgram().typeErrorOccurred();
		return null;
	}

}