package worms.model;

public class ExpressionNegation extends Expression {
	
	public ExpressionNegation(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Boolean> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( !(val instanceof Boolean) ) { //TODO static or dynamic type checked? should be dynamic type, because static type is "Object"
			getRootProgram().typeErrorOccurred();
			return null;
		}
		return new Type<Boolean>(!((boolean)val));
	}

}
