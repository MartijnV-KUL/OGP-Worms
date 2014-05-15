package worms.model;

public class ExpressionBinary extends Expression {

	private final Expression e1;
	private final Expression e2;
	
	public ExpressionBinary(int line, int column, Expression.Types type, Expression e1, Expression e2) {
		super(line,column,type);
		this.e1 = e1;
		this.e2 = e2;
	}
	
	@SuppressWarnings("unchecked")
	public Type<?> evaluate() {
		if (getType()==Expression.Types.ADDITION) {
			if ( !(e1.evaluate().getValue() instanceof Number) || !(e2.evaluate().getValue() instanceof Number) ) //TODO instance of "Double" or "Number"?
				getStatement().getRootProgram().typeErrorOccurred();
			return new Type<Double>(((double)((Type<Double>)e1.evaluate()).getValue())+((double)((Type<Double>)e2.evaluate()).getValue()));
		}
		if (getType()==Expression.Types.SUBTRACTION) {
			if ( !(e1.evaluate().getValue() instanceof Number) || !(e2.evaluate().getValue() instanceof Number) ) //TODO instance of "Double" or "Number"?
				getStatement().getRootProgram().typeErrorOccurred();
			return new Type<Double>(((double)((Type<Double>)e1.evaluate()).getValue())-((double)((Type<Double>)e2.evaluate()).getValue()));
		}
		if (getType()==Expression.Types.MULTIPLICATION) {
			if ( !(e1.evaluate().getValue() instanceof Number) || !(e2.evaluate().getValue() instanceof Number) ) //TODO instance of "Double" or "Number"?
				getStatement().getRootProgram().typeErrorOccurred();
			return new Type<Double>(((double)((Type<Double>)e1.evaluate()).getValue())*((double)((Type<Double>)e2.evaluate()).getValue()));
		}
		if (getType()==Expression.Types.DIVISION) {
			if ( !(e1.evaluate().getValue() instanceof Number) || !(e2.evaluate().getValue() instanceof Number) ) //TODO instance of "Double" or "Number"?
				getStatement().getRootProgram().typeErrorOccurred();
			return new Type<Double>(((double)((Type<Double>)e1.evaluate()).getValue())/((double)((Type<Double>)e2.evaluate()).getValue()));
		}
		if (getType()==Expression.Types.CONJUNCTION) {
			if ( !(e1.evaluate().getValue() instanceof Boolean) || !(e2.evaluate().getValue() instanceof Boolean) )
				getStatement().getRootProgram().typeErrorOccurred();
			return new Type<Boolean>(((boolean)((Type<Boolean>)e1.evaluate()).getValue())||((boolean)((Type<Boolean>)e2.evaluate()).getValue()));
		}
		if (getType()==Expression.Types.DISJUNCTION) {
			if ( !(e1.evaluate().getValue() instanceof Boolean) || !(e2.evaluate().getValue() instanceof Boolean) )
				getStatement().getRootProgram().typeErrorOccurred();
			return new Type<Boolean>(((boolean)((Type<Boolean>)e1.evaluate()).getValue())&&((boolean)((Type<Boolean>)e2.evaluate()).getValue()));
		}
		if (getType()==Expression.Types.LESSTHAN) {
			if ( !(e1.evaluate().getValue() instanceof Number) || !(e2.evaluate().getValue() instanceof Number) ) //TODO instance of "Double" or "Number"?
				getStatement().getRootProgram().typeErrorOccurred();
			return new Type<Boolean>(((double)((Type<Double>)e1.evaluate()).getValue())<((double)((Type<Double>)e2.evaluate()).getValue()));
		}
		if (getType()==Expression.Types.LESSTHANOREQUALTO) {
			if ( !(e1.evaluate().getValue() instanceof Number) || !(e2.evaluate().getValue() instanceof Number) ) //TODO instance of "Double" or "Number"?
				getStatement().getRootProgram().typeErrorOccurred();
			return new Type<Boolean>(((double)((Type<Double>)e1.evaluate()).getValue())<=((double)((Type<Double>)e2.evaluate()).getValue()));
		}
		if (getType()==Expression.Types.GREATERTHAN) {
			if ( !(e1.evaluate().getValue() instanceof Number) || !(e2.evaluate().getValue() instanceof Number) ) //TODO instance of "Double" or "Number"?
				getStatement().getRootProgram().typeErrorOccurred();
			return new Type<Boolean>(((double)((Type<Double>)e1.evaluate()).getValue())>((double)((Type<Double>)e2.evaluate()).getValue()));
		}
		if (getType()==Expression.Types.GREATERTHANOREQUALTO) {
			if ( !(e1.evaluate().getValue() instanceof Number) || !(e2.evaluate().getValue() instanceof Number) ) //TODO instance of "Double" or "Number"?
				getStatement().getRootProgram().typeErrorOccurred();
			return new Type<Boolean>(((double)((Type<Double>)e1.evaluate()).getValue())>=((double)((Type<Double>)e2.evaluate()).getValue()));
		}
		if (getType()==Expression.Types.EQUALTO) {
			if ( !(e1.evaluate().getValue() instanceof Number) || !(e2.evaluate().getValue() instanceof Number) ) //TODO instance of "Double" or "Number"?
				getStatement().getRootProgram().typeErrorOccurred();
			return new Type<Boolean>(((double)((Type<Double>)e1.evaluate()).getValue())==((double)((Type<Double>)e2.evaluate()).getValue())); //TODO only correct for primitive Expression.Types. Since casting to the primitive type double, this is correct.
		}
		if (getType()==Expression.Types.DIFFERENTFROM) {
			if ( !(e1.evaluate().getValue() instanceof Number) || !(e2.evaluate().getValue() instanceof Number) ) //TODO instance of "Double" or "Number"?
				getStatement().getRootProgram().typeErrorOccurred();
			return new Type<Boolean>(((double)((Type<Double>)e1.evaluate()).getValue())!=((double)((Type<Double>)e2.evaluate()).getValue()));
		}

		getStatement().getRootProgram().typeErrorOccurred();
		return null;
	}
	
}
