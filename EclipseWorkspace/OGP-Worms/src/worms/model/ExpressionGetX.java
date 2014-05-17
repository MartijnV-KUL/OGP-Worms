package worms.model;

public class ExpressionGetX extends Expression {
	
	public ExpressionGetX(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Double> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		
		
		
		
//		try {
//			return new Type<Double>(((Worm)(((Entity)val).getEntity())).getX());
//		} catch (Exception e1) {
//			try {
//				return new Type<Double>(((Food)(((Entity)val).getEntity())).getX());
//			} catch (Exception e2) {
//				System.out.println("derp");
//				getRootProgram().typeErrorOccurred();
//				return null;
//			}
//		}
		
		
		if ( val instanceof Worm ) //TODO static or dynamic type checked? should be dynamic type, because static type is "Object"
			return new Type<Double>(((Worm)val).getX());
		if ( val instanceof Food ) //TODO static or dynamic type checked? should be dynamic type, because static type is "Object"
			return new Type<Double>(((Food)val).getX());
		getRootProgram().typeErrorOccurred();
		return null;
		
		
		
		
		
	}

}
