package worms.model;

public class ExpressionGreaterThan extends Expression {
	
	public ExpressionGreaterThan(int line, int column, Expression e1, Expression e2) {
		super(line,column,e1,e2);
	}

	@Override
	public Type<Boolean> evaluate() {
		Object val1 = getExpressions().get(0).evaluate().getValue();
		Object val2 = getExpressions().get(1).evaluate().getValue();
		if ( (val1 instanceof Number) && (val2 instanceof Number) ) //TODO static or dynamic type checked? should be dynamic type, because static type is "Object"
			return new Type<Boolean>( (double)val1 > (double)val2 );
		getStatement().getRootProgram().typeErrorOccurred();
		return null;
	}

}
